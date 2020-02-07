package com.anhtam.gate9.v2.categories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import com.anhtam.domain.Banner
import com.anhtam.domain.v2.Gamev1
import of.bum.network.helper.Resource
import javax.inject.Inject

class CategoryViewmodel @Inject constructor() : ViewModel() {

    private val mBanner: MediatorLiveData<Resource<Banner>> = MediatorLiveData()
    val banner: LiveData<Resource<Banner>>
        get() = mBanner

    private val mNewGame = MediatorLiveData<Resource<List<Gamev1>>>()
    val newGame: LiveData<Resource<List<Gamev1>>>
        get() = mNewGame

    private val mPosts: MediatorLiveData<Resource<List<com.anhtam.domain.Post>>> = MediatorLiveData()
    val posts: LiveData<Resource<List<com.anhtam.domain.Post>>>
        get() = mPosts

    private var mUserAvatar: String?= null
    val userAvatar: String?
    get() = mUserAvatar

    private var mUserId: String?= null
    val userId: String?
        get() = mUserId

    fun requestData(){
    }

}