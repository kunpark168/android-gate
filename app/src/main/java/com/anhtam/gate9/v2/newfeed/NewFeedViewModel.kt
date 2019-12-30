package com.anhtam.gate9.v2.newfeed

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import com.anhtam.domain.v2.WrappedHome
import com.anhtam.gate9.repository.SocialRepository
import of.bum.network.FetchBoundResource
import of.bum.network.helper.LiveDataCallAdapter
import of.bum.network.helper.Resource
import of.bum.network.v2.SocialService
import javax.inject.Inject

/*
 * Common:
 *  - Banner
 *  - Group banners
 *  - User info load Storage ->
 */

class NewFeedViewModel @Inject constructor(
        val repository: SocialRepository) : ViewModel() {

    private val _data = MediatorLiveData<Resource<WrappedHome>>()
    val data: LiveData<Resource<WrappedHome>>
        get() = _data

    fun loadNewFeed(){
        val fetchData = repository.getListingPost()
        _data.addSource(fetchData){
            _data.value = it
        }
    }
}