package com.anhtam.gate9.di

import com.anhtam.gate9.di.scope.MainScope
import com.anhtam.gate9.v2.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Suppress("unused")
@Module
abstract class ActivityBuildersModule {

    @MainScope
    @ContributesAndroidInjector(
            modules = [
                FragmentBuildersModule::class,
                MainModule::class,
                NavigationModule::class]
    )
    abstract fun contributeMainActivity(): MainActivity
}
