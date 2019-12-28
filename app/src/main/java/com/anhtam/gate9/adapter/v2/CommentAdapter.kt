package com.anhtam.gate9.adapter.v2

import android.content.Context
import android.text.Html
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import com.anhtam.gate9.adapter.navigator.IPostNavigator
import com.anhtam.domain.v2.PostEntity
import com.anhtam.gate9.R
import com.anhtam.gate9.config.Config
import com.anhtam.gate9.navigation.Navigation
import com.anhtam.gate9.share.view.MoreDialog
import com.anhtam.gate9.storage.StorageManager
import com.anhtam.gate9.ui.base.BaseActivity
import com.anhtam.gate9.ui.discussion.user.UserDiscussionActivity
import com.anhtam.gate9.ui.report.post.ReportPostActivity
import com.anhtam.gate9.v2.auth.login.LoginScreen
import com.anhtam.gate9.v2.post.DetailPostScreen
import com.anhtam.gate9.vo.model.Category
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.chad.library.adapter.base.entity.MultiItemEntity
import com.squareup.phrase.Phrase
import kotlinx.android.synthetic.main.photo_n_item_layout.view.*
import kotlinx.android.synthetic.main.shared_post_item_layout.view.*


class CommentAdapter(private val navigation: Navigation?, private val listener: (PostEntity,Int)->Unit)
    : BaseQuickAdapter<PostEntity, BaseViewHolder>(R.layout.shared_post_item_layout, ArrayList()), IPostNavigator {

    private var more = 0

    override fun convert(helper: BaseViewHolder?, item: PostEntity?) {
        val unwrapPost = item ?: return
        val view = helper?.itemView ?: return
        when(unwrapPost.like) {
            "like" -> item.mReactionId = 1
            "dislike" -> item.mReactionId = 2
            "love" -> item.mReactionId = 3
            else -> item.mReactionId = 0
        }
        // Set content of Post
        view.contentTextView.text = unwrapPost.content?.let { Html.fromHtml(it) }
        view.likeTextView.text = unwrapPost.totalLike
        view.dislikeTextView.text = unwrapPost.totalDislike
        view.commentTextView.text = unwrapPost.totalReply
        view.loveTextView.text = unwrapPost.totalLove
        view.dateTextView.text = unwrapPost.createdDate

        // Set user
        val user = unwrapPost.user ?: return
        view.userNameTextView.text = user.mName
        //logic image
        val avatar = if(user.mAvatar?.startsWith("http") == true) {
                    user.mAvatar
                } else {
            Config.IMG_URL + user.mAvatar
        }
        Glide.with(mContext)
                .applyDefaultRequestOptions(
                        RequestOptions()
                                .placeholder(R.drawable.img_avatar_holder)
                                .error(R.drawable.img_avatar_holder)
                ).load(avatar)
                .into(view.avatarImageView)

        // Set game
        val game = unwrapPost.game
        if (game == null) {
            view.gameConstraintLayout.visibility = View.GONE
        } else {
            view.gameConstraintLayout.visibility = View.VISIBLE
            Glide.with(mContext)
                    .applyDefaultRequestOptions(
                            RequestOptions()
                                    .placeholder(R.drawable.img_holder_banner)
                                    .error(R.drawable.img_holder_banner)
                    ).load(Config.IMG_URL + game.avatar)
                    .into(view.gameImageView)
            view.titleGameTextView.text = game.name
            val followDescription = mContext.getString(R.string.follower_amount_and_post_amount)
            val followGame = Phrase.from(followDescription)
                    .put("follower", game.follower ?: "0")
                    .put("post", game.post.toString())
                    .format()
            view.contentGameTextView.text = followGame
            if (game.follow == "false") {
                // check follow here
                setFollow(view.followGameTextView)
            } else {
                setFollowing(view.followGameTextView)
            }
        }

        // photos
        val photos = unwrapPost.photo?.subSequence(1,unwrapPost.photo!!.length - 1)
        if (!photos.isNullOrEmpty()) {
            view.rvPhotos.visibility = View.VISIBLE
            val listPhotos = photos.split(",").toMutableList().map { it.trim() }
            val photoEntity = listPhotos.map { PhotoEntity(when(listPhotos.size) {
                1 -> PhotoEntity.GRID_1
                in 2..4 -> PhotoEntity.GRID_4
                else -> PhotoEntity.GRID_N
            }, it)}
            val adapter = PhotoAdapter()
            adapter.setSpanSizeLookup { _, position ->
                adapter.data[position].getSpanSize()
            }
            if (photoEntity.size > 4){
                more = photoEntity.size - 4
                val morePhotoList = arrayListOf<PhotoEntity>()
                for (index in 0..3) {
                    if (index == 3) {
                        morePhotoList.add(PhotoEntity(PhotoEntity.GRID_N, photoEntity[index].photo))
                    } else {
                        morePhotoList.add(PhotoEntity(PhotoEntity.GRID_4, photoEntity[index].photo))
                    }
                }
                adapter.setNewData(morePhotoList)
            } else {
                adapter.setNewData(photoEntity)
            }
            if (photoEntity.size == 1){
                view.rvPhotos.layoutManager = GridLayoutManager(mContext, 1)
            } else {
                view.rvPhotos.layoutManager = GridLayoutManager(mContext, 2)
            }
            view.rvPhotos.adapter = adapter
        }

        // initEvent
        // user discussion
        user.mId?.let {id ->
            view.userNameTextView.setOnClickListener { navigateToMemberDiscussion(id) }
            view.avatarImageView.setOnClickListener { navigateToMemberDiscussion(id) }
        }
        // post detail
        view.readMoreTextView.setOnClickListener { navigateToPostDetail(mContext, unwrapPost) }
        view.contentTextView.setOnClickListener { navigateToPostDetail(mContext, unwrapPost) }
        view.commentImageView.setOnClickListener { navigateToPostDetail(mContext, unwrapPost) }
        view.moreImageView.setOnClickListener {
            val mMoreDialog = MoreDialog(mContext, object : MoreDialog.IMore {
                override fun onreport() {
                    ReportPostActivity.start(mContext as BaseActivity)
                }
            })
            mMoreDialog.idPost = unwrapPost.commentId?.toString()
            mMoreDialog.show()
        }
        view.likeLayout.setOnClickListener {
            if(checkLogin()){
                // change icon color and send request
                reaction(view, 1, unwrapPost)
            } else {
                navigation?.addFragment(LoginScreen.newInstance())
            }
        }
        view.loveLayout.setOnClickListener {
            if(checkLogin()){
                // change icon color and send request
                reaction(view, 3, unwrapPost)
            } else {
                navigation?.addFragment(LoginScreen.newInstance())
            }
        }
        view.dislikeLayout.setOnClickListener {
            if(checkLogin()){
                // change icon color and send request
                reaction(view, 2, unwrapPost)
            } else {
                navigation?.addFragment(LoginScreen.newInstance())
            }
        }
        view.commentIcon.setOnClickListener {
            if(checkLogin()){
                // change icon color and send request
                navigateToPostDetail(mContext, unwrapPost)
            } else {
                navigation?.addFragment(LoginScreen.newInstance())
            }
        }
        view.followGameTextView.setOnClickListener {
            if (checkLogin()) {
                if(view.followGameTextView?.text == mContext.getString(R.string.follow)) {
                    setFollowing(view.followGameTextView)
                    //sending request
                } else {
                    setFollow(view.followGameTextView)
                    //sending request
                }
            } else {
                navigation?.addFragment(LoginScreen.newInstance())
            }
        }
    }

    private fun checkLogin() : Boolean {
        val accessToken = StorageManager.getAccessToken()
        return accessToken.isNotEmpty()
    }

    override fun navigateToMemberDiscussion(userId: Int) {
        navigation?.addFragment(UserDiscussionActivity.newInstance(userId, Category.Member))
    }

    override fun navigateToPostDetail(context: Context?, postEntity: PostEntity) {
        context?:return
        navigation?.addFragment(DetailPostScreen.newInstance(postEntity, DetailPostScreen.Detail.POST))
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

    private fun reaction(view: View, type: Int, data: PostEntity) {
        when (data.mReactionId) {
            0 -> {//change icon color and increase number}
            }
            1 -> {
                // change icon like
                val like = view.likeTextView.text.toString().toInt()  - 1
                view.likeTextView.text = like.toString()

            }
            2 -> {
                // change icon dislike
                val dislike = view.dislikeTextView.text.toString().toInt()  - 1
                view.dislikeTextView.text = dislike.toString()
            }
            3 -> {
                // change icon love
                val love = view.loveTextView.text.toString().toInt()  - 1
                view.loveTextView.text = love.toString()
            }
        }
        if(data.mReactionId == type) {
            data.mReactionId = 0
            return
        }
        reactionRequest(view, type, data)
    }

    private fun reactionRequest(view: View, type: Int, data: PostEntity) {
        data.mReactionId = type
        when(type) {
            1 -> {
                val like = view.likeTextView.text.toString().toInt()  + 1
                view.likeTextView.text = like.toString()
            }
            2 -> {
                val dislike = view.dislikeTextView.text.toString().toInt()  + 1
                view.dislikeTextView.text = dislike.toString()
            }
            3 -> {
                val love = view.loveTextView.text.toString().toInt()  + 1
                view.loveTextView.text = love.toString()
            }
        }
        // request api
        listener(data, type)

    }

    inner class PhotoAdapter : BaseMultiItemQuickAdapter<PhotoEntity, BaseViewHolder>(mutableListOf()){
        init {
            addItemType(PhotoEntity.GRID_1, R.layout.photo_1_item_layout)
            addItemType(PhotoEntity.GRID_4, R.layout.photo_4_item_layout)
            addItemType(PhotoEntity.GRID_N, R.layout.photo_n_item_layout)
        }

        override fun convert(helper: BaseViewHolder?, item: PhotoEntity?) {
            val photo = item?.photo ?: return
            val view = helper?.itemView ?: return
            val imgPhoto = view.findViewById<ImageView>(R.id.imgPhoto)
            Glide.with(mContext)
                    .applyDefaultRequestOptions(
                            RequestOptions()
                                    .placeholder(R.drawable.img_holder_banner)
                                    .error(R.drawable.img_holder_banner)
                                    .centerCrop()
                    ).load(Config.IMG_URL + photo)
                    .into(imgPhoto)
            if (item.type == PhotoEntity.GRID_N) {
                view.tvMore.text = "+".plus(more.toString())
            }
        }
    }

    class PhotoEntity(val type: Int, val photo: String) :MultiItemEntity{
        companion object {
            const val GRID_1 = 1
            const val GRID_4 = 2
            const val GRID_N = 3
        }
        override fun getItemType() = type

        fun getSpanSize():Int = when(type) {
            GRID_1 -> 1
            else -> 1
        }
    }
}