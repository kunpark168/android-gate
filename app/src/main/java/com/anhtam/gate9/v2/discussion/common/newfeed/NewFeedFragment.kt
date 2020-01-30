package com.anhtam.gate9.v2.discussion.common.newfeed

import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.anhtam.gate9.R
import com.anhtam.gate9.adapter.v2.PostAdapter
import com.anhtam.gate9.share.view.CustomLoadMoreView
import com.anhtam.gate9.utils.convertInt
import com.anhtam.gate9.v2.discussion.common.CommonDiscussionFragment
import com.anhtam.gate9.vo.EUser
import com.google.android.material.tabs.TabLayout
import com.squareup.phrase.Phrase
import kotlinx.android.synthetic.main.shared_discussion_layout.*
import of.bum.network.helper.Resource
import of.bum.network.helper.RestResponse
import javax.inject.Inject
import kotlin.math.roundToLong

class NewFeedFragment(private val _userId: Int, private val mEUser: EUser) : CommonDiscussionFragment() {

    @Inject lateinit var mAdapter: PostAdapter
    private var mCurrentCategory: PostCategory = PostCategory.BOTH
    override val colorTextTab = R.color.colorTabDiscussion
    private val viewModel: NewFeedViewModel by viewModels { vmFactory }

    /*
     *
     */
    private var mCountTab1: Double = 0.0
    private var mCountTab2: Double = 0.0
    private var mCountTab3: Double = 0.0

    override fun loadData() {
        viewModel.initialize(mEUser)
        viewModel.requestFirstPage(_userId, mCurrentCategory)
    }

    override fun initView() {
        super.initView()
        mAdapter.setLoadMoreView(CustomLoadMoreView())
        rvShareDiscussion?.adapter = mAdapter
        mAdapter.setOnLoadMoreListener ({
            viewModel.requestMore()
        }, rvShareDiscussion)
    }

    override fun observer() {
        super.observer()
        viewModel._post.observe(this, Observer {resource ->
            when(resource) {
                is Resource.Success -> {
                    val data = resource.data
                    val response = resource.mResponse?.body as? RestResponse<*>
                    mCountTab1 = (response?.mMeta?.get("countTab1") as? Double) ?: 0.0
                    mCountTab2 = (response?.mMeta?.get("countTab2") as? Double) ?: 0.0
                    mCountTab3 = (response?.mMeta?.get("countTab3") as? Double) ?: 0.0
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
    override fun configTabLayout() {
        tabLayout.apply {
            repeat(3) {
                addTab(newTab())
            }
        }
        updateTabLayout()
    }

    override fun updateTabLayout() {
        tabLayout.getTabAt(0)?.text = Phrase.from(resources.getString(R.string.number_all))
                .put("amount", mCountTab1.roundToLong().toString()).format()
        tabLayout.getTabAt(1)?.text = Phrase.from(resources.getString(R.string.number_post))
                .put("amount", mCountTab2.roundToLong().toString()).format()
        tabLayout.getTabAt(2)?.text = Phrase.from(resources.getString(R.string.number_comment))
                .put("amount", mCountTab3.roundToLong().toString()).format()
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
                        newRequestType(PostCategory.BOTH)
                    }
                    1 -> {
                        newRequestType(PostCategory.POST)
                    }
                    else -> {
                        newRequestType(PostCategory.COMMENT)
                    }
                }
            }

        })
        swipeRefreshLayout?.setOnRefreshListener {
            swipeRefreshLayout?.isRefreshing = false
            loadData()
        }
    }

    private fun newRequestType(category: PostCategory) {
        mCurrentCategory = category
        mAdapter.data.clear()
        mAdapter.notifyDataSetChanged()
        if (viewModel.mCategory != category) {
            viewModel.requestFirstPage(_userId, category)
        }
    }
}
