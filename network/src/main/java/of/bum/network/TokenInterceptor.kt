package of.bum.network

import of.bum.network.helper.LiveDataCallAdapterFactory
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class TokenInterceptor: Interceptor {
    var token: String = ""
    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()
        if(checkRequestIsLoginOrSignUp(original)) return chain.proceed(original)
        val originalHttpUrl = original.url()
        val requestBuilder = original.newBuilder().addHeader("Authorization", token)
                .url(originalHttpUrl)
        val request = requestBuilder.build()
        return chain.proceed(request)
    }

    /**
     *  If Login or Sign up NOT add header TODO
     */
    private fun checkRequestIsLoginOrSignUp(request: Request): Boolean{
        return request.method() == "post" && (request.url().encodedPath().contains("/login"))
    }
}

fun setupRetrofit(tokenInterceptor: TokenInterceptor, baseUrl: String) {
    val interceptor = HttpLoggingInterceptor()
    interceptor.level = HttpLoggingInterceptor.Level.BODY
    val okHttpBuilder = OkHttpClient().newBuilder().addInterceptor(interceptor).addInterceptor(tokenInterceptor)
    val client = okHttpBuilder.build()
    Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(LiveDataCallAdapterFactory())
            .baseUrl(baseUrl)
            .client(client)
            .build()
}