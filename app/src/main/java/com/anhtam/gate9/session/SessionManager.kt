package com.anhtam.gate9.session

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.anhtam.domain.v2.Userv1
import com.anhtam.gate9.di.scope.MainScope
import com.anhtam.gate9.navigation.Navigation
import com.anhtam.gate9.repository.SocialRepository
import com.anhtam.gate9.storage.StorageManager
import com.anhtam.gate9.utils.AbsentLiveData
import com.anhtam.gate9.v2.auth.login.LoginScreen
import of.bum.network.helper.Resource
import javax.inject.Inject

/*
 * Login response access_token -> request api
 * Get user -> check has User
 */
@MainScope
class SessionManager @Inject constructor(
        val navigation: Navigation,
        private val mAuthClient: AuthClient,
        private val repository: SocialRepository
) {

    private val mAccessToken = MediatorLiveData<AuthResource<String>>()
    val cachedAccessToken: MutableLiveData<AuthResource<String>>
        get() = mAccessToken

    fun initialize(){
        mAccessToken.value = AuthResource.loading(null)
        val cachedAccessToken = StorageManager.getAccessToken()
        if (cachedAccessToken.isEmpty()){
            mAccessToken.value = AuthResource.logout()
        } else {
            mAccessToken.value = AuthResource.authenticated(cachedAccessToken)
        }
    }

    fun logout(){
        mAccessToken.value = AuthResource.logout()
        StorageManager.deleteAll()
    }

    val cachedUser: LiveData<Resource<Userv1>> = Transformations.switchMap(cachedAccessToken){
        if (it == null || it.status != AuthResource.AuthStatus.AUTHENTICATED) AbsentLiveData.create()
        else repository.getUserDetail()
    }

    fun authenticatedWithEmail(email: String, password: String){
        mAuthClient.loginWithPassword(email, password)
        val source = mAuthClient.mAccessToken
        mAccessToken.value = AuthResource.loading(null)
        mAccessToken.addSource(source) { userAuthResource ->
            when(userAuthResource.status){
                AuthResource.AuthStatus.AUTHENTICATED -> {
                    StorageManager.setAccessToken(userAuthResource.data!!)
                    mAccessToken.value = userAuthResource
                    mAccessToken.removeSource(source)
                }
                AuthResource.AuthStatus.NOT_AUTHENTICATED -> {
                    mAccessToken.value = userAuthResource
                    mAccessToken.removeSource(source)
                }
                AuthResource.AuthStatus.ERROR -> {
                    mAccessToken.value = AuthResource.logout()
                    mAccessToken.removeSource(source)
                }
                AuthResource.AuthStatus.LOADING -> {}
            }
        }
    }

    fun checkLogin(isDirect: Boolean = false): Boolean{
        return if (cachedAccessToken.value?.status != AuthResource.AuthStatus.AUTHENTICATED){
            if (isDirect) {
                navigation.addFragment(LoginScreen.newInstance(false))
            }
            return false
        } else true
    }
}