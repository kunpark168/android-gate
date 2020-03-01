package com.anhtam.gate9.v2.chi_tiet_bai_viet

import android.os.Bundle
import android.text.Html
import android.view.View
import android.widget.ImageView
import androidx.annotation.LayoutRes
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
import com.anhtam.gate9.utils.toImage
import com.anhtam.gate9.v2.main.DaggerNavigationFragment
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.squareup.phrase.Phrase
import kotlinx.android.synthetic.main.chi_tiet_bai_viet_screen.*
import of.bum.network.helper.Resource
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadData()
        initView()
        observer()
    }

    private fun loadData() {
        showProgress()
        mViewModel.mId.value = mId
    }

    private fun initView() {
        articleContentWebView?.settings?.javaScriptEnabled = true
        concernRecyclerView?.adapter = mConcernAdapter
        concernRecyclerView?.layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
        newRecyclerView?.adapter = mNewListAdapter
        newRecyclerView?.layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
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
        articleContentWebView?.loadData(getHtmlData(content), "text/html", "UTF-8")
        articleTitleTextView?.text = mArticle?.mTitle
        articleCreateTimeTextView?.text = mArticle?.mCreatedTime

        //
        val game: Game? = mWrapArticle?.mArticle?.mGame
        initGame(game)
        mConcernAdapter.setNewData(mWrapArticle?.mConcernList)
        mNewListAdapter.setNewData(mWrapArticle?.mNewList)
    }

    private fun initPhoto(rv: RecyclerView) {
        val unwrapNav = navigation ?: return
        mPhotoAdapter = PhotoAdapter(unwrapNav, bannerOptions)
        rv.adapter = mPhotoAdapter
        val photos = mArticle?.mPhotos ?: return
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

    private fun getHtmlData(bodyHTML: String?): String {
        val head = "<head><style>img{max-width: 100%; width:auto; height: auto;}iframe{max-width: 100%; width:auto; height: auto;}</style></head>"
        return "<html>$head<body>$bodyHTML</body></html>"
    }


    private fun initGame(game: Game?) {
        val unwrapContext = context ?: return
        if (game == null) {
            csGame?.visibility = View.GONE
            imgGame?.visibility = View.GONE
        } else {
            csGame?.visibility = View.VISIBLE
            imgGame?.visibility = View.VISIBLE

            Glide.with(unwrapContext)
                    .load(game.avatar?.toImage())
                    .apply(bannerOptions)
                    .into(imgGame)

            Glide.with(unwrapContext)
                    .load(game.avatar?.toImage())
                    .apply(bannerOptions)
                    .into(imgNewGame)

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