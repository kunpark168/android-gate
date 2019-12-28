package com.anhtam.gate9.di

import com.anhtam.gate9.config.Config
import com.anhtam.gate9.di.moshi.DateJsonAdapter
import com.anhtam.gate9.session.AuthenticationInterceptor
import com.anhtam.gate9.v2.InfoService
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.Reusable
import of.bum.network.helper.LiveDataCallAdapterFactory
import of.bum.network.service.*
import of.bum.network.v2.MediaService
import of.bum.network.v2.SocialService
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
    fun provideInfoService(
            moshi: Moshi,
            client: OkHttpClient): InfoService {
        return Retrofit.Builder()
                .baseUrl(Config.BASE_URL)
                .client(client)
                .addConverterFactory(MoshiConverterFactory.create(moshi))
                .build()
                .create(InfoService::class.java)
    }

    @Reusable
    @Provides
    fun provideMediaService(
            moshi: Moshi,
            client: OkHttpClient): MediaService {
        return Retrofit.Builder()
                .baseUrl(Config.BASE_URL)
                .client(client)
                .addConverterFactory(MoshiConverterFactory.create(moshi))
                .build()
                .create(MediaService::class.java)
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
    fun providePostService(
            retrofit: Retrofit
    ): PostService {
        return retrofit.create(PostService::class.java)
    }

    @Singleton
    @Provides
    fun provideGameService(
            retrofit: Retrofit
    ): GameService {
        return retrofit.create(GameService::class.java)
    }

    @Singleton
    @Provides
    fun provideUserService(
            retrofit: Retrofit
    ): UserService {
        return retrofit.create(UserService::class.java)
    }

    @Singleton
    @Provides
    fun provideBannerService(
            retrofit: Retrofit
    ): BannerService {
        return retrofit.create(BannerService::class.java)
    }

    @Singleton
    @Provides
    fun provideArticleService(
            retrofit: Retrofit
    ): ArticleService {
        return retrofit.create(ArticleService::class.java)
    }

    @Singleton
    @Provides
    fun provideReviewService(
            retrofit: Retrofit
    ): ReviewService {
        return retrofit.create(ReviewService::class.java)
    }

    @Singleton
    @Provides
    fun provideSocialService(
            retrofit: Retrofit
    ): SocialService {
        return retrofit.create(SocialService::class.java)
    }

    @Singleton
    @Provides
    fun provideGameServicev2(
            retrofit: Retrofit
    ): of.bum.network.v2.GameService {
        return retrofit.create(of.bum.network.v2.GameService::class.java)
    }
}

