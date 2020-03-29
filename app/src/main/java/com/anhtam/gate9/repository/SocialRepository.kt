package com.anhtam.gate9.repository

import androidx.lifecycle.LiveData
import com.anhtam.domain.Base
import com.anhtam.domain.v2.*
import com.anhtam.domain.v2.wrap.WrapArticle
import com.anhtam.domain.v2.wrap.WrapComments
import com.anhtam.domain.v2.wrap.WrapGame
import com.anhtam.domain.v2.wrap.WrapThaoLuan
import com.anhtam.gate9.restful.SocialService
import com.anhtam.gate9.restful.entities.Notification
import com.anhtam.gate9.vo.Rating
import of.bum.network.FetchBoundResource
import of.bum.network.helper.Lv1FetchResource
import of.bum.network.helper.Lv2FetchResource
import of.bum.network.helper.Resource
import okhttp3.MultipartBody
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SocialRepository @Inject constructor(
        val service: SocialService
) {

    companion object {
        const val DEFAULT_LIMIT_5 = 5
        const val DEFAULT_LIMIT_10 = 10
    }

    fun getPostAndCommentByUser(userId: Int, type: Int, page: Int, limit: Int) = object: Lv2FetchResource<List<Post>>(){
        override fun createCall() = service.getPostAndCommentByUser(userId, type, page, limit)
    }.asLiveData()

    fun getNPHPost(userId: Int, type: Int, page: Int, limit: Int) = object: Lv2FetchResource<List<Post>>(){
        override fun createCall() = service.getNPHPost(userId, type, page, limit)
    }.asLiveData()

    fun getUserDetail() = object: Lv2FetchResource<Userv1>(){
        override fun createCall() = service.getInfo()
    }.asLiveData()

    fun getListRanking(roleId: Int, page: Int, limit: Int = 10) = object: Lv2FetchResource<List<Userv1>>(){
        override fun createCall() = service.getRanking(roleId, page, limit)
    }.asLiveData()

    fun getGameRelatedToUser(userId: Int, type: Int, page: Int, limit: Int)= object: Lv2FetchResource<List<WrapGame>>(){
        override fun createCall() = service.getGameRelatedToUser(userId, type, page, limit)
    }.asLiveData()

    fun getNPHGame(userId: Int, type: Int, page: Int, limit: Int)= object: Lv2FetchResource<List<WrapGame>>(){
        override fun createCall() = service.getNPHGame(userId, type, page, limit)
    }.asLiveData()

    fun getNewGame(page: Int, limit: Int)= object: Lv2FetchResource<List<Gamev1>>(){
        override fun createCall() = service.getNewGame(page, limit)
    }.asLiveData()

    fun getDataRelatedToUser(userId: Int, type: Int, page: Int, limit: Int)= object: Lv2FetchResource<List<Article>>(){
        override fun createCall() = service.getDataRelatedToUser(userId, type, page, limit)
    }.asLiveData()

    fun getNPHData(userId: Int, type: Int, page: Int, limit: Int)= object: Lv2FetchResource<List<Article>>(){
        override fun createCall() = service.getNPHData(userId, type, page, limit)
    }.asLiveData()

    fun getOtherUserInfoById(userId: Int) = object: Lv2FetchResource<Userv1>(){
        override fun createCall() = service.getOtherUserInfoById(userId)
    }.asLiveData()

    fun getGamesByTab(pageNumber: Int, tab: Int) = object: FetchBoundResource<List<Gamev2>>() {
        override fun createCall() = service.getMXHGame(tab, pageNumber)
    }.asLiveData()

    fun react(params: Map<String, Int>) = object : Lv2FetchResource<Base>(){
        override fun createCall() = service.react(params)

    }.asLiveData()

    fun postViewForum(commentId: Int) = object : Lv2FetchResource<Base>(){
        override fun createCall() = service.postViewForum(commentId)
    }.asLiveData()

    fun getListingPost(page: Int, limit: Int) = object : FetchBoundResource<List<Post>>(){
        override fun createCall() = service.getListPosts(page, limit)
    }.asLiveData()

    fun getFollowingInfo(tab: Int, page: Int) = object : FetchBoundResource<List<Post>>(){
        override fun createCall() = service.getFollowingInfo(tab, page)
    }.asLiveData()

    fun getBanners() = object : FetchBoundResource<List<Banner>>(){
        override fun createCall() = service.getBanners()
    }.asLiveData()

    fun getGameNominate() = object : FetchBoundResource<List<Gamev2>>(){
        override fun createCall() = service.getGameNominate()
    }.asLiveData()

    fun getSocialContact(commentId: Int, tab: Int, page: Int) = object : FetchBoundResource<List<Userv1>>(){
        override fun createCall() = service.getListSocialContact(commentId, tab, page)
    }.asLiveData()

    fun getChildComment(postId: Int, page: Int) = object : FetchBoundResource<WrapComments>(){
        override fun createCall() = service.getDetailPosts(postId.toLong(), page)
    }.asLiveData()

    fun uploadImages(file: List<MultipartBody.Part>) = object : Lv1FetchResource<List<String>>(){
        override fun createCall() = service.upload(file)
    }.asLiveData()

    fun listUser(role: Int, page: Int) = object : Lv2FetchResource<List<Userv1>>(){
        override fun createCall() = service.listUser(role, page)
    }.asLiveData()

    fun follow(){
        val params = hashMapOf<String, String>()
        params["userid"] = "2"
        params["roleid"] = "4"
        params["gameid"] = "0"
        service.follow(params)
    }

    fun registerWithEmail(email: String, password: String, name: String): LiveData<Resource<Base>>{
        val params = hashMapOf<String, String>()
        params["email"] = email
        params["password"] = password
        params["name"] = name
        return object: Lv2FetchResource<Base>(){
            override fun createCall() = service.register(params)
        }.asLiveData()
    }

    // Rating
    fun getNPHRating(userId: Int, tab: Int, page: Int, limit: Int) = object : FetchBoundResource<List<Rating>>(){
        override fun createCall() = service.getNPHRating(userId, tab, page, limit)
    }.asLiveData()

    fun getUserRating(userId: Int, tab: Int, page: Int, limit: Int) = object : FetchBoundResource<List<Rating>>(){
        override fun createCall() = service.getUserRating(userId, tab, page, limit)
    }.asLiveData()

    fun getGameDetail(userId: Int) = object: Lv2FetchResource<Gamev2>(){
        override fun createCall() = service.getGameDetail(userId)
    }.asLiveData()

    fun getThaoLuanGame(gameId: Int, page: Int, limit: Int = 5) = object: Lv2FetchResource<WrapThaoLuan>(){
        override fun createCall() = service.getThaoLuanGame(gameId, page, limit)
    }.asLiveData()

    fun getGameRating(gameId: Int, page: Int, limit: Int = 5) = object: Lv2FetchResource<List<Rating>>(){
        override fun createCall() = service.getGameRating(gameId, page, limit)
    }.asLiveData()

    fun getBaiViet(articleType: Int, page: Int, limit: Int = 5) = object: Lv2FetchResource<List<Article>>(){
        override fun createCall() = service.getBaiViet(articleType, page, limit)
    }.asLiveData()

    fun getChiTietBaiViet(id: Int, articleType: Int) = object: Lv2FetchResource<WrapArticle>(){
        override fun createCall() = service.getChiTietBaiViet(id, articleType)
    }.asLiveData()

    fun getRankingInPost(commentId: Int, page: Int, limit: Int = 10) = object: Lv2FetchResource<List<Userv1>>(){
        override fun createCall() = service.getRankingInPost(commentId, page, limit)
    }.asLiveData()


    /*
     *
     */
    fun getDuLieuGame(gameId: Int, page: Int, limit: Int = 5) = object: Lv2FetchResource<List<Article>>() {
        override fun createCall() = service.getDuLieuGame(0, gameId, page, limit)
    }.asLiveData()

    // --> Notification
    fun getNotificationList(page: Int, limit: Int = DEFAULT_LIMIT_10) = object: Lv2FetchResource<List<Notification>>() {
        override fun createCall() = service.getNotificationList(page, limit)
    }.asLiveData()

    // --> Post
    // DELETE
    fun deletePost(id: Long) = object: Lv2FetchResource<Base>(){
        override fun createCall() = service.deletePost(id)
    }.asLiveData()

    fun report(id: Long, type: Int, note: String) = object: Lv2FetchResource<Base>(){
        override fun createCall() = service.report(id, type, note)
    }.asLiveData()
}
