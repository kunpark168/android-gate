package com.anhtam.gate9.di

import com.anhtam.gate9.config.Config
import com.anhtam.gate9.restful.SocialService
import com.anhtam.gate9.session.AuthService
import com.anhtam.gate9.session.AuthenticationInterceptor
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.Reusable
import of.bum.network.helper.LiveDataCallAdapterFactory
import of.bum.network.v2.MediaService
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
class NetworkModule {

    @Singleton
    @Provides
    fun provideMoshi(): Moshi = Moshi.Builder()
//            .add(DateJsonAdapter())
            .add(KotlinJsonAdapterFactory())
            .build()

    @Singleton
    @Provides
    fun provideAuthInterceptor(): AuthenticationInterceptor = AuthenticationInterceptor()

    @Singleton
    @Provides
    fun provideClient(authInterceptor: AuthenticationInterceptor): OkHttpClient {
        val builder = OkHttpClient.Builder().addInterceptor(authInterceptor)
        builder.apply {
            readTimeout(Config.DEFAULT_TIME_OUT, TimeUnit.SECONDS)
            connectTimeout(Config.DEFAULT_TIME_OUT, TimeUnit.SECONDS)
        }
        return builder.build()
    }

    @Reusable
    @Provides
    fun provideAuthService(): AuthService {
        return Retrofit.Builder()
                .baseUrl(Config.LOGIN_URL)
                .addConverterFactory(MoshiConverterFactory.create())
                .build()
                .create(AuthService::class.java)
    }

    @Reusable
    @Provides
    fun provideMediaService(
            retrofit: Retrofit
    ): MediaService {
        return retrofit.create(MediaService::class.java)
    }

    @Singleton
    @Provides
    fun provideRetrofit(
            moshi: Moshi,
            client: OkHttpClient
    ): Retrofit {
        return Retrofit.Builder()
                .addConverterFactory(MoshiConverterFactory.create(moshi))
                .addCallAdapterFactory(LiveDataCallAdapterFactory())
                .baseUrl(Config.BASE_URL)
                .client(client)
                .build()
    }

    @Singleton
    @Provides
    fun provideSocialService(
            retrofit: Retrofit
    ): SocialService {
        return retrofit.create(SocialService::class.java)
    }
}

