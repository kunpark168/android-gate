package com.anhtam.gate9.di

import com.anhtam.gate9.navigation.HideKeyboardNavigation
import com.anhtam.gate9.navigation.Navigation
import dagger.Binds
import dagger.Module

@Module
interface NavigationModule {
    @Binds
    fun bindNavigation(navigation: HideKeyboardNavigation): Navigation
}