package com.anhtam.gate9.v2.detail_post

import android.graphics.PorterDuff
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
import com.anhtam.gate9.v2.discussion.user.UserDiscussionScreen
import com.anhtam.gate9.v2.reaction.ReactionScreen
import com.anhtam.gate9.utils.autoCleared
import com.anhtam.gate9.utils.toImage
import com.anhtam.gate9.v2.auth.login.LoginScreen
import com.anhtam.gate9.v2.discussion.game.GameDiscussionScreen
import com.anhtam.gate9.v2.gallery.GalleryScreen
import com.anhtam.gate9.v2.main.DaggerNavigationFragment
import com.anhtam.gate9.vo.Reaction
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
import javax.inject.Inject
import javax.inject.Named

open class DetailPostScreen private constructor(
        val _post: PostEntity,
        val _type: Detail
): DaggerNavigationFragment(), INavigator{

    override fun toLogin() {
        navigation?.addFragment(LoginScreen.newInstance())
    }

    companion object{
        fun newInstance(postEntity: PostEntity, type: Detail): DetailPostScreen {
            val fragment = DetailPostScreen(postEntity, type)
            return fragment
        }
    }

    private val viewModel: DetailPostViewModel by viewModels { vmFactory }
    private var mAdapter: Adapter by autoCleared()
    private var mPhotoAdapter: PhotoAdapter by autoCleared()
    private var more = 0

    private var _react: Reaction = Reaction.None

    @field:Named("avatar") @Inject lateinit var avatarOptions: RequestOptions
    @field:Named("banner") @Inject lateinit var bannerOptions: RequestOptions
    @Inject lateinit var mMediaService: MediaService

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        activity?.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
        return inflater.inflate(R.layout.detail_post_screen, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    override fun menuRes() = R.menu.menu_chat_search_more

    private fun init() {
        viewModel.initialize(_post, this)
        if(!(_post.totalReply.isEmpty() || _post.totalReply.toInt() == 0)){
            showProgress()
            fetchComment()
        }
        csOriPost?.visibility = if (_type ==  Detail.COMMENT) View.VISIBLE else View.GONE
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
        // Change icon display
    private fun reaction(type: Int) {
            if (true) {
                when (type) {
                    1 -> {
                        // change icon like
                        Glide.with(this@DetailPostScreen)
                                .load(R.drawable.ic_like_post)
                                .into(imgLike)
                    }
                    2 -> {
                        // change icon dislike
                        Glide.with(this@DetailPostScreen)
                                .load(R.drawable.ic_dislike_post)
                                .into(imgDislike)

                    }
                    3 -> {
                        // change icon love
                        Glide.with(this@DetailPostScreen)
                                .load(R.drawable.ic_reaction_love)
                                .into(imgFavorite)
                    }
                }
//                _react = 0
            } else {
                when (type) {
                    1 -> {
                        // change icon like
                        imgLike?.setColorFilter(ContextCompat.getColor(requireContext(), R.color.color_main_blue), PorterDuff.Mode.MULTIPLY)

                    }
                    2 -> {
                        // change icon dislike
                        imgDislike?.setColorFilter(ContextCompat.getColor(requireContext(), R.color.color_main_blue), PorterDuff.Mode.MULTIPLY)

                    }
                    3 -> {
                        // change icon love
                        imgFavorite?.setColorFilter(ContextCompat.getColor(requireContext(), R.color.color_main_blue), PorterDuff.Mode.MULTIPLY)
                    }
                }
//                _react = type
            }
            val id = _post.commentId?.toInt() ?: 0
            val params = hashMapOf<String, Int>()
            params["commentId"] = id
//            params["type"] = _react
            params["userId"] = 5
            viewModel.react(params).observe(viewLifecycleOwner, Observer {

            })
        }

    private fun fetchComment() {
        val commentId = _post.commentId
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
        val unwrapPost = _post
        val user = unwrapPost.user
        Glide.with(this)
                .load(user?.mAvatar?.toImage())
                .apply(avatarOptions)
                .into(imgAvatar)
        tvUserName?.text = user?.mName
        val follow = Phrase.from(getString(R.string.following_amount_and_follower_amount))
                .put("following", user?.mFlowing ?: "0")
                .put("follower", user?.mFlower ?: "0")
                .format()

        tvFollowNumber.text = follow
        // Set post
        tvContent?.text = Html.fromHtml(unwrapPost.content ?: "")
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
                .load(game.avatar?.toImage())
                .apply(bannerOptions)
                .into(imgGame)
        Glide.with(this)
                .load(game.avatar?.toImage())
                .apply(bannerOptions)
                .into(imgNewGame)
        tvNameGame.text = game.name

        tvTitle?.text = game.name
        val contentStr = Phrase.from(getString(R.string.follower_amount_and_post_amount))
                .put("follower", _post.game?.follower ?: "0")
                .put("post", _post.game?.post?.toString() ?: "0")
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
        // Reaction
        imgLike.setOnClickListener { viewModel.react(Reaction.Like) }
        imgFavorite.setOnClickListener { viewModel.react(Reaction.Love) }
        imgDislike.setOnClickListener { viewModel.react(Reaction.Dislike) }

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
        btnDonate?.setOnClickListener {
            val unwrapContext = context ?: return@setOnClickListener
            DonateDialog(unwrapContext).show()
        }
        tvAction?.setOnClickListener {
            navigation?.addFragment(ReactionScreen.newInstance())
        }
        ichome?.setOnClickListener { navigation?.back() }
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
            val parentId = _post.commentId ?: return@setOnClickListener
            postRequest(parentId, content, "")

            updateUIAfterPost()
        }

        // navigation
        imgAvatar?.setOnClickListener {
            val id = _post.user?.mId ?: return@setOnClickListener
            navigation?.addFragment(UserDiscussionScreen.newInstance(id, Category.Member))
        }
        tvUserName?.setOnClickListener {
            val id = _post.user?.mId ?: return@setOnClickListener
            navigation?.addFragment(UserDiscussionScreen.newInstance(id, Category.Member))
        }
        imgGame?.setOnClickListener {
            val id = _post.game?.gameId ?: return@setOnClickListener
            val link = ""
            navigation?.addFragment(GameDiscussionScreen.newInstance(link, id))
        }
        imgNewGame?.setOnClickListener {
            val id = _post.game?.gameId ?: return@setOnClickListener
            val link = ""
            navigation?.addFragment(GameDiscussionScreen.newInstance(link, id))
        }
        tvTitle?.setOnClickListener {
            val id = _post.game?.gameId ?: return@setOnClickListener
            val link = ""
            navigation?.addFragment(GameDiscussionScreen.newInstance(link, id))
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
        return accessToken.isNotEmpty()
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
                    .load(user.mAvatar?.toImage())
                    .apply(avatarOptions)
                    .into(view.avatarImageView)
            view.contentTextView?.setOnClickListener {
                navigation?.addFragment(newInstance(unwrapPost, Detail.COMMENT))
            }
            view.commentImageView?.setOnClickListener {
                navigation?.addFragment(newInstance(unwrapPost, Detail.COMMENT))
            }
            view.userNameTextView?.setOnClickListener { navigation?.addFragment(UserDiscussionScreen.newInstance(unwrapPost.user?.mId ?: 0, Category.Member)) }
            view.avatarImageView?.setOnClickListener { navigation?.addFragment(UserDiscussionScreen.newInstance(unwrapPost.user?.mId ?: 0, Category.Member)) }

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
                    .load(user.mAvatar?.toImage())
                    .apply(avatarOptions)
                    .into(view.avatarImageView)
            view.contentTextView?.setOnClickListener {
                navigation?.addFragment(newInstance(unwrapPost, Detail.COMMENT))
            }
            view.commentImageView?.setOnClickListener {
                navigation?.addFragment(newInstance(unwrapPost, Detail.COMMENT))
            }
            view.userNameTextView?.setOnClickListener { navigation?.addFragment(UserDiscussionScreen.newInstance(unwrapPost.user?.mId ?: 0, Category.Member)) }
            view.avatarImageView?.setOnClickListener { navigation?.addFragment(UserDiscussionScreen.newInstance(unwrapPost.user?.mId ?: 0, Category.Member)) }
            view.moreImageView?.setOnClickListener {
                val mMoreDialog = MoreDialog(context!!, object : MoreDialog.IMore {
                    override fun onreport() {
//                        ReportPostActivity.start(mContext as BaseActivity)
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
            imgPhoto.setOnClickListener {
                navigation?.addFragment(GalleryScreen.newInstance(data.map { it.photo}, _post.user!!))
            }
            Glide.with(mContext)
                    .load(photo.toImage())
                    .apply(bannerOptions)
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

