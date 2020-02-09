package com.anhtam.gate9.v2.newfeed

import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.anhtam.gate9.R
import com.anhtam.gate9.adapter.v2.PostAdapter
import com.anhtam.gate9.share.view.CustomLoadMoreView
import com.anhtam.gate9.storage.StorageManager
import com.anhtam.gate9.utils.toImage
import com.anhtam.gate9.v2.BackgroundViewModel
import com.anhtam.gate9.v2.discussion.user.UserDiscussionScreen
import com.anhtam.gate9.v2.main.DaggerNavigationFragment
import com.anhtam.gate9.v2.notification.NotificationFragment
import com.anhtam.gate9.v2.main.member.MemberFragment
import com.anhtam.gate9.v2.messenger.ChannelFragment
import com.anhtam.gate9.vo.model.Category
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.new_feed_screen.*
import kotlinx.android.synthetic.main.toolbar_new_feed.*
import of.bum.network.helper.Resource
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Named

class NewFeedScreen : DaggerNavigationFragment(R.layout.new_feed_screen) {

    fun update() {
        Timber.d("War")
        showProgress()
        loadData()
    }

    companion object{
        fun newInstance() = NewFeedScreen()
    }

    private val mViewModel: NewFeedViewModel by viewModels ({requireActivity()}, {vmFactory })
    private val mTopViewModel: BackgroundViewModel by viewModels({requireActivity()},{vmFactory})
    private var mNewFeedHeaderView: NewFeedHeaderView? = null

    @Inject lateinit var mAdapter : PostAdapter
    @Inject @field:Named("avatar") lateinit var avatarOptions: RequestOptions
    @Inject @field:Named("banner") lateinit var bannerOptions: RequestOptions

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Timber.d("ACCESS TOKEN ${StorageManager.getAccessToken()}")
        init()
    }

    private fun init() {
        loadData()
        initCommentRecyclerView()
        initEventListener()
        observer()
    }

    private fun observer(){
        mViewModel.data.observe(viewLifecycleOwner, Observer {
            when(it) {
                is Resource.Success -> {
                    hideProgress()
                    val data = it.data
                    if (data.isNullOrEmpty()) {
                        mAdapter.loadMoreEnd()
                    } else {
                        if (mViewModel.mPage == 0) {
                            mAdapter.setNewData(data)
                        } else {
                            mAdapter.addData(data)
                        }
                        mAdapter.loadMoreComplete()
                    }
                }
                is Resource.Loading -> {

                }
                else -> {
                    hideProgress()
                    mAdapter.loadMoreFail()
                }
            }
        })
        observerGroup()
        observerBanner()

        mSessionManager.cachedUser.observe(viewLifecycleOwner, Observer {
            if (it == null) return@Observer
            val avatar = when(it) {
                is Resource.Loading, is Resource.Error -> ""
                is Resource.Success -> it.data?.mAvatar
            }
            avatar?.run {
                Glide.with(this@NewFeedScreen)
                        .load(avatar.toImage())
                        .apply {avatarOptions}
                        .into(imgAvatar)
            }
        })
    }

    private fun observerGroup(){
        mTopViewModel.banners.observe(viewLifecycleOwner, Observer {
            if (it is Resource.Success) mNewFeedHeaderView?.bindingBanner(it.data)
        })
    }

    private fun observerBanner(){
        mTopViewModel.games.observe(viewLifecycleOwner, Observer {
            if (it is Resource.Success) mNewFeedHeaderView?.bindingGroupGames(it.data)
        })
    }

    private fun loadData() {
        mViewModel.loadData(refresh = true)
    }

    private fun initCommentRecyclerView() {
        mNewFeedHeaderView = NewFeedHeaderView(context)
        mAdapter.addHeaderView(SearchHeaderView(context))
        mNewFeedHeaderView?.initialize(navigation, bannerOptions)
        mAdapter.addHeaderView(mNewFeedHeaderView)
        mAdapter.setLoadMoreView(CustomLoadMoreView())
        mAdapter.setOnLoadMoreListener({
            mViewModel.loadData()
        }, rvComment)
        rvComment?.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        rvComment?.adapter = mAdapter
        val unwrappedContext = context ?: return
        val dividerItemDecoration = DividerItemDecoration(unwrappedContext, LinearLayout.VERTICAL)
        val drawableDivider = ContextCompat.getDrawable(unwrappedContext, R.drawable.divider_item_decorator) ?: return
        dividerItemDecoration.setDrawable(drawableDivider)
        rvComment?.addItemDecoration(dividerItemDecoration)
    }

    private fun initEventListener() {
        swipeRefreshLayout?.setOnRefreshListener {
            swipeRefreshLayout?.isRefreshing = false
            showProgress()
            mTopViewModel.getBanner()
            mTopViewModel.getGames()
            loadData()
        }

        icNotification?.setOnClickListener {
            navigation?.addFragment(NotificationFragment.newInstance())
        }
        icChat?.setOnClickListener {
            navigation?.addFragment(ChannelFragment.newInstance())
        }
        icGroup?.setOnClickListener { navigation?.addFragment(MemberFragment.newInstance()) }
        imgAvatar?.setOnClickListener {
            val idUser: Int = mSessionManager.cachedUser.value?.data?.mId ?: return@setOnClickListener
            navigation?.addFragment(UserDiscussionScreen.newInstance(idUser, Category.Member))
        }
    }
}