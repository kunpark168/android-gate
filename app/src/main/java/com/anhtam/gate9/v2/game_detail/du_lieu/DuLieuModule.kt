package com.anhtam.gate9.v2.game_detail.du_lieu

import androidx.lifecycle.ViewModel
import com.anhtam.gate9.di.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface DuLieuModule {
    @Binds
    @IntoMap
    @ViewModelKey(DuLieuViewModel::class)
    fun bindViewModel(duLieuViewModel: DuLieuViewModel) : ViewModel
}