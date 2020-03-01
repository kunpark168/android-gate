package com.anhtam.gate9.v2.discussion

import androidx.lifecycle.ViewModel
import com.anhtam.gate9.di.ViewModelKey
import com.anhtam.gate9.v2.discussion.DiscussionViewModel
import com.anhtam.gate9.v2.discussion.common.data.DataFragment
import com.anhtam.gate9.v2.discussion.common.data.DataModule
import com.anhtam.gate9.v2.discussion.common.game.GGameFragment
import com.anhtam.gate9.v2.discussion.common.game.GGameModule
import com.anhtam.gate9.v2.discussion.common.newfeed.NewFeedFragment
import com.anhtam.gate9.v2.discussion.common.newfeed.NewFeedModule
import com.anhtam.gate9.v2.discussion.common.rating.RatingFragment
import com.anhtam.gate9.v2.discussion.common.rating.RatingModule
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

@Module
abstract class UserDiscussionModule {


    @ContributesAndroidInjector(
            modules = [RatingModule::class]
    )
    abstract fun contributeRatingFragment(): RatingFragment

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