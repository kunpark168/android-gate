package of.bum.network.v2

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*

interface MediaService {


    @POST("social/post-forum")
    fun updatePostForum(@Body params: @JvmSuppressWildcards Map<String, Any?>): Call<ResponseBody>

    @POST("social/post-rate")
    fun uploadRating(@Body params: @JvmSuppressWildcards Map<String, Any?>): Call<ResponseBody>
}