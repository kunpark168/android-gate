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
import com.anhtam.gate9.share.view.MoreDialog
import com.anhtam.gate9.storage.StorageManager
import com.anhtam.gate9.utils.convertInt
import com.anhtam.gate9.v2.discussion.game.GameDiscussionScreen
import com.anhtam.gate9.v2.discussion.user.UserDiscussionScreen
import com.anhtam.gate9.v2.auth.login.LoginScreen
import com.anhtam.gate9.v2.detail_post.DetailPostScreen
import com.anhtam.gate9.v2.report.post.ReportPostActivity
import com.anhtam.gate9.vo.Reaction
import com.anhtam.gate9.vo.model.Category
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

    init {
        setOnItemChildClickListener { _, view, position ->
            when(view.id){
                R.id.readMoreTextView, R.id.contentTextView, R.id.commentImageView -> {
                    val post = data[position]
                    navigateToPostDetail(post){
                        changeReaction(it, position)
                    }
                }
                R.id.userNameTextView, R.id.avatarImageView -> {
                    val userId = data[position].user?.mId ?: return@setOnItemChildClickListener
                    navigateToMemberDiscussion(userId)
                }
                R.id.gameImageView, R.id.titleGameTextView -> {
                    navigateToGameDiscussion()
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
                    mMoreDialog.idPost = data[position].commentId?.toString()
                    mMoreDialog.show()
                }
                R.id.followGameTextView -> {
                    if (checkLogin()) {
                        if(view.followGameTextView?.text == mContext.getString(R.string.follow)) {
                            setFollowing(view.followGameTextView)
                            //sending request
                        } else {
                            setFollow(view.followGameTextView)
                            //sending request
                        }
                    } else {
                        navigation.addFragment(LoginScreen.newInstance(false))
                    }
                }
            }
        }
    }

    override fun convert(helper: BaseViewHolder?, item: Post?) {
        val unwrapPost = item ?: return
        val view = helper?.itemView ?: return
        // Set content of Post
        view.contentTextView.text = unwrapPost.content?.let { Html.fromHtml(it) }
        view.likeTextView.text = unwrapPost.totalLike
        view.dislikeTextView.text = unwrapPost.totalDislike
        view.commentTextView.text = unwrapPost.totalReply
        view.pointTextView.text = unwrapPost.point
        view.loveTextView.text = unwrapPost.totalLove
        view.dateTextView.text = unwrapPost.createdDate
        val react = Reaction.react(unwrapPost.like?.convertInt()?:0)
        when(react){
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
        val user = unwrapPost.user ?: return
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
                    .put("follower", game.follower ?: "0")
                    .put("post", game.post.toString())
                    .format()
            view.contentGameTextView.text = followGame
            if (game.follow != true) {
                // check follow here
                setFollow(view.followGameTextView)
            } else {
                setFollowing(view.followGameTextView)
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

    private fun checkLogin() : Boolean {
        val accessToken = StorageManager.getAccessToken()
        return accessToken.isNotEmpty()
    }

    private fun navigateToMemberDiscussion(userId: Int) {
        navigation.addFragment(UserDiscussionScreen.newInstance(userId, Category.Member))
    }

    private fun navigateToPostDetail(post: Post, listener: (Reaction)->Unit) {
        navigation.addFragment(DetailPostScreen.newInstance(post, DetailPostScreen.Detail.POST, listener), tag = Config.DETAIL_POST_FRAGMENT_TAG)
    }

    private fun navigateToGameDiscussion(){
        navigation.addFragment(GameDiscussionScreen.newInstance("", "1"))
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

    private fun changeReaction(react: Reaction, position: Int){
        val post = data[position]
        val preReact = Reaction.react(data[position].like?.convertInt() ?: 0)
        post.like = Reaction.value(react).toString()
        // count
        when(preReact){
            Reaction.Love -> {
                post.totalLove = (post.totalLove.toInt() - 1).toString()
            }
            Reaction.Like -> {
                post.totalLike = (post.totalLike.toInt() - 1).toString()
            }
            Reaction.Dislike -> {
                post.totalDislike = (post.totalDislike.toInt() - 1).toString()
            }
        }
        when(react){
            Reaction.Love -> {
                post.totalLove = (post.totalLove.toInt() + 1).toString()
            }
            Reaction.Like -> {
                post.totalLike = (post.totalLike.toInt() + 1).toString()
            }
            Reaction.Dislike -> {
                post.totalDislike = (post.totalDislike.toInt() + 1).toString()
            }
        }
        notifyItemChanged(position)
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