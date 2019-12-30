package com.anhtam.gate9.di

import com.anhtam.gate9.R
import com.anhtam.gate9.config.Config
import com.anhtam.gate9.session.AuthService
import com.bumptech.glide.request.RequestOptions
import dagger.Module
import dagger.Provides
import dagger.Reusable
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Named

@Module
class AppModule {

    @Reusable
    @Provides
    fun provideAuthService(): AuthService {
        return Retrofit.Builder()
                .baseUrl(Config.LOGIN_URL)
                .addConverterFactory(MoshiConverterFactory.create())
                .build()
                .create(AuthService::class.java)
    }
}