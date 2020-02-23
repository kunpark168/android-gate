package com.anhtam.gate9.v2.discussion.game.danh_gia

import androidx.lifecycle.ViewModel
import com.anhtam.gate9.di.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface DanhGiaModule{
    @Binds
    @IntoMap
    @ViewModelKey(DanhGiaViewModel::class)
    fun bindViewModel(danhGiaViewModel: DanhGiaViewModel): ViewModel
}