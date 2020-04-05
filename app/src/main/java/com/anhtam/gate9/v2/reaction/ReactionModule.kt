package com.anhtam.gate9.v2.reaction

import androidx.lifecycle.ViewModel
import com.anhtam.gate9.di.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

@Module
interface ReactionModule {
    @Binds
    @IntoMap
    @ViewModelKey(ReactionViewModel::class)
    fun bindViewModel(reactionViewModel: ReactionViewModel): ViewModel

    @ContributesAndroidInjector
    fun contributeReactionFragment(): ReactionFragment
}