package com.anhtam.gate9.v2

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import com.anhtam.domain.Base
import com.anhtam.domain.v2.Banner
import com.anhtam.domain.v2.Gamev1
import com.anhtam.domain.v2.Gamev2
import com.anhtam.gate9.repository.SocialRepository
import of.bum.network.FetchBoundResource
import of.bum.network.helper.Resource
import javax.inject.Inject

class BackgroundViewModel @Inject constructor(
        private val repository: SocialRepository

): ViewModel(){

    private val _banners = MediatorLiveData<Resource<List<Banner>>>()
    val banners: LiveData<Resource<List<Banner>>>
        get() = _banners

    private val _games = MediatorLiveData<Resource<List<Gamev2>>>()
    val games: LiveData<Resource<List<Gamev2>>>
        get() = _games

    init {
        getBanner()
        getGames()
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