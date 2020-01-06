package com.anhtam.gate9.session

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import com.anhtam.domain.v2.User
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
        val navigation: Navigation
) {

    private val cachedUser = MediatorLiveData<AuthResource<User>>()

    private val _accessToken = MutableLiveData<AuthResource<String>>()
    val mAccessToken: LiveData<AuthResource<String>>
        get() = _accessToken

    val user: MutableLiveData<AuthResource<User>>
        get() = cachedUser

    init {
        cachedUser.value = AuthResource.logout()
    }

    fun checkLogin(): Boolean{
        return if (StorageManager.getAccessToken().isEmpty()){
            navigation.addFragment(LoginScreen.newInstance(false))
            return false
        } else true
    }

    fun checkAuthentication(){

    }

    fun logOut(){
        cachedUser.value = AuthResource.logout()
    }

//
//    fun checkLogin(directToLogin: Boolean = true): Boolean{
//        return when(cachedUser.value?.status){
//            AuthResource.AuthStatus.AUTHENTICATED -> {
//                true
//            }
//            AuthResource.AuthStatus.ERROR -> {
//                false
//            }
//            AuthResource.AuthStatus.LOADING -> {
//                false
//            }
//            AuthResource.AuthStatus.NOT_AUTHENTICATED -> {
//                if (directToLogin) navigation.addFragment(LoginScreen.newInstance())
//                false
//            }
//            null -> {
//                false
//            }
//        }
//    }
}