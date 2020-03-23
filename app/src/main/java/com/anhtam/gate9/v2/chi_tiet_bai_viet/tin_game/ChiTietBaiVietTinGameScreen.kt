package com.anhtam.gate9.v2.chi_tiet_bai_viet.tin_game

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.anhtam.domain.v2.Article
import com.anhtam.domain.v2.protocol.Game
import com.anhtam.domain.v2.protocol.User
import com.anhtam.gate9.R
import com.anhtam.gate9.share.view.donate.DonateDialog
import com.anhtam.gate9.utils.toImage
import com.anhtam.gate9.v2.BackgroundViewModel
import com.anhtam.gate9.v2.chi_tiet_bai_viet.ChiTietBaiVietScreen
import com.anhtam.gate9.v2.game_detail.download.DownloadGameFragment
import com.anhtam.gate9.v2.newfeed.SliderAdapter
import com.bumptech.glide.Glide
import com.squareup.phrase.Phrase
import kotlinx.android.synthetic.main.bottom_bar_type_layout.*
import kotlinx.android.synthetic.main.chi_tiet_bai_viet_screen.*
import of.bum.network.helper.Resource

class ChiTietBaiVietTinGameScreen private constructor(
        mId: Int,
        private val mArticle: Article?,
        mTab: Int
): ChiTietBaiVietScreen(mId,
        mArticle,
        R.layout.chi_tiet_bai_viet_screen,
        mTab) {

    private var mBannerAdapter: SliderAdapter? = null
    private val mTopViewModel: BackgroundViewModel by viewModels({requireActivity()}, {vmFactory})
    companion object {
        fun newInstance(id: Int, article: Article?, tab: Int) = ChiTietBaiVietTinGameScreen(id, article, tab)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initEvents()
        observerBanner()
    }


    private fun initEvents() {
        imgAvatar?.setOnClickListener { navigateToUser() }
        tvUserName?.setOnClickListener { navigateToUser() }

        imgNewGame?.setOnClickListener { navigateToGame() }
        imgGame?.setOnClickListener { navigateToGame() }
        tvTitle?.setOnClickListener { navigateToGame() }
        tvDownload?.setOnClickListener {
            navigation?.addFragment(DownloadGameFragment.newInstance(mArticle?.mGame ?: return@setOnClickListener))
        }
        btnDonate?.setOnClickListener {
            val unwrapContext = context ?: return@setOnClickListener
            DonateDialog(unwrapContext).show()
        }
    }

    private fun observerBanner() {
        mTopViewModel.banners.observe(viewLifecycleOwner, Observer {resource ->
            if (resource is Resource.Success) {
                mBannerAdapter?.setData(resource.data?.map { it.url ?: "" } ?: mutableListOf())
            }
        })
    }

    private fun initView() {
        articleContentWebView?.settings?.javaScriptEnabled = true
        concernRecyclerView?.apply {
            mConcernAdapter.setOnItemClickListener { _, _, position ->
                val article = mConcernAdapter.getItem(position)
                val id = article?.mArticleId ?: return@setOnItemClickListener
                when(val type = article.mArticleType ?: return@setOnItemClickListener) {
                    0, 3 -> navigation?.addFragment(newInstance(id, article, type))
                    2 -> navigation?.addFragment(ChiTietBaiVietVideoScreen.newInstance(id, article, type))
                    4 -> navigation?.addFragment(ChiTietBaiVietHinhAnhScreen.newInstance(id, article, type))
                    else -> return@setOnItemClickListener
                }
            }
            adapter = mConcernAdapter
            layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
        }
        newRecyclerView?.apply {
            mNewListAdapter.setOnItemClickListener { _, _, position ->
                val article = mNewListAdapter.getItem(position)
                val id = article?.mArticleId ?: return@setOnItemClickListener

                when(val type = article.mArticleType ?: return@setOnItemClickListener) {
                    0, 3 -> navigation?.addFragment(newInstance(id, article, type))
                    2 -> navigation?.addFragment(ChiTietBaiVietVideoScreen.newInstance(id, article, type))
                    4 -> navigation?.addFragment(ChiTietBaiVietHinhAnhScreen.newInstance(id, article, type))
                    else -> return@setOnItemClickListener
                }
            }
            adapter = mNewListAdapter
            layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
        }


        val unwrapContext = context ?: return
        mBannerAdapter = SliderAdapter(unwrapContext, bannerOptions)
        slider?.adapter = mBannerAdapter
    }

    override fun updateUI() {

        val user: User? = mWrapArticle?.mArticle?.mUser
        view?.findViewById<ImageView>(R.id.imgAvatar)?.run {
            Glide.with(this)
                    .load(user?.mAvatar?.toImage())
                    .apply(avatarOptions)
                    .into(this)
        }
        tvUserName?.text = user?.mName
        val follow = Phrase.from(getString(R.string.following_amount_and_follower_amount))
                .put("following", user?.mFlowing?.toString() ?: "0")
                .put("follower", user?.mFlower?.toString() ?: "0")
                .format()
        tvFollowNumber?.text = follow

        val article = mWrapArticle?.mArticle
        val content = article?.mContent
        articleContentWebView?.loadData(getHtmlData(content), "text/html", "UTF-8")
        articleTitleTextView?.text = mArticle?.mTitle
        articleCreateTimeTextView?.text = mArticle?.mCreatedDate
        articleTypeTextView?.text = mArticle?.mCategory?.mCategoryName
        //
        val game: Game? = mWrapArticle?.mArticle?.mGame
        initGame(game)
        mConcernAdapter.setNewData(mWrapArticle?.mConcernList)
        mNewListAdapter.setNewData(mWrapArticle?.mNewList)
    }
}