package com.anhtam.gate9.v2.post_detail

import android.os.Bundle
import android.text.Editable
import android.text.Html
import android.text.TextWatcher
import android.view.MenuItem
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.anhtam.domain.v2.Post
import com.anhtam.gate9.R
import com.anhtam.gate9.adapter.v2.ChooseGalleryAdapter
import com.anhtam.gate9.adapter.v2.CommentAdapter
import com.anhtam.gate9.adapter.v2.PhotoAdapter
import com.anhtam.gate9.config.Config
import com.anhtam.gate9.restful.BackgroundTasks
import com.anhtam.gate9.share.view.donate.DonateDialog
import com.anhtam.gate9.utils.convertInt
import com.anhtam.gate9.utils.toImage
import com.anhtam.gate9.v2.auth.login.LoginScreen
import com.anhtam.gate9.v2.game_detail.DetailGameFragment
import com.anhtam.gate9.v2.nph_detail.DetailNPHFragment
import com.anhtam.gate9.v2.reaction.ReactionScreen
import com.anhtam.gate9.v2.report.post.ReportPostActivity
import com.anhtam.gate9.v2.shared.AbstractGalleryFragment
import com.anhtam.gate9.v2.user_detail.DetailUserFragment
import com.anhtam.gate9.vo.Reaction
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.squareup.phrase.Phrase
import kotlinx.android.synthetic.main.bottom_bar_type_layout.*
import kotlinx.android.synthetic.main.detail_post_screen.*
import of.bum.network.helper.Resource
import javax.inject.Inject
import javax.inject.Named

class DetailPostScreen private constructor(
        private val _post: Post,
        private val _type: Detail,
        private val mListener: ((Reaction)-> Unit)?
): AbstractGalleryFragment(R.layout.detail_post_screen), INavigator{

    override fun toLogin() {
        navigation?.addFragment(LoginScreen.newInstance(false))
    }

    override fun toUserDiscussion() {
        val user = _post.user ?: return
        val roleId = user.mRoleId ?: return
        val id = user.mId ?: return
        if (roleId != 5){
            navigation?.addFragment(DetailUserFragment.newInstance(id))
        } else {
            navigation?.addFragment(DetailNPHFragment.newInstance(id))
        }
    }

    override fun toGameDiscussion() {
        val id = _post.game?.gameId ?: return
        navigation?.addFragment(DetailGameFragment.newInstance(id))
    }

    override fun toReact() {
        val commentId = _post.commentId?.toInt() ?: return
        navigation?.addFragment(ReactionScreen.newInstance(commentId))
    }

    companion object{
        fun newInstance(post: Post, type: Detail, listener: ((Reaction)->Unit)? = null) = DetailPostScreen(post, type, listener)
    }

    private val viewModel: DetailPostViewModel by viewModels { vmFactory }
    private var mIsRefresh = false
    private var mPost: Post? = null
    private var mIsFollowing = false

    @field:Named("avatar") @Inject lateinit var avatarOptions: RequestOptions
    @field:Named("banner") @Inject lateinit var bannerOptions: RequestOptions

    @Inject lateinit var mPhotoAdapter: PhotoAdapter
    @Inject lateinit var mAdapter: CommentAdapter
    @Inject lateinit var mGalleryAdapter: ChooseGalleryAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    override fun menuRes() = R.menu.menu_chat_search_more

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.icReport -> {
                navigation?.addFragment(ReportPostActivity.newInstance())
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun init() {
        mIsRefresh = true
        viewModel.initialize(_post, this)
        viewModel.getChildComment()
        initView()
        initEvents()
        observer()
    }

    private fun initView(){
        mIsFollowing = _post.game?.follow ?: false
        onUpdateFollow()
        csOriPost?.visibility = if (_type ==  Detail.COMMENT) View.VISIBLE else View.GONE
        initRvPhoto()
        initRvComment()
        initGalleryRv()
    }

    private fun onUpdateFollow(){
        if (mIsFollowing){
            // check follow here
            setFollow()
        } else {
            setFollowing()
        }
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
                    if (mIsRefresh) {
                        mPost = resource.data?.mDetails ?: _post
                        mIsRefresh = false
                        updateUI()
                    }
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
                }
                else -> {
                    hideProgress()
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
    private fun updateUI() {
        initPhoto()
        // Set User
        val unwrapPost = mPost ?: return
        val user = unwrapPost.user
        Glide.with(this)
                .load(user?.mAvatar?.toImage())
                .apply(avatarOptions)
                .into(imgAvatar)
        tvUserName?.text = user?.mName
        val follow = Phrase.from(getString(R.string.following_amount_and_follower_amount))
                .put("following", user?.mFlowing?.toString() ?: "0")
                .put("follower", user?.mFlower?.toString() ?: "0")
                .format()

        tvFollowNumber.text = follow
        // Set post
        tvContent?.text = Html.fromHtml(unwrapPost.content ?: "")
        tvTime?.text = unwrapPost.createdDate
        val react = unwrapPost.like?.convertInt() ?: 0
        val like = unwrapPost.totalLike ?: 0
        val dislike = unwrapPost.totalDislike ?: 0
        val love = unwrapPost.totalLove ?: 0
        val comment = unwrapPost.totalReply ?: 0
        val view = unwrapPost.totalView ?: 0
        reactionView?.initialize(like, dislike, love, Reaction.react(react), view, comment)

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
                .put("follower", _post.game?.follower?.toString() ?: "0")
                .put("post", _post.game?.post?.toString() ?: "0")
                .format()
        tvContentGame?.text = contentStr
        if (game.follow == true){
            // check follow here
            setFollowing()
        } else {
            setFollow()
        }
    }

    private fun initPhoto(){
        val photos = mPost?.photo ?: return
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
        reactionView?.onReactionChange(mSessionManager){current, previous ->
            mListener?.invoke(current)
            viewModel.react(current, previous).observe(viewLifecycleOwner, Observer {})
        }

        tvFollowGame?.setOnClickListener {
            val id = _post.game?.gameId ?: return@setOnClickListener
            if (mSessionManager.checkLogin(isDirect = true)){
                mIsFollowing = !mIsFollowing
                onUpdateFollow()
                BackgroundTasks.postFollowGame(id)
            }
        }

        swipeRefreshLayout?.setOnRefreshListener {
            mIsRefresh = true
            showProgress()
            swipeRefreshLayout?.isRefreshing = false
            viewModel.getChildComment()
        }
        btnDonate?.setOnClickListener {
            val unwrapContext = context ?: return@setOnClickListener
            DonateDialog(unwrapContext).show()
        }
        tvOriPost?.setOnClickListener {
            navigation?.clear(Config.DETAIL_POST_FRAGMENT_TAG, false)
        }

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

        imgFrameLayout?.setOnClickListener {
            selectedMultiImages()
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
        iconFrameLayout?.visibility = View.GONE
    }

    private fun noneTypeMode(){
        imgSend?.visibility = View.GONE
        iconFrameLayout?.visibility = View.VISIBLE
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

