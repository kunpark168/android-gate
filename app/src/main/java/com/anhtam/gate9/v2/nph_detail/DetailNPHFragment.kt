package com.anhtam.gate9.v2.nph_detail

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.viewpager.widget.ViewPager
import com.anhtam.domain.v2.protocol.User
import com.anhtam.gate9.R
import com.anhtam.gate9.adapter.SharePageAdapter
import com.anhtam.gate9.restful.BackgroundTasks
import com.anhtam.gate9.utils.toImage
import com.anhtam.gate9.v2.bxh.BXHScreen
import com.anhtam.gate9.v2.discussion.DiscussionViewModel
import com.anhtam.gate9.v2.discussion.common.data.DataFragment
import com.anhtam.gate9.v2.discussion.common.game.GGameFragment
import com.anhtam.gate9.v2.discussion.common.newfeed.NewFeedFragment
import com.anhtam.gate9.v2.discussion.common.rating.RatingFragment
import com.anhtam.gate9.v2.main.DaggerNavigationFragment
import com.anhtam.gate9.v2.main.home.HomeFragment
import com.anhtam.gate9.v2.report.user.ReportUserActivity
import com.anhtam.gate9.v2.shared.views.AbstractVisibleFragment
import com.anhtam.gate9.vo.model.Category
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.squareup.phrase.Phrase
import kotlinx.android.synthetic.main.detail_nph_fragment.*
import of.bum.network.helper.Resource
import javax.inject.Inject
import javax.inject.Named

class DetailNPHFragment(private val mId: Int) : DaggerNavigationFragment(R.layout.detail_nph_fragment){


    companion object{
        fun newInstance(id: Int) = DetailNPHFragment(id)
    }

    private val mViewModel: DiscussionViewModel by viewModels { vmFactory }
    private var mAdapter: SharePageAdapter? = null
    private var mFragments = mutableListOf<AbstractVisibleFragment>()
    private var mCurrent: Int = 0
    @Inject @field:Named("banner") lateinit var bannerOption: RequestOptions
    private var mIsFollowing: Boolean = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mViewModel.mUserId.value = mId
        initView()
        initEvents()
        observeGame()
    }

    override fun menuRes() = R.menu.menu_chat_search_more

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.icReport -> {
                navigation?.addFragment(ReportUserActivity.newInstance())
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
                onTabChanged(position)
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
        tabGame?.setOnClickListener {
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
                BackgroundTasks.postUserFollow(mId, 5)
            }
        }
        tabNewFeed?.setOnClickListener {
            navigation?.newRootFragment(HomeFragment.newInstance())
        }
        
        imgChart?.setOnClickListener {
            navigation?.addFragment(BXHScreen.newInstance(5))
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
            3 -> tabGame?.setVisibilityIndicator(false)
            -1 -> tabThongTin?.setVisibilityIndicator(false)
        }
        when(mCurrent){
            0 -> tabThaoLuan?.setVisibilityIndicator(true)
            1 -> tabDanhGia?.setVisibilityIndicator(true)
            2 -> tabDuLieu?.setVisibilityIndicator(true)
            3 -> tabGame?.setVisibilityIndicator(true)
            -1 -> tabThongTin?.setVisibilityIndicator(true)
        }
    }

    private fun observeGame(){
        mViewModel.mUser.observe(viewLifecycleOwner, Observer {
            when(it){
                is Resource.Success -> {
                    updateDetailNPH(it.data)
                }
                is Resource.Error -> {
                    updateDetailNPH(null)
                }
                else -> {}
            }
        })
    }

    private fun updateDetailNPH(user: User?){
        mIsFollowing = user?.mIsFollowing ?: false
        onUpdateFollow()
        // tab thong tin
        Glide.with(this).applyDefaultRequestOptions(RequestOptions().placeholder(R.drawable.img_avatar_holder))
                .load(user?.mAvatar?.toImage())
                .into(imgAvatar)
        tvUserName?.text = user?.mName

        val followString = Phrase.from(resources.getString(R.string.following_follower_publisher))
                .put("following", user?.mFlowing?.toString() ?: "0")
                .put("follower", user?.mFlower?.toString() ?: "0")
                .put("game", "100")
                .format()
        tvGameCount?.text = followString

        tvSlogan?.text = user?.mNote
        val avg = user?.mAvg?.toFloat() ?: 0.0f
        ratingBar?.rating = avg
        ratingTextview?.text = Phrase.from(getString(R.string.splash_phrase))
                .put("rating_avg", avg.toString())
                .put("num_rating", user?.mNumRating ?: 0)
                .format()
        numGameTextView?.text = (user?.mNumGame ?: 0).toString()
        
        // header

        tvId?.text = infoOrDefault(user?.mId?.toString())
        tvShortName?.text = infoOrDefault(user?.mName)
        tvDisplayName?.text = infoOrDefault(user?.mName)

        tvReview?.text = "-"
        tvRanking?.text = infoOrDefault(user?.mRanking)
        tvTotalGame?.text = "-"
        tvCountry?.text = "-"
        tvCreatedDate?.text = infoOrDefault(user?.mCreatedDate)
        tvIntro?.text = infoOrDefault(user?.mNote)
        tvHome?.text = infoOrDefault(user?.mWeb)
        tvEmail?.text = infoOrDefault(user?.mEmail)
        tvPhone?.text = infoOrDefault(user?.mPhone)
        tvSkype?.text = infoOrDefault(user?.mSkype)
        tvLocation?.text = infoOrDefault(user?.mAddress)
        tvQQ?.text = infoOrDefault(user?.mQQ)
    }

    private fun infoOrDefault(text: String?) : String {
        if (text.isNullOrBlank()) {
            return "-"
        }
        return text
    }

    private fun initViewPager(){
        mFragments.add(NewFeedFragment())
        mFragments.add(RatingFragment.newInstance(mId, Category.Publisher))
        mFragments.add(DataFragment.newInstance(mId))
        mFragments.add(GGameFragment.newInstance())
        mAdapter = SharePageAdapter(childFragmentManager, mFragments)
        viewPager?.adapter = mAdapter
        viewPager?.offscreenPageLimit = mFragments.size
    }
}