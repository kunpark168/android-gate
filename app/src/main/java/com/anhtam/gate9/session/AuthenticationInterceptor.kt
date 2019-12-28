package com.anhtam.gate9.session

import android.text.TextUtils
import com.anhtam.gate9.storage.StorageManager
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

class AuthenticationInterceptor: Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()
        return when {
            checkRequestIsLoginOrSignUp(original) || !hasAccessToken() -> chain.proceed(original)
            else -> {
                val builder = original.newBuilder()
                builder.header("Authorization", "Bearer " + StorageManager.getAccessToken())
                val request = builder.build()
                chain.proceed(request)
            }
        }
    }

    /**
     *  If Login or Sign up NOT add header TODO
     */
    private fun checkRequestIsLoginOrSignUp(request: Request): Boolean{
        return request.method() == "post" && (request.url().encodedPath().contains("/login"))
    }

    private fun hasAccessToken(): Boolean {
        return !TextUtils.isEmpty(StorageManager.getAccessToken())
    }
}