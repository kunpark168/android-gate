package com.anhtam.gate9.v2.newfeed

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import com.anhtam.domain.Banner
import com.anhtam.domain.v2.Gamev1
import com.anhtam.domain.v2.Gamev2
import com.anhtam.domain.v2.Post
import com.anhtam.gate9.repository.SocialRepository
import of.bum.network.helper.Resource
import javax.inject.Inject

/*
 * Common:
 *  - Banner
 *  - Group banners
 *  - User info load Storage ->
 */

class NewFeedViewModel @Inject constructor(
        val repository: SocialRepository) : ViewModel() {

    private val _data = MediatorLiveData<Resource<List<Post>>>()
    val data: LiveData<Resource<List<Post>>>
        get() = _data

    var mPage = 0
    private var mTab: Int = 1

    private val _banners = MediatorLiveData<Resource<List<Banner>>>()
    val banners: LiveData<Resource<List<Banner>>>
        get() = _banners

    private val _games = MediatorLiveData<Resource<List<Gamev1>>>()
    val games: LiveData<Resource<List<Gamev1>>>
        get() = _games

    fun loadNewFeed(){
        val fetchData = repository.getListingPost()
        _data.addSource(fetchData){
            _data.value = it
        }
    }

    fun loadFirstPage(tab: Int){
        mPage = 0
        mTab = tab
        val fetchData = repository.getFollowingInfo(mTab, mPage)
        _data.addSource(fetchData){
            _data.value = it
        }
    }

    fun loadMore(){
        mPage++
        val fetchData = repository.getFollowingInfo(mTab, mPage)
        _data.addSource(fetchData){
            _data.value = it
        }
    }

    fun getBanner(){
        _banners.addSource(repository.getBanners()){
            _banners.value = it
        }
    }

    fun getGames(){
        _games.addSource(repository.getGameNominate()){
            _games.value = it
        }
    }

    fun follow(){
        repository.follow()
    }

}