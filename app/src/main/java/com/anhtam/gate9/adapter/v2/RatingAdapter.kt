package com.anhtam.gate9.adapter.v2

import android.text.Html
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.AppCompatRatingBar
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import com.anhtam.domain.v2.Post
import com.anhtam.domain.v2.protocol.Game
import com.anhtam.domain.v2.protocol.User
import com.anhtam.gate9.R
import com.anhtam.gate9.config.Config
import com.anhtam.gate9.navigation.Navigation
import com.anhtam.gate9.restful.BackgroundTasks
import com.anhtam.gate9.share.view.MoreDialog
import com.anhtam.gate9.storage.StorageManager
import com.anhtam.gate9.utils.toImage
import com.anhtam.gate9.v2.auth.login.LoginScreen
import com.anhtam.gate9.v2.game_detail.DetailGameFragment
import com.anhtam.gate9.v2.nph_detail.DetailNPHFragment
import com.anhtam.gate9.v2.post_detail.DetailPostScreen
import com.anhtam.gate9.v2.report.post.ReportPostActivity
import com.anhtam.gate9.v2.user_detail.DetailUserFragment
import com.anhtam.gate9.vo.Rating
import com.anhtam.gate9.vo.Reaction
import com.anhtam.gate9.vo.model.Category
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import javax.inject.Inject
import javax.inject.Named

class RatingAdapter @Inject constructor(
        val navigation: Navigation,
        @Named("avatar") val avatarOptions: RequestOptions,
        @Named("banner") val bannerOptions: RequestOptions)
    : BaseMultiItemQuickAdapter<Rating, BaseViewHolder>(ArrayList()) {

    private var mUser: User? = null
    private var mCategory: Int = 1
    fun setUser(user: User) {
        mUser = user
    }

    fun setCategory(category: Int) {
        mCategory = category
    }

    init {
        addItemType(1, R.layout.user_rating_item_layout)
        addItemType(2, R.layout.game_rating_item_layout)

        setOnItemChildClickListener { _, view, position ->
            when (view.id) {
                R.id.readMoreTextView, R.id.contentTextView, R.id.commentImageView -> {
//                    val post = data[position]
//                    navigateToPostDetail(post) {
//                        changeReaction(it, position)
//                    }
                }
                R.id.userNameTextView, R.id.avatarImageView -> {
                    val user = data[position].mCreatedUser ?: return@setOnItemChildClickListener
                    navigateToMemberDiscussion(user)
                }
                R.id.raterImageView, R.id.nameRaterTextView -> {
                    val user = data[position].mUser ?: return@setOnItemChildClickListener
                    navigateToMemberDiscussion(user)
                }
                R.id.gameImageView, R.id.titleGameTextView -> {
                    navigateToGameDiscussion(data[position].mGame ?: return@setOnItemChildClickListener)
                }
                R.id.moreImageView -> {
                    val mMoreDialog = MoreDialog.newInstance(mContext)
                    mMoreDialog.show()
                }
                R.id.tvFollow -> {
                    val tvFollow = view as? TextView
                    val user = if(mUser?.mId != data[position].mCreatedUser?.mId){
                        data[position].mCreatedUser ?: return@setOnItemChildClickListener
                    } else {
                        data[position].mUser ?: return@setOnItemChildClickListener
                    }
                    val idUser = user.mId ?: return@setOnItemChildClickListener
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
        val ratingData = item ?: return
        val view = helper?.itemView ?: return
        // Set content of Post
        helper.getView<TextView>(R.id.contentTextView)?.text = ratingData.mContent?.let { Html.fromHtml(it) }
        helper.getView<TextView>(R.id.likeTextView)?.text = ratingData.mNumLiked
        helper.getView<TextView>(R.id.dislikeTextView)?.text = ratingData.mNumDislike
        helper.getView<TextView>(R.id.commentTextView)?.text = "0"
        helper.getView<TextView>(R.id.loveTextView)?.text = ratingData.mNumFavorite
        helper.getView<TextView>(R.id.dateTextView)?.text = ratingData.mCreatedDate
        helper.getView<AppCompatRatingBar>(R.id.ratingBar).rating = ratingData.mRating?.toFloat() ?: 0.0f
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
        when (mCategory) {
            1 -> {
                initView(ratingData.mCreatedUser, helper)
                helper.getView<ConstraintLayout>(R.id.subLayout)?.visibility = View.GONE
            }
            2 -> {
                initView(ratingData.mCreatedUser, helper)
                initGame(ratingData.mGame, helper)
                helper.addOnClickListener(R.id.gameImageView)
                        .addOnClickListener(R.id.titleGameTextView)
            }
            3, 4 -> {
                initView(ratingData.mCreatedUser, helper)
                initSubView(ratingData.mUser, helper)
                helper.getView<ConstraintLayout>(R.id.subLayout)?.visibility = View.VISIBLE
                helper.addOnClickListener(R.id.raterImageView)
                        .addOnClickListener(R.id.tvFollow)
                        .addOnClickListener(R.id.nameRaterTextView)
            }
            else -> {}
        }

        // initEvent
        helper.addOnClickListener(R.id.readMoreTextView)
                .addOnClickListener(R.id.contentTextView)
                .addOnClickListener(R.id.commentImageView)
                .addOnClickListener(R.id.moreImageView)
                .addOnClickListener(R.id.userNameTextView)
                .addOnClickListener(R.id.avatarImageView)
    }

    private fun initView(user: User?, helper: BaseViewHolder) {
        helper.getView<TextView>(R.id.userNameTextView)?.text = user?.mName
        helper.getView<ImageView>(R.id.avatarImageView)?.run{
            Glide.with(mContext).load(user?.mAvatar?.toImage())
                    .apply(avatarOptions)
                    .into(this)
        }

    }

    private fun initSubView(user: User?, helper: BaseViewHolder) {
        helper.getView<ImageView>(R.id.raterImageView)
                .run {
                    Glide.with(mContext)
                            .load(user?.mAvatar?.toImage())
                            .apply(avatarOptions)
                            .into(this)
                }
        helper.getView<TextView>(R.id.nameRaterTextView)?.text = user?.mName

        val type = when (user?.mRoleId) {
            5 -> mContext.getString(R.string.nha_phat_hanh)
            else -> mContext.getString(R.string.game_thu)
        }
        helper.getView<TextView>(R.id.typeTextView)?.text = type
        val tvFollow = helper.getView<TextView>(R.id.tvFollow)
        if (user?.mIsFollowing != true) {
            // check follow here
            setFollow(tvFollow)
        } else {
            setFollowing(tvFollow)
        }
    }

    private fun initGame(game: Game?,helper: BaseViewHolder){
        helper.getView<ImageView>(R.id.gameImageView)
                .run {
                    Glide.with(mContext)
                            .load(game?.avatar?.toImage())
                            .apply(bannerOptions)
                            .into(this)
                }

        val titleTextView = helper.getView<TextView>(R.id.titleTextView)
        val typeTextView = helper.getView<TextView>(R.id.typeTextView)
        titleTextView?.text = game?.name
        typeTextView?.text = game?.gameType?.name
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
        val id = post.commentId ?: return
        navigation.addFragment(DetailPostScreen.newInstance(id, DetailPostScreen.Detail.POST, listener), tag = Config.DETAIL_POST_FRAGMENT_TAG)
    }

    private fun navigateToGameDiscussion(game: Game) {
        val id = game.gameId ?: return
        navigation.addFragment(DetailGameFragment.newInstance(id))
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