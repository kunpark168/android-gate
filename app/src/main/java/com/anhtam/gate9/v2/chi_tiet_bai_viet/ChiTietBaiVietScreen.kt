package com.anhtam.gate9.v2.chi_tiet_bai_viet

import android.os.Bundle
import android.view.View
import android.webkit.WebView
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.LayoutRes
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.anhtam.domain.v2.Article
import com.anhtam.domain.v2.protocol.Game
import com.anhtam.domain.v2.protocol.User
import com.anhtam.domain.v2.wrap.WrapArticle
import com.anhtam.gate9.R
import com.anhtam.gate9.adapter.v2.PhotoAdapter
import com.anhtam.gate9.adapter.v2.du_lieu.RelatedAdapter
import com.anhtam.gate9.restful.BackgroundTasks
import com.anhtam.gate9.utils.toImage
import com.anhtam.gate9.v2.auth.login.LoginScreen
import com.anhtam.gate9.v2.discussion.user.UserDiscussionScreen
import com.anhtam.gate9.v2.game_detail.DetailGameFragment
import com.anhtam.gate9.v2.main.DaggerNavigationFragment
import com.anhtam.gate9.vo.model.Category
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.squareup.phrase.Phrase
import of.bum.network.helper.Resource
import org.w3c.dom.Text
import javax.inject.Inject
import javax.inject.Named

class ChiTietBaiVietScreen(private val mId: Int,
                           private val mArticle: Article?,
                           @LayoutRes private val layoutRes: Int,
                           private val mTab: Int) : DaggerNavigationFragment(layoutRes) {

    companion object {
        fun newInstance(id: Int, article: Article?, layoutRes: Int, tab: Int) = ChiTietBaiVietScreen(id, article, layoutRes, tab)
    }

    private val mViewModel: ChiTietBaiVietViewModel by viewModels { vmFactory }
    @Inject
    @field:Named("banner")
    lateinit var bannerOptions: RequestOptions
    @Inject
    @field:Named("avatar")
    lateinit var avatarOptions: RequestOptions
    @Inject
    lateinit var mConcernAdapter: RelatedAdapter
    @Inject
    lateinit var mNewListAdapter: RelatedAdapter
    private var mWrapArticle: WrapArticle? = null
    private var mPhotoAdapter: PhotoAdapter? = null

    override fun menuRes() = R.menu.menu_chat_search_more

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadData()
        initView()
        observer()
        initEvents()
    }

    private fun initEvents() {
        view?.findViewById<View>(R.id.imgAvatar)?.setOnClickListener { navigateToUser() }
        view?.findViewById<View>(R.id.tvUserName)?.setOnClickListener { navigateToUser() }

        view?.findViewById<View>(R.id.imgGame)?.setOnClickListener { navigateToGame() }
        view?.findViewById<View>(R.id.imgNewGame)?.setOnClickListener { navigateToGame() }
        view?.findViewById<View>(R.id.tvTitle)?.setOnClickListener { navigateToGame() }
        view?.findViewById<TextView>(R.id.tvFollowGame)?.run {
            setOnClickListener {
                val id = mArticle?.mGame?.gameId ?: return@setOnClickListener
                if (mSessionManager.checkLogin(isDirect = true)) {
                    BackgroundTasks.postFollowGame(id)
                    if (this.text == context.getString(R.string.follow)) {
                        setFollowing()
                    } else {
                        setFollow()
                    }
                }
            }
        }
    }

    private fun navigateToUser() {
        val user = mArticle?.mUser ?: return
        val id = user.mId ?: return
        val role = when (user.mRoleId) {
            5 -> Category.Publisher
            else -> Category.Member
        }
        navigation?.addFragment(UserDiscussionScreen.newInstance(id, role))
    }

    private fun navigateToGame() {
        val id = mArticle?.mGame?.gameId ?: return
        navigation?.addFragment(DetailGameFragment?.newInstance(id))
    }

    private fun loadData() {
        showProgress()
        mViewModel.setId(mId, mTab)
    }

    private fun initView() {
        view?.findViewById<WebView>(R.id.articleContentWebView)?.settings?.javaScriptEnabled = true
        view?.findViewById<RecyclerView>(R.id.concernRecyclerView)?.apply {
            adapter = mConcernAdapter
            layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
        }
        view?.findViewById<RecyclerView>(R.id.newRecyclerView)?.apply {
            adapter = mNewListAdapter
            layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
        }
    }

    private fun observer() {
        mViewModel.mArticle.observe(viewLifecycleOwner, Observer {
            when (it) {
                is Resource.Success -> {
                    hideProgress()
                    mWrapArticle = it.data
                    updateUI()
                }
                is Resource.Error -> {
                    hideProgress()
                }
            }
        })
    }

    private fun updateUI() {

        val user: User? = mWrapArticle?.mArticle?.mUser
        view?.findViewById<ImageView>(R.id.imgAvatar)?.run {
            Glide.with(this@ChiTietBaiVietScreen)
                    .load(user?.mAvatar?.toImage())
                    .apply(avatarOptions)
                    .into(this)
        }
        view?.findViewById<TextView>(R.id.tvUserName)?.text = user?.mName
        val follow = Phrase.from(getString(R.string.following_amount_and_follower_amount))
                .put("following", user?.mFlowing?.toString() ?: "0")
                .put("follower", user?.mFlower?.toString() ?: "0")
                .format()
        view?.findViewById<TextView>(R.id.tvFollowNumber)?.text = follow

        view?.findViewById<ImageView>(R.id.articleAvatarImageView)?.run {
            Glide.with(this)
                    .load(mArticle?.mAvatar)
                    .apply(bannerOptions)
                    .into(this)
        }
        view?.findViewById<RecyclerView>(R.id.rvPhotos)?.run {
            initPhoto(this)
        }

        val article = mWrapArticle?.mArticle
        val content = article?.mContent
        view?.findViewById<WebView>(R.id.articleContentWebView)?.loadData(getHtmlData(content), "text/html", "UTF-8")
        view?.findViewById<TextView>(R.id.articleTitleTextView)?.text = mArticle?.mTitle
        view?.findViewById<TextView>(R.id.articleCreateTimeTextView)?.text = mArticle?.mCreatedTime

        //
        val game: Game? = mWrapArticle?.mArticle?.mGame
        initGame(game)
        mConcernAdapter.setNewData(mWrapArticle?.mConcernList)
        mNewListAdapter.setNewData(mWrapArticle?.mNewList)
    }

    private fun initPhoto(rv: RecyclerView) {
        val unwrapNav = navigation ?: return
        mPhotoAdapter = PhotoAdapter(unwrapNav, bannerOptions)
        mPhotoAdapter?.user = mArticle?.mUser ?: return
        rv.adapter = mPhotoAdapter
        val photos = mArticle.mPhotos ?: return
//        val isFormat = "[[.*]]".toRegex().matches(photos) TODO Regex
        val isFormat = (photos.startsWith('[') && photos.endsWith(']'))
        val stringConcat = if (!isFormat) {
            photos
        } else {
            photos.substring(1, photos.length - 1)
        }
        val spanCount = mPhotoAdapter!!.setPhoto(stringConcat)
        rv.layoutManager = GridLayoutManager(context, spanCount)
    }

    private fun setFollow() {
        val tvFollow = view?.findViewById<TextView>(R.id.tvFollowGame)
        tvFollow?.text = getString(R.string.follow)
        tvFollow?.setBackgroundResource(R.drawable.bg_follow)
        val unwrapContext = context ?: return
        tvFollow?.setTextColor(ContextCompat.getColor(unwrapContext, R.color.text_color_red_light))
    }

    private fun setFollowing() {
        val tvFollow = view?.findViewById<TextView>(R.id.tvFollowGame)
        tvFollow?.text = getString(R.string.following)
        tvFollow?.setBackgroundResource(R.drawable.bg_following)
        val unwrapContext = context ?: return
        tvFollow?.setTextColor(ContextCompat.getColor(unwrapContext, R.color.text_color_blue))
    }

    private fun getHtmlData(bodyHTML: String?): String {
        val head = "<head><style>img{max-width: 100%; width:auto; height: auto;}iframe{max-width: 100%; width:auto; height: auto;}</style></head>"
        return "<html>$head<body>$bodyHTML</body></html>"
    }


    private fun initGame(game: Game?) {
        val unwrapContext = context ?: return
        val csGame = view?.findViewById<ConstraintLayout>(R.id.csGame)
        val imgGame = view?.findViewById<ImageView>(R.id.imgGame)
        val imgNewGame = view?.findViewById<ImageView>(R.id.imgNewGame)
        val tvTitle = view?.findViewById<TextView>(R.id.tvTitle)
        val tvContentGame = view?.findViewById<TextView>(R.id.tvContentGame)
        if (game == null) {
            csGame?.visibility = View.GONE
            imgGame?.visibility = View.GONE
        } else {
            csGame?.visibility = View.VISIBLE
            imgGame?.visibility = View.VISIBLE

            Glide.with(unwrapContext)
                    .load(game.avatar?.toImage())
                    .apply(bannerOptions)
                    .into(imgGame!!)

            Glide.with(unwrapContext)
                    .load(game.avatar?.toImage())
                    .apply(bannerOptions)
                    .into(imgNewGame!!)

            tvTitle?.text = game.name
            val contentStr = Phrase.from(getString(R.string.follower_amount_and_post_amount))
                    .put("follower", game.follower?.toString() ?: "0")
                    .put("post", game.post?.toString() ?: "0")
                    .format()
            tvContentGame?.text = contentStr
            if (game.follow == true) {
                // check follow here
                setFollowing()
            } else {
                setFollow()
            }
        }
    }
}