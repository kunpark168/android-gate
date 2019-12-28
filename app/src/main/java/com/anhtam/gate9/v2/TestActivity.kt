package com.anhtam.gate9.v2

import android.os.Bundle
import android.view.View
import com.anhtam.gate9.R
import com.anhtam.gate9.v2.main.NavigationScreen
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_test.*
import kotlinx.android.synthetic.main.member_item_layout.*
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import timber.log.Timber

class TestActivity : NavigationScreen() {

    val retrofit = Retrofit.Builder()
            .baseUrl("http://183.91.11.93")
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
    override fun layoutOrigin() = R.layout.activity_test

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val service = retrofit.create(TestService::class.java)
        service.call().enqueue(object: Callback<Map<String, Any>> {
            override fun onFailure(call: Call<Map<String, Any>>, t: Throwable) {

            }

            override fun onResponse(call: Call<Map<String, Any>>, response: Response<Map<String, Any>>) {
                val data = response.body()?.get("data") as? Map<String, Any>
                val new_news = data?.get("new_news") as? ArrayList<Map<String, Any>> ?: return
                for (item in new_news) {

                }
                val avatar = new_news[0]["avatar"] as? String
            }

        })
    }
}

interface TestService {
    @GET("api/v1/index?page=0&limit=5&type=new_news")
    fun call(): Call<Map<String, Any>>
}
