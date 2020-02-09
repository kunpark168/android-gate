package com.anhtam.gate9.restful

import com.anhtam.gate9.config.Config
import com.anhtam.gate9.session.AuthenticationInterceptor
import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import timber.log.Timber

class BackgroundTasks{

    companion object{
        private var mService: BackgroundService? = null
        private fun getService(): BackgroundService = mService ?: synchronized(BackgroundTasks::class.java){
            mService ?: Retrofit.Builder()
                    .baseUrl(Config.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(OkHttpClient.Builder().addInterceptor(AuthenticationInterceptor()).build())
                    .build()
                    .create(BackgroundService::class.java)
                    .also { mService = it }
        }

        fun postUserFollow(userId: Int, roleId: Int){
            val params = hashMapOf<String, String>()
            params["userId"] = userId.toString()
            params["roleId"] = roleId.toString()
            params["gameId"] = "0"
            getService().follow(params).enqueue(object: Callback<ResponseBody>{
                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    Timber.d("Fail to follow $userId")
                }

                override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                    Timber.d("User just follow $userId")
                }

            })
        }

        fun postFollowGame(gameId: Int){
            val params = hashMapOf<String, String>()
            params["userId"] = "0"
            params["roleId"] = "0"
            params["gameId"] = gameId.toString()
            getService().follow(params).enqueue(object: Callback<ResponseBody>{
                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    Timber.d("Fail to follow $gameId")
                }

                override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                    Timber.d("User just follow $gameId")
                }

            })
        }
    }



}