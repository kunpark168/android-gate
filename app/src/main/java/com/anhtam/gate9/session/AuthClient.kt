package com.anhtam.gate9.session

import android.text.TextUtils
import com.anhtam.gate9.App
import com.anhtam.gate9.storage.StorageManager
import com.anhtam.gate9.v2.InfoService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber
import javax.inject.Inject

class AuthClient
@Inject constructor(private val mAuthService: AuthService, private val mInfoService: InfoService) {

    fun loginWithPassword(email: String, password: String, callback: AuthCallBack) {
        val params = hashMapOf<String, String>()
        params["grant_type"] = "password"
        params["username"] = email
        params["password"] = password
        //grant_type=password&username=2&password=2&client_id=user&client_secret=user
        params["client_id"] = "user"
        params["client_secret"] = "user"
        requestAccessToken(email, password, callback)
    }

    private fun requestAccessToken(email: String, password: String, callback: AuthCallBack) {
        mAuthService.loginV2(userName = email, password = password).enqueue(object: Callback<Map<String, Any?>> {
            override fun onFailure(call: Call<Map<String, Any?>>, t: Throwable) {
                t.message?.let { callback.onUnauthorized(it) }
            }

            override fun onResponse(call: Call<Map<String, Any?>>, response: Response<Map<String, Any?>>) {
                if (response.isSuccessful && response.code() == 200) {
                    val data = response.body() ?: return callback.onUnauthorized("Response empty")
//                    data.token ?: return callback.onUnauthorized(data.message ?: "")
                    val accessToken = data["access_token"] as? String ?: return callback.onUnauthorized("Access token empty")
                    data.let {
                        onAuthSuccess(accessToken)
                        callback.onAuthorized()
                        return
                    }
                }
                if (!App.isInternetAvailable()) {
                    // TODO permission NETWORK == Bottom Dialog
                    requestAccessToken(email, password, callback)

                }
                response.errorBody()?.string()?.let { callback.onUnauthorized(it) }
            }

        })
    }

    private fun onAuthSuccess(accessToken : String) {
        StorageManager.setAccessToken(accessToken)
//        authResponse.user?.let {
//            StorageManager.setUserId(it.user_id)
//            StorageManager.setUserAvatar(it.avatar)
//        }
        // Clear all service before
        // TODO
    }


    fun checkAuth(callback: AuthCallBack) {
        if (!App.isInternetAvailable()) {
            //TODO permission
            checkAuth(callback)
            return
        }

        if(!hasAccessToken()){
            callback.onUnauthorized("Unauthorized")
            return
        }
        callback.onAuthorized()
    }

    private fun hasAccessToken(): Boolean {
        return !TextUtils.isEmpty(StorageManager.getAccessToken())
    }

    companion object {
        fun logout() {
            StorageManager.deleteAll()
            // Clear all service before
            // TODO
        }
    }
}