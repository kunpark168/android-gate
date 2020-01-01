package com.anhtam.gate9.v2.report.user

import androidx.lifecycle.ViewModel
import com.anhtam.gate9.di.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ReportUserModule {
    @Binds
    @IntoMap
    @ViewModelKey(ReportUserViewModel::class)
    abstract fun bindReportUserViewModel(reportUserViewModel: ReportUserViewModel): ViewModel

}