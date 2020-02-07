package com.anhtam.gate9.v2.messenger.chat

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.anhtam.domain.WrappedUser
import com.anhtam.gate9.utils.AbsentLiveData
import of.bum.network.helper.Resource
import javax.inject.Inject

class ChatViewModel @Inject constructor() : ViewModel() {
    val userId = MutableLiveData<String>()
    val userInfo: LiveData<Resource<WrappedUser>> = Transformations.switchMap(userId) {AbsentLiveData.create<Resource<WrappedUser>>()
    }
}