package com.anhtam.gate9.adapter.v2

import android.text.Html
import android.view.View
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.anhtam.domain.v2.Post
import com.anhtam.domain.v2.Rating
import com.anhtam.domain.v2.protocol.Game
import com.anhtam.domain.v2.protocol.User
import com.anhtam.gate9.R
import com.anhtam.gate9.config.Config
import com.anhtam.gate9.navigation.Navigation
import com.anhtam.gate9.share.view.MoreDialog
import com.anhtam.gate9.utils.toImage
import com.anhtam.gate9.v2.discussion.game.GameDiscussionScreen
import com.anhtam.gate9.v2.discussion.user.UserDiscussionScreen
import com.anhtam.gate9.v2.detail_post.DetailPostScreen
import com.anhtam.gate9.v2.report.post.ReportPostActivity
import com.anhtam.gate9.vo.Reaction
import com.anhtam.gate9.vo.model.Category
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import kotlinx.android.synthetic.main.item_rating.view.*
import javax.inject.Inject
import javax.inject.Named

class RatingAdapter @Inject constructor(
        val navigation: Navigation,
        @Named("avatar") val avatarOptions: RequestOptions,
        @Named("banner") val bannerOptions: RequestOptions)
    : BaseQuickAdapter<Rating, BaseViewHolder>(R.layout.item_rating, ArrayList()) {

    private var mUser: User? = null
    fun setUser(user: User){
        mUser = user
    }

    init {
        setOnItemChildClickListener { _, view, position ->
            when (view.id) {
                R.id.readMoreTextView, R.id.contentTextView, R.id.commentImageView -> {
//                    val post = data[position]
//                    navigateToPostDetail(post) {
//                        changeReaction(it, position)
//                    }
                }
                R.id.userNameTextView, R.id.avatarImageView -> {
                    val userId = data[position].mCreatedUserId?.mId ?: return@setOnItemChildClickListener
                    navigateToMemberDiscussion(userId)
                }
                R.id.moreImageView -> {
                    val mMoreDialog = MoreDialog(mContext, object : MoreDialog.IMore {
                        override fun delete() {

                        }

                        override fun update() {

                        }

                        override fun onReport() {
                            navigation.addFragment(ReportPostActivity.newInstance())
                        }
                    })
                    mMoreDialog.idPost = data[position].mCommentId?.toString()
                    mMoreDialog.show()
                }
            }
        }
    }

        private fun navigateToMemberDiscussion(userId: Int) {
            navigation.addFragment(UserDiscussionScreen.newInstance(userId, Category.Member))
        }

        private fun navigateToPostDetail(post: Post, listener: (Reaction) -> Unit) {
            navigation.addFragment(DetailPostScreen.newInstance(post, DetailPostScreen.Detail.POST, listener), tag = Config.DETAIL_POST_FRAGMENT_TAG)
        }

        private fun navigateToGameDiscussion(game: Game) {
            navigation.addFragment(GameDiscussionScreen.newInstance(game))
        }

        private fun changeReaction(react: Reaction, position: Int) {
            val post = data[position]
            val preReact = Reaction.react(0)
            // count
            when (preReact) {
                Reaction.Love -> {
                    post.mNumFavorite = (post.mNumFavorite?.toInt() ?: 0 - 1).toString()
                }
                Reaction.Like -> {
                    post.mNumLiked = (post.mNumLiked?.toInt() ?: 0 - 1).toString()
                }
                Reaction.Dislike -> {
                    post.mNumDislike = (post.mNumDislike?.toInt() ?: 0 - 1).toString()
                }
            }
            when (react) {
                Reaction.Love -> {
                    post.mNumFavorite = (post.mNumFavorite?.toInt() ?: 0 + 1).toString()
                }
                Reaction.Like -> {
                    post.mNumLiked = (post.mNumLiked?.toInt() ?: 0 + 1).toString()
                }
                Reaction.Dislike -> {
                    post.mNumDislike = (post.mNumDislike?.toInt() ?: 0 + 1).toString()
                }
            }
            notifyItemChanged(position)
        }
    override fun convert(helper: BaseViewHolder?, item: Rating?) {
        val unwrapPost = item ?: return
        val view = helper?.itemView ?: return
        // Set content of Post
        view.contentTextView.text = unwrapPost.mContent?.let { Html.fromHtml(it) }
        view.likeTextView.text = unwrapPost.mNumLiked
        view.dislikeTextView.text = unwrapPost.mNumDislike
        view.commentTextView.text = "0"
        view.loveTextView.text = unwrapPost.mNumFavorite
        view.dateTextView.text = unwrapPost.mCreatedDate
        view.ratingBar.rating = unwrapPost.mRating?.toFloat() ?: 0.0f
        when (Reaction.react(0)) {
            Reaction.Like -> {
                view.likeIcon?.setColorFilter(ContextCompat.getColor(mContext, R.color.color_main_blue))
                view.dislikeIcon?.setColorFilter(ContextCompat.getColor(mContext, R.color.color_react_grey_dark))
                view.loveIcon?.setColorFilter(ContextCompat.getColor(mContext, R.color.color_react_grey_dark))
            }
            Reaction.Love -> {
                view.loveIcon?.setColorFilter(ContextCompat.getColor(mContext, R.color.color_main_blue))
                view.likeIcon?.setColorFilter(ContextCompat.getColor(mContext, R.color.color_react_grey_dark))
                view.dislikeIcon?.setColorFilter(ContextCompat.getColor(mContext, R.color.color_react_grey_dark))
            }
            Reaction.Dislike -> {
                view.dislikeIcon?.setColorFilter(ContextCompat.getColor(mContext, R.color.color_main_blue))
                view.likeIcon?.setColorFilter(ContextCompat.getColor(mContext, R.color.color_react_grey_dark))
                view.loveIcon?.setColorFilter(ContextCompat.getColor(mContext, R.color.color_react_grey_dark))
            }
            Reaction.None -> {
                view.likeIcon?.setColorFilter(ContextCompat.getColor(mContext, R.color.color_react_grey_dark))
                view.dislikeIcon?.setColorFilter(ContextCompat.getColor(mContext, R.color.color_react_grey_dark))
                view.loveIcon?.setColorFilter(ContextCompat.getColor(mContext, R.color.color_react_grey_dark))
            }
        }
        initMe(view)
        initRater(unwrapPost.mCreatedUserId, view)

//        // Set game
//        val game = unwrapPost.game
//        if (game == null) {
//            view.gameConstraintLayout.visibility = View.GONE
//        } else {
//            view.gameConstraintLayout.visibility = View.VISIBLE
//            Glide.with(mContext)
//                    .load(Config.IMG_URL + game.avatar)
//                    .apply(bannerOptions)
//                    .into(view.gameImageView)
//            view.titleGameTextView.text = game.name
//            val followDescription = mContext.getString(R.string.follower_amount_and_post_amount)
//            val followGame = Phrase.from(followDescription)
//                    .put("follower", game.follower?.toString() ?: "0")
//                    .put("post", game.post?.toString() ?: "0")
//                    .format()
//            view.contentGameTextView.text = followGame
//            if (game.follow != true) {
//                // check follow here
//                setFollow(view.followGameTextView)
//            } else {
//                setFollowing(view.followGameTextView)
//            }
//        }

        // initEvent
        helper.addOnClickListener(R.id.readMoreTextView)
                .addOnClickListener(R.id.contentTextView)
                .addOnClickListener(R.id.commentImageView)
                .addOnClickListener(R.id.moreImageView)
                .addOnClickListener(R.id.userNameTextView)
                .addOnClickListener(R.id.avatarImageView)
                .addOnClickListener(R.id.gameImageView)
                .addOnClickListener(R.id.titleGameTextView)
    }

    private fun initMe(view: View){
        view.userNameTextView.text = mUser?.mName
        Glide.with(mContext).load(mUser?.mAvatar?.toImage())
                .apply(avatarOptions)
                .into(view.avatarImageView)

    }

    private fun initRater(user: User?, view: View){
        Glide.with(mContext)
                .load(user?.mAvatar?.toImage())
                .apply(avatarOptions)
                .into(view.raterImageView)
        view.nameRaterTextView?.text = user?.mName

        val type = when(user?.mRoleId){
            "5" -> mContext.getString(R.string.nha_phat_hanh)
            else -> mContext.getString(R.string.game_thu)
        }
        view.typeTextView?.text = type
    }
}