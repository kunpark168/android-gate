package com.anhtam.gate9.v2.search.result.game

import androidx.lifecycle.ViewModel
import com.anhtam.gate9.di.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface GameSearchModule {
    @Binds
    @IntoMap
    @ViewModelKey(GameSearchViewModel::class)
    fun bindViewModel(gameSearchViewModel: GameSearchViewModel): ViewModel
}