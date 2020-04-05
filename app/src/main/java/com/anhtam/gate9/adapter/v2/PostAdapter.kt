package com.anhtam.gate9.adapter.v2

import android.text.Html
import android.view.View
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.anhtam.domain.v2.Post
import com.anhtam.domain.v2.protocol.User
import com.anhtam.gate9.R
import com.anhtam.gate9.config.Config
import com.anhtam.gate9.navigation.Navigation
import com.anhtam.gate9.utils.convertInt
import com.anhtam.gate9.vo.Reaction
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.squareup.phrase.Phrase
import kotlinx.android.synthetic.main.shared_post_item_layout.view.*
import javax.inject.Inject
import javax.inject.Named

class PostAdapter @Inject constructor(
        val navigation: Navigation,
        @Named("avatar") val avatarOptions: RequestOptions,
        @Named("banner") val bannerOptions: RequestOptions)
    : BaseQuickAdapter<Post, BaseViewHolder>(R.layout.shared_post_item_layout, ArrayList()) {

    override fun convert(helper: BaseViewHolder?, item: Post?) {
        val unwrapPost = item ?: return
        val view = helper?.itemView ?: return
        // Set content of Post
        view.contentTextView.text = unwrapPost.content?.let { Html.fromHtml(it) }
        view.likeTextView.text = unwrapPost.totalLike?.toString() ?: "0"
        view.dislikeTextView.text = unwrapPost.totalDislike?.toString() ?: "0"
        view.commentTextView.text = unwrapPost.totalReply?.toString() ?: "0"
        view.pointTextView.text = unwrapPost.point?.toString() ?: "0"
        view.loveTextView.text = unwrapPost.totalLove?.toString() ?: "0"
        view.dateTextView.text = unwrapPost.createdDate
        when(Reaction.react(unwrapPost.like?.convertInt()?:0)){
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
            Reaction.Dislike ->{
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

        // Set user
        val user = unwrapPost.user ?:  unwrapPost.createdUser ?: return
        view.userNameTextView.text = user.mName
        //logic image
        val avatar = if(user.mAvatar?.startsWith("http") == true) {
                    user.mAvatar
                } else {
            Config.IMG_URL + user.mAvatar
        }
        Glide.with(mContext).load(avatar)
                .apply(avatarOptions)
                .into(view.avatarImageView)

        // Set game
        val game = unwrapPost.game
        if (game == null) {
            view.gameConstraintLayout.visibility = View.GONE
        } else {
            view.gameConstraintLayout.visibility = View.VISIBLE
            Glide.with(mContext)
                    .load(Config.IMG_URL + game.avatar)
                    .apply(bannerOptions)
                    .into(view.gameImageView)
            view.titleGameTextView.text = game.name
            val followDescription = mContext.getString(R.string.follower_amount_and_post_amount)
            val followGame = Phrase.from(followDescription)
                    .put("follower", game.follower?.toString() ?: "0")
                    .put("post", game.post?.toString() ?: "0")
                    .format()
            view.contentGameTextView.text = followGame
            val mIsFollowing = game.follow ?: false
            if (mIsFollowing) {
                // check follow here
                setFollowing(view.followGameTextView)
            } else {
                setFollow(view.followGameTextView)
            }
        }

        // photos
        val photos = unwrapPost.photo
        initPhoto(photos, view.rvPhotos, user)

        // initEvent
        helper.addOnClickListener(R.id.readMoreTextView)
                .addOnClickListener(R.id.contentTextView)
                .addOnClickListener(R.id.commentImageView)
                .addOnClickListener(R.id.moreImageView)
                .addOnClickListener(R.id.userNameTextView)
                .addOnClickListener(R.id.avatarImageView)
                .addOnClickListener(R.id.gameImageView)
                .addOnClickListener(R.id.titleGameTextView)
                .addOnClickListener(R.id.followGameTextView)
    }

    private fun setFollow(tvFollowGame: TextView) {
        tvFollowGame.text = mContext.getString(R.string.follow)
        tvFollowGame.setBackgroundResource(R.drawable.bg_follow)
        tvFollowGame.setTextColor(ContextCompat.getColor(mContext, R.color.text_color_red_light))
    }

    private fun setFollowing(tvFollowGame: TextView) {
        tvFollowGame.text = mContext.getString(R.string.following)
        tvFollowGame.setBackgroundResource(R.drawable.bg_following)
        tvFollowGame.setTextColor(ContextCompat.getColor(mContext, R.color.text_color_blue))
    }

    private fun initPhoto(photo: String?, rv: RecyclerView, user: User){
        if(photo.isNullOrEmpty() || photo.length == 2){//[]
            rv.visibility = View.GONE
            return
        }
        rv.visibility = View.VISIBLE
        rv.isNestedScrollingEnabled = false
//        val isFormat = "[[.+]]".toRegex().matches(photos) TODO Regex
        val isFormat = (photo.startsWith('[') && photo.endsWith(']'))
        val stringConcat = if (!isFormat) {
            photo
        } else {
            photo.substring(1, photo.length - 1)
        }
        val adapter = PhotoAdapter(navigation, bannerOptions)
        adapter.user = user
        val spanCount = adapter.setPhoto(stringConcat)
        rv.layoutManager = GridLayoutManager(mContext, spanCount)
        rv.adapter = adapter
    }
}