package com.anhtam.gate9.v2.discussion.common.game

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.anhtam.gate9.adapter.GameQuickAdapter

import com.anhtam.gate9.R
import com.anhtam.gate9.share.view.CustomLoadMoreView
import com.anhtam.gate9.v2.discussion.common.CommonDiscussionFragment
import com.anhtam.gate9.utils.autoCleared
import com.anhtam.gate9.v2.discussion.DiscussionViewModel
import com.anhtam.gate9.vo.EUser
import com.google.android.material.tabs.TabLayout
import com.squareup.phrase.Phrase
import kotlinx.android.synthetic.main.shared_discussion_layout.*
import of.bum.network.helper.Resource
import of.bum.network.helper.RestResponse
import kotlin.math.roundToLong


class GGameFragment: CommonDiscussionFragment() {

    private var mAdapter by autoCleared<GameQuickAdapter>()
    private var mUserId: Int = 0
    private var mCountTab1: Double = 0.0
    private var mCountTab2: Double = 0.0
    private var mCountTab3: Double = 0.0
    private var mCountTab4: Double = 0.0
    private var mCurrentCategory = GameCategory.OPEN

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        if (userVisibleHint) loadFirst()
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        if (isVisibleToUser && isResumed){
            loadFirst()
        }
    }

    private fun loadFirst(){
        viewModel.requestFirstPage(mUserId, mCurrentCategory)
    }

//    private lateinit var mAdapter: GameQuickAdapter
    private val viewModel: GGameViewModel by viewModels { vmFactory }
    private val mDiscussionViewModel: DiscussionViewModel by viewModels({requireParentFragment()}, { vmFactory })

    override val colorTextTab: Int = R.color.colorTabGame

    override fun loadData() {

    }

    override fun initView() {
        super.initView()
        initRv()
    }

    override fun configTabLayout() {
        tabLayout.apply {
            repeat(4) {
                addTab(newTab())
            }
        }
        updateTabLayout()
    }

    override fun updateTabLayout() {
        tabLayout.getTabAt(0)?.text = Phrase.from(resources.getString(R.string.open_category))
                .put("amount", mCountTab1.roundToLong().toString()).format()
        tabLayout.getTabAt(1)?.text = Phrase.from(resources.getString(R.string.beta_category))
                .put("amount", mCountTab2.roundToLong().toString()).format()
        tabLayout.getTabAt(2)?.text = Phrase.from(resources.getString(R.string.coming_category))
                .put("amount", mCountTab3.roundToLong().toString()).format()
        tabLayout.getTabAt(3)?.text = Phrase.from(resources.getString(R.string.closed_category))
                .put("amount", mCountTab4.roundToLong().toString()).format()
    }

    private fun initRv() {
        mAdapter = GameQuickAdapter()
        mAdapter.setLoadMoreView(CustomLoadMoreView())
        rvShareDiscussion?.adapter = mAdapter
        mAdapter.setOnLoadMoreListener ({
            viewModel.requestMore()
        }, rvShareDiscussion)
    }

    override fun observer() {
        super.observer()
        mDiscussionViewModel.mUser.observe(viewLifecycleOwner, Observer {
            val user = it?.data ?: return@Observer
            val role = when(user.mRoleId){
                "5" -> EUser.NPH
                else -> EUser.TV
            }
            mUserId = user.mId ?: return@Observer
            viewModel.initialize(role)
            viewModel.requestFirstPage(mUserId, mCurrentCategory)
        })

        viewModel._game.observe(viewLifecycleOwner, Observer {resource ->
            when(resource) {
                is Resource.Success -> {
                    val data = resource.data?.map { it.wrap }
                    val response = resource.mResponse?.body as? RestResponse<*>
                    mCountTab1 = (response?.mMeta?.get("countTab1") as? Double) ?: 0.0
                    mCountTab2 = (response?.mMeta?.get("countTab2") as? Double) ?: 0.0
                    mCountTab3 = (response?.mMeta?.get("countTab3") as? Double) ?: 0.0
                    mCountTab4 = (response?.mMeta?.get("countTab4") as? Double) ?: 0.0
                    updateTabLayout()
                    if (data.isNullOrEmpty()) {
                        mAdapter.loadMoreEnd()
                    } else {
                        if (viewModel.page == 0) {
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
                    mAdapter.loadMoreFail()
                }
            }
        })
    }

    override fun initEvents() {
        tabLayout?.addOnTabSelectedListener(object: TabLayout.OnTabSelectedListener {
            override fun onTabReselected(p0: TabLayout.Tab?) {
                // Don't do anything here
            }

            override fun onTabUnselected(p0: TabLayout.Tab?) {

            }

            override fun onTabSelected(tab: TabLayout.Tab?) {
                when(tab?.position) {
                    0 -> {
                        newRequestType(GameCategory.OPEN)
                    }
                    1 -> {
                        newRequestType(GameCategory.BETA)
                    }
                    2 -> {
                        newRequestType(GameCategory.COMING)
                    }
                    else -> {
                        newRequestType(GameCategory.CLOSED)
                    }
                }
            }

        })
        swipeRefreshLayout?.setOnRefreshListener {
            swipeRefreshLayout?.isRefreshing = false
            loadData()
        }
    }

    private fun newRequestType(category: GameCategory) {
        mCurrentCategory = category
        mAdapter.data.clear()
        mAdapter.notifyDataSetChanged()
        if (viewModel.mCategory != category) {
            viewModel.requestFirstPage(mUserId, category)
        }
    }

    companion object {
        fun newInstance() = GGameFragment()
    }

}
