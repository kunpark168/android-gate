package com.anhtam.gate9.ui.discussion.common.newfeed

import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.anhtam.gate9.R
import com.anhtam.gate9.adapter.v2.CommentAdapter
import com.anhtam.gate9.share.view.CustomLoadMoreView
import com.anhtam.gate9.ui.discussion.common.CommonDiscussionFragment
import com.anhtam.gate9.utils.autoCleared
import com.google.android.material.tabs.TabLayout
import com.squareup.phrase.Phrase
import kotlinx.android.synthetic.main.shared_discussion_layout.*
import of.bum.network.helper.Resource

class NewFeedFragment(private val _userId: Int) : CommonDiscussionFragment() {

    private var mAdapter by autoCleared<CommentAdapter>()
    private var mCurrentCategory: PostCategory = PostCategory.BOTH
    override val colorTextTab = R.color.colorTabDiscussion
    private val viewModel: NewFeedViewModel by viewModels { vmFactory }

    override fun loadData() {
        viewModel.requestFirstPage(_userId, mCurrentCategory)
    }

    override fun initView() {
        super.initView()
        mAdapter = CommentAdapter(null){data, type ->
            val id = data.commentId?.toInt() ?: 0
            val params = hashMapOf<String, Int>()
            params["commentId"] = id
            params["type"] = type
            params["userId"] = _userId
//            viewModel.react(params).enqueue(object: Callback<Base> {
//                override fun onFailure(call: Call<Base>, t: Throwable) {
//                    Timber.d("Failure")
//                }
//
//                override fun onResponse(call: Call<Base>, response: Response<Base>) {
//                    Timber.d(StorageManager.getAccessToken())
//                    Timber.d("Success")
//                }
//
//            })
        }
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
                    val data = resource.data?.wrap
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
                .put("amount", "0").format()
        tabLayout.getTabAt(1)?.text = Phrase.from(resources.getString(R.string.number_post))
                .put("amount", "0").format()
        tabLayout.getTabAt(2)?.text = Phrase.from(resources.getString(R.string.number_comment))
                .put("amount", "0").format()
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
    }

    private fun newRequestType(category: PostCategory) {
        mAdapter.data.clear()
        mAdapter.notifyDataSetChanged()
        if (viewModel.mCategory != category) {
            viewModel.requestFirstPage(_userId, category)
        }
    }
}
