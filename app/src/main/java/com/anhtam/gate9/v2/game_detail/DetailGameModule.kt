package com.anhtam.gate9.v2.game_detail

import androidx.lifecycle.ViewModel
import com.anhtam.gate9.di.ViewModelKey
import com.anhtam.gate9.v2.discussion.common.discussion.DiscussionGameFragment
import com.anhtam.gate9.v2.discussion.common.discussion.DiscussionGameModule
import com.anhtam.gate9.v2.discussion.common.game.GGameFragment
import com.anhtam.gate9.v2.discussion.common.game.GGameModule
import com.anhtam.gate9.v2.game_detail.danh_gia.DanhGiaGameFragment
import com.anhtam.gate9.v2.game_detail.danh_gia.DanhGiaModule
import com.anhtam.gate9.v2.game_detail.du_lieu.DuLieuFragment
import com.anhtam.gate9.v2.game_detail.du_lieu.DuLieuModule
import com.anhtam.gate9.v2.game_detail.thao_luan.ThaoLuanFragment
import com.anhtam.gate9.v2.game_detail.thao_luan.ThaoLuanModule
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

@Module
abstract class DetailGameModule {

    @ContributesAndroidInjector(
            modules = [DanhGiaModule::class]
    )
    abstract fun contributeRatingFragment(): DanhGiaGameFragment

    @ContributesAndroidInjector(
            modules = [DuLieuModule::class]
    )
    abstract fun contributeDataFragment(): DuLieuFragment

    @ContributesAndroidInjector(
            modules = [ThaoLuanModule::class]
    )
    abstract fun contributeThaoLuanFragment(): ThaoLuanFragment

    @ContributesAndroidInjector(
            modules = [GGameModule::class]
    )
    abstract fun contributeGGameFragment(): GGameFragment

    @ContributesAndroidInjector(
            modules = [DiscussionGameModule::class]
    )
    abstract fun contributeDiscussionFragment(): DiscussionGameFragment

    @Binds
    @IntoMap
    @ViewModelKey(DetailGameViewModel::class)
    abstract fun bindViewModel(detailGameViewModel: DetailGameViewModel): ViewModel

}