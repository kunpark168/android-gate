package com.anhtam.gate9.v2.game_detail

import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
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
import javax.inject.Inject

abstract class AbstractTabGameFragment<D, A: BaseQuickAdapter<D, BaseViewHolder>, VM: PagingViewModel<D>> : AbstractVisibleFragment(R.layout.shared_only_recycler_view_layout) {

    private val mDetailViewModel: DetailGameViewModel by viewModels({ requireParentFragment() }, { vmFactory })
    abstract val mViewModel: VM
    @Inject lateinit var mAdapter: A
    abstract fun initViewModel(id: Int)
    open fun setUpAdapter(){}

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
        observerGame()
        observerData()
    }

    private fun observerGame() {
        mDetailViewModel.mGame.observe(viewLifecycleOwner, Observer {
            when (it) {
                is Resource.Success -> {
                    val mId = it.data?.gameId ?: return@Observer
                    initViewModel(mId)
                    showProgress()
                    mViewModel.loadData(refresh = true)
                }
                else -> {

                }
            }
        })
    }

    private fun observerData(){
        mViewModel.data.observe(viewLifecycleOwner, Observer {
            when(it){
                is Resource.Success ->{
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
                is Resource.Error -> {
                    hideProgress()
                }
                else ->{}
            }
        })
    }
}