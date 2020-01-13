package com.anhtam.gate9.di

import android.app.Application
import android.content.ContentResolver
import com.anhtam.gate9.R
import com.anhtam.gate9.navigation.HideKeyboardNavigation
import com.anhtam.gate9.navigation.NavigationDispatcher
import com.anhtam.gate9.v2.MainActivity
import com.bumptech.glide.request.RequestOptions
import dagger.Module
import dagger.Provides
import dagger.Reusable
import javax.inject.Named

@Module
class MainModule {
    @Provides
    fun provideNavigation(activity: MainActivity): HideKeyboardNavigation{
        return HideKeyboardNavigation(
                NavigationDispatcher(activity, R.id.container)
        )
    }

    @Provides
    @Reusable
    @Named("banner")
    fun provideBannerRequestOptions(): RequestOptions {
        return RequestOptions().placeholder(R.drawable.img_holder_banner)
                .error(R.drawable.img_holder_banner)
    }

    @Provides
    @Reusable
    @Named("avatar")
    fun provideAvatarRequestOptions(): RequestOptions {
        return RequestOptions.circleCropTransform()
                .placeholder(R.drawable.img_avatar_holder)
                .error(R.drawable.img_avatar_holder)
    }
}