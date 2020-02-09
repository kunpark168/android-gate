package com.anhtam.gate9.v2.charts

import androidx.lifecycle.ViewModel
import com.anhtam.gate9.di.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface ChartModule {
    @Binds
    @IntoMap
    @ViewModelKey(ChartViewModel::class)
    fun bindViewModel(chartViewModel: ChartViewModel): ViewModel
}