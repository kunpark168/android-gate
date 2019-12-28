package com.anhtam.gate9.di

import com.anhtam.gate9.config.Config
import com.anhtam.gate9.di.moshi.DateJsonAdapter
import com.anhtam.gate9.session.AuthClient
import com.anhtam.gate9.session.AuthService
import com.anhtam.gate9.v2.InfoService
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import dagger.Reusable
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
class AppModule {

    @Reusable
    @Provides
    fun provideAuthService(): AuthService {
        return Retrofit.Builder()
                .baseUrl(Config.IMG_URL)
                .addConverterFactory(MoshiConverterFactory.create())
                .build()
                .create(AuthService::class.java)
    }

    @Singleton
    @Provides
    fun provideAuthClient(
            authService: AuthService,
            infoService: InfoService
    ): AuthClient {
        return AuthClient(authService, infoService)
    }
}