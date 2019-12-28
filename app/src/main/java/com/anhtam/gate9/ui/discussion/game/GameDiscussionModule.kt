package com.anhtam.gate9.ui.discussion.game

import androidx.lifecycle.ViewModel
import com.anhtam.gate9.di.ViewModelKey
import com.anhtam.gate9.ui.discussion.DiscussionViewModel
import com.anhtam.gate9.ui.discussion.common.info.UserInfoFragment
import com.anhtam.gate9.ui.discussion.common.data.DataFragment
import com.anhtam.gate9.ui.discussion.common.data.DataModule
import com.anhtam.gate9.ui.discussion.common.discussion.DiscussionGameFragment
import com.anhtam.gate9.ui.discussion.common.discussion.DiscussionGameModule
import com.anhtam.gate9.ui.discussion.common.document.GameDocumentFragment
import com.anhtam.gate9.ui.discussion.common.game.GGameFragment
import com.anhtam.gate9.ui.discussion.common.game.GGameModule
import com.anhtam.gate9.ui.discussion.common.info.GameInfoFragment
import com.anhtam.gate9.ui.discussion.common.rating.RatingFragment
import com.anhtam.gate9.ui.discussion.common.rating.RatingModule
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
    )
    abstract fun contributeDocumentFragment(): GameDocumentFragment

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

}