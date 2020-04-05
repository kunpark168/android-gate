package com.anhtam.gate9.v2.newfeed

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import of.bum.network.helper.Resource

abstract class PagingViewModel<T>: ViewModel(){

    val _data = MediatorLiveData<Resource<List<T>>>()
    val data: LiveData<Resource<List<T>>>
    get() = _data

    var mPage = 0

    fun loadData(refresh: Boolean = false){
        mPage = if (refresh) 0 else mPage + 1
        val source: LiveData<Resource<List<T>>> = fetchData()
        _data.addSource(source){
            _data.value = it
            when(it){
                is Resource.Success, is Resource.Error -> {
                    _data.removeSource(source)
                }
            }
        }
    }

    abstract fun fetchData(): LiveData<Resource<List<T>>>
}