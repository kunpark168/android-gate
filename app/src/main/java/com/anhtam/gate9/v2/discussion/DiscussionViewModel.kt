package com.anhtam.gate9.v2.discussion

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
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
}