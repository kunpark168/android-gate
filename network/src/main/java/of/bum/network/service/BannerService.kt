package of.bum.network.service

import androidx.lifecycle.LiveData
import com.anhtam.domain.Banner
import of.bum.network.helper.ApiResponse
import of.bum.network.helper.RestResponse
import retrofit2.http.GET

interface BannerService {
    /*
     * Main banner
     */
    @GET("v1/api/social/banner")
    fun getBanner(): LiveData<ApiResponse<RestResponse<com.anhtam.domain.Banner>>>
}