package com.anhtam.gate9.v2.mxh_gate.tin_game

import android.os.Bundle
import android.view.*
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.anhtam.domain.v2.Article
import com.anhtam.gate9.R
import com.anhtam.gate9.share.view.CustomLoadMoreView
import com.anhtam.gate9.v2.mxh_gate.DuLieuViewModel
import com.anhtam.gate9.v2.shared.views.AbstractVisibleFragment
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import kotlinx.android.synthetic.main.mxh_gate_tin_game_screen.*
import of.bum.network.helper.Resource
import timber.log.Timber
import javax.inject.Inject

open class MXHGateTinGameScreen<A: BaseQuickAdapter<Article, BaseViewHolder>> constructor(val mTab: Int) : AbstractVisibleFragment(R.layout.mxh_gate_tin_game_screen) {

    companion object{
        fun <A: BaseQuickAdapter<Article, BaseViewHolder>> newInstance(tab: Int) = MXHGateTinGameScreen<A>(tab)
    }

    private var mFirstLoad = true
    protected open var mLayoutManager: RecyclerView.LayoutManager? = null
    @Inject lateinit var mAdapter: A
    private val mViewModel: DuLieuViewModel by viewModels { vmFactory }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun initRecyclerView(){
        mAdapter.setLoadMoreView(CustomLoadMoreView())
        mAdapter.setOnLoadMoreListener({
            mViewModel.loadData()
        }, rvTinGame)
        rvTinGame?.adapter = mAdapter
        rvTinGame?.layoutManager = mLayoutManager
        val unwrappedContext = context ?: return
        val dividerItemDecoration = DividerItemDecoration(unwrappedContext, LinearLayout.VERTICAL)
        val drawableDivider = ContextCompat.getDrawable(unwrappedContext, R.drawable.divider_item_decorator) ?: return
        dividerItemDecoration.setDrawable(drawableDivider)
        rvTinGame?.addItemDecoration(dividerItemDecoration)
    }
    private fun init(){
        mViewModel.setCategory(mTab)
        preloadData()
        initRecyclerView()
        observerDataChanged()
    }

    override fun onUiVisibleChange(isUiVisible: Boolean) {
        super.onUiVisibleChange(isUiVisible)
        if (isUiVisible && mFirstLoad){
            mFirstLoad = false
            lazyLoad()
        }
    }

    private fun preloadData(){
        if (mTab != 1){
            lazyLoad()
        } else {
            loadData()
        }
    }

    private fun lazyLoad(){
        if (!mFirstLoad && mTab != 1){
            Timber.d("Has loading G Game h.........")
            loadData()
        }
    }

    private fun loadData(){
        showProgress()
        mViewModel.loadData(refresh = true)
    }

    private fun observerDataChanged(){
        mViewModel.data.observe(viewLifecycleOwner, Observer {resource ->
            when(resource) {
                is Resource.Success -> {
                    hideProgress()
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
                is Resource.Loading -> {

                }
                else -> {
                    hideProgress()
                    mAdapter.loadMoreFail()
                }
            }
        })
    }
}