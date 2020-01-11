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
import com.anhtam.gate9.adapter.v2.ChooseGalleryAdapter
import com.anhtam.gate9.adapter.v2.CommentAdapter
import com.anhtam.gate9.adapter.v2.PhotoAdapter
import com.anhtam.gate9.session.SessionManager
import com.anhtam.gate9.share.view.donate.DonateDialog
import com.anhtam.gate9.utils.convertInt
import com.anhtam.gate9.v2.discussion.user.UserDiscussionScreen
import com.anhtam.gate9.v2.reaction.ReactionScreen
import com.anhtam.gate9.utils.toImage
import com.anhtam.gate9.v2.auth.login.LoginScreen
import com.anhtam.gate9.v2.discussion.game.GameDiscussionScreen
import com.anhtam.gate9.v2.shared.AbstractGalleryFragment
import com.anhtam.gate9.vo.Reaction
import com.anhtam.gate9.vo.model.Category
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.squareup.phrase.Phrase
import kotlinx.android.synthetic.main.bottom_bar_type_layout.*
import kotlinx.android.synthetic.main.detail_post_screen.*
import of.bum.network.helper.Resource
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Named

class DetailPostScreen private constructor(
        private val _post: PostEntity,
        private val _type: Detail,
        private val mListener: ((Reaction)-> Unit)?
): AbstractGalleryFragment(), INavigator{

    override fun toLogin() {
        navigation?.addFragment(LoginScreen.newInstance(false))
    }

    override fun toUserDiscussion() {
        navigation?.addFragment(UserDiscussionScreen.newInstance(viewModel._userId, Category.Member))
    }

    override fun toGameDiscussion() {
        navigation?.addFragment(GameDiscussionScreen.newInstance("", viewModel._gameId.toString()))
    }

    override fun toReact() {
        val commentId = _post.commentId?.toInt() ?: return
        navigation?.addFragment(ReactionScreen.newInstance(commentId))
    }

    companion object{
        fun newInstance(postEntity: PostEntity, type: Detail, listener: ((Reaction)->Unit)? = null) = DetailPostScreen(postEntity, type, listener)
    }

    private val viewModel: DetailPostViewModel by viewModels { vmFactory }

    @field:Named("avatar") @Inject lateinit var avatarOptions: RequestOptions
    @field:Named("banner") @Inject lateinit var bannerOptions: RequestOptions

    @Inject lateinit var mPhotoAdapter: PhotoAdapter
    @Inject lateinit var mAdapter: CommentAdapter
    @Inject lateinit var mGalleryAdapter: ChooseGalleryAdapter


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
        postViewForum()
        initView()
        initEvents()
        observer()
    }

    private fun postViewForum(){
        // check auth
        viewModel.postViewForum()
    }

    private fun loadComment(){
        val reply = _post.totalReply.convertInt()
        if(reply > 0){
            viewModel.getChildComment()
        }
    }

    private fun initView(){
        csOriPost?.visibility = if (_type ==  Detail.COMMENT) View.VISIBLE else View.GONE
        initRvPhoto()
        initRvComment()
        initGalleryRv()
        initUI()
    }

    private fun initGalleryRv(){
        mGalleryAdapter.setOnItemChildClickListener { _, _, position ->
            mGalleryAdapter.remove(position)
            if (mGalleryAdapter.data.isEmpty()){
                rvImage?.visibility = View.GONE
                noneTypeMode()
            }
        }
        rvImage?.adapter = mGalleryAdapter
        rvImage?.layoutManager = LinearLayoutManager(context)
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

    override fun onSelectedImage(urls: List<String>) {
        super.onSelectedImage(urls)
        mGalleryAdapter.setNewData(urls)
        rvImage?.visibility = View.VISIBLE
        readySendMode()
    }

    private fun observer(){
        viewModel.comments.observe(viewLifecycleOwner, Observer { resource ->
            when(resource) {
                is Resource.Success -> {
                    hideProgress()
                    val data = resource.data?.mComments
                    if (data.isNullOrEmpty()) {
                        mAdapter.loadMoreEnd()
                    } else {
                        if (viewModel.mPage == 0) {
                            mAdapter.setNewData(data)
                        } else {
                            mAdapter.addData(data)
                        }
                        mAdapter.loadMoreComplete()
                    }
                }
                is Resource.Loading -> {
                    hideProgress()
                }
                else -> {
                    mAdapter.loadMoreFail()
                }
            }
        })
    }

    /*
     * 1) Display user
     * 2) Display post - comment
     * 3) Display game
     */
    private fun initUI() {
        initPhoto()
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
        val react = unwrapPost.like?.convertInt() ?: 0
        val like = unwrapPost.totalLike.convertInt()
        val dislike = unwrapPost.totalDislike.convertInt()
        val love = unwrapPost.totalLove.convertInt()
        reactionView?.initialize(like, dislike, love, Reaction.react(react))

        // Set photo
        // photos
        // Set Game
        val game = unwrapPost.game ?: return
        mAdapter.initialize(game)
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

    private fun initPhoto(){
        val photos = _post.photo ?: return
//        val isFormat = "[[.*]]".toRegex().matches(photos) TODO Regex
        val isFormat = (photos.startsWith('[') && photos.endsWith(']'))
        val stringConcat = if (!isFormat) {
            photos
        } else {
            photos.substring(1, photos.length - 1)
        }
        val spanCount = mPhotoAdapter.setPhoto(stringConcat)
        rvPhotos.layoutManager = GridLayoutManager(context, spanCount)
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
        reactionView?.onReactionChange(mSessionManager){
            mListener?.invoke(it)
            viewModel.react(it).observe(viewLifecycleOwner, Observer {
                Timber.d("Test") // TODO Bug
            })
        }

        tvFollowGame?.setOnClickListener {
            if(tvFollowGame?.text == getString(R.string.follow)) {
                setFollowing()
            } else {
                setFollow()
            }
        }
        csShare?.setOnClickListener{
            viewModel.sharePost()
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
                if (etPost.length() > 0) readySendMode()
                if (etPost.length() == 0 && mGalleryAdapter.data.isEmpty()) noneTypeMode()
            }
        })
        /*
         *  1) Hide keyboard
         *  2) Show progress
         *  3) Cached data
         *  4) Clear UI
         *  5) post comment
         *
         *
         *   -> Success -> Fetch comment + Update UI
         */
        imgSend?.setOnClickListener {
            hideKeyboard()
            showProgress()
            postComment()
        }

        imgChooseImg?.setOnClickListener {
            chooseImage()
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

    enum class Detail {
        COMMENT, POST
    }

    private fun readySendMode(){
        imgSend?.visibility = View.VISIBLE
        rightLayout?.visibility = View.GONE
    }

    private fun noneTypeMode(){
        imgSend?.visibility = View.GONE
        rightLayout?.visibility = View.VISIBLE
    }

    private fun postComment(){
        val content = etPost?.text?.toString()
        viewModel.postComment(content, mGalleryAdapter.data.joinToString(",")).observe(viewLifecycleOwner, Observer {
            when(it){
                is Resource.Success ->{
                    hideProgress()
                    viewModel.getChildComment()
                }
                is Resource.Error ->{
                    hideProgress()
                }
                else ->{}
            }
        })
        initializeState()
    }

    private fun initializeState(){
        etPost?.setText("")
        mGalleryAdapter.data.clear()
        noneTypeMode()
        rvImage?.visibility = View.GONE
    }
}

