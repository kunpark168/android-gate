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
import com.anhtam.gate9.utils.getColorCompat
import com.anhtam.gate9.v2.discussion.DiscussionViewModel
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

abstract class CommonDiscussionFragment<T, A: BaseQuickAdapter<T, BaseViewHolder>, V: CommonDiscussionViewModel<T>>
    : AbstractVisibleFragment(R.layout.shared_discussion_layout) {

    private val mDiscussionViewModel: DiscussionViewModel by viewModels({requireParentFragment()}, {vmFactory})
    private var mFirstLoad = true
    private var mHasUser = false
    @Inject lateinit var mAdapter: A
    protected var mCurrentCategory = 1
    open var mLazyLoad = true
    open val mViewModel: V? = null
    open fun onResponseSuccess(data: RestResponse<*>?){}
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
        for (index in tabTitle.indices){
            val amount = if (index < tabAmount.size) tabAmount[index] else 0
            tabLayout.getTabAt(index)?.text = Phrase.from(
                    getString(tabTitle[index]))
                    .put("amount", amount)
                    .format()
        }
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
        setUpRecyclerView()
    }

    private fun setUpRecyclerView(){
        val unwrappedContext = context ?: return
        tabLayout.setTabTextColors(unwrappedContext.getColorCompat(R.color.defaultGrayColor), unwrappedContext.getColorCompat(colorTextTab))
        context?.let {
            val dividerItemDecoration = DividerItemDecoration(it, LinearLayout.VERTICAL)
            val drawableDivider = ContextCompat.getDrawable(it, R.drawable.divider_item_decorator) ?: return
            dividerItemDecoration.setDrawable(drawableDivider)
            rvShareDiscussion?.addItemDecoration(dividerItemDecoration)

        }
        mAdapter.setLoadMoreView(CustomLoadMoreView())
        rvShareDiscussion?.adapter = mAdapter
        mAdapter.setOnLoadMoreListener ({
            mViewModel?.loadData()
        }, rvShareDiscussion)
    }

    protected open fun onLoadUser(user: User){}

    protected open fun observer() {
        mDiscussionViewModel.mUser.observe(viewLifecycleOwner, Observer {
            val user = it?.data ?: return@Observer
            onLoadUser(user)
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
                    hideProgress()
                    val data = resource.data
                    if (tabAmount.isEmpty()){
                        val response = resource.mResponse?.body as? RestResponse<*>
                        val countTab1 = (response?.mMeta?.get("countTab1") as? Double)?.toInt() ?: 0
                        val countTab2 = (response?.mMeta?.get("countTab2") as? Double)?.toInt() ?: 0
                        val countTab3 = (response?.mMeta?.get("countTab3") as? Double)?.toInt() ?: 0
                        tabAmount.add(countTab1)
                        tabAmount.add(countTab2)
                        tabAmount.add(countTab3)
                        onResponseSuccess(response)
                        if (tabTitle.size == 4){
                            val countTab4 = (response?.mMeta?.get("countTab4") as? Double)?.toInt() ?: 0
                            tabAmount.add(countTab4)
                        }
                        updateTabLayout()
                    }
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
                    hideProgress()
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


    protected fun loadData(){
        showProgress()
        mViewModel?.loadData(mCurrentCategory, refresh = true)
    }
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

    protected open fun onTabChanged(){}

    private fun newRequestType(position: Int) {
        val category = position + 1
        mCurrentCategory = category
        onTabChanged()
        mAdapter.setNewData(null)
        if (mViewModel?.mCategory != category) {
            mViewModel?.loadData(category, refresh = true)
        }
    }

}