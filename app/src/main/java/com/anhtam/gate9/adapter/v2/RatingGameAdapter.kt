package com.anhtam.gate9.adapter.v2

import android.text.Html
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.AppCompatRatingBar
import androidx.core.content.ContextCompat
import com.anhtam.domain.v2.Post
import com.anhtam.domain.v2.protocol.User
import com.anhtam.gate9.R
import com.anhtam.gate9.config.Config
import com.anhtam.gate9.navigation.Navigation
import com.anhtam.gate9.restful.BackgroundTasks
import com.anhtam.gate9.share.view.MoreDialog
import com.anhtam.gate9.storage.StorageManager
import com.anhtam.gate9.utils.toImage
import com.anhtam.gate9.v2.auth.login.LoginScreen
import com.anhtam.gate9.v2.nph_detail.DetailNPHFragment
import com.anhtam.gate9.v2.post_detail.DetailPostScreen
import com.anhtam.gate9.v2.report.post.ReportPostActivity
import com.anhtam.gate9.v2.user_detail.DetailUserFragment
import com.anhtam.gate9.vo.Rating
import com.anhtam.gate9.vo.Reaction
import com.anhtam.gate9.vo.model.Category
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import javax.inject.Inject
import javax.inject.Named

class RatingGameAdapter @Inject constructor(
        val navigation: Navigation,
        @Named("avatar") val avatarOptions: RequestOptions)
    : BaseQuickAdapter<Rating, BaseViewHolder>(R.layout.danh_gia_game_item_layout, mutableListOf()) {

    init {
        setOnItemChildClickListener { _, view, position ->
            when (view.id) {
                R.id.readMoreTextView, R.id.contentTextView, R.id.commentImageView -> {

                }
                R.id.userNameTextView, R.id.avatarImageView -> {
                    val user = data[position]?.mUser ?: return@setOnItemChildClickListener
                    navigateToMemberDiscussion(user)
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
                R.id.tvFollow -> {
                    val tvFollow = view as? TextView
                    val user = data[position]?.mUser
                    val idUser = user?.mId ?: return@setOnItemChildClickListener
                    val role = when(user.mRoleId){
                        5 -> Category.Publisher
                        else -> Category.Member
                    }
                    if (checkLogin()) {
                        BackgroundTasks.postUserFollow(idUser, role.id)
                        if(tvFollow?.text == mContext.getString(R.string.follow)) {
                            setFollowing(tvFollow)
                            //sending request
                        } else {
                            setFollow(tvFollow)
                            //sending request
                        }
                    } else {
                        navigation.addFragment(LoginScreen.newInstance(false))
                    }
                }
            }
        }
    }



    override fun convert(helper: BaseViewHolder?, item: Rating?) {
        val unwrapPost = item ?: return
        if (helper == null) return
        // Set content of Post
        helper.getView<TextView>(R.id.contentTextView)?.text = unwrapPost.mContent?.let { Html.fromHtml(it) }
        helper.getView<TextView>(R.id.likeTextView)?.text = unwrapPost.mNumLiked
        helper.getView<TextView>(R.id.dislikeTextView)?.text = unwrapPost.mNumDislike
        helper.getView<TextView>(R.id.commentTextView)?.text = "0"
        helper.getView<TextView>(R.id.loveTextView)?.text = unwrapPost.mNumFavorite
        helper.getView<TextView>(R.id.dateTextView)?.text = unwrapPost.mCreatedDate
        helper.getView<AppCompatRatingBar>(R.id.ratingBar).rating = unwrapPost.mRating?.toFloat() ?: 0.0f
        when (Reaction.react(0)) {
            Reaction.Like -> {
                helper.getView<ImageView>(R.id.likeIcon)?.setColorFilter(ContextCompat.getColor(mContext, R.color.color_main_blue))
                helper.getView<ImageView>(R.id.dislikeIcon)?.setColorFilter(ContextCompat.getColor(mContext, R.color.color_react_grey_dark))
                helper.getView<ImageView>(R.id.loveIcon)?.setColorFilter(ContextCompat.getColor(mContext, R.color.color_react_grey_dark))
            }
            Reaction.Love -> {
                helper.getView<ImageView>(R.id.loveIcon)?.setColorFilter(ContextCompat.getColor(mContext, R.color.color_main_blue))
                helper.getView<ImageView>(R.id.likeIcon)?.setColorFilter(ContextCompat.getColor(mContext, R.color.color_react_grey_dark))
                helper.getView<ImageView>(R.id.dislikeIcon)?.setColorFilter(ContextCompat.getColor(mContext, R.color.color_react_grey_dark))
            }
            Reaction.Dislike -> {
                helper.getView<ImageView>(R.id.dislikeIcon)?.setColorFilter(ContextCompat.getColor(mContext, R.color.color_main_blue))
                helper.getView<ImageView>(R.id.likeIcon)?.setColorFilter(ContextCompat.getColor(mContext, R.color.color_react_grey_dark))
                helper.getView<ImageView>(R.id.loveIcon)?.setColorFilter(ContextCompat.getColor(mContext, R.color.color_react_grey_dark))
            }
            Reaction.None -> {
                helper.getView<ImageView>(R.id.likeIcon)?.setColorFilter(ContextCompat.getColor(mContext, R.color.color_react_grey_dark))
                helper.getView<ImageView>(R.id.dislikeIcon)?.setColorFilter(ContextCompat.getColor(mContext, R.color.color_react_grey_dark))
                helper.getView<ImageView>(R.id.loveIcon)?.setColorFilter(ContextCompat.getColor(mContext, R.color.color_react_grey_dark))
            }
        }

        val user = unwrapPost.mUser
        helper.getView<TextView>(R.id.userNameTextView)?.text = user?.mName
        helper.getView<ImageView>(R.id.avatarImageView)?.run{
            Glide.with(mContext).load(user?.mAvatar?.toImage())
                    .apply(avatarOptions)
                    .into(this)
        }

        // initEvent
        helper.addOnClickListener(R.id.readMoreTextView)
                .addOnClickListener(R.id.contentTextView)
                .addOnClickListener(R.id.commentImageView)
                .addOnClickListener(R.id.moreImageView)
                .addOnClickListener(R.id.userNameTextView)
                .addOnClickListener(R.id.nameTextView)
    }

    private fun setFollow(tvFollowGame: TextView?) {
        tvFollowGame?.text = mContext.getString(R.string.follow)
        tvFollowGame?.setBackgroundResource(R.drawable.bg_follow)
        tvFollowGame?.setTextColor(ContextCompat.getColor(mContext, R.color.text_color_red_light))
    }

    private fun setFollowing(tvFollowGame: TextView?) {
        tvFollowGame?.text = mContext.getString(R.string.following)
        tvFollowGame?.setBackgroundResource(R.drawable.bg_following)
        tvFollowGame?.setTextColor(ContextCompat.getColor(mContext, R.color.text_color_blue))
    }
    private fun checkLogin() : Boolean {
        val accessToken = StorageManager.getAccessToken()
        return accessToken.isNotEmpty()
    }

    private fun navigateToMemberDiscussion(user: User) {
        val roleId = user.mRoleId ?: return
        val id = user.mId ?: return
        if (roleId != 5){
            navigation.addFragment(DetailUserFragment.newInstance(id))
        } else {
            navigation.addFragment(DetailNPHFragment.newInstance(id))
        }
    }

    private fun navigateToPostDetail(post: Post, listener: (Reaction) -> Unit) {
        navigation.addFragment(DetailPostScreen.newInstance(post, DetailPostScreen.Detail.POST, listener), tag = Config.DETAIL_POST_FRAGMENT_TAG)
    }

    private fun changeReaction(react: Reaction, position: Int) {
        val post = data[position]
        // count
        when (Reaction.react(0)) {
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
}