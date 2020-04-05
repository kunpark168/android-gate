package com.anhtam.gate9.v2.discussion.common.game

import androidx.lifecycle.ViewModel
import com.anhtam.gate9.di.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class GGameModule {
    @Binds
    @IntoMap
    @ViewModelKey(GGameViewModel::class)
    abstract fun bindGGameViewModel(newFeedViewModel: GGameViewModel): ViewModel

}