package com.anhtam.gate9.ui.search

import androidx.lifecycle.ViewModel
import com.anhtam.gate9.di.ViewModelKey
import com.anhtam.gate9.ui.search.chart.ChartSearchFragment
import com.anhtam.gate9.ui.search.result.ResultSearchFragment
import com.anhtam.gate9.ui.search.temp.TempSearchFragment
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

@Module
abstract class SearchModule {

    /*
     * Fragment
     */
    @ContributesAndroidInjector
    abstract fun contributeChartSearchFragment(): ChartSearchFragment

    @ContributesAndroidInjector
    abstract fun contributeTempSearchFragment(): TempSearchFragment

    @ContributesAndroidInjector
    abstract fun contributeResultSearchFragment(): ResultSearchFragment

    @Binds
    @IntoMap
    @ViewModelKey(SearchViewModel::class)
    abstract fun bindNotificationViewModel(searchViewModel: SearchViewModel): ViewModel
}