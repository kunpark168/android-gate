package com.anhtam.gate9.v2.createpost

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import com.anhtam.gate9.R
import com.anhtam.gate9.adapter.v2.ChooseGalleryAdapter
import com.anhtam.gate9.v2.shared.AbstractGalleryFragment
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.create_post_screen.*
import of.bum.network.v2.MediaService
import of.bum.network.v2.SocialService
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Named

class CreatePostScreen : AbstractGalleryFragment() {

    @Inject lateinit var mediaService: MediaService
    @Inject lateinit var mAdapter: ChooseGalleryAdapter

    @field:Named("banner") @Inject lateinit var bannerOptions: RequestOptions

    companion object {
        fun newInstance()= CreatePostScreen()
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        activity?.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
        return inflater.inflate(R.layout.create_post_screen, container, false)
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
    }

    private fun eventClicks() {
        tvCancel?.setOnClickListener {
            navigation?.back()
        }
        imgPhoto?.setOnClickListener {
            chooseImage()
        }
        tvPost?.setOnClickListener {
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
        params["gameId"] = 0
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