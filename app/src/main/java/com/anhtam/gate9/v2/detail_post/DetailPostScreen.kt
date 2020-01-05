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
import com.anhtam.gate9.session.SessionManager
import com.anhtam.gate9.share.view.donate.DonateDialog
import com.anhtam.gate9.utils.convertInt
import com.anhtam.gate9.v2.discussion.user.UserDiscussionScreen
import com.anhtam.gate9.v2.reaction.ReactionScreen
import com.anhtam.gate9.utils.toImage
import com.anhtam.gate9.v2.auth.login.LoginScreen
import com.anhtam.gate9.v2.discussion.game.GameDiscussionScreen
import com.anhtam.gate9.v2.main.DaggerNavigationFragment
import com.anhtam.gate9.vo.IllegalReturn
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
        private val _post: PostEntity,
        private val _type: Detail
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
        val commentId = _post.commentId?.toInt() ?: return
        navigation?.addFragment(ReactionScreen.newInstance(commentId))
    }

    companion object{
        fun newInstance(postEntity: PostEntity, type: Detail) = DetailPostScreen(postEntity, type)
    }

    private val viewModel: DetailPostViewModel by viewModels { vmFactory }

    @field:Named("avatar") @Inject lateinit var avatarOptions: RequestOptions
    @field:Named("banner") @Inject lateinit var bannerOptions: RequestOptions

    @Inject lateinit var mPhotoAdapter: PhotoAdapter
    @Inject lateinit var mAdapter: CommentAdapter
    @Inject lateinit var mSessionManager: SessionManager

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
        initUI()
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

    private fun observer(){
        viewModel.react.observe(viewLifecycleOwner, Observer {
            // clear action
            // set reaction
        })
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
        if (!isFormat) {
            throw IllegalReturn("Photo format return wrong!!!")
        }
        val spanCount = mPhotoAdapter.setPhoto(photos.substring(1, photos.length - 1))
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
            viewModel.react(it)
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
            showProgress()
            viewModel.postComment(content, "").observe(viewLifecycleOwner, Observer {
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
            etPost?.setText("")
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

}

