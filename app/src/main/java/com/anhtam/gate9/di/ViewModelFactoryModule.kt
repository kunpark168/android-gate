package com.anhtam.gate9.di

import androidx.lifecycle.ViewModelProvider
import com.anhtam.gate9.viewmodel.ViewModelProviderFactory
import dagger.Binds
import dagger.Module

@Suppress("unused")
@Module
abstract class ViewModelFactoryModule {
    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelProviderFactory): ViewModelProvider.Factory
}