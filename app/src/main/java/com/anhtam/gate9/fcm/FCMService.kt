package com.anhtam.gate9.fcm

import retrofit2.Call
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Query

interface FCMService {
    @POST("user/add-token")
    fun addTokenFCM(@Query("userId") idUser: Int,
                    @Query("token") token: String): Call<Map<String, Any>>

    @PUT("user/update-token")
    fun updateTokenFCM(@Query("userId") idUser: Int,
                       @Query("token") token: String): Call<Map<String, Any>>
}