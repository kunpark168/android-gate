package com.anhtam.gate9.v2.user_detail

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
import com.anhtam.gate9.utils.convertInt
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
import kotlinx.android.synthetic.main.detail_user_fragment.*
import of.bum.network.helper.Resource
import javax.inject.Inject
import javax.inject.Named

class DetailUserFragment(private val mId: Int) : DaggerNavigationFragment(R.layout.detail_user_fragment){


    companion object{
        fun newInstance(id: Int) = DetailUserFragment(id)
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
                BackgroundTasks.postUserFollow(mId, 4)
            }
        }
        tabNewFeed?.setOnClickListener {
            navigation?.newRootFragment(HomeFragment.newInstance())
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
        //header
        Glide.with(this).applyDefaultRequestOptions(RequestOptions().placeholder(R.drawable.img_avatar_holder))
                .load(user?.mAvatar?.toImage())
                .into(imgAvatar)

        tvUserName?.text = user?.mName
        tvFollow?.text = user?.mFlowing?.toString() ?: "0"
        tvFollower?.text = user?.mFlower?.toString() ?: "0"
        tvCounting?.text = Phrase.from(resources.getString(R.string.number_point))
                .put("point", user?.mPoint?.toString())
                .put("medal", user?.mAppellation).format()
        tvSlogan?.text = user?.mNote
        val icon = when(user?.mGender?.convertInt()) {
            1 -> R.drawable.ic_female
            2 -> R.drawable.ic_male_circle
            else -> R.drawable.ic_gender
        }
        imgGender.visibility = View.VISIBLE
        Glide.with(this).load(icon)
                .into(imgGender)
        iconChart?.setOnClickListener {
            navigation?.addFragment(BXHScreen.newInstance(4))
        }

        // tab thong tin
        tvId?.text = infoOrDefault(user?.mId?.toString())
        tvShortName?.text = infoOrDefault(user?.mName)
        tvDisplayName?.text = infoOrDefault(user?.mName)
        tvMedal?.text = infoOrDefault(user?.mAppellation)
        tvTotalPoint?.text = infoOrDefault(user?.mPoint?.toString())

        tvReview?.text = "-"
        tvRanking?.text = infoOrDefault(user?.mRanking)
        val gender = when(user?.mGender?.convertInt()) {
            1 -> "Nam"
            2 -> "Nữ"
            else -> "Không xác định"
        }
        tvGender?.text = infoOrDefault(gender)
        tvBirthDate?.text = infoOrDefault(user?.mBirth)
        tvCurrentLive?.text = infoOrDefault(user?.mAddress)
        tvStatus?.text = "-"
        tvCreatedDate?.text = infoOrDefault(user?.mCreatedDate)
        tvIntro?.text = infoOrDefault(user?.mNote)
        tvHome?.text = infoOrDefault("http://9gate.net/u/${user?.mId}")
        tvFacebook?.text = infoOrDefault(user?.mFacebook)
        tvEmail?.text = infoOrDefault(user?.mEmail)
        tvPhone?.text = infoOrDefault(user?.mPhone)
        tvSkype?.text = infoOrDefault(user?.mSkype)
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