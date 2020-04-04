package com.anhtam.gate9.v2.search

import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.anhtam.gate9.R
import com.anhtam.gate9.share.view.CustomLoadMoreView
import com.anhtam.gate9.v2.newfeed.PagingViewModel
import com.anhtam.gate9.v2.shared.views.AbstractVisibleFragment
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import kotlinx.android.synthetic.main.shared_only_recycler_view_layout.*
import of.bum.network.helper.Resource
import of.bum.network.helper.RestResponse
import javax.inject.Inject

abstract class AbstractTabSearchFragment<D, A: BaseQuickAdapter<D, BaseViewHolder>, VM: PagingViewModel<D>> : AbstractVisibleFragment(R.layout.shared_only_recycler_view_layout) {
    abstract val mViewModel: VM
    @Inject lateinit var mAdapter: A
    open fun onResponseSuccess(response: RestResponse<*>?){}
    open fun setUpAdapter(){}
    protected var mGameId: Int = 0

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initEvent()
        observer()
    }

    private fun initView() {
        initRecyclerView()
    }

    private fun initRecyclerView() {
        mAdapter.setLoadMoreView(CustomLoadMoreView())
        setUpAdapter()
        mAdapter.setOnLoadMoreListener({
            mViewModel.loadData(false)
        }, recyclerView)
        context?.let {
            val dividerItemDecoration = DividerItemDecoration(it, LinearLayout.VERTICAL)
            val drawableDivider = ContextCompat.getDrawable(it, R.drawable.divider_item_decorator)
                    ?: return
            dividerItemDecoration.setDrawable(drawableDivider)
            recyclerView?.addItemDecoration(dividerItemDecoration)

        }
        recyclerView?.adapter = mAdapter
        recyclerView?.layoutManager = LinearLayoutManager(context)
    }

    private fun initEvent() {
        swipeRefreshLayout?.setOnRefreshListener {
            swipeRefreshLayout?.isRefreshing = false
            showProgress()
            mViewModel.loadData(refresh = true)
        }
    }

    private fun observer() {
        observerData()
    }

    private fun observerData(){
        mViewModel.data.observe(viewLifecycleOwner, Observer {resource ->
            when(resource){
                is Resource.Success ->{
                    hideProgress()
                    val response = resource.mResponse?.body as? RestResponse<*>
                    onResponseSuccess(response)
                    val data = resource.data
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
                is Resource.Error -> {
                    hideProgress()
                }
                else ->{}
            }
        })
    }
}