package com.anhtam.gate9.v2

import com.anhtam.domain.Base
import com.anhtam.domain.v2.User
import of.bum.network.helper.RestResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface InfoService {

    @POST("api/v1/social/post-like")
    fun react(@Body params: Map<String, Int>): Call<Base>
}