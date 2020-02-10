package com.anhtam.gate9.v2.discussion.common

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import com.anhtam.domain.v2.protocol.User
import of.bum.network.helper.Resource

abstract class CommonDiscussionViewModel<T>: ViewModel(){

    private val _data = MediatorLiveData<Resource<List<T>>>()
    val data: LiveData<Resource<List<T>>>
    get() = _data

    var mPage = 0
    var mCategory: Int = 1
    private var mUser: User? = null

    fun initialize(user: User){
        mUser = user
    }

    fun loadData(category: Int? = null, refresh: Boolean = false){
        mPage = if (refresh) 0 else mPage + 1
        mCategory = category ?: mCategory
        val userId = mUser?.mId ?: 0
        val source: LiveData<Resource<List<T>>> = when(mUser?.mRoleId) {
            5 -> getNPHData(userId)
            else -> getUserData(userId)
        }
        _data.addSource(source){
            _data.value = it
            when(it){
                is Resource.Success, is Resource.Error -> {
                    _data.removeSource(source)
                }
            }
        }
    }

    abstract fun getNPHData(userId: Int): LiveData<Resource<List<T>>>
    abstract fun getUserData(userId: Int): LiveData<Resource<List<T>>>
}