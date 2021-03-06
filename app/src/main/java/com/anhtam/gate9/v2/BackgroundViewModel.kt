package com.anhtam.gate9.v2

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import com.anhtam.domain.Base
import com.anhtam.domain.v2.Banner
import com.anhtam.domain.v2.Gamev2
import com.anhtam.gate9.repository.SocialRepository
import of.bum.network.helper.Resource
import javax.inject.Inject

class BackgroundViewModel @Inject constructor(
        private val repos: SocialRepository

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
        _banners.addSource(repos.getBanners()){
            _banners.value = it
        }
    }

    fun getGames(){
        _games.addSource(repos.getGameNominate()){
            _games.value = it
        }
    }

    fun delete(id: Long): LiveData<Resource<Base>> {
        return repos.deletePost(id)
    }

}