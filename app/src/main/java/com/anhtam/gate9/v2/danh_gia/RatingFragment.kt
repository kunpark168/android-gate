package com.anhtam.gate9.v2.danh_gia

import android.os.Bundle
import android.view.View
import com.anhtam.gate9.R
import com.anhtam.gate9.v2.main.DaggerNavigationFragment
import kotlinx.android.synthetic.main.rating_fragment.*
import of.bum.network.v2.MediaService
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class RatingFragment : DaggerNavigationFragment(R.layout.rating_fragment) {

    @Inject
    lateinit var mediaService: MediaService
    private var mGameId: Int? = null
    private var mUserId: Int? = null
    private var mRating: Float = 0.0f

    companion object {
        fun newInstance(id: Int, isUser: Boolean = true, rating: Float): RatingFragment {
            val fragment = RatingFragment()
            if (isUser) {
                fragment.mUserId = id
            } else {
                fragment.mGameId = id
            }
            fragment.mRating = rating
            return fragment
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rtReview?.rating = mRating
        initEvent()
    }

    private fun initEvent() {
        tvPost?.setOnClickListener {
            hideKeyboard()
            if (rtReview.rating == 0.0f || edtPostContent.text.isNullOrEmpty()) return@setOnClickListener
            val rating = rtReview.rating
            val content = edtPostContent.text.toString()

            val params = if (mUserId == null) gameParams(rating, content) else userParams(rating, content)

            mediaService.uploadRating(params).enqueue(object : Callback<ResponseBody> {
                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                }

                override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                    hideProgress()
                    if (response.isSuccessful && response.code() == 200) {
                        navigation?.returnResult(Bundle())
                    }
                }
            })
        }
        rtReview?.setOnRatingBarChangeListener { _, rating, _ ->
            when (rating.toInt()) {
                in 0..1 -> {
                    tvStatusRating?.text = "Poor"
                }
                else -> {
                    tvStatusRating?.text = "Good"
                }
            }
        }
    }

    private fun userParams(rating: Float, content: String): Map<String, Any> {
        val params = HashMap<String, Any>()
        params["userId"] = mUserId ?: 0
        params["content"] = content
        params["point"] = rating
        params["child"] = 0
        params["gameId"] = 0
        return params
    }

    private fun gameParams(rating: Float, content: String): Map<String, Any> {
        val params = HashMap<String, Any>()
        params["userId"] = 0
        params["content"] = content
        params["point"] = rating
        params["child"] = 0
        params["gameId"] = mGameId ?: 0
        return params
    }

}