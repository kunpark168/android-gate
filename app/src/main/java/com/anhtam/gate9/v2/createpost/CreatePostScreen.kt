package com.anhtam.gate9.v2.createpost

import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import com.anhtam.domain.v2.protocol.Game
import com.anhtam.gate9.R
import com.anhtam.gate9.adapter.v2.ChooseGalleryAdapter
import com.anhtam.gate9.restful.BackgroundTasks
import com.anhtam.gate9.utils.toImage
import com.anhtam.gate9.v2.shared.AbstractGalleryFragment
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.squareup.phrase.Phrase
import kotlinx.android.synthetic.main.create_post_screen.*
import of.bum.network.v2.MediaService
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Named

class CreatePostScreen(
        private val mGame: Game?
) : AbstractGalleryFragment(R.layout.create_post_screen) {

    @Inject lateinit var mediaService: MediaService
    @Inject lateinit var mAdapter: ChooseGalleryAdapter
    @field:Named("banner") @Inject lateinit var bannerOptions: RequestOptions
    private var mIsFollowing = false

    companion object {
        fun newInstance(game: Game? = null) = CreatePostScreen(game)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        eventClicks()
        initView()
    }

    private fun initView() {
        mAdapter.setOnItemChildClickListener { _, _, position ->
            mAdapter.remove(position)
            if (mAdapter.data.isEmpty()){
                imageRecyclerView.visibility = View.GONE
            }
        }
        imageRecyclerView?.adapter = mAdapter
        if (mGame != null){
            mIsFollowing = mGame.follow ?: false
            gameLayout?.visibility = View.VISIBLE

            Glide.with(this)
                    .load(mGame.avatar?.toImage())
                    .apply(bannerOptions)
                    .into(imgNewGame)
            tvTitle?.text = mGame.name
            val contentStr = Phrase.from(getString(R.string.follower_amount_and_post_amount))
                    .put("follower", mGame.follower?.toString() ?: "0")
                    .put("post", mGame.post?.toString() ?: "0")
                    .format()
            tvContentGame?.text = contentStr
            onUpdateFollow()
            tvFollowGame?.setOnClickListener {
                val id = mGame.gameId ?: return@setOnClickListener
                if (mSessionManager.checkLogin(isDirect = true)){
                    mIsFollowing = !mIsFollowing
                    onUpdateFollow()
                    BackgroundTasks.postFollowGame(id)
                }
            }
        }
    }

    private fun onUpdateFollow(){
        if (mIsFollowing){
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

    private fun eventClicks() {
        tvCancel?.setOnClickListener {
            navigation?.back()
        }
        imgPhoto?.setOnClickListener {
            chooseImage()
        }
        tvPost?.setOnClickListener {
            hideKeyboard()
            postForum()
        }
    }

    override fun onSelectedImage(urls: List<String>) {
        super.onSelectedImage(urls)
        mAdapter.addData(urls)
        imageRecyclerView?.visibility = View.VISIBLE
    }

    private fun postForum() {
        val params = HashMap<String, Any>()
        params["appKey"] = "0mg0y5suxqa1vkaufens"
        params["content"] = edtPostContent?.text?.toString() ?: ""
        params["domain"] = "https://www.9gate.net/"
        params["domainCrc"] = "3891665734"
        params["gameId"] = mGame?.gameId ?: 0
        params["link"] = "https://www.9gate.net/social"
        params["linkCrc"] = "3891665734"
        params["parentId"] = 0
        params["photos"] = mAdapter.data
        params["title"] = "Tao new post"

        mediaService.updatePostForum(params).enqueue(object: Callback<ResponseBody>{
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Timber.d("")
                hideProgress()
            }

            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                hideProgress()
                if (response.isSuccessful && response.code() == 200) {
                    navigation?.returnResult(Bundle())
                }
            }
        })
    }
}