package com.anhtam.gate9.v2.discussion.common.data

import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.anhtam.domain.v2.Article
import com.anhtam.gate9.R
import com.anhtam.gate9.adapter.article.ArticleImageAdapter
import com.anhtam.gate9.share.view.CustomLoadMoreView
import com.anhtam.gate9.v2.discussion.common.CommonDiscussionFragment
import com.google.android.material.tabs.TabLayout
import com.squareup.phrase.Phrase
import kotlinx.android.synthetic.main.shared_discussion_layout.*
import of.bum.network.helper.Resource

class DataFragment: CommonDiscussionFragment() {
    override fun loadData() {
        viewModel.requestFirstPage(mUserId, mCurrentCategory)
    }

    private var mUserId: Int = 0
    private lateinit var mAdapter: ArticleImageAdapter
    private val viewModel: DataViewModel by viewModels { vmFactory }
    private var mCurrentCategory = DataCategory.NEWS

    override val colorTextTab: Int = R.color.colorTabData

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
        tabLayout.getTabAt(0)?.text = Phrase.from(resources.getString(R.string.news_category))
                .put("amount", "0").format()
        tabLayout.getTabAt(1)?.text = Phrase.from(resources.getString(R.string.tip_category))
                .put("amount", "0").format()
        tabLayout.getTabAt(2)?.text = Phrase.from(resources.getString(R.string.video_category))
                .put("amount", "0").format()
        tabLayout.getTabAt(3)?.text = Phrase.from(resources.getString(R.string.image_category))
                .put("amount", "0").format()
    }

    private fun initRv() {
        mAdapter = ArticleImageAdapter()
        mAdapter.setLoadMoreView(CustomLoadMoreView())
        rvShareDiscussion?.adapter = mAdapter
        mAdapter.setOnLoadMoreListener ({
            viewModel.requestMore()
        }, rvShareDiscussion)
    }

    override fun observer() {
        super.observer()
        viewModel._articles.observe(viewLifecycleOwner, Observer {resource ->
            when(resource) {
                is Resource.Success -> {
                    val data: List<Article>? = resource.data?.let {
                        when{
                            !it.mGallery.isNullOrEmpty() -> it.mGallery
                            !it.mVideo.isNullOrEmpty() -> it.mVideo
                            !it.mGames.isNullOrEmpty() -> it.mGames
                            !it.mManual.isNullOrEmpty() -> it.mManual
                            else -> null
                        }
                    }
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
                        newRequestType(DataCategory.NEWS)
                    }
                    1 -> {
                        newRequestType(DataCategory.TIP)
                    }
                    2 -> {
                        newRequestType(DataCategory.VIDEO)
                    }
                    else -> {
                        newRequestType(DataCategory.IMAGE)
                    }
                }
            }

        })
        swipeRefreshLayout?.setOnRefreshListener {
            swipeRefreshLayout?.isRefreshing = false
            loadData()
        }
    }

    private fun newRequestType(category: DataCategory) {
        mCurrentCategory = category
        mAdapter.data.clear()
        mAdapter.notifyDataSetChanged()
        if (viewModel.mCategory != category) {
            viewModel.requestFirstPage(mUserId, category)
        }
    }

    companion object {
        fun newInstance(userId: Int) : DataFragment {
            val fragment = DataFragment()
            fragment.mUserId = userId
            return fragment
        }
    }

}
