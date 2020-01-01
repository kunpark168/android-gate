package com.anhtam.gate9.v2.discussion.common.rating

import androidx.lifecycle.ViewModel
import com.anhtam.gate9.di.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class RatingModule {
    @Binds
    @IntoMap
    @ViewModelKey(RatingViewModel::class)
    abstract fun bindRatingViewModel(ratingViewModel: RatingViewModel): ViewModel
}