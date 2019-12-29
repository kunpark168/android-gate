package com.anhtam.gate9.repository

import androidx.lifecycle.LiveData
import com.anhtam.domain.v2.User
import com.anhtam.domain.v2.wrap.WrapGame
import com.anhtam.domain.v2.wrap.WrapGames
import com.anhtam.domain.v2.wrap.WrapListing
import of.bum.network.FetchBoundResource
import of.bum.network.helper.ApiResponse
import of.bum.network.helper.Lv1FetchResource
import of.bum.network.helper.Lv2FetchResource
import of.bum.network.v2.SocialService
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SocialRepository @Inject constructor(
        var socialService: SocialService
) {
    fun getPostAndCommentByUser(userId: Int, type: Int, page: Int, limit: Int) = object: Lv2FetchResource<WrapListing>(){
        override fun createCall() = socialService.getPostAndCommentByUser(userId, type, page, limit)
    }.asLiveData()

    fun getUserDetail() = object: Lv2FetchResource<User>(){
        override fun createCall() = socialService.getInfo()
    }.asLiveData()

    fun getGameRelatedToUser(userId: Int, type: Int, page: Int, limit: Int)= object: Lv2FetchResource<List<WrapGame>>(){
        override fun createCall() = socialService.getGameRelatedToUser(userId, type, page, limit)
    }.asLiveData()

    fun getDataRelatedToUser(userId: Int, type: Int, page: Int, limit: Int)= object: Lv2FetchResource<WrapGames>(){
        override fun createCall() = socialService.getDataRelatedToUser(userId, type, page, limit)
    }.asLiveData()

    fun getOtherUserInfoById(userId: Int) = object: Lv2FetchResource<User>(){
        override fun createCall() = socialService.getOtherUserInfoById(userId)
    }.asLiveData()

    fun getGamesByTab(pageNumber: Int, tab: Int) = object: FetchBoundResource<com.anhtam.domain.v2.WrapGame>() {
        override fun createCall() = socialService.GetMXHGame(tab, pageNumber)
    }.asLiveData()
}