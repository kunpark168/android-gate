package com.anhtam.gate9.v2.mxh_gate

import androidx.lifecycle.ViewModel
import com.anhtam.gate9.adapter.v2.ArticleAdapter
import com.anhtam.gate9.adapter.v2.du_lieu.HinhAnhAdapter
import com.anhtam.gate9.adapter.v2.du_lieu.VideoAdapter
import com.anhtam.gate9.di.ViewModelKey
import com.anhtam.gate9.v2.mxh_gate.anh.MXHGateImageScreen
import com.anhtam.gate9.v2.mxh_gate.cam_nang.MXHGateCamNangScreen
import com.anhtam.gate9.v2.mxh_gate.tin_game.MXHGateTinGameScreen
import com.anhtam.gate9.v2.mxh_gate.video.MXHGateVideoScreen
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

@Module
interface DuLieuModule {
    @Binds
    @IntoMap
    @ViewModelKey(DuLieuViewModel::class)
    fun bindViewModel(duLieuViewModel: DuLieuViewModel): ViewModel

    @ContributesAndroidInjector
    fun contributeFragment(): MXHGateCamNangScreen

    @ContributesAndroidInjector
    fun contributeHinhAnhFragment(): MXHGateImageScreen

    @ContributesAndroidInjector
    fun contributeVideoFragment(): MXHGateVideoScreen
}