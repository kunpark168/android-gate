package com.anhtam.gate9.v2.chi_tiet_bai_viet

import androidx.lifecycle.ViewModel
import com.anhtam.gate9.di.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface ChiTietBaiVietModule {
    @Binds
    @IntoMap
    @ViewModelKey(ChiTietBaiVietViewModel::class)
    fun bindViewModel(chiTietBaiVietViewModel: ChiTietBaiVietViewModel): ViewModel
}