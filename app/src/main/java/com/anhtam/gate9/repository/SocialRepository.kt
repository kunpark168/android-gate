package com.anhtam.gate9.repository

import androidx.lifecycle.LiveData
import com.anhtam.domain.v2.Banner
import com.anhtam.domain.Base
import com.anhtam.domain.v2.*
import com.anhtam.domain.v2.wrap.WrapComments
import com.anhtam.domain.v2.wrap.WrapGame
import of.bum.network.FetchBoundResource
import of.bum.network.helper.*
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

    fun getListRanking(roleId: Int, page: Int, limit: Int = 10) = object: Lv2FetchResource<List<Userv1>>(){
        override fun createCall() = socialService.getRanking(roleId, page, limit)
    }.asLiveData()

    fun getGameRelatedToUser(userId: Int, type: Int, page: Int, limit: Int)= object: Lv2FetchResource<List<WrapGame>>(){
        override fun createCall() = socialService.getGameRelatedToUser(userId, type, page, limit)
    }.asLiveData()

    fun getNPHGame(userId: Int, type: Int, page: Int, limit: Int)= object: Lv2FetchResource<List<WrapGame>>(){
        override fun createCall() = socialService.getNPHGame(userId, type, page, limit)
    }.asLiveData()

    fun getNewGame(page: Int, limit: Int)= object: Lv2FetchResource<List<Gamev1>>(){
        override fun createCall() = socialService.getNewGame(page, limit)
    }.asLiveData()

    fun getDataRelatedToUser(userId: Int, type: Int, page: Int, limit: Int)= object: Lv2FetchResource<List<Article>>(){
        override fun createCall() = socialService.getDataRelatedToUser(userId, type, page, limit)
    }.asLiveData()

    fun getNPHData(userId: Int, type: Int, page: Int, limit: Int)= object: Lv2FetchResource<List<Article>>(){
        override fun createCall() = socialService.getNPHData(userId, type, page, limit)
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

    fun getListingPost(page: Int, limit: Int) = object : FetchBoundResource<List<Post>>(){
        override fun createCall() = socialService.getListPosts(page, limit)
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

    fun follow(){
        val params = hashMapOf<String, String>()
        params["userid"] = "2"
        params["roleid"] = "4"
        params["gameid"] = "0"
        socialService.follow(params)
    }

    fun registerWithEmail(email: String, password: String, name: String): LiveData<Resource<Base>>{
        val params = hashMapOf<String, String>()
        params["email"] = email
        params["password"] = password
        params["name"] = name
        return object: Lv2FetchResource<Base>(){
            override fun createCall() = socialService.register(params)
        }.asLiveData()
    }
}