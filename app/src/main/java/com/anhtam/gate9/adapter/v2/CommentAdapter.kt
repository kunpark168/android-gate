package com.anhtam.gate9.adapter.v2

import android.text.Html
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.anhtam.domain.v2.Game
import com.anhtam.domain.v2.PostEntity
import com.anhtam.gate9.R
import com.anhtam.gate9.navigation.Navigation
import com.anhtam.gate9.share.view.MoreDialog
import com.anhtam.gate9.utils.toImage
import com.anhtam.gate9.v2.detail_post.DetailPostScreen
import com.anhtam.gate9.v2.detail_post.ShowMoreFooterView
import com.anhtam.gate9.v2.discussion.user.UserDiscussionScreen
import com.anhtam.gate9.vo.model.Category
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.squareup.phrase.Phrase
import kotlinx.android.synthetic.main.comment_item_layout.view.*
import java.lang.NumberFormatException
import javax.inject.Inject
import javax.inject.Named


class CommentAdapter @Inject constructor(
        val navigation: Navigation,
        @Named("avatar") val avatarOptions: RequestOptions,
        @Named("banner") val bannerOptions: RequestOptions
) : BaseQuickAdapter<PostEntity, BaseViewHolder>(R.layout.comment_item_layout, arrayListOf()){

    companion object{
        private const val DEFAULT_DISPLAY_CHILD_NUM = 2
    }

    private var mGame: Game? = null

    init {
        setOnItemChildClickListener { _, view, position ->
            when(view.id){
                R.id.contentTextView, R.id.commentImageView -> {
                    val comment = data[position]
                    comment.game = mGame
                    toDetailComment(data[position])
                }
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

    fun initialize(game: Game?){
        mGame = game
    }
    override fun convert(helper: BaseViewHolder?, item: PostEntity?) {

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

        // Check has reply comment
        val totalReply = try {
            comment.totalReply.toInt()
        } catch (e: NumberFormatException) {
            0
        }
        val childComment = comment.child
        if (totalReply == 0 || childComment.isNullOrEmpty()){
            view.rvChildComment?.visibility = View.GONE
        } else {
            view.rvChildComment?.visibility = View.VISIBLE
            val childAdapter = ChildCommentAdapter(navigation, avatarOptions)
            childAdapter.setNewData(if(childComment.size <= DEFAULT_DISPLAY_CHILD_NUM) childComment else childComment.subList(0, DEFAULT_DISPLAY_CHILD_NUM))
            // Add footer
            if(totalReply > DEFAULT_DISPLAY_CHILD_NUM) {
                val footer = ShowMoreFooterView(mContext)
                footer.setOnClickListener {
                    toUserDiscussion(user.mId ?: 0)
                }
                childAdapter.addFooterView(footer)
                val remain = totalReply - DEFAULT_DISPLAY_CHILD_NUM
                val showMoreText = Phrase.from(mContext.getString(R.string.xem_them_amount_comment))
                        .put("number", remain)
                        .put("s", if(remain > 1) "s" else "")
                        .format()
                        .toString()
                footer.setText(showMoreText) {
                    // set show more here
                    toDetailComment(comment)
                }
            }
            view.rvChildComment?.adapter = childAdapter
            view.rvChildComment?.layoutManager = LinearLayoutManager(mContext)
        }


        // event click
        helper.addOnClickListener(R.id.contentTextView)
                .addOnClickListener(R.id.commentImageView)
                .addOnClickListener(R.id.userNameTextView)
                .addOnClickListener(R.id.avatarImageView)
                .addOnClickListener(R.id.moreImageView)
    }

    private fun showMoreDialog(comment: PostEntity){
        val mMoreDialog = MoreDialog(mContext, object : MoreDialog.IMore {
            override fun onreport() {
//                        ReportPostActivity.start(mContext as BaseActivity)
            }
        })
        mMoreDialog.idPost = comment.commentId?.toString()
        mMoreDialog.show()
    }

    private fun toDetailComment(comment: PostEntity){
        navigation.addFragment(DetailPostScreen.newInstance(comment, DetailPostScreen.Detail.COMMENT))
    }

    private fun toUserDiscussion(userId: Int){
        navigation.addFragment(UserDiscussionScreen.newInstance(userId, Category.Member))
    }
}