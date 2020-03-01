package com.anhtam.gate9.restful

import androidx.lifecycle.LiveData
import com.anhtam.domain.Base
import com.anhtam.domain.v2.*
import com.anhtam.domain.v2.wrap.WrapArticle
import com.anhtam.domain.v2.wrap.WrapComments
import com.anhtam.domain.v2.wrap.WrapGame
import com.anhtam.gate9.vo.Rating
import of.bum.network.helper.ApiResponse
import of.bum.network.helper.RestResponse
import okhttp3.MultipartBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*

interface SocialService {

    /*
     *
     */
    @GET("login")
    fun getInfo(): LiveData<ApiResponse<RestResponse<Userv1>>>

    @POST("user/registration")
    fun register(@Body params: @JvmSuppressWildcards Map<String, String>): LiveData<ApiResponse<RestResponse<Base>>>

    @GET("social/listing")
    fun getListPosts(
            @Query("page") page: Int,
            @Query("limit") limit: Int
    ): LiveData<ApiResponse<RestResponse<List<Post>>>>

    @GET("social/get-banner")
    fun getBanners(): LiveData<ApiResponse<RestResponse<List<Banner>>>>

    @GET("social/get-game-nominate")
    fun getGameNominate(): LiveData<ApiResponse<RestResponse<List<Gamev1>>>>

    @GET("social/get-post-detail")
    fun getDetailPosts(@Query("postId") postId: Long,
                       @Query("page") page: Int = 0,
                       @Query("limit") limit: Int = 15
    ) : LiveData<ApiResponse<RestResponse<WrapComments>>>

    @POST("social/post-comment")
    fun postComment(
            @Query("parentId")parentId: Long,
            @Query("content")content: String? = null,
            @Query("imageUrl") imageUrl: String? = ""
    ): LiveData<ApiResponse<RestResponse<Base>>>

    @GET("social/get-all-post-by-user")
    fun getPostAndCommentByUser(
            @Query("userId") userId: Int,
            @Query("tab") type: Int,
            @Query("page") page: Int,
            @Query("limit") limit: Int
    ): LiveData<ApiResponse<RestResponse<List<Post>>>>

    @GET("social/nph/discuss")
    fun getNPHPost(
            @Query("userId") userId: Int,
            @Query("tab") type: Int,
            @Query("page") page: Int,
            @Query("limit") limit: Int
    ): LiveData<ApiResponse<RestResponse<List<Post>>>>

    @GET("social/get-list-game-by-user")
    fun getGameRelatedToUser(
            @Query("userId") userId: Int,
            @Query("tab") type: Int,
            @Query("page") page: Int,
            @Query("limit") limit: Int
    ): LiveData<ApiResponse<RestResponse<List<WrapGame>>>>

    @GET("social/nph/game")
    fun getNPHGame(
            @Query("userId") userId: Int,
            @Query("tab") type: Int,
            @Query("page") page: Int,
            @Query("limit") limit: Int
    ): LiveData<ApiResponse<RestResponse<List<WrapGame>>>>

    @GET("social/nph/rate")
    fun getNPHRating(
            @Query("userId") userId: Int,
            @Query("tab") tab: Int,
            @Query("page") page: Int,
            @Query("limit") limit: Int
    ): LiveData<ApiResponse<RestResponse<List<Rating>>>>

    @GET("social/user-rating")
    fun getUserRating(
            @Query("userId") userId: Int,
            @Query("tab") tab: Int,
            @Query("page") page: Int,
            @Query("limit") limit: Int
    ): LiveData<ApiResponse<RestResponse<List<Rating>>>>

    @GET("social/get-new-game")
    fun getNewGame(
            @Query("page") page: Int,
            @Query("limit") limit: Int
    ): LiveData<ApiResponse<RestResponse<List<Gamev1>>>>

    @GET("social/user/data-info")
    fun getDataRelatedToUser(
            @Query("userId") userId: Int,
            @Query("tab") type: Int,
            @Query("page") page: Int,
            @Query("limit") limit: Int
    ): LiveData<ApiResponse<RestResponse<List<Article>>>>

    @GET("social/nph/data-info")
    fun getNPHData(
            @Query("userId") userId: Int,
            @Query("tab") type: Int,
            @Query("page") page: Int,
            @Query("limit") limit: Int
    ): LiveData<ApiResponse<RestResponse<List<Article>>>>

    @GET("user/userInfo")
    fun getOtherUserInfoById(@Query("userId") userId: Int): LiveData<ApiResponse<RestResponse<Userv1>>>

    @GET("social/get-game-info")
    fun getGameDetail(@Query("gameId") userId: Int): LiveData<ApiResponse<RestResponse<Gamev1>>>

    @GET("social/all-game")
    fun getMXHGame(@Query("tab") type: Int,
                   @Query("page") page:Int = 0,
                   @Query("limit") limit: Int = 15) : LiveData<ApiResponse<RestResponse<List<Gamev1>>>>

    @POST("social/post-like")
    fun react(@Body params: Map<String, Int>): LiveData<ApiResponse<RestResponse<Base>>>

    @GET("social/get-love-like-dislike")
    fun getListSocialContact(@Query("commentId") commentId: Int,
                             @Query("tab") tab: Int,
                             @Query("page") page: Int,
                             @Query("limit") limit: Int = 15): LiveData<ApiResponse<RestResponse<List<Userv1>>>>

    @POST("social/post-view-forum")
    fun postViewForum(@Query("commentId") commentId: Int): LiveData<ApiResponse<RestResponse<Base>>>

    @Multipart
    @POST("social/upload/file")
    fun upload(
            @Part file: List<MultipartBody.Part>
    ): LiveData<ApiResponse<List<String>>>

    // Get data following
    @GET("social/get-following-info")
    fun getFollowingInfo(
            @Query("tab") tab: Int,
            @Query("page") page: Int,
            @Query("limit") limit: Int = 40): LiveData<ApiResponse<RestResponse<List<Post>>>>

    /*
     *  POST
     *  - create
     *  - update
     *  - delete
     */

    @DELETE("social/delete-post-forum")
    fun delete(@Query("commentId") id: Int): LiveData<ApiResponse<RestResponse<Base>>>

    @POST("social/update-post-forum")
    fun update()

    @GET("user/list-user")
    fun listUser(@Query("role") role: Int,
                 @Query("page") page: Int,
                 @Query("limit") limit: Int = 10,
                 @Query("keyword") keyword: String? = ""): LiveData<ApiResponse<RestResponse<List<Userv1>>>>

    @POST("social/post-follow")
    fun follow(@Body params: Map<String, String>): LiveData<ApiResponse<RestResponse<ResponseBody>>>

    @GET("user/ranking-list")
    fun getRanking(@Query("roleId") roleId: Int,
                 @Query("page") page: Int,
                 @Query("limit") limit: Int = 10): LiveData<ApiResponse<RestResponse<List<Userv1>>>>

    @GET("social/get-game-discuss")
    fun getThaoLuanGame(
            @Query("gameId") userId: Int,
            @Query("page") page: Int,
            @Query("limit") limit: Int
    ): LiveData<ApiResponse<RestResponse<List<Post>>>>

    @GET("social/get-game-rate")
    fun getGameRating(
            @Query("gameId") userId: Int,
            @Query("page") page: Int,
            @Query("limit") limit: Int
    ): LiveData<ApiResponse<RestResponse<List<Rating>>>>

    @GET("social/get-list-article")
    fun getBaiViet(
            @Query("articleType") type: Int,
            @Query("page") page: Int,
            @Query("limit") limit: Int
    ): LiveData<ApiResponse<RestResponse<List<Article>>>>

    @GET("social/get-detail-article")
    fun getChiTietBaiViet(
            @Query("articleId") id: Int,
            @Query("articleType") type: Int
    ): LiveData<ApiResponse<RestResponse<WrapArticle>>>
}