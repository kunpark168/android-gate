package of.bum.network.v2

import androidx.lifecycle.LiveData
import com.anhtam.domain.v2.GameEntity
import com.anhtam.domain.v2.WrapGame
import of.bum.network.helper.ApiResponse
import of.bum.network.helper.RestResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface GameService {
    @GET("social/all-game")
    fun GetMXHGame(@Query("type") type: Int,
                   @Query("page") page:Int = 0,
                   @Query("limit") limit: Int = 15) : LiveData<ApiResponse<RestResponse<WrapGame>>>
}