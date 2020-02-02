package com.anhtam.gate9.v2.charts

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import com.anhtam.domain.v2.Userv1
import com.anhtam.gate9.config.Config
import com.anhtam.gate9.repository.SocialRepository
import of.bum.network.helper.Resource
import javax.inject.Inject

class ChartViewModel @Inject constructor(
        private var mRepository: SocialRepository
) : ViewModel(){

    private val mUsers = MediatorLiveData<Resource<List<Userv1>>>()
    val users: LiveData<Resource<List<Userv1>>>
    get() = mUsers
    var mPage = 0

    fun getUserRanking(refresh: Boolean = false){
        mPage = if (refresh) 0 else mPage + 1
        val source = mRepository.getListRanking(Config.USER_ROLEID, mPage)
        mUsers.addSource(source){
            mUsers.value = it
        }
    }

    fun getNPHRanking(refresh: Boolean = false){
        mPage = if (refresh) 0 else mPage + 1
        val source = mRepository.getListRanking(Config.NPH_ROLEID, mPage)
        mUsers.addSource(source){
            mUsers.value = it
        }
    }
}