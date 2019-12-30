package com.anhtam.gate9.di

import com.anhtam.gate9.R
import com.anhtam.gate9.navigation.HideKeyboardNavigation
import com.anhtam.gate9.navigation.NavigationDispatcher
import com.anhtam.gate9.v2.MainActivity
import dagger.Module
import dagger.Provides

@Module
class MainModule {
    @Provides
    fun provideNavigation(activity: MainActivity): HideKeyboardNavigation{
        return HideKeyboardNavigation(
                NavigationDispatcher(activity, R.id.container)
        )
    }
}