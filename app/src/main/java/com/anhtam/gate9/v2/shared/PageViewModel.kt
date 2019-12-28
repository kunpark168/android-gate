package com.anhtam.gate9.v2.shared

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import com.anhtam.gate9.utils.setValueDiff
import of.bum.network.helper.Resource

abstract class PageViewModel<T> : ViewModel() {

    private val mData: MediatorLiveData<Resource<List<T>>> = MediatorLiveData()
    val data: LiveData<Resource<List<T>>>
        get() = mData

    private var mPage = 0
    val page: Int
        get() = mPage

    abstract fun request(pageNumber: Int): LiveData<Resource<List<T>>>

    fun requestFirstPage() {
        mPage = 0
        val newData = request(pageNumber = 0)
        mData.addSource(newData) {
            it?.let { mData.setValueDiff(it) }
        }
    }

    fun requestMore() {
        mPage++
        val newData = request(page)
        mData.addSource(newData) {
            it?.let { mData.setValueDiff(it) }
        }
    }

}