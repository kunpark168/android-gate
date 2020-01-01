package com.anhtam.gate9.adapter

import android.text.Html
import android.view.View
import androidx.core.content.ContextCompat
import com.anhtam.domain.Post
import com.anhtam.gate9.R
import com.anhtam.gate9.share.view.CustomLoadMoreView
import com.anhtam.gate9.share.view.MoreDialog
import com.anhtam.gate9.utils.debounceClick
import com.anhtam.gate9.utils.ifNotNull
import com.bumptech.glide.RequestManager
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import kotlinx.android.synthetic.main.shared_post_item_layout.view.*

class PostQuickAdapter(private val requestManager: RequestManager) :
        BaseQuickAdapter<Post, BaseViewHolder>(R.layout.shared_post_item_layout, mutableListOf()){

    init {
        setLoadMoreView(CustomLoadMoreView())
    }

    var mListener: IItemClick? = null
//    override fun navigateToMemberDiscussion(context: Context?, userId: String?) {
//        userId?.let {
//            UserDiscussionScreen.start(context, userId, Category.Member)
//        }
//    }
//
//    override fun navigateToPostDetail(context: Context?, postEntity: PostEntity) {
////        val unwrappedId = post?.commentId?: return
//        context?: return
//    }

    override fun convert(helper: BaseViewHolder?, item: Post?) {
        ifNotNull(helper, item) { holder, post ->

            /*
             *
             */
            val unwrappedGame = post.game
            if (unwrappedGame == null) {
                holder.itemView.gameConstraintLayout.visibility = View.GONE
            } else {
                holder.itemView.apply {
                    gameConstraintLayout.visibility = View.VISIBLE
                    requestManager
                            .load(unwrappedGame.avatar)
                            .into(gameImageView)

                    titleGameTextView.text = unwrappedGame.name ?: ""
                    val content = "Follower: ".plus(unwrappedGame.follower
                            ?: 0).plus("  |  Post: ").plus(unwrappedGame.post ?: "")
                    contentGameTextView.text = content

                    if (unwrappedGame.follow == "not_follow") {
                        followGameTextView.background = ContextCompat.getDrawable(context, R.drawable.bg_follow)
                        followGameTextView.text = context.resources.getString(R.string.follow_plus)
                        followGameTextView.setTextColor(ContextCompat.getColor(context, R.color.color_follow))
                    } else {
                        followGameTextView.background = ContextCompat.getDrawable(context, R.drawable.bg_following)
                        followGameTextView.text = context.resources.getString(R.string.following)
                        followGameTextView.setTextColor(ContextCompat.getColor(context, R.color.color_following))
                    }
                    val link = unwrappedGame.link ?: ""
                    val gameId = unwrappedGame.gameId ?: ""
//                    gameImageView.debounceClick {
//                        GameDiscussionScreen.start(context, link, gameId)
//                    }
//                    titleGameTextView.debounceClick {
//                        GameDiscussionScreen.start(context, link, gameId)
//                    }
                }
            }


            /*
             * Set text view
             */
            holder.setText(R.id.userNameTextView, post.name ?: "")
                    .setText(R.id.contentTextView, post.content?.let { Html.fromHtml(it) } ?: "")
                    .setText(R.id.dateTextView, post.createdDate ?: "")
                    .setText(R.id.likeTextView, post.totalLike?.toString() ?: "0")
                    .setText(R.id.dislikeTextView, post.totalDislike?.toString() ?: "0")
                    .setText(R.id.commentTextView, post.totalReply?.toString() ?: "0")

            /*
             * Set event clicks
             */
            holder.itemView.apply {
                requestManager
                        .load(post.avatar)
                        .into(avatarImageView)

                userNameTextView.debounceClick {
//                    navigateToMemberDiscussion(context, post.user_id)
                }
                avatarImageView.debounceClick {
//                    navigateToMemberDiscussion(context, post.user_id)
                }

                moreImageView?.debounceClick {
                    val mMoreDialog = MoreDialog(context, object : MoreDialog.IMore {
                        override fun onreport() {
//                            ReportPostActivity.start(context as BaseActivity)
                        }
                    })
                    mMoreDialog.idPost = post.commentId
                    mMoreDialog.show()
                }

                /*
                 * Navigate PostDetailActivity
                 */

//
//                commentImageView.debounceClick {
//                    navigateToPostDetail(context, post)
//                }
//                contentTextView.debounceClickWithoutHolder {
//                    navigateToPostDetail(context, post)
//                }
//                readMoreTextView.debounceClick {
//                    navigateToPostDetail(context, post)
//                }

                moreImageView.debounceClick { moreClick(post.commentId) }
            }

        }
    }

    private fun moreClick(commentId: String?) {
        commentId?.let {
            mListener?.moreClick(it)
        }
    }

    interface IItemClick {
        fun moreClick(idPost: String)
    }
}