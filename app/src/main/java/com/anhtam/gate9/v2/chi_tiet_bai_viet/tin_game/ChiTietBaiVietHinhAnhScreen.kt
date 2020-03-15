package com.anhtam.gate9.v2.chi_tiet_bai_viet.tin_game

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.anhtam.domain.v2.Article
import com.anhtam.domain.v2.protocol.Game
import com.anhtam.domain.v2.protocol.User
import com.anhtam.gate9.R
import com.anhtam.gate9.adapter.v2.PhotoAdapter
import com.anhtam.gate9.utils.toImage
import com.anhtam.gate9.v2.chi_tiet_bai_viet.ChiTietBaiVietScreen
import com.anhtam.gate9.v2.game_detail.download.DownloadGameFragment
import com.bumptech.glide.Glide
import com.squareup.phrase.Phrase
import kotlinx.android.synthetic.main.chi_tiet_anh_screen.*

class ChiTietBaiVietHinhAnhScreen private constructor(
        mId: Int,
        private val mArticle: Article?,
        mTab: Int
): ChiTietBaiVietScreen(mId,
        mArticle,
        R.layout.chi_tiet_anh_screen,
        mTab) {

    private var mPhotoAdapter: PhotoAdapter? = null

    companion object {
        fun newInstance(id: Int, article: Article?, tab: Int) = ChiTietBaiVietHinhAnhScreen(id, article, tab)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initEvents()
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
    }

    private fun initView() {
        concernRecyclerView?.apply {
            mConcernAdapter.setOnItemClickListener { _, _, position ->
                val article = mConcernAdapter.getItem(position)
                val id = article?.mId ?: return@setOnItemClickListener

                when(val type = article.mArticleType ?: return@setOnItemClickListener) {
                    0, 3 -> navigation?.addFragment(newInstance(id, article, type))
                    2 -> navigation?.addFragment(ChiTietBaiVietVideoScreen.newInstance(id, article, type))
                    4 -> navigation?.addFragment(newInstance(id, article, type))
                    else -> return@setOnItemClickListener
                }
            }
            adapter = mConcernAdapter
            layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
        }
        newRecyclerView?.apply {
            mNewListAdapter.setOnItemClickListener { _, _, position ->
                val article = mNewListAdapter.getItem(position)
                val id = article?.mId ?: return@setOnItemClickListener

                when(val type = article.mArticleType ?: return@setOnItemClickListener) {
                    0, 3 -> navigation?.addFragment(ChiTietBaiVietTinGameScreen.newInstance(id, article, type))
                    2 -> navigation?.addFragment(ChiTietBaiVietVideoScreen.newInstance(id, article, type))
                    4 -> navigation?.addFragment(newInstance(id, article, type))
                    else -> return@setOnItemClickListener
                }
            }
            adapter = mNewListAdapter
            layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
        }


        val unwrapNav = navigation ?: return
        mPhotoAdapter = PhotoAdapter(unwrapNav, bannerOptions)
        mPhotoAdapter?.user = mArticle?.mUser ?: return
        rvPhotos?.adapter = mPhotoAdapter
    }

    override fun updateUI() {
        updatePhoto()
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
        articleSapoTextView?.text = article?.mSapo
        articleTitleTextView?.text = mArticle?.mTitle
        articleCreateTimeTextView?.text = mArticle?.mCreatedDate
        articleTypeTextView?.text = mArticle?.mCategory?.mCategoryName
        //
        val game: Game? = mWrapArticle?.mArticle?.mGame
        initGame(game)
        mConcernAdapter.setNewData(mWrapArticle?.mConcernList)
        mNewListAdapter.setNewData(mWrapArticle?.mNewList)
    }


    private fun updatePhoto() {
        val photos = mArticle?.mPhotos ?: return
//        val isFormat = "[[.*]]".toRegex().matches(photos) TODO Regex
        val isFormat = (photos.startsWith('[') && photos.endsWith(']'))
        val stringConcat = if (!isFormat) {
            photos
        } else {
            photos.substring(1, photos.length - 1)
        }
        val spanCount = mPhotoAdapter!!.setPhoto(stringConcat)
        rvPhotos?.layoutManager = GridLayoutManager(context, spanCount)
    }
}