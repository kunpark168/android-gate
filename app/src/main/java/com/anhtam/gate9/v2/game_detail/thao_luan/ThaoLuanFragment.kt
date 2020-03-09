package com.anhtam.gate9.v2.game_detail.thao_luan


import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.anhtam.gate9.R
import com.anhtam.gate9.adapter.v2.PostAdapter
import com.anhtam.gate9.share.view.CustomLoadMoreView
import com.anhtam.gate9.v2.game_detail.DetailGameViewModel
import com.anhtam.gate9.v2.shared.views.AbstractVisibleFragment
import kotlinx.android.synthetic.main.shared_only_recycler_view_layout.*
import of.bum.network.helper.Resource
import javax.inject.Inject

class ThaoLuanFragment : AbstractVisibleFragment(R.layout.shared_only_recycler_view_layout){

    private val mDetailViewModel: DetailGameViewModel by viewModels({requireParentFragment()},{vmFactory})
    private val mViewModel: ThaoLuanViewModel by viewModels { vmFactory }
    @Inject lateinit var mAdapter: PostAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initEvent()
        observer()
    }

    private fun initView(){
        initRecyclerView()
    }

    private fun initRecyclerView(){
        mAdapter.setLoadMoreView(CustomLoadMoreView())
        mAdapter.setOnLoadMoreListener({
            mViewModel.loadData(false)
        }, recyclerView)
        context?.let {
            val dividerItemDecoration = DividerItemDecoration(it, LinearLayout.VERTICAL)
            val drawableDivider = ContextCompat.getDrawable(it, R.drawable.divider_item_decorator) ?: return
            dividerItemDecoration.setDrawable(drawableDivider)
            recyclerView?.addItemDecoration(dividerItemDecoration)

        }
        recyclerView?.adapter = mAdapter
        recyclerView?.layoutManager = LinearLayoutManager(context)
    }

    private fun initEvent(){
        swipeRefreshLayout?.setOnRefreshListener {
            swipeRefreshLayout?.isRefreshing = false
            showProgress()
            mViewModel.loadData(refresh = true)
        }
    }

    private fun observer(){
        observerGame()
        observerData()
    }

    private fun observerGame(){
        mDetailViewModel.mGame.observe(viewLifecycleOwner, Observer {
            when(it){
                is Resource.Success -> {
                    val mId = it.data?.gameId ?: return@Observer
                    mViewModel.initialize(mId)
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
                    val data = it.data?.mList
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

    companion object{
        fun newInstance() = ThaoLuanFragment()
    }
}