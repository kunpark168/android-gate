package com.anhtam.gate9.v2.discussion.common.newfeed

import androidx.lifecycle.ViewModel
import com.anhtam.gate9.di.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class NewFeedModule {
    @Binds
    @IntoMap
    @ViewModelKey(NewFeedViewModel::class)
    abstract fun bindNewFeedViewModel(newFeedViewModel: NewFeedViewModel): ViewModel

}