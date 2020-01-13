package com.anhtam.gate9.adapter.v2

import android.text.Html
import com.anhtam.domain.v2.Post
import com.anhtam.gate9.R
import com.anhtam.gate9.navigation.Navigation
import com.anhtam.gate9.share.view.MoreDialog
import com.anhtam.gate9.utils.toImage
import com.anhtam.gate9.v2.detail_post.DetailPostScreen
import com.anhtam.gate9.v2.discussion.user.UserDiscussionScreen
import com.anhtam.gate9.vo.model.Category
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import kotlinx.android.synthetic.main.comment_item_layout.view.*
import javax.inject.Inject
import javax.inject.Named

class ChildCommentAdapter @Inject constructor(
        val navigation: Navigation,
        @Named("avatar") val avatarOptions: RequestOptions
): BaseQuickAdapter<Post, BaseViewHolder>(R.layout.child_comment_item_layout, arrayListOf()){

    init {
        setOnItemChildClickListener { _, view, position ->
            when(view.id){
                R.id.contentTextView, R.id.commentImageView -> toDetailComment(data[position])
                R.id.userNameTextView, R.id.avatarImageView -> {
                    val userId = data[position].user?.mId ?: 0
                    toUserDiscussion(userId)
                }
                R.id.moreImageView ->{
                    showMoreDialog(data[position])
                }
            }
        }
    }

    override fun convert(helper: BaseViewHolder?, item: Post?) {
        val comment = item ?: return
        val view = helper?.itemView ?: return
        // Set content of Post
        view.contentTextView.text = comment.content?.let { Html.fromHtml(it) }
        view.dateTextView.text = comment.createdDate
        // Set user
        val user = comment.user ?: return
        view.userNameTextView.text = user.mName
        Glide.with(mContext)
                .load(user.mAvatar?.toImage())
                .apply(avatarOptions)
                .into(view.avatarImageView)

        // event click
        helper.addOnClickListener(R.id.contentTextView)
                .addOnClickListener(R.id.commentImageView)
                .addOnClickListener(R.id.userNameTextView)
                .addOnClickListener(R.id.avatarImageView)
                .addOnClickListener(R.id.moreImageView)
    }

    private fun showMoreDialog(comment: Post){
        val mMoreDialog = MoreDialog(mContext, object : MoreDialog.IMore {
            override fun onreport() {
//                        ReportPostActivity.start(mContext as BaseActivity)
            }
        })
        mMoreDialog.idPost = comment.commentId?.toString()
        mMoreDialog.show()
    }

    private fun toDetailComment(comment: Post){
        navigation.addFragment(DetailPostScreen.newInstance(comment, DetailPostScreen.Detail.COMMENT))
    }

    private fun toUserDiscussion(userId: Int){
        navigation.addFragment(UserDiscussionScreen.newInstance(userId, Category.Member))
    }
}