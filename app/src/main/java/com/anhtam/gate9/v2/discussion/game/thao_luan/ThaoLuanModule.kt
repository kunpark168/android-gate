package com.anhtam.gate9.v2.discussion.game.thao_luan

import androidx.lifecycle.ViewModel
import com.anhtam.gate9.di.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface ThaoLuanModule {
    @Binds
    @IntoMap
    @ViewModelKey(ThaoLuanViewModel::class)
    fun bindViewModel(thaoLuanViewModel: ThaoLuanViewModel): ViewModel
}