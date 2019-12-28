package com.anhtam.gate9.v2

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import com.anhtam.domain.v2.User
import com.anhtam.gate9.repository.SocialRepository
import of.bum.network.helper.Resource
import javax.inject.Inject

class MainViewModel @Inject constructor(
        private val repository: SocialRepository
) : ViewModel() {
    val _user = MediatorLiveData<Resource<User>>()

    fun getUserDetail(){
        _user.addSource(repository.getUserDetail()){
            _user.value = it
        }
    }

}