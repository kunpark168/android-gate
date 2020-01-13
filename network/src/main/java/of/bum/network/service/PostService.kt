package of.bum.network.service

import androidx.lifecycle.LiveData
import com.anhtam.domain.Parent
import com.anhtam.domain.Post
import of.bum.network.helper.ApiResponse
import of.bum.network.helper.RestResponse
import okhttp3.RequestBody
import retrofit2.http.*

interface PostService {

    @POST("api/v1/social/listPostlink")
    fun getAllPostInGame(@Body link: Map<String, String?>,@Query("page") page: Int): LiveData<ApiResponse<RestResponse<List<Post>>>>

    @GET("v1/api/social/listing")
    fun getAllPosts(@Query("page") page: Int): LiveData<ApiResponse<RestResponse<List<Post>>>>

    @GET("v1/api/social/listallpost/{id}")
    fun getPostByUser(@Path("id") userId: String, @Query("page") page: String?,
                      @Query("type") type: String?) : LiveData<ApiResponse<RestResponse<List<Post>>>>
}
