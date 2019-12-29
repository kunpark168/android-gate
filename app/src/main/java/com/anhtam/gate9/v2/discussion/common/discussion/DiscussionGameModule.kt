package com.anhtam.gate9.v2.discussion.common.discussion

import androidx.lifecycle.ViewModel
import com.anhtam.gate9.di.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class DiscussionGameModule {
    @Binds
    @IntoMap
    @ViewModelKey(DiscussionGameViewModel::class)
    abstract fun bindNewFeedViewModel(newFeedViewModel: DiscussionGameViewModel): ViewModel

}