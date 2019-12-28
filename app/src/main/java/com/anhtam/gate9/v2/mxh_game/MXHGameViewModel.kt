package com.anhtam.gate9.v2.mxh_game

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import com.anhtam.domain.v2.GameEntity
import com.anhtam.domain.v2.WrapGame
import com.anhtam.domain.v2.wrap.WrapListing
import com.anhtam.gate9.repository.SocialRepository
import com.anhtam.gate9.v2.shared.PageViewModel
import of.bum.network.FetchBoundResource
import of.bum.network.helper.Resource
import of.bum.network.v2.GameService
import of.bum.network.v2.SocialService
import javax.inject.Inject

class MXHGameViewModel @Inject constructor(private val repository: SocialRepository) : ViewModel() {
    val _game = MediatorLiveData<Resource<WrapGame>>()
    private var mPage = 0
    val page: Int
        get() = mPage
    var type = MXHGameScreen.MXHGameTab.ALL

    fun request(tab: MXHGameScreen.MXHGameTab){
        mPage = 0
        type = tab
        _game.addSource(repository.getGamesByTab(mPage, tab.data)){
            _game.value = it
        }
    }

    fun requestMore(){
        mPage++
        _game.addSource(repository.getGamesByTab(mPage, type.data)){
            _game.value = it
        }
    }
}