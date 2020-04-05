package com.anhtam.gate9.v2.follow

import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
interface FollowModule {
    @ContributesAndroidInjector
    fun contributeTatCaFollowingScreen(): TatCaFollowingScreen
}