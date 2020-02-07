package com.anhtam.gate9.v2.main.member

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import com.anhtam.domain.v2.Userv1
import com.anhtam.gate9.repository.SocialRepository
import com.anhtam.gate9.utils.setValueDiff
import com.anhtam.gate9.vo.EUser
import of.bum.network.helper.Resource
import javax.inject.Inject

class MemberDefaultViewModel @Inject constructor(
        private val repository: SocialRepository
): ViewModel() {

    private var mPage = 0
    private var mType = EUser.TV
    private var mUserList: MediatorLiveData<Resource<List<Userv1>>> = MediatorLiveData()
    val page: Int
        get() = mPage
    val userList: LiveData<Resource<List<Userv1>>>
        get() = mUserList
    fun requestMemberList(
                          type: EUser
    ) {
        mPage = 0
        mType = type
        val role = when(mType){
            EUser.NPH -> 5
            EUser.TV -> 4
        }
        val newData = repository.listUser(role, mPage)
        mUserList.addSource(newData) {
            it?.let { mUserList.setValueDiff(it) }
        }
    }

    fun requestMore() {
        ++mPage
        val role = when(mType){
            EUser.NPH -> 5
            EUser.TV -> 4
        }
        val newData = repository.listUser(role, mPage)
        mUserList.addSource(newData) {
            it?.let { mUserList.setValueDiff(it) }
        }
    }
}
/*
 * TODO() 1) Remove Source
 *  TODO() 2) Cached
 */
