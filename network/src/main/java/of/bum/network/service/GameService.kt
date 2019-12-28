package of.bum.network.service

import androidx.lifecycle.LiveData
import com.anhtam.domain.Game
import com.anhtam.domain.GameDTO
import of.bum.network.helper.ApiResponse
import of.bum.network.helper.RestResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GameService {

    @GET("v1/api/social/game/{gameId}")
    fun getDetailGame(@Path("gameId") gameId: String): LiveData<ApiResponse<RestResponse<Game>>>

    @GET("v1/api/social/game")
    fun getAllGame(@Query("page") page : Int) : LiveData<ApiResponse<RestResponse<List<Game>>>>

    @GET("v1/api/social/newgamecomment")
    fun getBannerGames(): LiveData<ApiResponse<RestResponse<List<Game>>>>

    @GET("/v1/api/u/games/{id}")
    fun getGameOfUser(@Path("id") userId : String,
                      @Query("page") page: String?,
                      @Query("type") type: String?) : LiveData<ApiResponse<RestResponse<List<GameDTO>>>>
}