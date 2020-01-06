package com.anhtam.gate9.repository

import androidx.lifecycle.LiveData
import com.anhtam.domain.Base
import com.anhtam.domain.v2.*
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

    fun getGamesByTab(pageNumber: Int, tab: Int) = object: FetchBoundResource<List<GameEntity>>() {
        override fun createCall() = socialService.GetMXHGame(tab, pageNumber)
    }.asLiveData()

    fun react(params: Map<String, Int>) = object : Lv2FetchResource<Base>(){
        override fun createCall() = socialService.react(params)

    }.asLiveData()

    fun getListingPost() = object : FetchBoundResource<WrappedHome>(){
        override fun createCall() = socialService.getListPosts()
    }.asLiveData()

    fun getSocialContact(commentId: Int, tab: Int, page: Int) = object : FetchBoundResource<List<User>>(){
        override fun createCall() = socialService.getListSocialContact(commentId, tab, page)
    }.asLiveData()

    fun getChildComment(postId: Int, page: Int) = object : FetchBoundResource<WrapComments>(){
        override fun createCall() = socialService.getDetailPosts(postId.toLong(), page)
    }.asLiveData()
}