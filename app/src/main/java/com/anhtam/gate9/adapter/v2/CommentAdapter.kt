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
import com.anhtam.domain.v2.User
import com.anhtam.gate9.R
import com.anhtam.gate9.config.Config
import com.anhtam.gate9.navigation.Navigation
import com.anhtam.gate9.share.view.MoreDialog
import com.anhtam.gate9.storage.StorageManager
import com.anhtam.gate9.v2.discussion.game.GameDiscussionScreen
import com.anhtam.gate9.v2.discussion.user.UserDiscussionScreen
import com.anhtam.gate9.v2.auth.login.LoginScreen
import com.anhtam.gate9.v2.gallery.GalleryScreen
import com.anhtam.gate9.v2.detail_post.DetailPostScreen
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
import javax.inject.Inject
import javax.inject.Named

class CommentAdapter @Inject constructor(
        val navigation: Navigation?,
        @Named("avatar") val avatarOptions: RequestOptions,
        @Named("banner") val bannerOptions: RequestOptions)
    : BaseQuickAdapter<PostEntity, BaseViewHolder>(R.layout.shared_post_item_layout, ArrayList()), IPostNavigator {

    private var more = 0

    override fun convert(helper: BaseViewHolder?, item: PostEntity?) {
        val unwrapPost = item ?: return
        val view = helper?.itemView ?: return
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
            adapter.setUser(user)
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
//                    ReportPostActivity.start(mContext as BaseActivity)
                }
            })
            mMoreDialog.idPost = unwrapPost.commentId?.toString()
            mMoreDialog.show()
        }

        // game
        view.gameImageView?.setOnClickListener { navigation?.addFragment(GameDiscussionScreen.newInstance("", "0")) }
        view.titleGameTextView?.setOnClickListener { navigation?.addFragment(GameDiscussionScreen.newInstance("", "0")) }
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
        navigation?.addFragment(UserDiscussionScreen.newInstance(userId, Category.Member))
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

    inner class PhotoAdapter : BaseMultiItemQuickAdapter<PhotoEntity, BaseViewHolder>(mutableListOf()){

        private lateinit var mUser: User

        init {
            addItemType(PhotoEntity.GRID_1, R.layout.photo_1_item_layout)
            addItemType(PhotoEntity.GRID_4, R.layout.photo_4_item_layout)
            addItemType(PhotoEntity.GRID_N, R.layout.photo_n_item_layout)
        }

        fun setUser(user: User){
            mUser = user
        }

        override fun convert(helper: BaseViewHolder?, item: PhotoEntity?) {
            val photo = item?.photo ?: return
            val view = helper?.itemView ?: return
            val imgPhoto = view.findViewById<ImageView>(R.id.imgPhoto)
            imgPhoto.setOnClickListener {
                navigation?.addFragment(GalleryScreen.newInstance(data.map { it.photo}, mUser))
            }
            Glide.with(mContext)
                    .load(Config.IMG_URL + photo)
                    .apply(bannerOptions)
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