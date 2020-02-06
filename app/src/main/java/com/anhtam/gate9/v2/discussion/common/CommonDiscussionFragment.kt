package com.anhtam.gate9.v2.discussion.common

import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import com.anhtam.domain.v2.protocol.User
import com.anhtam.gate9.R
import com.anhtam.gate9.share.view.CustomLoadMoreView
import com.anhtam.gate9.v2.discussion.DiscussionViewModel
import com.anhtam.gate9.utils.getColorCompat
import com.anhtam.gate9.v2.shared.views.AbstractVisibleFragment
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.shared_discussion_layout.*
import timber.log.Timber
import javax.inject.Inject

abstract class CommonDiscussionFragment<T, A: BaseQuickAdapter<T, BaseViewHolder>> : AbstractVisibleFragment(R.layout.shared_discussion_layout) {

    private val mDiscussionViewModel: DiscussionViewModel by viewModels({requireParentFragment()}, {vmFactory})
    private var mFirstLoad = true
    private var mHasUser = false
    @Inject lateinit var mAdapter: A
    open var mLazyLoad = true

    abstract val colorTextTab: Int
    abstract fun configTabLayout()
    abstract fun updateTabLayout()
    open fun loadMore(){}

    open fun inflateLayout() : Int? {
        return null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initEvents()
        observer()
    }

    protected open fun initView() {
        configTabLayout()
        val unwrappedContext = context ?: return
        context?.let {
            val dividerItemDecoration = DividerItemDecoration(it, LinearLayout.VERTICAL)
            val drawableDivider = ContextCompat.getDrawable(it, R.drawable.divider_item_decorator) ?: return
            dividerItemDecoration.setDrawable(drawableDivider)
            rvShareDiscussion?.addItemDecoration(dividerItemDecoration)

        }
        tabLayout.setTabTextColors(unwrappedContext.getColorCompat(R.color.defaultGrayColor), unwrappedContext.getColorCompat(colorTextTab))
        mAdapter.setLoadMoreView(CustomLoadMoreView())
        rvShareDiscussion?.adapter = mAdapter
        mAdapter.setOnLoadMoreListener ({
            loadMore()
        }, rvShareDiscussion)
    }

    protected open fun observer() {
        mDiscussionViewModel.mUser.observe(viewLifecycleOwner, Observer {
            val user = it?.data ?: return@Observer
            onAttachUser(user)
            mHasUser = true
            if (mLazyLoad){
                lazyLoad()
            } else {
                loadData()
            }
        })
    }

    open fun onAttachUser(user: User){}

    override fun onUiVisibleChange(isUiVisible: Boolean) {
        super.onUiVisibleChange(isUiVisible)
        if (isUiVisible && mFirstLoad){
            mFirstLoad = false
            lazyLoad()
        }
    }

    private fun lazyLoad(){
        if (!mFirstLoad && mHasUser && mLazyLoad){
            Timber.d("Has loading G Game h.........")
            loadData()
        }
    }


    open fun loadData(){}
    abstract fun onTabChanged(id: Int)
    private fun initEvents(){
        swipeRefreshLayout?.setOnRefreshListener {
            swipeRefreshLayout?.isRefreshing = false
            loadData()
        }
        tabLayout?.addOnTabSelectedListener(object: TabLayout.OnTabSelectedListener{
            override fun onTabReselected(p0: TabLayout.Tab?) {

            }

            override fun onTabUnselected(p0: TabLayout.Tab?) {

            }

            override fun onTabSelected(tab: TabLayout.Tab?) {
                val id = tab?.position ?: return
                onTabChanged(id)
            }
        })
    }

}