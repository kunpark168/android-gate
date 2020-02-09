package com.anhtam.gate9.v2.follow

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
import com.anhtam.gate9.v2.BackgroundViewModel
import com.anhtam.gate9.v2.newfeed.NewFeedHeaderView
import com.anhtam.gate9.v2.newfeed.NewFeedViewModel
import com.anhtam.gate9.v2.shared.views.AbstractVisibleFragment
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.tat_ca_following_screen.*
import of.bum.network.helper.Resource
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Named

class TatCaFollowingScreen constructor(val mTab: Int) : AbstractVisibleFragment(R.layout.tat_ca_following_screen){

    companion object{
        fun newInstance(tab: Int) = TatCaFollowingScreen(tab)
    }

    private val mViewModel: NewFeedViewModel by viewModels {vmFactory }
    private val mTopViewModel: BackgroundViewModel by viewModels({requireActivity()},{vmFactory})
    private var mNewFeedHeaderView: NewFeedHeaderView? = null
    private var mFirstLoad = true

    @Inject lateinit var mAdapter : PostAdapter
    @Inject @field:Named("avatar") lateinit var avatarOptions: RequestOptions
    @Inject @field:Named("banner") lateinit var bannerOptions: RequestOptions

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        preloadData()
        initCommentRecyclerView()
        observer()
        initEvents()
    }

    private fun observer(){
        observerPost()
        observerBanner()
        observerGroup()
    }

    private fun observerPost(){
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
    }
    private fun preloadData(){
        if (mTab != 1){
            lazyLoad()
        } else {
            loadData()
        }
    }

    private fun loadData() {
        mViewModel.loadFirstPage(mTab)
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

    private fun initCommentRecyclerView() {
        mNewFeedHeaderView = NewFeedHeaderView(context)
        mNewFeedHeaderView?.initialize(navigation, bannerOptions)
        mAdapter.addHeaderView(mNewFeedHeaderView)
        mAdapter.setLoadMoreView(CustomLoadMoreView())
        mAdapter.setOnLoadMoreListener({
            mViewModel.loadMore(mTab)
        }, rvComment)
        rvComment?.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        rvComment?.adapter = mAdapter
        val unwrappedContext = context ?: return
        val dividerItemDecoration = DividerItemDecoration(unwrappedContext, LinearLayout.VERTICAL)
        val drawableDivider = ContextCompat.getDrawable(unwrappedContext, R.drawable.divider_item_decorator) ?: return
        dividerItemDecoration.setDrawable(drawableDivider)
        rvComment?.addItemDecoration(dividerItemDecoration)
    }

    private fun initEvents() {
        swipeRefreshLayout?.setOnRefreshListener {
            swipeRefreshLayout?.isRefreshing = false
            mViewModel.loadFirstPage(mTab)
        }
    }

    override fun onUiVisibleChange(isUiVisible: Boolean) {
        super.onUiVisibleChange(isUiVisible)
        if (isUiVisible && mFirstLoad){
            mFirstLoad = false
            lazyLoad()
        }
    }

    private fun lazyLoad(){
        if (!mFirstLoad && mTab != 1){
            Timber.d("Has loading G Game h.........")
            loadData()
        }
    }
}