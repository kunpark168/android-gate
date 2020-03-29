package com.anhtam.gate9.v2.createpost

import androidx.lifecycle.ViewModel
import com.anhtam.gate9.di.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface CreatePostModule {
    @Binds
    @IntoMap
    @ViewModelKey(CreatePostViewModel::class)
    fun bindViewModel(createPostViewModel: CreatePostViewModel): ViewModel
}