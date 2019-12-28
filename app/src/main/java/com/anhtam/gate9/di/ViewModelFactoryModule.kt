package com.anhtam.gate9.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.anhtam.gate9.v2.MainViewModel
import com.anhtam.gate9.viewmodel.ViewModelProviderFactory
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Suppress("unused")
@Module
abstract class ViewModelFactoryModule {
    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelProviderFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    abstract fun bindViewModel(mainViewModel: MainViewModel): ViewModel
}