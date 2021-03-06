package com.anhtam.gate9.restful

import androidx.lifecycle.LiveData
import com.anhtam.domain.Base
import com.anhtam.domain.v2.*
import com.anhtam.domain.v2.wrap.WrapArticle
import com.anhtam.domain.v2.wrap.WrapComments
import com.anhtam.domain.v2.wrap.WrapGame
import com.anhtam.domain.v2.wrap.WrapThaoLuan
import com.anhtam.gate9.restful.entities.Notification
import com.anhtam.gate9.vo.Letter
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
    fun getGameNominate(): LiveData<ApiResponse<RestResponse<List<Gamev2>>>>

    @GET("social/get-post-detail")
    fun getDetailPosts(@Query("postId") postId: Long,
                       @Query("page") page: Int = 0,
                       @Query("limit") limit: Int = 15
    ): LiveData<ApiResponse<RestResponse<WrapComments>>>


    @POST("social/post-comment")
    fun postComment(
            @Query("parentId") parentId: Long,
            @Query("content") content: String? = null,
            @Query("imageUrl") imageUrl: String? = ""
    ): LiveData<ApiResponse<RestResponse<Base>>>

    @POST("social/post-article-comment")
    fun postArticleComment(
            @Query("parentId") parentId: Long,
            @Query("content") content: String? = null,
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
    fun getGameDetail(@Query("gameId") userId: Int): LiveData<ApiResponse<RestResponse<Gamev2>>>

    @GET("social/all-game")
    fun getMXHGame(@Query("tab") type: Int,
                   @Query("page") page: Int = 0,
                   @Query("limit") limit: Int = 15): LiveData<ApiResponse<RestResponse<List<Gamev2>>>>

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
    ): LiveData<ApiResponse<RestResponse<WrapThaoLuan>>>

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

    @GET("user/ranking-in-post")
    fun getRankingInPost(
            @Query("commentId") commentId: Int,
            @Query("page") page: Int,
            @Query("limit") limit: Int
    ): LiveData<ApiResponse<RestResponse<List<Userv1>>>>

    /*
     *
     */
    @GET("social/get-list-article-by-game")
    fun getDuLieuGame(
            @Query("articleType") articleType: Int,
            @Query("gameId") gameId: Int,
            @Query("page") page: Int,
            @Query("limit") limit: Int
    ): LiveData<ApiResponse<RestResponse<List<Article>>>>

    // --> Notification
    @GET("social/get-notification-list")
    fun getNotificationList(@Query("page") page: Int, @Query("limit") limit: Int): LiveData<ApiResponse<RestResponse<List<Notification>>>>

    // --> Post
    // DELETE
    @DELETE("social/delete-post-forum")
    fun deletePost(@Query("commentId") id: Long): LiveData<ApiResponse<RestResponse<Base>>>

    @POST("social/update-post-forum")
    fun update()

    @POST("social/post-report")
    fun report(@Query("id") id: Long,
               @Query("type") type: Int,
               @Query("note") note: String): LiveData<ApiResponse<RestResponse<Base>>>

    // --> Search
    @GET("social/search-game")
    fun searchGame(@Query("key") key: String?,
                   @Query("page") page: Int,
                   @Query("limit") limit: Int): LiveData<ApiResponse<RestResponse<List<Gamev2>>>>

    @GET("user/list-user")
    fun searchMember(@Query("keyword") key: String?,
                     @Query("role") role: Int,
                     @Query("page") page: Int,
                     @Query("limit") limit: Int): LiveData<ApiResponse<RestResponse<List<Userv1>>>>

    @GET("social/search-article")
    fun searchArticle(@Query("keyword") key: String?,
                   @Query("page") page: Int,
                   @Query("limit") limit: Int): LiveData<ApiResponse<RestResponse<List<Article>>>>

    @POST("user/letter-send")
    fun createLetter(
            @Query("userId") userId: Int,
            @Query("content") content: String,
            @Query("title") title: String): LiveData<ApiResponse<RestResponse<Letter>>>

    @GET("user/letter-detail")
    fun getLetterByUser(
            @Query("userId") userId: Int): LiveData<ApiResponse<RestResponse<List<Letter>>>>

    @GET("user/letter-list")
    fun filterLetter(
            @Query("keyword") keyword: String?,
            @Query("start") startDate: String?,
            @Query("end") endDate: String?,
            @Query("type") type: Int,
            @Query("page") page: Int,
            @Query("limit") limit: Int
    ): LiveData<ApiResponse<RestResponse<List<Letter>>>>
}