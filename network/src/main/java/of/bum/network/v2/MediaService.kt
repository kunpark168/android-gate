package of.bum.network.v2

import okhttp3.MultipartBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*

interface MediaService {
    @Multipart
    @POST("social/upload/file")
    fun upload(
            @Part file: List<MultipartBody.Part>
    ): Call<List<String>>


    @POST("social/post-forum")
    fun updatePostForum(@Body params: @JvmSuppressWildcards Map<String, Any?>): Call<ResponseBody>
}