package com.anhtam.gate9.v2.game_detail.thao_luan

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import com.anhtam.domain.v2.Post
import com.anhtam.domain.v2.wrap.WrapThaoLuan
import com.anhtam.gate9.repository.SocialRepository
import of.bum.network.helper.Resource
import javax.inject.Inject

class ThaoLuanViewModel @Inject constructor(
        private val repos: SocialRepository
) : ViewModel() {

    private var mGameId: Int = 0
    val _data = MediatorLiveData<Resource<WrapThaoLuan>>()
    val data: LiveData<Resource<WrapThaoLuan>>
        get() = _data

    var mPage = 0

    fun initialize(gameId: Int) {
        mGameId = gameId
    }

    fun loadData(refresh: Boolean = false){
        mPage = if (refresh) 0 else mPage + 1
        val source: LiveData<Resource<WrapThaoLuan>> = repos.getThaoLuanGame(mGameId, mPage)
        _data.addSource(source){
            _data.value = it
            when(it){
                is Resource.Success, is Resource.Error -> {
                    _data.removeSource(source)
                }
            }
        }
    }
}