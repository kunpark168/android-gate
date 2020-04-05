package com.anhtam.gate9.v2.report.game

import androidx.lifecycle.ViewModel
import com.anhtam.gate9.di.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ReportGameModule {
    @Binds
    @IntoMap
    @ViewModelKey(ReportGameViewModel::class)
    abstract fun bindReportGameViewModel(reportGameViewModel: ReportGameViewModel): ViewModel

}