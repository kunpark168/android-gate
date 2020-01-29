package com.anhtam.gate9.v2.main.member

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.anhtam.gate9.R
import com.anhtam.gate9.share.view.CustomLoadMoreView
import com.anhtam.gate9.adapter.v2.MemberAdapter
import com.anhtam.gate9.utils.customOnClickHolder
import com.anhtam.gate9.v2.main.DaggerNavigationFragment
import com.anhtam.gate9.vo.EUser
import kotlinx.android.synthetic.main.member_tab_fragment.*
import of.bum.network.helper.Resource
import javax.inject.Inject

class MemberTabFragment : DaggerNavigationFragment() {

    private val viewModel: MemberDefaultViewModel by viewModels { vmFactory }
    private lateinit var mType: EUser

    @Inject lateinit var mAdapter: MemberAdapter

    private var mINavigator: INavigator? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.member_tab_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        init()
    }

    private fun init(){
        setData()
        setUpRecyclerView()
        initEvents()
        observer()
    }

    private fun setData() {
        viewModel.requestMemberList(mType)
    }

    private fun initEvents() {
        mINavigator = parentFragment as INavigator?
        viewAllLinearLayout?.customOnClickHolder {
            mINavigator?.navigateToListFragment(mType)
        }
    }


    private fun setUpRecyclerView() {
        mAdapter.setLoadMoreView(CustomLoadMoreView())
        rvUsers.adapter = mAdapter
        rvUsers.layoutManager = LinearLayoutManager(context)
        mAdapter.setOnLoadMoreListener({
            /* Request */
            viewModel.requestMore()
        },rvUsers)
        mAdapter.setEmptyView(R.layout.share_loading_load_more_view)
        mAdapter.isUseEmpty(true)
    }

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

    companion object {
        fun newInstance(type: EUser): MemberTabFragment {
            val fragment = MemberTabFragment()
            fragment.mType = type
            return fragment
        }
    }
}