package com.anhtam.gate9.repository

import androidx.lifecycle.LiveData
import of.bum.network.FetchBoundResource
import com.anhtam.domain.User
import com.anhtam.domain.WrappedUser
import of.bum.network.helper.ApiResponse
import of.bum.network.helper.Resource
import of.bum.network.helper.RestResponse
import of.bum.network.service.UserService
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepository @Inject constructor(
        private val userService: UserService
) {

    fun requestMemberList(page: Int,
                      orderBy: String?,
                      type: String
    ): LiveData<Resource<List<User>>>  {
        return object: FetchBoundResource<List<User>>() {
            override fun createCall(): LiveData<ApiResponse<RestResponse<List<User>>>> {
                return userService.getListUser(page, orderBy, type)
            }
        }.asLiveData()
    }

    fun getUserInformation(publisherId: String): LiveData<Resource<WrappedUser>>{
        return object: FetchBoundResource<WrappedUser>(){
            override fun createCall(): LiveData<ApiResponse<RestResponse<WrappedUser>>> {
                return userService.getUserInfo(publisherId)
            }
        }.asLiveData()
    }
}