package com.anhtam.gate9.di

import com.anhtam.gate9.v2.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Suppress("unused")
@Module
abstract class ActivityBuildersModule {

    @ContributesAndroidInjector(
            modules = [FragmentBuildersModule::class]
    )
    abstract fun contributeMainActivity(): MainActivity
}
