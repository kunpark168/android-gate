package of.bum.network.v2

import androidx.lifecycle.LiveData
import of.bum.network.helper.ApiResponse
import okhttp3.MultipartBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*

interface MediaService {


    @POST("social/post-forum")
    fun updatePostForum(@Body params: @JvmSuppressWildcards Map<String, Any?>): Call<ResponseBody>
}