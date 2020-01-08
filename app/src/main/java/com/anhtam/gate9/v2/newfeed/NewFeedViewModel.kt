package com.anhtam.gate9.v2.newfeed

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import com.anhtam.domain.Banner
import com.anhtam.domain.v2.Game
import com.anhtam.domain.v2.GameEntity
import com.anhtam.domain.v2.PostEntity
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

    private val _data = MediatorLiveData<Resource<List<PostEntity>>>()
    val data: LiveData<Resource<List<PostEntity>>>
        get() = _data

    private val _banners = MediatorLiveData<Resource<List<Banner>>>()
    val banners: LiveData<Resource<List<Banner>>>
        get() = _banners

    private val _games = MediatorLiveData<Resource<List<com.anhtam.domain.Game>>>()
    val games: LiveData<Resource<List<com.anhtam.domain.Game>>>
        get() = _games

    fun loadNewFeed(){
        val fetchData = repository.getListingPost()
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

}