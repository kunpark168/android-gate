package com.anhtam.gate9.v2.newfeed

import androidx.lifecycle.ViewModel
import com.anhtam.gate9.di.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface NewFeedModule {
    @Binds
    @IntoMap
    @ViewModelKey(NewFeedViewModel::class)
    fun bindViewModel(newFeedViewModel: NewFeedViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(com.anhtam.gate9.v2.discussion.common.newfeed.NewFeedViewModel::class)
    fun bindMoreViewModel(newFeedViewModel: com.anhtam.gate9.v2.discussion.common.newfeed.NewFeedViewModel): ViewModel
}