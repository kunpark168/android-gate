package com.anhtam.gate9.v2.post_detail

import androidx.lifecycle.ViewModel
import com.anhtam.gate9.di.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class DetailPostModule {
    @Binds
    @IntoMap
    @ViewModelKey(DetailPostViewModel::class)
    abstract fun bindViewModel(detailPostViewModel: DetailPostViewModel): ViewModel
}