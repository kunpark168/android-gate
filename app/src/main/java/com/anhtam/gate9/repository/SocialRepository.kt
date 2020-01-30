package com.anhtam.gate9.repository

import androidx.lifecycle.LiveData
import com.anhtam.domain.Banner
import com.anhtam.domain.Base
import com.anhtam.domain.Game
import com.anhtam.domain.v2.*
import com.anhtam.domain.v2.wrap.WrapComments
import com.anhtam.domain.v2.wrap.WrapGame
import com.anhtam.domain.v2.wrap.WrapGames
import of.bum.network.FetchBoundResource
import of.bum.network.helper.ApiResponse
import of.bum.network.helper.Lv1FetchResource
import of.bum.network.helper.Lv2FetchResource
import of.bum.network.helper.RestResponse
import of.bum.network.v2.SocialService
import okhttp3.MultipartBody
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SocialRepository @Inject constructor(
        val socialService: SocialService
) {
    fun getPostAndCommentByUser(userId: Int, type: Int, page: Int, limit: Int) = object: Lv2FetchResource<List<Post>>(){
        override fun createCall() = socialService.getPostAndCommentByUser(userId, type, page, limit)
    }.asLiveData()

    fun getNPHPost(userId: Int, type: Int, page: Int, limit: Int) = object: Lv2FetchResource<List<Post>>(){
        override fun createCall() = socialService.getNPHPost(userId, type, page, limit)
    }.asLiveData()

    fun getUserDetail() = object: Lv2FetchResource<Userv1>(){
        override fun createCall() = socialService.getInfo()
    }.asLiveData()

    fun getGameRelatedToUser(userId: Int, type: Int, page: Int, limit: Int)= object: Lv2FetchResource<List<WrapGame>>(){
        override fun createCall() = socialService.getGameRelatedToUser(userId, type, page, limit)
    }.asLiveData()

    fun getNewGame(page: Int, limit: Int)= object: Lv2FetchResource<List<Gamev1>>(){
        override fun createCall() = socialService.getNewGame(page, limit)
    }.asLiveData()

    fun getDataRelatedToUser(userId: Int, type: Int, page: Int, limit: Int)= object: Lv2FetchResource<WrapGames>(){
        override fun createCall() = socialService.getDataRelatedToUser(userId, type, page, limit)
    }.asLiveData()

    fun getOtherUserInfoById(userId: Int) = object: Lv2FetchResource<Userv1>(){
        override fun createCall() = socialService.getOtherUserInfoById(userId)
    }.asLiveData()

    fun getGamesByTab(pageNumber: Int, tab: Int) = object: FetchBoundResource<List<Gamev1>>() {
        override fun createCall() = socialService.getMXHGame(tab, pageNumber)
    }.asLiveData()

    fun react(params: Map<String, Int>) = object : Lv2FetchResource<Base>(){
        override fun createCall() = socialService.react(params)

    }.asLiveData()

    fun postViewForum(commentId: Int) = object : Lv2FetchResource<Base>(){
        override fun createCall() = socialService.postViewForum(commentId)
    }.asLiveData()

    fun getListingPost() = object : FetchBoundResource<List<Post>>(){
        override fun createCall() = socialService.getListPosts()
    }.asLiveData()

    fun getFollowingInfo(tab: Int, page: Int) = object : FetchBoundResource<List<Post>>(){
        override fun createCall() = socialService.getFollowingInfo(tab, page)
    }.asLiveData()

    fun getBanners() = object : FetchBoundResource<List<Banner>>(){
        override fun createCall() = socialService.getBanners()
    }.asLiveData()

    fun getGameNominate() = object : FetchBoundResource<List<Gamev1>>(){
        override fun createCall() = socialService.getGameNominate()
    }.asLiveData()

    fun getSocialContact(commentId: Int, tab: Int, page: Int) = object : FetchBoundResource<List<Userv1>>(){
        override fun createCall() = socialService.getListSocialContact(commentId, tab, page)
    }.asLiveData()

    fun getChildComment(postId: Int, page: Int) = object : FetchBoundResource<WrapComments>(){
        override fun createCall() = socialService.getDetailPosts(postId.toLong(), page)
    }.asLiveData()

    fun uploadImages(file: List<MultipartBody.Part>) = object : Lv1FetchResource<List<String>>(){
        override fun createCall() = socialService.upload(file)
    }.asLiveData()

    fun listUser(role: Int, page: Int) = object : Lv2FetchResource<List<Userv1>>(){
        override fun createCall() = socialService.listUser(role, page)
    }.asLiveData()
}