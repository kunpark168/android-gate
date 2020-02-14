package com.anhtam.gate9.v2.mxh_gate.tin_game

import android.os.Bundle
import android.view.*
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import com.anhtam.gate9.R
import com.anhtam.gate9.adapter.v2.ArticleAdapter
import com.anhtam.gate9.share.view.CustomLoadMoreView
import com.anhtam.gate9.v2.mxh_gate.DuLieuViewModel
import com.anhtam.gate9.v2.shared.views.AbstractVisibleFragment
import kotlinx.android.synthetic.main.mxh_gate_tin_game_screen.*
import of.bum.network.helper.Resource
import timber.log.Timber
import javax.inject.Inject

class MXHGateTinGameScreen constructor(val mTab: Int) : AbstractVisibleFragment(R.layout.mxh_gate_tin_game_screen) {

    companion object{
        fun newInstance(tab: Int) = MXHGateTinGameScreen(tab)
    }

    private var mFirstLoad = true

    @Inject lateinit var mAdapter: ArticleAdapter
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
        val unwrappedContext = context ?: return
        val dividerItemDecoration = DividerItemDecoration(unwrappedContext, LinearLayout.VERTICAL)
        val drawableDivider = ContextCompat.getDrawable(unwrappedContext, R.drawable.divider_item_decorator) ?: return
        dividerItemDecoration.setDrawable(drawableDivider)
        rvTinGame?.addItemDecoration(dividerItemDecoration)
    }
    private fun init(){
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