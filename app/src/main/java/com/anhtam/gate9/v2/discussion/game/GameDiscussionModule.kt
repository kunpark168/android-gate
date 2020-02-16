package com.anhtam.gate9.v2.discussion.game

import androidx.lifecycle.ViewModel
import com.anhtam.gate9.di.ViewModelKey
import com.anhtam.gate9.v2.ChiTietGameViewModel
import com.anhtam.gate9.v2.discussion.DiscussionViewModel
import com.anhtam.gate9.v2.discussion.common.data.DataFragment
import com.anhtam.gate9.v2.discussion.common.data.DataModule
import com.anhtam.gate9.v2.discussion.common.discussion.DiscussionGameFragment
import com.anhtam.gate9.v2.discussion.common.discussion.DiscussionGameModule
import com.anhtam.gate9.v2.discussion.common.document.GameDocumentFragment
import com.anhtam.gate9.v2.discussion.common.game.GGameFragment
import com.anhtam.gate9.v2.discussion.common.game.GGameModule
import com.anhtam.gate9.v2.discussion.common.info.GameInfoFragment
import com.anhtam.gate9.v2.discussion.common.rating.RatingFragment
import com.anhtam.gate9.v2.discussion.common.rating.RatingModule
import com.anhtam.gate9.v2.discussion.game.thao_luan.ThaoLuanFragment
import com.anhtam.gate9.v2.discussion.game.thao_luan.ThaoLuanModule
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

@Module
abstract class GameDiscussionModule {

    /*
     * Social Discussion
     */
    @ContributesAndroidInjector
    abstract fun contributeHeaderFragment(): GameHeaderFragment

    @ContributesAndroidInjector(
            modules = [RatingModule::class]
    )
    abstract fun contributeRatingFragment(): RatingFragment

    @ContributesAndroidInjector
    abstract fun contributeGameInfoFragment(): GameInfoFragment

    @ContributesAndroidInjector(
            modules = [DataModule::class]
    )
    abstract fun contributeDataFragment(): DataFragment

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
    @ViewModelKey(DiscussionViewModel::class)
    abstract fun bindGameDiscussionViewModel(discussionViewModel: DiscussionViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ChiTietGameViewModel::class)
    abstract fun bindChiTietDiscussionViewModel(chiTietGameViewModel: ChiTietGameViewModel): ViewModel

}