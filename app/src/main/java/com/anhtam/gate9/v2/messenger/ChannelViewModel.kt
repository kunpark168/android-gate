package com.anhtam.gate9.v2.messenger

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import com.anhtam.domain.User
import of.bum.network.helper.Resource
import javax.inject.Inject

class ChannelViewModel @Inject constructor() : ViewModel() {

    val users = MediatorLiveData<Resource<List<User>>>()

}