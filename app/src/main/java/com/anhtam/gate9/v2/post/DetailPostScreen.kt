package com.anhtam.gate9.v2.post

import android.os.Bundle
import android.text.Editable
import android.text.Html
import android.text.TextUtils
import android.text.TextWatcher
import android.view.*
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.anhtam.domain.v2.PostEntity
import com.anhtam.gate9.R
import com.anhtam.gate9.adapter.v2.CommentAdapter
import com.anhtam.gate9.share.view.MoreDialog
import com.anhtam.gate9.share.view.donate.DonateDialog
import com.anhtam.gate9.storage.StorageManager
import com.anhtam.gate9.ui.base.BaseActivity
import com.anhtam.gate9.ui.discussion.user.UserDiscussionActivity
import com.anhtam.gate9.ui.reaction.ReactionActivity
import com.anhtam.gate9.ui.report.post.ReportPostActivity
import com.anhtam.gate9.utils.autoCleared
import com.anhtam.gate9.utils.toImage
import com.anhtam.gate9.v2.auth.login.LoginScreen
import com.anhtam.gate9.v2.main.DaggerNavigationFragment
import com.anhtam.gate9.vo.model.Category
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.squareup.phrase.Phrase
import kotlinx.android.synthetic.main.bottom_bar_type_layout.*
import kotlinx.android.synthetic.main.comment_item_layout.view.*
import kotlinx.android.synthetic.main.detail_post_screen.*
import kotlinx.android.synthetic.main.photo_n_item_layout.view.*
import of.bum.network.helper.Resource
import of.bum.network.v2.MediaService
import timber.log.Timber
import javax.inject.Inject

open class DetailPostScreen : DaggerNavigationFragment(){

    companion object{
        fun newInstance(postEntity: PostEntity, type: Detail): DetailPostScreen {
            val fragment = DetailPostScreen()
            fragment.mPostEntity = postEntity
            fragment.mType = type
            Timber.d(StorageManager.getAccessToken())
            Timber.d("DataNDN $postEntity , ${postEntity.commentId}")
            return fragment
        }
    }

    private val viewModel: DetailPostViewModel by viewModels { vmFactory }
    private var mPostEntity: PostEntity? = null
    private var mAdapter: Adapter by autoCleared()
    private var mPhotoAdapter: PhotoAdapter by autoCleared()
    private var mType = Detail.POST
    @Inject lateinit var mMediaService: MediaService
    private var more = 0

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        activity?.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
        return inflater.inflate(R.layout.detail_post_screen, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
        setSupportActionBar(toolbar)
        init()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        menu.clear()
        inflater.inflate(R.menu.menu_chat_search_more, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    private fun init() {
        if(!(mPostEntity?.totalReply.isNullOrEmpty() || mPostEntity?.totalReply?.toInt() == 0)){
            showProgress()
            fetchComment()
        }
        csOriPost?.visibility = if (mType ==  Detail.COMMENT) View.VISIBLE else View.GONE
        initRvPhoto()
        initRvComment()
        bindingViewPost()
        initEvents()
    }

    private fun initRvPhoto() {
        mPhotoAdapter = PhotoAdapter()
        rvPhotos?.layoutManager = LinearLayoutManager(context)
        rvPhotos?.adapter = mPhotoAdapter
        rvPhotos?.isNestedScrollingEnabled = false
    }

    private fun initRvComment() {
        mAdapter = Adapter()
        rvComment?.layoutManager = LinearLayoutManager(context)
        rvComment?.adapter = mAdapter
        rvComment?.isNestedScrollingEnabled = false
    }
//
//    view.likeLayout.setOnClickListener {
//        if(checkLogin()){
//            // change icon color and send request
//            reaction(view, 1, unwrapPost)
//        } else {
//            navigation?.addFragment(LoginScreen.newInstance())
//        }
//    }
//    view.loveLayout.setOnClickListener {
//        if(checkLogin()){
//            // change icon color and send request
//            reaction(view, 3, unwrapPost)
//        } else {
//            navigation?.addFragment(LoginScreen.newInstance())
//        }
//    }
//    view.dislikeLayout.setOnClickListener {
//        if(checkLogin()){
//            // change icon color and send request
//            reaction(view, 2, unwrapPost)
//        } else {
//            navigation?.addFragment(LoginScreen.newInstance())
//        }
//    }
//    private fun reactionRequest(view: View, type: Int, data: PostEntity) {
//        data.mReactionId = type
//        when(type) {
//            1 -> {
//                val like = view.likeTextView.text.toString().toInt()  + 1
//                view.likeTextView.text = like.toString()
//            }
//            2 -> {
//                val dislike = view.dislikeTextView.text.toString().toInt()  + 1
//                view.dislikeTextView.text = dislike.toString()
//            }
//            3 -> {
//                val love = view.loveTextView.text.toString().toInt()  + 1
//                view.loveTextView.text = love.toString()
//            }
//        }
//        // request api
//        listener(data, type)
//
//    }
//
//
//    private fun reaction(view: View, type: Int, data: PostEntity) {
//        when (data.mReactionId) {
//            0 -> {//change icon color and increase number}
//            }
//            1 -> {
//                // change icon like
//                val like = view.likeTextView.text.toString().toInt()  - 1
//                view.likeTextView.text = like.toString()
//
//            }
//            2 -> {
//                // change icon dislike
//                val dislike = view.dislikeTextView.text.toString().toInt()  - 1
//                view.dislikeTextView.text = dislike.toString()
//            }
//            3 -> {
//                // change icon love
//                val love = view.loveTextView.text.toString().toInt()  - 1
//                view.loveTextView.text = love.toString()
//            }
//        }
//    }
//{ data, type ->
//    val id = data.commentId?.toInt() ?: 0
//    val params = hashMapOf<String, Int>()
//    params["commentId"] = id
//    params["type"] = type
//    params["userId"] = mUserId
//            mViewModel.react(params).enqueue(object: Callback<Base>{
//                override fun onFailure(call: Call<Base>, t: Throwable) {
//                    Timber.d("Failure")
//                }
//
//                override fun onResponse(call: Call<Base>, response: Response<Base>) {
//                    Timber.d(StorageManager.getAccessToken())
//                    Timber.d("Success")
//                }
//
//            })
//}

    private fun fetchComment() {
        val commentId = mPostEntity?.commentId
        viewModel.getDetailPost(commentId?.toString() ?: "0").observe(viewLifecycleOwner, Observer {
            when(it) {
                is Resource.Success -> {
                    val comments = it.data?.mComments ?: return@Observer
                    mAdapter.setNewData(comments)
                    hideProgress()
                }
                is Resource.Error -> {
                    hideProgress()
                }
            }
        })
    }

    private fun bindingViewPost() {
        // Set User
        val unwrapPost = mPostEntity ?: return
        val user = unwrapPost.user
        Glide.with(this)
                .load(user?.mAvatar?.toImage())
                .apply {
                    RequestOptions.circleCropTransform()
                            .placeholder(R.drawable.img_avatar_holder)
                            .error(R.drawable.img_avatar_holder)
                }
                .into(imgAvatar)
        tvUserName?.text = user?.mName
        val follow = Phrase.from(getString(R.string.following_amount_and_follower_amount))
                .put("following", user?.mFlowing ?: "0")
                .put("follower", user?.mFlower ?: "0")
                .format()

        tvFollowNumber.text = follow
        // Set post
        tvContent?.text = Html.fromHtml(unwrapPost.content)
        tvTime?.text = unwrapPost.createdDate

        // Set photo
        // photos
        val photos = unwrapPost.photo?.subSequence(1,unwrapPost.photo!!.length - 1)
        if (!photos.isNullOrEmpty()) {
            val listPhotos = photos.split(",").toMutableList().map { it.trim() }
            val photoEntity = listPhotos.map {
                CommentAdapter.PhotoEntity(when (listPhotos.size) {
                    1 -> CommentAdapter.PhotoEntity.GRID_1
                    in 2..4 -> CommentAdapter.PhotoEntity.GRID_4
                    else -> CommentAdapter.PhotoEntity.GRID_N
                }, it)
            }
            mPhotoAdapter.setSpanSizeLookup { _, position ->
                mPhotoAdapter.data[position].getSpanSize()
            }
            if (photoEntity.size > 4) {
                more = photoEntity.size - 4
                val morePhotoList = arrayListOf<CommentAdapter.PhotoEntity>()
                for (index in 0..3) {
                    if (index == 3) {
                        morePhotoList.add(CommentAdapter.PhotoEntity(CommentAdapter.PhotoEntity.GRID_N, photoEntity[index].photo))
                    } else {
                        morePhotoList.add(CommentAdapter.PhotoEntity(CommentAdapter.PhotoEntity.GRID_4, photoEntity[index].photo))
                    }
                }
                mPhotoAdapter.setNewData(morePhotoList)
            } else {
                mPhotoAdapter.setNewData(photoEntity)
            }
            if (photoEntity.size == 1) {
                rvPhotos.layoutManager = GridLayoutManager(context, 1)
            } else {
                rvPhotos.layoutManager = GridLayoutManager(context, 2)
            }
            rvPhotos.adapter = mPhotoAdapter
        }
        // Set Game
        val game = unwrapPost.game ?: return
        csGame.visibility = View.VISIBLE
        imgGame.visibility = View.VISIBLE
        tvNameGame.visibility = View.VISIBLE
        imgDropDown.visibility = View.VISIBLE
        Glide.with(this)
                .applyDefaultRequestOptions(
                        RequestOptions()
                                .placeholder(R.drawable.img_holder_banner)
                                .error(R.drawable.img_holder_banner)
                )
                .load(game.avatar?.toImage())
                .into(imgGame)
        Glide.with(this)
                .applyDefaultRequestOptions(
                        RequestOptions()
                                .placeholder(R.drawable.img_holder_banner)
                                .error(R.drawable.img_holder_banner)
                )
                .load(game.avatar?.toImage())
                .into(imgNewGame)
        tvNameGame.text = game.name

        tvTitle?.text = game.name
        val contentStr = Phrase.from(getString(R.string.follower_amount_and_post_amount))
                .put("follower", mPostEntity?.game?.follower ?: "0")
                .put("post", mPostEntity?.game?.post?.toString() ?: "0")
                .format()
        tvContentGame?.text = contentStr
        if (false){
            // check follow here
            setFollow()
        } else {
            setFollowing()
        }
    }

    private fun setFollow() {
        tvFollowGame?.text = getString(R.string.follow)
        tvFollowGame?.setBackgroundResource(R.drawable.bg_follow)
        val unwrapContext = context ?: return
        tvFollowGame?.setTextColor(ContextCompat.getColor(unwrapContext, R.color.text_color_red_light))
    }

    private fun setFollowing() {
        tvFollowGame?.text = getString(R.string.following)
        tvFollowGame?.setBackgroundResource(R.drawable.bg_following)
        val unwrapContext = context ?: return
        tvFollowGame?.setTextColor(ContextCompat.getColor(unwrapContext, R.color.text_color_blue))
    }

    private fun initEvents() {
        tvFollowGame?.setOnClickListener {
            if(tvFollowGame?.text == getString(R.string.follow)) {
                setFollowing()
            } else {
                setFollow()
            }
        }
        csShare?.setOnClickListener{
            checkLogin()
        }
        csLove?.setOnClickListener{
            checkLogin()
        }
        backFrameLayout?.setOnClickListener{ navigation?.back() }
        btnDonate?.setOnClickListener {
            val unwrapContext = context ?: return@setOnClickListener
            DonateDialog(unwrapContext).show()
        }
        tvAction?.setOnClickListener {
            ReactionActivity.start(context)
        }
        ichome?.setOnClickListener { navigation?.back() }
        csDisLike?.setOnClickListener { checkLogin() }
        csLike?.setOnClickListener { checkLogin() }
        csComment?.setOnClickListener { checkLogin() }
        tvPrePost?.setOnClickListener { navigation?.back() }
        tvOriPost?.setOnClickListener { /**/ }
        etPost.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {

            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                imgSend.visibility = if(etPost.length() > 0) View.VISIBLE else View.GONE
                rightLayout.visibility = if(etPost.length() <= 0) View.VISIBLE else View.GONE
            }

        })
        imgSend?.setOnClickListener {
            hideKeyboard()
            val content = etPost?.text?.toString()
            val parentId = mPostEntity?.commentId ?: return@setOnClickListener
            postRequest(parentId, content, "")

            updateUIAfterPost()
        }
    }

    private fun postRequest(parentId: Long, content: String? = null, imageUrl: String? = "") {
        showProgress()
        viewModel.postComment(parentId, content, imageUrl).observe(viewLifecycleOwner, Observer {
            when(it) {
                is Resource.Success -> {
                    fetchComment()
                }
                is Resource.Error -> {
                    hideProgress()
                }
                else -> {

                }
            }
        })
    }

    private fun updateUIAfterPost() {
        etPost?.setText("")
    }

    private fun checkLogin() : Boolean {
        val accessToken = StorageManager.getAccessToken()
        return if (accessToken.isEmpty()) {
            navigation?.addFragment(LoginScreen.newInstance())
            false
        } else {
            true
        }
    }

    inner class Adapter : BaseQuickAdapter<PostEntity, BaseViewHolder>(R.layout.comment_item_layout, arrayListOf()){
        override fun convert(helper: BaseViewHolder?, item: PostEntity?) {

            val unwrapPost = item ?: return
            val view = helper?.itemView ?: return
            // Set content of Post
            view.contentTextView.text = unwrapPost.content?.let { Html.fromHtml(it) }
            view.dateTextView.text = unwrapPost.createdDate

            // Set user
            val user = unwrapPost.user ?: return
            view.userNameTextView.text = user.mName
            Glide.with(mContext)
                    .applyDefaultRequestOptions(
                            RequestOptions.circleCropTransform()
                                    .placeholder(R.drawable.img_avatar_holder)
                                    .error(R.drawable.img_avatar_holder)
                    ).load(user.mAvatar?.toImage())
                    .apply(RequestOptions.circleCropTransform())
                    .into(view.avatarImageView)
            view.contentTextView?.setOnClickListener {
                navigation?.addFragment(newInstance(unwrapPost, Detail.COMMENT))
            }
            view.commentImageView?.setOnClickListener {
                navigation?.addFragment(newInstance(unwrapPost, Detail.COMMENT))
            }
            view.userNameTextView?.setOnClickListener { navigation?.addFragment(UserDiscussionActivity.newInstance(unwrapPost.user?.mId ?: 0, Category.Member)) }
            view.avatarImageView?.setOnClickListener { navigation?.addFragment(UserDiscussionActivity.newInstance(unwrapPost.user?.mId ?: 0, Category.Member)) }

            if(TextUtils.isEmpty(unwrapPost.totalReply)){
                return
            }
            val totalChild = unwrapPost.totalReply.toInt()
            when(totalChild) {
                0 -> {
                    view.rvChildComment?.visibility = View.GONE
                }
                else -> {
                    if(unwrapPost.child.isNullOrEmpty()) {
                        view.rvChildComment?.visibility = View.GONE
                        return
                    }
                    view.rvChildComment?.visibility = View.VISIBLE
                    val childs = unwrapPost.child
                    val childAdapter = ChildAdapter()
                    childAdapter.setNewData(childs)
                    if(totalChild > 2) {
                        val footer = ShowMoreFooterView(context)
                        childAdapter.addFooterView(footer)
                        val remain = totalChild - 2
                        val showMoreText = Phrase.from(getString(R.string.xem_them_amount_comment))
                                .put("number", remain)
                                .put("s", if(remain > 1) "s" else "")
                                .format()
                                .toString()
                        footer.setText(showMoreText) {
                            // set show more here
                        }
                    }
                    view.rvChildComment?.adapter = childAdapter
                    view.rvChildComment?.layoutManager = LinearLayoutManager(context)
                }
            }
        }
    }

    inner class ChildAdapter : BaseQuickAdapter<PostEntity, BaseViewHolder>(R.layout.child_comment_item_layout, arrayListOf()){
        override fun convert(helper: BaseViewHolder?, item: PostEntity?) {
            val unwrapPost = item ?: return
            val view = helper?.itemView ?: return
            // Set content of Post
            view.contentTextView.text = unwrapPost.content?.let { Html.fromHtml(it) }
            view.dateTextView.text = unwrapPost.createdDate

            // Set user
            val user = unwrapPost.user ?: return
            view.userNameTextView.text = user.mName
            Glide.with(mContext)
                    .applyDefaultRequestOptions(
                            RequestOptions()
                                    .placeholder(R.drawable.img_avatar_holder)
                                    .error(R.drawable.img_avatar_holder)
                    ).load(user.mAvatar)
                    .apply(RequestOptions.circleCropTransform())
                    .into(view.avatarImageView)
            view.contentTextView?.setOnClickListener {
                navigation?.addFragment(newInstance(unwrapPost, Detail.COMMENT))
            }
            view.commentImageView?.setOnClickListener {
                navigation?.addFragment(newInstance(unwrapPost, Detail.COMMENT))
            }
            view.userNameTextView?.setOnClickListener { navigation?.addFragment(UserDiscussionActivity.newInstance(unwrapPost.user?.mId ?: 0, Category.Member)) }
            view.avatarImageView?.setOnClickListener { navigation?.addFragment(UserDiscussionActivity.newInstance(unwrapPost.user?.mId ?: 0, Category.Member)) }
            view.moreImageView?.setOnClickListener {
                val mMoreDialog = MoreDialog(context!!, object : MoreDialog.IMore {
                    override fun onreport() {
                        ReportPostActivity.start(mContext as BaseActivity)
                    }
                })
                mMoreDialog.idPost = unwrapPost.commentId?.toString()
                mMoreDialog.show()
            }
        }

    }

    inner class PhotoAdapter : BaseMultiItemQuickAdapter<CommentAdapter.PhotoEntity, BaseViewHolder>(mutableListOf()){
        init {
            addItemType(CommentAdapter.PhotoEntity.GRID_1, R.layout.photo_1_item_layout)
            addItemType(CommentAdapter.PhotoEntity.GRID_4, R.layout.photo_4_item_layout)
            addItemType(CommentAdapter.PhotoEntity.GRID_N, R.layout.photo_n_item_layout)
        }

        override fun convert(helper: BaseViewHolder?, item: CommentAdapter.PhotoEntity?) {
            val photo = item?.photo ?: return
            val view = helper?.itemView ?: return
            val imgPhoto = view.findViewById<ImageView>(R.id.imgPhoto)
            Glide.with(mContext)
                    .applyDefaultRequestOptions(
                            RequestOptions()
                                    .placeholder(R.drawable.img_holder_banner)
                                    .error(R.drawable.img_holder_banner)
                                    .centerCrop()
                    ).load(photo.toImage())
                    .into(imgPhoto)
            if (item.type == CommentAdapter.PhotoEntity.GRID_N) {
                view.tvMore.text = "+".plus(more.toString())
            }
        }
    }

    enum class Detail {
        COMMENT, POST
    }

}

