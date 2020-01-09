package com.anhtam.gate9.session

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Query

interface AuthService {
    @POST("uaa/oauth/token")
    fun loginV2(@Body params: Map<String, String>): Call<Map<String, Any?>>
}