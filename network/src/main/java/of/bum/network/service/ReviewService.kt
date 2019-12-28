package of.bum.network.service

import androidx.lifecycle.LiveData
import com.anhtam.domain.ReviewDTO
import of.bum.network.helper.ApiResponse
import of.bum.network.helper.RestResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ReviewService {

    @GET("/v1/api/review/listuser/{id}")
    fun getReviewUser(
            @Path("id") id: Int,
            @Query("page") page: Int
    ): LiveData<ApiResponse<RestResponse<List<ReviewDTO>>>>
}