package com.anhtam.gate9.v2.discussion.common.newfeed

import androidx.fragment.app.viewModels
import com.anhtam.domain.v2.Post
import com.anhtam.gate9.R
import com.anhtam.gate9.adapter.v2.PostAdapter
import com.anhtam.gate9.v2.discussion.common.CommonDiscussionFragment

class NewFeedFragment : CommonDiscussionFragment<Post, PostAdapter, NewFeedViewModel>() {
    override val mViewModel: NewFeedViewModel by viewModels { vmFactory }

    override val colorTextTab = R.color.colorTabDiscussion
    override var mLazyLoad = false
    override val tabTitle: List<Int>
        get() = arrayListOf(R.string.number_all, R.string.number_post, R.string.number_comment)
}
