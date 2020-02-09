package com.anhtam.gate9.v2.main.member.all

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.anhtam.gate9.R
import com.anhtam.gate9.share.view.CustomLoadMoreView
import com.anhtam.gate9.v2.main.member.MemberDefaultViewModel
import com.anhtam.gate9.adapter.AlphabetAdapter
import com.anhtam.gate9.adapter.v2.MemberAdapter
import com.anhtam.gate9.v2.main.DaggerNavigationFragment
import com.anhtam.gate9.vo.EUser
import kotlinx.android.synthetic.main.member_list_fragment.*
import of.bum.network.helper.Resource
import javax.inject.Inject

class MemberListFragment
    : DaggerNavigationFragment(R.layout.member_list_fragment) , AlphabetAdapter.IChangeAlphabetIndex {


    companion object {
        fun newInstance(type: EUser): MemberListFragment {
            val fragment = MemberListFragment()
            fragment.mType = type
            return fragment
        }
    }

    override fun onChangeAlphabetIndex(letter: String) {
        /* Request page 1 and new letter */
        mAdapter.setNewData(emptyList())
        viewModel.requestMemberList(mType)
    }

    private val viewModel: MemberDefaultViewModel by viewModels { vmFactory }
    private lateinit var mType: EUser

    @Inject lateinit var mAdapter: MemberAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init() {
        viewModel.requestMemberList(mType)
        setUpRecyclerView()
        observer()
        initEvents()
    }

    private fun initEvents() {
    }

    private fun setUpRecyclerView() {
        alphabetRecyclerView.adapter = AlphabetAdapter(context, this)
        alphabetRecyclerView.layoutManager = LinearLayoutManager(context)

        mAdapter.setLoadMoreView(CustomLoadMoreView())
        rvAllMembers.adapter = mAdapter
        rvAllMembers.layoutManager = LinearLayoutManager(context)
        mAdapter.setOnLoadMoreListener({
            /* Request */
            viewModel.requestMore()
        },rvAllMembers)
        mAdapter.setEmptyView(R.layout.share_loading_load_more_view)
        mAdapter.isUseEmpty(true)
    }

    /* No request && observer, 1) observer*/
    private fun observer() {
        viewModel.userList.observe(this, Observer {
            when(it) {
                is Resource.Success -> {
                    val data = it.data
                    if (data.isNullOrEmpty()) {
                        mAdapter.loadMoreEnd()
                    } else {
                        if (viewModel.page == 0) {
                            mAdapter.setNewData(data)
                        } else {
                            mAdapter.addData(data)
                        }
                        mAdapter.loadMoreComplete()
                        mAdapter.removeAllHeaderView()
                    }
                }
                is Resource.Error -> {
                    mAdapter.removeAllHeaderView()
                }
                else -> {

                }
            }
        })
    }

}