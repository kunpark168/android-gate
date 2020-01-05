package of.bum.network.v2

import androidx.lifecycle.LiveData
import com.anhtam.domain.Base
import com.anhtam.domain.v2.*
import com.anhtam.domain.v2.wrap.WrapBase
import com.anhtam.domain.v2.wrap.WrapGame
import com.anhtam.domain.v2.wrap.WrapGames
import com.anhtam.domain.v2.wrap.WrapListing
import of.bum.network.helper.ApiResponse
import of.bum.network.helper.Resource
import of.bum.network.helper.RestResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface SocialService {
    @GET("login")
    fun getInfo(): LiveData<ApiResponse<RestResponse<User>>>

    @GET("social/listing")
    fun getListPosts(
            @Query("page") page: Int = 0,
            @Query("limit") limit: Int = 60
    ): LiveData<ApiResponse<RestResponse<WrappedHome>>>

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
    ): LiveData<ApiResponse<RestResponse<WrapListing>>>

    @GET("social/get-list-game-by-user")
    fun getGameRelatedToUser(
            @Query("userId") userId: Int,
            @Query("tab") type: Int,
            @Query("page") page: Int,
            @Query("limit") limit: Int
    ): LiveData<ApiResponse<RestResponse<List<WrapGame>>>>

    @POST("social/get-data-info")
    fun getDataRelatedToUser(
            @Query("userId") userId: Int,
            @Query("tab") type: Int,
            @Query("page") page: Int,
            @Query("limit") limit: Int
    ): LiveData<ApiResponse<RestResponse<WrapGames>>>

    @GET("user/userInfo")
    fun getOtherUserInfoById(@Query("userId") userId: Int): LiveData<ApiResponse<RestResponse<User>>>

    @GET("social/all-game")
    fun GetMXHGame(@Query("tab") type: Int,
                   @Query("page") page:Int = 0,
                   @Query("limit") limit: Int = 15) : LiveData<ApiResponse<RestResponse<com.anhtam.domain.v2.WrapGame>>>

    @POST("social/post-like")
    fun react(@Body params: Map<String, Int>): LiveData<ApiResponse<Base>>

    @GET("social/get-love-like-dislike")
    fun getListSocialContact(@Query("commentId") commentId: Int,
                             @Query("tab") tab: Int,
                             @Query("page") page: Int,
                             @Query("limit") limit: Int = 15): LiveData<ApiResponse<RestResponse<List<User>>>>

    @POST("social/post-view-forum")
    fun postViewForum(@Query("commentId") commentId: Int): LiveData<ApiResponse<Base>>
}