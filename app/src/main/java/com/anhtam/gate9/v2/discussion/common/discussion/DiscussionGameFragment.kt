package com.anhtam.gate9.v2.discussion.common.discussion

import com.anhtam.domain.v2.Article
import com.anhtam.gate9.R
import com.anhtam.gate9.adapter.v2.ArticleAdapter
import com.anhtam.gate9.v2.discussion.common.CommonDiscussionFragment
import com.anhtam.gate9.v2.discussion.common.data.DataViewModel

class DiscussionGameFragment : CommonDiscussionFragment<Article, ArticleAdapter, DataViewModel>() {

    private var mLink: String = ""
    override val colorTextTab = R.color.colorTabDiscussion
    override val tabTitle: List<Int>
        get() = arrayListOf(R.string.number_all, R.string.number_post, R.string.number_comment)

    companion object {
        fun newInstance(link: String) : DiscussionGameFragment {
            val fragment = DiscussionGameFragment()
            fragment.mLink = link
            return fragment
        }
    }
}
