package com.anhtam.gate9.v2.main.member

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import com.anhtam.gate9.repository.UserRepository
import com.anhtam.gate9.utils.setValueDiff
import of.bum.network.helper.Resource
import javax.inject.Inject

class MemberDefaultViewModel @Inject constructor(
        private val userRepository: UserRepository
): ViewModel() {

    private var mPage = 1
    private var mOrderBy: String? = null
    private var mType = "member"
    private var mUserList: MediatorLiveData<Resource<List<com.anhtam.domain.User>>> = MediatorLiveData()
    val page: Int
        get() = mPage
    val userList: LiveData<Resource<List<com.anhtam.domain.User>>>
        get() = mUserList
    fun requestMemberList(orderBy: String?,
                          type: String
    ) {
        mPage = 1
        mOrderBy = orderBy
        mType = type
        val newData = userRepository.requestMemberList(mPage, mOrderBy, mType)
        mUserList.addSource(newData) {
            it?.let { mUserList.setValueDiff(it) }
        }
    }

    fun requestMore() {
        ++mPage
        userRepository.requestMemberList(mPage, mOrderBy, mType)
        val newData = userRepository.requestMemberList(mPage, mOrderBy, mType)
        mUserList.addSource(newData) {
            it?.let { mUserList.setValueDiff(it) }
        }
    }
}
/*
 * TODO() 1) Remove Source
 *  TODO() 2) Cached
 */
