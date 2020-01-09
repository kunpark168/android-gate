package com.anhtam.gate9.session

import androidx.lifecycle.MediatorLiveData
import com.anhtam.gate9.di.scope.MainScope
import com.anhtam.gate9.navigation.Navigation
import com.anhtam.gate9.storage.StorageManager
import com.anhtam.gate9.v2.auth.login.LoginScreen
import javax.inject.Inject

/*
 * Login response access_token -> request api
 * Get user -> check has User
 */
@MainScope
class SessionManager @Inject constructor(
        val navigation: Navigation,
        private val mAuthClient: AuthClient
) {
    private val mAccessToken = MediatorLiveData<AuthResource<String>>()


    fun authenticatedWithEmail(email: String, password: String){
        mAuthClient.loginWithPassword(email, password)
        val source = mAuthClient.mAccessToken
        mAccessToken.value = AuthResource.loading(null)
        mAccessToken.addSource(source) { userAuthResource ->
            mAccessToken.value = userAuthResource
            mAccessToken.removeSource(source)

            if (userAuthResource.status.equals(AuthResource.AuthStatus.ERROR)) {
                mAccessToken.value = AuthResource.logout()
            }
        }
    }

    fun checkLogin(): Boolean{
        return if (StorageManager.getAccessToken().isEmpty()){
            navigation.addFragment(LoginScreen.newInstance(false))
            return false
        } else true
    }

    fun logOut(){
        mAccessToken.value = AuthResource.logout()
    }
}