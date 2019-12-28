package com.anhtam.gate9.session

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Query

interface AuthService {
    @POST("uaa/oauth/token")
    fun loginV2(@Query("grant_type") grantType : String = "password",
                @Query("username") userName: String,
                @Query("password") password: String,
                @Query("client_id") clientId: String = "user",
                @Query("client_secret") clientSecret: String = "user"): Call<Map<String, Any?>>
}