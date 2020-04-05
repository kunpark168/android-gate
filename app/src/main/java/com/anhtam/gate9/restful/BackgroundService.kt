package com.anhtam.gate9.restful

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface BackgroundService {

    @POST("social/post-follow")
    fun follow(@Body params: Map<String, String>): Call<ResponseBody>
}