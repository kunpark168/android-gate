package com.anhtam.gate9.v2.messenger

import androidx.lifecycle.ViewModel
import com.anhtam.gate9.di.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ChannelModule {
    @Binds
    @IntoMap
    @ViewModelKey(ChannelViewModel::class)
    abstract fun bindViewModel(channelViewModel: ChannelViewModel): ViewModel
}