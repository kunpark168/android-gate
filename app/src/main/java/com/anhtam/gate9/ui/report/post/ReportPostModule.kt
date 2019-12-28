package com.anhtam.gate9.ui.report.post

import androidx.lifecycle.ViewModel
import com.anhtam.gate9.di.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ReportPostModule {
    @Binds
    @IntoMap
    @ViewModelKey(ReportPostViewModel::class)
    abstract fun bindReportPostViewModel(reportUserViewModel: ReportPostViewModel): ViewModel

}