package com.anhtam.gate9.v2.game_detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.anhtam.domain.v2.Gamev1
import com.anhtam.gate9.repository.SocialRepository
import com.anhtam.gate9.utils.AbsentLiveData
import of.bum.network.helper.Resource
import javax.inject.Inject

class DetailGameViewModel @Inject constructor(
        private val repos: SocialRepository) : ViewModel() {

    val mGameId: MutableLiveData<Int> = MutableLiveData()

    val mGame: LiveData<Resource<Gamev1>> = Transformations.switchMap(mGameId) {
        if (it == null) {
            AbsentLiveData.create()
        } else {
            repos.getGameDetail(it)
        }
    }

    val _bottomStatus = MutableLiveData(false)
}