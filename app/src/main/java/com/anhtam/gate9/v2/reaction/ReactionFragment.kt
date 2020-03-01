package com.anhtam.gate9.v2.reaction

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.anhtam.gate9.R
import com.anhtam.gate9.adapter.v2.MemberAdapter
import com.anhtam.gate9.share.view.CustomLoadMoreView
import com.anhtam.gate9.v2.main.DaggerNavigationFragment
import kotlinx.android.synthetic.main.reaction_fragment.*
import of.bum.network.helper.Resource
import javax.inject.Inject

class ReactionFragment(
        private val mCommentId: Int,
        private val mTabId: Int
): DaggerNavigationFragment(R.layout.reaction_fragment){

    @Inject lateinit var mAdapter: MemberAdapter
    private val viewModel: ReactionViewModel by viewModels { vmFactory }
    companion object{
        fun newInstance(commentId: Int, tabId: Int) = ReactionFragment(commentId, tabId)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init(){
        loadData()
        initRecyclerView()
        observer()
    }

    private fun loadData(){
        viewModel.requestFirst(mCommentId, mTabId)
    }

    private fun initRecyclerView(){
        mAdapter.setLoadMoreView(CustomLoadMoreView())
        mAdapter.setOnLoadMoreListener({
            viewModel.requestMore()
        }, rvReaction)
        rvReaction?.adapter = mAdapter
        rvReaction?.layoutManager = LinearLayoutManager(context)
    }

    private fun observer(){
        viewModel.members.observe(viewLifecycleOwner, Observer {resource ->
            when(resource) {
                is Resource.Success -> {
                    hideProgress()
                    val data = resource.data
                    if (data.isNullOrEmpty()) {
                        mAdapter.loadMoreEnd()
                    } else {
                        if (viewModel.mPage == 0) {
                            mAdapter.setNewData(data)
                        } else {
                            mAdapter.addData(data)
                        }
                        mAdapter.loadMoreComplete()
                    }
                }
                is Resource.Loading -> {
                    hideProgress()
                }
                else -> {
                    mAdapter.loadMoreFail()
                }
            }
        })
    }
}