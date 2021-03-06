package com.anhtam.gate9.v2.newfeed

import androidx.lifecycle.ViewModel
import com.anhtam.gate9.di.ViewModelKey
import com.anhtam.gate9.v2.BackgroundViewModel
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

@Module
interface NewFeedModule {
    @Binds
    @IntoMap
    @ViewModelKey(com.anhtam.gate9.v2.discussion.common.newfeed.NewFeedViewModel::class)
    fun bindMoreViewModel(newFeedViewModel: com.anhtam.gate9.v2.discussion.common.newfeed.NewFeedViewModel): ViewModel

    @ContributesAndroidInjector
    fun contributeHeaderView(): NewFeedHeaderView

    @Binds
    @IntoMap
    @ViewModelKey(BackgroundViewModel::class)
    fun bindViewModel(backgroundViewModel: BackgroundViewModel): ViewModel
}