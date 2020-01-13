package com.anhtam.gate9.v2.discussion

import androidx.lifecycle.*
import com.anhtam.domain.Game
import com.anhtam.domain.v2.Userv1
import com.anhtam.gate9.repository.GameRepository
import com.anhtam.gate9.repository.SocialRepository
import com.anhtam.gate9.repository.UserRepository
import com.anhtam.gate9.utils.AbsentLiveData
import of.bum.network.helper.Resource
import javax.inject.Inject

class DiscussionViewModel @Inject constructor(private val userRepository: UserRepository,
                                              private val gameRepository: GameRepository,
                                              private val socialRepository: SocialRepository) : ViewModel() {

    val mUserId: MutableLiveData<Int> = MutableLiveData()

    val mUser: LiveData<Resource<Userv1>> = Transformations.switchMap(mUserId) {
        if (it == null) {
            AbsentLiveData.create()
        } else {
            socialRepository.getOtherUserInfoById(it)
        }
    }

    val _bottomStatus = MutableLiveData(false)

    val mGameId: MutableLiveData<String> = MutableLiveData()

    val mGame: LiveData<Resource<Game>> = Transformations.switchMap(mGameId) {
        if (it == null) {
            AbsentLiveData.create()
        } else {
            gameRepository.getGameDetail(it)
        }
    }
}