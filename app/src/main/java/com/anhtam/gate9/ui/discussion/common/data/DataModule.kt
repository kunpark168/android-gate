package com.anhtam.gate9.ui.discussion.common.data

import androidx.lifecycle.ViewModel
import com.anhtam.gate9.di.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class DataModule {
    @Binds
    @IntoMap
    @ViewModelKey(DataViewModel::class)
    abstract fun bindDataViewModel(newFeedViewModel: DataViewModel): ViewModel

}