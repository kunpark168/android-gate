package com.anhtam.gate9.di

import com.anhtam.gate9.ui.create_post.CreatePostWithFileActivity
import com.anhtam.gate9.ui.create_post.RatingActivity
import com.anhtam.gate9.ui.discussion.game.GameDiscussionActivity
import com.anhtam.gate9.ui.discussion.game.GameDiscussionModule
import com.anhtam.gate9.ui.discussion.user.UserDiscussionModule
import com.anhtam.gate9.ui.discussion.user.UserDiscussionActivity
import com.anhtam.gate9.ui.reaction.ReactionActivity
import com.anhtam.gate9.ui.report.game.ReportGameActivity
import com.anhtam.gate9.ui.report.game.ReportGameModule
import com.anhtam.gate9.ui.report.post.ReportPostActivity
import com.anhtam.gate9.ui.report.post.ReportPostModule
import com.anhtam.gate9.ui.report.user.ReportUserActivity
import com.anhtam.gate9.ui.report.user.ReportUserModule
import com.anhtam.gate9.ui.search.SearchScreen
import com.anhtam.gate9.ui.search.SearchModule
import com.anhtam.gate9.v2.MainActivity
import com.anhtam.gate9.v2.messenger.ChannelActivity
import com.anhtam.gate9.v2.messenger.ChannelModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Suppress("unused")
@Module
abstract class ActivityBuildersModule {

    @ContributesAndroidInjector(
            modules = [FragmentBuildersModule::class]
    )
    abstract fun contributeMainActivity(): MainActivity

    @ContributesAndroidInjector(
            modules = [ChannelModule::class, FragmentBuildersModule::class]
    )
    abstract fun contributeChannelActivity(): ChannelActivity

    @ContributesAndroidInjector(
            modules = [UserDiscussionModule::class])
    abstract fun contributeGamerDiscussionActivity(): UserDiscussionActivity


    @ContributesAndroidInjector(
            modules = [GameDiscussionModule::class]
    )
    abstract fun contributeGameDiscussionActivity(): GameDiscussionActivity

    @ContributesAndroidInjector(
            modules = [SearchModule::class]
    )
    abstract fun contributeCreatePostWithFileActivity(): CreatePostWithFileActivity
    @ContributesAndroidInjector(
            modules = [SearchModule::class]
    )
    abstract fun contributeRatingActivity(): RatingActivity

    @ContributesAndroidInjector(
            modules = [ReportGameModule::class]
    )
    abstract fun contributeReportGameActivity(): ReportGameActivity



    @ContributesAndroidInjector
    abstract fun contributeReactionActivity(): ReactionActivity

    @ContributesAndroidInjector(
            modules = [ReportUserModule::class]
    )
    abstract fun contributeReportUserActivity(): ReportUserActivity

    @ContributesAndroidInjector(
            modules = [ReportPostModule::class]
    )
    abstract fun contributeReportPostActivity(): ReportPostActivity
}
