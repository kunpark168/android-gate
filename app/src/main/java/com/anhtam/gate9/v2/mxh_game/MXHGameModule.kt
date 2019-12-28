package com.anhtam.gate9.v2.mxh_game

import androidx.lifecycle.ViewModel
import com.anhtam.gate9.di.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

@Module
abstract class MXHGameModule {

    @ContributesAndroidInjector
    abstract fun contributeFragment(): MXHGameTabFragment

    @Binds
    @IntoMap
    @ViewModelKey(MXHGameViewModel::class)
    abstract fun bindViewModel(mxhGameViewModel: MXHGameViewModel): ViewModel
}