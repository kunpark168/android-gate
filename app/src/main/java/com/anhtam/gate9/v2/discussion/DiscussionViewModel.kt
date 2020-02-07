package com.anhtam.gate9.v2.discussion

import androidx.lifecycle.*
import com.anhtam.domain.v2.Gamev1
import com.anhtam.domain.v2.Userv1
import com.anhtam.gate9.repository.SocialRepository
import com.anhtam.gate9.utils.AbsentLiveData
import of.bum.network.helper.Resource
import javax.inject.Inject

class DiscussionViewModel @Inject constructor(
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

    val mGame: LiveData<Resource<Gamev1>> = Transformations.switchMap(mGameId) {AbsentLiveData.create<Resource<Gamev1>>() }
}