package com.anhtam.gate9.ui.discussion.user

import androidx.lifecycle.ViewModel
import com.anhtam.gate9.di.ViewModelKey
import com.anhtam.gate9.ui.discussion.DiscussionViewModel
import com.anhtam.gate9.ui.discussion.common.info.UserInfoFragment
import com.anhtam.gate9.ui.discussion.common.data.DataFragment
import com.anhtam.gate9.ui.discussion.common.data.DataModule
import com.anhtam.gate9.ui.discussion.common.game.GGameFragment
import com.anhtam.gate9.ui.discussion.common.game.GGameModule
import com.anhtam.gate9.ui.discussion.common.info.NPHInfoFragment
import com.anhtam.gate9.ui.discussion.common.newfeed.NewFeedFragment
import com.anhtam.gate9.ui.discussion.common.newfeed.NewFeedModule
import com.anhtam.gate9.ui.discussion.common.rating.RatingFragment
import com.anhtam.gate9.ui.discussion.common.rating.RatingModule
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

@Module
abstract class UserDiscussionModule {

    /*
     * Social Discussion
     */
    @ContributesAndroidInjector
    abstract fun contributeHeaderFragment(): UserHeaderFragment

    @ContributesAndroidInjector(
            modules = [RatingModule::class]
    )
    abstract fun contributeRatingFragment(): RatingFragment

    @ContributesAndroidInjector
    abstract fun contributeUserInfoFragment(): UserInfoFragment


    @ContributesAndroidInjector
    abstract fun contributeNPHInfoFragment(): NPHInfoFragment

    @ContributesAndroidInjector(
            modules = [DataModule::class]
    )
    abstract fun contributeDataFragment(): DataFragment

    @ContributesAndroidInjector(
            modules = [NewFeedModule::class]
    )
    abstract fun contributeNewFeedFragment(): NewFeedFragment

    @ContributesAndroidInjector(
            modules = [GGameModule::class]
    )
    abstract fun contributeGGameFragment(): GGameFragment

    @Binds
    @IntoMap
    @ViewModelKey(DiscussionViewModel::class)
    abstract fun bindGameDiscussionViewModel(discussionViewModel: DiscussionViewModel): ViewModel

}