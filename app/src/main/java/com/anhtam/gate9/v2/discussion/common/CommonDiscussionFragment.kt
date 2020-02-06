package com.anhtam.gate9.v2.discussion.common

import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import com.anhtam.gate9.R
import com.anhtam.gate9.share.view.CustomLoadMoreView
import com.anhtam.gate9.v2.discussion.DiscussionViewModel
import com.anhtam.gate9.utils.getColorCompat
import com.anhtam.gate9.v2.shared.views.AbstractVisibleFragment
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.google.android.material.tabs.TabLayout
import com.squareup.phrase.Phrase
import kotlinx.android.synthetic.main.shared_discussion_layout.*
import of.bum.network.helper.Resource
import of.bum.network.helper.RestResponse
import timber.log.Timber
import javax.inject.Inject

abstract class CommonDiscussionFragment<T, A: BaseQuickAdapter<T, BaseViewHolder>, V: CommonDiscussionViewModel<T>> : AbstractVisibleFragment(R.layout.shared_discussion_layout) {

    private val mDiscussionViewModel: DiscussionViewModel by viewModels({requireParentFragment()}, {vmFactory})
    private var mFirstLoad = true
    private var mHasUser = false
    @Inject lateinit var mAdapter: A
    private var mCurrentCategory = 1
    open var mLazyLoad = true
    open val mViewModel: V? = null

    abstract val colorTextTab: Int
    abstract val tabTitle: List<Int>
    private val tabAmount = mutableListOf<Int>()
    open fun configTabLayout(){
        tabLayout.apply {
            repeat(tabTitle.size) {
                addTab(newTab())
            }
        }
        updateTabLayout()
    }
    private fun updateTabLayout(){
        (0..tabTitle.size).forEach { index ->
            val amount = if (index < tabAmount.size - 1) index else 0
            tabLayout.getTabAt(index)?.text = Phrase.from(
                    getString(tabTitle[index]))
                    .put("amount", amount)
                    .format() }
    }

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
            mViewModel?.loadData()
        }, rvShareDiscussion)
    }

    protected open fun observer() {
        mDiscussionViewModel.mUser.observe(viewLifecycleOwner, Observer {
            val user = it?.data ?: return@Observer
            mViewModel?.initialize(user)
            mHasUser = true
            if (mLazyLoad){
                lazyLoad()
            } else {
                loadData()
            }
        })
        mViewModel?.data?.observe(viewLifecycleOwner, Observer {resource ->
            when(resource) {
                is Resource.Success -> {
                    val data = resource.data
                    val response = resource.mResponse?.body as? RestResponse<*>
                    val countTab1 = (response?.mMeta?.get("countTab1") as? Double)?.toInt() ?: 0
                    val countTab2 = (response?.mMeta?.get("countTab2") as? Double)?.toInt() ?: 0
                    val countTab3 = (response?.mMeta?.get("countTab3") as? Double)?.toInt() ?: 0
                    tabAmount.add(countTab1)
                    tabAmount.add(countTab2)
                    tabAmount.add(countTab3)
                    if (tabTitle.size == 4){
                        val countTab4 = (response?.mMeta?.get("countTab4") as? Double)?.toInt() ?: 0
                        tabAmount.add(countTab4)
                    }
                    updateTabLayout()

                    if (data.isNullOrEmpty()) {
                        mAdapter.loadMoreEnd()
                    } else {
                        if (mViewModel?.mPage == 0) {
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
                    mAdapter.loadMoreFail()
                }
            }
        })
    }

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


    private fun loadData(){
        mViewModel?.loadData(mCurrentCategory, refresh = true)}
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
                newRequestType(id)
            }
        })
    }

    private fun newRequestType(category: Int) {
        mCurrentCategory = category
        mAdapter.data.clear()
        mAdapter.notifyDataSetChanged()
        if (mViewModel?.mCategory != category) {
            mViewModel?.loadData(category, refresh = true)
        }
    }

}