package com.anhtam.gate9.v2.bxh

import androidx.lifecycle.ViewModel
import com.anhtam.gate9.di.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface BXHModule {
    @Binds
    @IntoMap
    @ViewModelKey(BXHViewModel::class)
    fun bindViewModel(BXHViewModel: BXHViewModel): ViewModel
}