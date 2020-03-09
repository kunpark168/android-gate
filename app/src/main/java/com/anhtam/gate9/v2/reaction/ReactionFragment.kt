package com.anhtam.gate9.v2.reaction

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.anhtam.domain.v2.protocol.User
import com.anhtam.gate9.R
import com.anhtam.gate9.adapter.v2.MemberAdapter
import com.anhtam.gate9.share.view.CustomLoadMoreView
import com.anhtam.gate9.v2.main.DaggerNavigationFragment
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.reaction_fragment.*
import of.bum.network.helper.Resource
import javax.inject.Inject
import javax.inject.Named

class ReactionFragment(
        private val mCommentId: Int,
        private val mTabId: Int,
        private val mUser: User
): DaggerNavigationFragment(R.layout.reaction_fragment){

    @Inject lateinit var mAdapter: MemberAdapter
    @field:Named("avatar") @Inject lateinit var mOptions: RequestOptions
    private val viewModel: ReactionViewModel by viewModels { vmFactory }
    private var mHeaderView: CurrentUserHeaderView? = null

    companion object{
        fun newInstance(commentId: Int, tabId: Int, user: User) = ReactionFragment(commentId, tabId, user)
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
        if (mTabId == ReactionScreen.CODE_VIEW) {
            mHeaderView?.let {
                mAdapter.removeHeaderView(mHeaderView)
            }
            mHeaderView = CurrentUserHeaderView(context)
            mHeaderView?.setUser(mUser, mOptions, navigation, mSessionManager)
            mAdapter.addHeaderView(mHeaderView)
        }
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