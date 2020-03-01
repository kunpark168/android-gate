package com.anhtam.gate9.session

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.anhtam.gate9.App
import com.anhtam.gate9.di.scope.MainScope
import com.anhtam.gate9.storage.StorageManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@MainScope
class AuthClient
@Inject constructor(private val mAuthService: AuthService) {


    fun loginWithPassword(email: String, password: String) {
        _accessToken.value = AuthResource.loading(null)
        val param = createParams(email, password)
        requestAccessToken(email, password, param)
    }

    private fun createParams(email: String, password: String): Map<String, String>{
        val params = hashMapOf<String, String>()
        params["grant_type"] = "password"
        params["username"] = email
        params["password"] = password
        params["client_id"] = "user"
        params["client_secret"] = "user"
        return params
    }

    private fun requestAccessToken(email: String, password: String, params: Map<String, String>) {
        mAuthService.loginV2(userName = email, password = password).enqueue(object: Callback<Map<String, Any?>> {
            override fun onFailure(call: Call<Map<String, Any?>>, t: Throwable) {
                _accessToken.value = AuthResource.error(t.message ?: "", null)
            }

            override fun onResponse(call: Call<Map<String, Any?>>, response: Response<Map<String, Any?>>) {
                if (response.isSuccessful && response.code() == 200) {
                    val data = response.body()
                    if (data == null) {
                        _accessToken.value = AuthResource.error("Response empty", null)
                    } else {
                        val accessToken = data["access_token"] as? String
                        if (accessToken.isNullOrEmpty()) {
                            _accessToken.value = AuthResource.error("Access token empty", null)
                        } else {
                            _accessToken.value = AuthResource.authenticated(accessToken)
                        }
                    }
                }
                if (!App.isInternetAvailable()) {
                    // TODO permission NETWORK == Bottom Dialog
                    requestAccessToken(email, password, params)

                }
                _accessToken.value = AuthResource.error(response.errorBody()?.string() ?: "", null)
            }

        })
    }

    private val _accessToken = MutableLiveData<AuthResource<String>>()
    val mAccessToken: LiveData<AuthResource<String>>
        get() = _accessToken

    companion object {
        fun logout() {
            StorageManager.deleteAll()
            // Clear all service before
            // TODO
        }
    }
}