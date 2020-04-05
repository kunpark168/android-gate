package com.anhtam.gate9.v2.main.home

import com.anhtam.gate9.v2.newfeed.NewFeedModule
import com.anhtam.gate9.v2.newfeed.NewFeedScreen
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
interface HomeModule {
    @ContributesAndroidInjector(
            modules = [NewFeedModule::class]
    )
    fun contributeNewFeedScreen(): NewFeedScreen
}