package com.anhtam.gate9.v2.game_detail

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.viewpager.widget.ViewPager
import com.anhtam.domain.v2.protocol.Game
import com.anhtam.gate9.R
import com.anhtam.gate9.adapter.SharePageAdapter
import com.anhtam.gate9.restful.BackgroundTasks
import com.anhtam.gate9.utils.format
import com.anhtam.gate9.utils.toImage
import com.anhtam.gate9.v2.createpost.CreatePostScreen
import com.anhtam.gate9.v2.game_detail.danh_gia.DanhGiaGameFragment
import com.anhtam.gate9.v2.game_detail.thao_luan.ThaoLuanFragment
import com.anhtam.gate9.v2.main.DaggerNavigationFragment
import com.anhtam.gate9.v2.main.home.HomeFragment
import com.anhtam.gate9.v2.report.game.ReportGameActivity
import com.anhtam.gate9.v2.shared.views.AbstractVisibleFragment
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.squareup.phrase.Phrase
import kotlinx.android.synthetic.main.detail_game_fragment.*
import of.bum.network.helper.Resource
import javax.inject.Inject
import javax.inject.Named

class DetailGameFragment constructor(private val mGameId: Int) : DaggerNavigationFragment(R.layout.detail_game_fragment){

    companion object{
        fun newInstance(id: Int) = DetailGameFragment(id)
    }

    private val mViewModel: DetailGameViewModel by viewModels { vmFactory }
    private var mAdapter: SharePageAdapter? = null
    private var mFragments = mutableListOf<AbstractVisibleFragment>()
    private var mCurrent: Int = 0
    @Inject @field:Named("banner") lateinit var bannerOption: RequestOptions
    private var mIsFollowing: Boolean = false
    private var mGame: Game? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mViewModel.mGameId.value = mGameId
        initView()
        initEvents()
        observeGame()
    }

    override fun menuRes() = R.menu.menu_chat_search_more

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.icReport -> {
                navigation?.addFragment(ReportGameActivity.newInstance())
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun initView(){
        initViewPager()
    }

    private fun initEvents(){
        viewPager?.addOnPageChangeListener(object: ViewPager.OnPageChangeListener{
            override fun onPageScrollStateChanged(state: Int) {}

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {}

            override fun onPageSelected(position: Int) {
                mFragments.forEachIndexed { index, fragment -> fragment.changeVisible(position == index)}
            }
        })
        tabThaoLuan?.setOnClickListener {
            onTabChanged(0)
        }
        tabDanhGia?.setOnClickListener {
            onTabChanged(1)
        }
        tabDuLieu?.setOnClickListener {
            onTabChanged(2)
        }
        tabHoSo?.setOnClickListener {
            onTabChanged(3)
        }
        tabThongTin?.setOnClickListener {
            if (mCurrent == -1) return@setOnClickListener
            updateTabIndicator(-1)
            infoScrollView?.visibility = View.VISIBLE
            viewPager?.visibility = View.GONE
        }

        tabFollow?.setOnClickListener {
            if (mSessionManager.checkLogin(isDirect = true)){
                mIsFollowing = !mIsFollowing
                onUpdateFollow()
                BackgroundTasks.postFollowGame(mGameId)
            }
        }
        tabNewFeed?.setOnClickListener {
            navigation?.newRootFragment(HomeFragment.newInstance())
        }

        tabMessage?.setOnClickListener {
            navigation?.addFragment(CreatePostScreen.newInstance(mGame))
        }
    }

    private fun onUpdateFollow(){
        if (mIsFollowing){
            follow()
        } else {
            unFollow()
        }
    }

    private fun follow(){
        val unwrapContext = context ?: return
        tabFollow?.changeText(unwrapContext.getString(R.string.following))
        tabFollow?.changeColor(R.color.color_main_blue)
    }

    private fun unFollow(){
        val unwrapContext = context ?: return
        tabFollow?.changeText(unwrapContext.getString(R.string.follow))
        tabFollow?.changeColor(R.color.color_main_orange)
    }

    private fun onTabChanged(index: Int){
        if (mCurrent == index) return
        if (mCurrent == -1){
            infoScrollView?.visibility = View.GONE
            viewPager?.visibility = View.VISIBLE
        }
        updateTabIndicator(index)
        viewPager?.currentItem = index
    }

    private fun updateTabIndicator(index: Int){
        val previous = mCurrent
        mCurrent = index
        when(previous){
            0 -> tabThaoLuan?.setVisibilityIndicator(false)
            1 -> tabDanhGia?.setVisibilityIndicator(false)
            2 -> tabDuLieu?.setVisibilityIndicator(false)
            3 -> tabHoSo?.setVisibilityIndicator(false)
            -1 -> tabThongTin?.setVisibilityIndicator(false)
        }
        when(mCurrent){
            0 -> tabThaoLuan?.setVisibilityIndicator(true)
            1 -> tabDanhGia?.setVisibilityIndicator(true)
            2 -> tabDuLieu?.setVisibilityIndicator(true)
            3 -> tabHoSo?.setVisibilityIndicator(true)
            -1 -> tabThongTin?.setVisibilityIndicator(true)
        }
    }

    private fun observeGame(){
        mViewModel.mGame.observe(viewLifecycleOwner, Observer {
            when(it){
                is Resource.Success -> {
                    updateDetailGame(it.data)
                    mGame = it.data
                }
                is Resource.Error -> {
                    updateDetailGame(null)
                }
                else -> {}
            }
        })
    }

    private fun updateDetailGame(game: Game?){
        mIsFollowing = game?.follow ?: false
        onUpdateFollow()
        // header view
        val unwrapContext = context ?: return
        Glide.with(unwrapContext)
                .load(game?.imgCover?.toImage())
                .apply(bannerOption)
                .into(coverImageView)

        Glide.with(unwrapContext)
                .load(game?.avatar?.toImage())
                .apply(bannerOption)
                .into(avatarImageView)
        nameTextView?.text = game?.name
        parameterTextView?.text = Phrase.from(getString(R.string.follower_amount_and_post_amount))
                .put("follower", game?.follower?.toString() ?: "0")
                .put("post", game?.post?.toString() ?: "0")
                .format()
        ratingBar?.rating = game?.mRating?.toFloat() ?: 0.0f
        ratingTextView?.text = "${game?.mRating?.format(1) ?: "0"} /"
        amountRateTextView?.text = game?.mNumRating?.toString() ?: "0"
        // tab thong tin
        tvNameGame?.text = infoOrDefault(game?.name)
        tvTypeGame?.text = infoOrDefault(game?.gameType?.name)
        tvNPH?.text = infoOrDefault(game?.mNPH?.mName)
        tvCountry?.text = infoOrDefault(game?.country?.mName)
        val status = when(game?.mState){
            1 -> "Đang hoạt động"
            2 -> "Thử nghiệm"
            3 -> "Sắp ra mắt"
            4 -> "Ngừng hoạt động"
            else -> ""
        }
        tvStatus?.text = infoOrDefault(status)

        tvPublishDate?.text = infoOrDefault(game?.releasedDate)
        tvIntro?.text = infoOrDefault(game?.description)

        tvHome?.text = infoOrDefault(game?.homepage)
        tvFanpage?.text = infoOrDefault(game?.fanPage)
        tvEmail?.text = infoOrDefault(game?.email)
        tvGroup?.text = infoOrDefault(game?.group)
        tvPhone?.text = infoOrDefault(game?.phone)
    }

    private fun infoOrDefault(text: String?) : String {
        if (text.isNullOrBlank()) {
            return "-"
        }
        return text
    }

    private fun initViewPager(){
        mFragments.add(ThaoLuanFragment.newInstance())
        mFragments.add(DanhGiaGameFragment.newInstance())
        mFragments.add(ThaoLuanFragment.newInstance())
        mFragments.add(ThaoLuanFragment.newInstance())
        mAdapter = SharePageAdapter(childFragmentManager, mFragments)
        viewPager?.adapter = mAdapter
        viewPager?.offscreenPageLimit = mFragments.size
    }
}