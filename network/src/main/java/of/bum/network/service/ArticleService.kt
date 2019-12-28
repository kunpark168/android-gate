package of.bum.network.service

import androidx.lifecycle.LiveData
import com.anhtam.domain.Article
import of.bum.network.helper.ApiResponse
import of.bum.network.helper.RestResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ArticleService {

    @GET("/v1/api/u/data/{id}")
    fun getDataBelowType(@Path("id") userId : String,
                      @Query("page") page: String?,
                      @Query("article_type") type: String?) : LiveData<ApiResponse<RestResponse<List<Article>>>>

}