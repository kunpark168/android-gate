package com.anhtam.gate9.v2.detail_post

import android.os.Bundle
import android.text.Editable
import android.text.Html
import android.text.TextWatcher
import android.view.*
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.anhtam.domain.v2.PostEntity
import com.anhtam.gate9.R
import com.anhtam.gate9.adapter.v2.CommentAdapter
import com.anhtam.gate9.adapter.v2.PhotoAdapter
import com.anhtam.gate9.adapter.v2.PhotoEntity
import com.anhtam.gate9.share.view.donate.DonateDialog
import com.anhtam.gate9.storage.StorageManager
import com.anhtam.gate9.v2.discussion.user.UserDiscussionScreen
import com.anhtam.gate9.v2.reaction.ReactionScreen
import com.anhtam.gate9.utils.toImage
import com.anhtam.gate9.v2.auth.login.LoginScreen
import com.anhtam.gate9.v2.discussion.game.GameDiscussionScreen
import com.anhtam.gate9.v2.main.DaggerNavigationFragment
import com.anhtam.gate9.vo.Reaction
import com.anhtam.gate9.vo.model.Category
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.squareup.phrase.Phrase
import kotlinx.android.synthetic.main.bottom_bar_type_layout.*
import kotlinx.android.synthetic.main.detail_post_screen.*
import of.bum.network.helper.Resource
import javax.inject.Inject
import javax.inject.Named

open class DetailPostScreen private constructor(
        val _post: PostEntity,
        val _type: Detail
): DaggerNavigationFragment(), INavigator{

    override fun toLogin() {
        navigation?.addFragment(LoginScreen.newInstance())
    }

    override fun toUserDiscussion() {
        navigation?.addFragment(UserDiscussionScreen.newInstance(viewModel._userId, Category.Member))
    }

    override fun toGameDiscussion() {
        navigation?.addFragment(GameDiscussionScreen.newInstance("", viewModel._gameId.toString()))
    }

    override fun toReact() {
        navigation?.addFragment(ReactionScreen.newInstance())
    }

    companion object{
        fun newInstance(postEntity: PostEntity, type: Detail) = DetailPostScreen(postEntity, type)
    }

    private val viewModel: DetailPostViewModel by viewModels { vmFactory }
    private var more = 0

    @field:Named("avatar") @Inject lateinit var avatarOptions: RequestOptions
    @field:Named("banner") @Inject lateinit var bannerOptions: RequestOptions

    @Inject lateinit var mPhotoAdapter: PhotoAdapter
    @Inject lateinit var mAdapter: CommentAdapter

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
        loadComment()
        initView()
        initEvents()
        observer()
    }

    private fun loadComment(){
        if(!(_post.totalReply.isEmpty() || _post.totalReply.toInt() == 0)){
            showProgress()
            fetchComment()
        }
    }

    private fun initView(){
        csOriPost?.visibility = if (_type ==  Detail.COMMENT) View.VISIBLE else View.GONE
        initRvPhoto()
        initRvComment()
        bindingViewPost()
    }

    private fun initRvPhoto() {
        rvPhotos?.layoutManager = LinearLayoutManager(context)
        rvPhotos?.adapter = mPhotoAdapter
        rvPhotos?.isNestedScrollingEnabled = false
        mPhotoAdapter.user = _post.user ?: return
    }

    private fun initRvComment() {
        rvComment?.layoutManager = LinearLayoutManager(context)
        rvComment?.adapter = mAdapter
        rvComment?.isNestedScrollingEnabled = false
    }
//        // Change icon display
//    private fun reaction(type: Int) {
//            if (true) {
//                when (type) {
//                    1 -> {
//                        // change icon like
//                        Glide.with(this@DetailPostScreen)
//                                .load(R.drawable.ic_like_post)
//                                .into(imgLike)
//                    }
//                    2 -> {
//                        // change icon dislike
//                        Glide.with(this@DetailPostScreen)
//                                .load(R.drawable.ic_dislike_post)
//                                .into(imgDislike)
//
//                    }
//                    3 -> {
//                        // change icon love
//                        Glide.with(this@DetailPostScreen)
//                                .load(R.drawable.ic_reaction_love)
//                                .into(imgFavorite)
//                    }
//                }
////                _react = 0
//            } else {
//                when (type) {
//                    1 -> {
//                        // change icon like
//                        imgLike?.setColorFilter(ContextCompat.getColor(requireContext(), R.color.color_main_blue), PorterDuff.Mode.MULTIPLY)
//
//                    }
//                    2 -> {
//                        // change icon dislike
//                        imgDislike?.setColorFilter(ContextCompat.getColor(requireContext(), R.color.color_main_blue), PorterDuff.Mode.MULTIPLY)
//
//                    }
//                    3 -> {
//                        // change icon love
//                        imgFavorite?.setColorFilter(ContextCompat.getColor(requireContext(), R.color.color_main_blue), PorterDuff.Mode.MULTIPLY)
//                    }
//                }
////                _react = type
//            }
//            val id = _post.commentId?.toInt() ?: 0
//            val params = hashMapOf<String, Int>()
//            params["commentId"] = id
////            params["type"] = _react
//            params["userId"] = 5
//            viewModel.react(params).observe(viewLifecycleOwner, Observer {
//
//            })
//        }


    private fun observer(){

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
                PhotoEntity(when (listPhotos.size) {
                    1 -> PhotoEntity.GRID_1
                    in 2..4 -> PhotoEntity.GRID_4
                    else -> PhotoEntity.GRID_N
                }, it)
            }
            mPhotoAdapter.setSpanSizeLookup { _, position ->
                mPhotoAdapter.data[position].getSpanSize()
            }
            if (photoEntity.size > 4) {
                more = photoEntity.size - 4
                val morePhotoList = arrayListOf<PhotoEntity>()
                for (index in 0..3) {
                    if (index == 3) {
                        morePhotoList.add(PhotoEntity(PhotoEntity.GRID_N, photoEntity[index].photo))
                    } else {
                        morePhotoList.add(PhotoEntity(PhotoEntity.GRID_4, photoEntity[index].photo))
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
        tvOriPost?.setOnClickListener { /**/ }
        csComment?.setOnClickListener { etPost?.requestFocus() }
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
        /*
         * 1) Post comment
         *   -> Success -> Fetch comment + Update UI
         */
        imgSend?.setOnClickListener {
            hideKeyboard()
            val content = etPost?.text?.toString()
            viewModel.postComment(content, "")
        }

        // navigation
        imgAvatar?.setOnClickListener { toUserDiscussion() }
        tvUserName?.setOnClickListener { toUserDiscussion() }
        imgGame?.setOnClickListener { toGameDiscussion() }
        imgNewGame?.setOnClickListener { toGameDiscussion() }
        tvTitle?.setOnClickListener { toGameDiscussion() }

        tvAction?.setOnClickListener {toReact() }
        ichome?.setOnClickListener { navigation?.back() }
        tvPrePost?.setOnClickListener { navigation?.back() }
    }

    private fun checkLogin() : Boolean {
        val accessToken = StorageManager.getAccessToken()
        return accessToken.isNotEmpty()
    }

    enum class Detail {
        COMMENT, POST
    }

}

