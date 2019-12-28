package of.bum.network.service

import androidx.lifecycle.LiveData
import com.anhtam.domain.User
import com.anhtam.domain.WrappedUser
import of.bum.network.helper.ApiResponse
import of.bum.network.helper.RestResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface UserService {
    @GET("v1/api/listuser")
    fun getListUser(@Query("page") page: Int,
                    @Query("alphabet") orderBy: String?,
                    @Query("type") type: String
    ): LiveData<ApiResponse<RestResponse<List<com.anhtam.domain.User>>>>

    @GET("v1/api/social/u/{id}")
    fun getUserInfo(@Path("id") userId: String): LiveData<ApiResponse<RestResponse<WrappedUser>>>
}