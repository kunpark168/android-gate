package com.anhtam.gate9.v2.discussion.common.discussion


import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.anhtam.gate9.R
import com.anhtam.gate9.adapter.PostQuickAdapter
import com.anhtam.gate9.v2.discussion.common.CommonDiscussionFragment
import com.anhtam.gate9.v2.discussion.common.newfeed.PostCategory
import com.anhtam.gate9.utils.autoCleared
import com.anhtam.gate9.utils.loadingToAdapter
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayout
import com.squareup.phrase.Phrase
import kotlinx.android.synthetic.main.shared_discussion_layout.*

class DiscussionGameFragment : CommonDiscussionFragment() {

    private var mLink: String = ""
    private var mAdapter by autoCleared<PostQuickAdapter>()
    private var mCurrentCategory: PostCategory = PostCategory.BOTH
    override val colorTextTab = R.color.colorTabDiscussion
    private val viewModel: DiscussionGameViewModel by viewModels { vmFactory }

    override fun loadData() {
        viewModel.requestFirstPage(mLink, mCurrentCategory)
    }

    override fun initView() {
        super.initView()
        mAdapter = PostQuickAdapter(Glide.with(context!!))
        rvShareDiscussion?.adapter = mAdapter
        mAdapter.setOnLoadMoreListener ({
            viewModel.requestMore()
        }, rvShareDiscussion)
    }

    override fun observer() {
        super.observer()
        viewModel.posts.observe(this, Observer {
            it.loadingToAdapter(mAdapter, viewModel.page)
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
            viewModel.requestFirstPage(mLink, category)
        }
    }


    companion object {
        fun newInstance(link: String) : DiscussionGameFragment {
            val fragment = DiscussionGameFragment()
            fragment.mLink = link
            return fragment
        }
    }
}
