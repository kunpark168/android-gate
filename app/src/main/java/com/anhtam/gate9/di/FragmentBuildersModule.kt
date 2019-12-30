package com.anhtam.gate9.di

import androidx.lifecycle.ViewModel
import com.anhtam.gate9.v2.reaction.ReactionScreen
import com.anhtam.gate9.v2.search.SearchModule
import com.anhtam.gate9.v2.search.SearchScreen
import com.anhtam.gate9.v2.newfeed.NewFeedModule
import com.anhtam.gate9.v2.categories.CategoryModule
import com.anhtam.gate9.v2.categories.FeatureScreen
import com.anhtam.gate9.v2.charts.ChartScreen
import com.anhtam.gate9.v2.notification.NotificationFragment
import com.anhtam.gate9.v2.notification.NotificationModule
import com.anhtam.gate9.v2.main.member.MemberHomeFragment
import com.anhtam.gate9.v2.main.member.MemberModule
import com.anhtam.gate9.v2.main.member.all.MemberListFragment
import com.anhtam.gate9.v2.main.member.all.MemberListModule
import com.anhtam.gate9.v2.auth.register.RegisterScreen
import com.anhtam.gate9.v2.auth.login.LoginScreen
import com.anhtam.gate9.v2.auth.login.LoginModule
import com.anhtam.gate9.v2.ca_nhan.CaNhanScreen
import com.anhtam.gate9.v2.createimage.CreateImageScreen
import com.anhtam.gate9.v2.createpost.CreatePostScreen
import com.anhtam.gate9.v2.discussion.game.GameDiscussionScreen
import com.anhtam.gate9.v2.discussion.game.GameDiscussionModule
import com.anhtam.gate9.v2.discussion.user.UserDiscussionScreen
import com.anhtam.gate9.v2.discussion.user.UserDiscussionModule
import com.anhtam.gate9.v2.gallery.GalleryScreen
import com.anhtam.gate9.v2.mxh_game.MXHGameScreen
import com.anhtam.gate9.v2.main.home.HomeFragment
import com.anhtam.gate9.v2.main.home.HomeModule
import com.anhtam.gate9.v2.messenger.ChannelFragment
import com.anhtam.gate9.v2.messenger.chat.ChatFragment
import com.anhtam.gate9.v2.messenger.inbox.InboxFragment
import com.anhtam.gate9.v2.newfeed.NewFeedScreen
import com.anhtam.gate9.v2.messenger.ChannelModule
import com.anhtam.gate9.v2.messenger.chat.ChatModule
import com.anhtam.gate9.v2.mxh_game.MXHGameModule
import com.anhtam.gate9.v2.mxh_gate.MXHGateScreen
import com.anhtam.gate9.v2.mxh_gate.anh.MXHGateImageScreen
import com.anhtam.gate9.v2.mxh_gate.cam_nang.MXHGateCamNangScreen
import com.anhtam.gate9.v2.mxh_gate.tin_game.MXHGateTinGameScreen
import com.anhtam.gate9.v2.mxh_gate.video.MXHGateVideoScreen
import com.anhtam.gate9.v2.newfeed.NewFeedViewModel
import com.anhtam.gate9.v2.post.DetailPostModule
import com.anhtam.gate9.v2.post.DetailPostScreen
import com.anhtam.gate9.v2.splash.SplashScreen
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

@Module
@Suppress("unused")
abstract class FragmentBuildersModule {

    @ContributesAndroidInjector
    abstract fun contributeCreatePostScreen(): CreatePostScreen

    @ContributesAndroidInjector
    abstract fun contributeCreateImageScreen(): CreateImageScreen

    @ContributesAndroidInjector
    abstract fun contributeSplashScreen(): SplashScreen

    @ContributesAndroidInjector(
            modules = [LoginModule::class]
    )
    abstract fun contributeLoginScreen(): LoginScreen

    @ContributesAndroidInjector
    abstract fun contributeRegisterScreen(): RegisterScreen

    @ContributesAndroidInjector(
            modules = [HomeModule::class]
    )
    abstract fun contributeHomeFragment(): HomeFragment

    @ContributesAndroidInjector(
            modules = [NotificationModule::class]
    )
    abstract fun contributeNotificationFragment(): NotificationFragment


    @ContributesAndroidInjector(
            modules = [MemberModule::class]
    )
    abstract fun contributeMemberHomeFragment(): MemberHomeFragment


    @ContributesAndroidInjector(
            modules = [MemberListModule::class]
    )
    abstract fun contributeMemberListFragment(): MemberListFragment

    @ContributesAndroidInjector(
            modules = [ChannelModule::class]
    )
    abstract fun contributeMessengerFragment(): ChannelFragment

    @ContributesAndroidInjector
    abstract fun contributeInboxFragment(): InboxFragment

    @ContributesAndroidInjector(
            modules = [ChatModule::class]
    )
    abstract fun contributeChatFragment(): ChatFragment


    @ContributesAndroidInjector
    abstract fun contributeChartsActivity(): ChartScreen


    /*FeatureScreen*/
    @ContributesAndroidInjector(modules = [CategoryModule::class])
    abstract fun contributeFeatureScreen(): FeatureScreen

    @ContributesAndroidInjector(
            modules = [DetailPostModule::class]
    )
    abstract fun contributeDetailPostScreen() :DetailPostScreen


    @ContributesAndroidInjector(
            modules = [MXHGameModule::class]
    )
    abstract fun contributeGameScreen() :MXHGameScreen

    @ContributesAndroidInjector
    abstract fun contributeCaNhanScreen() :CaNhanScreen

    @ContributesAndroidInjector
    abstract fun contributeMXHGateScreen() :MXHGateScreen

    @ContributesAndroidInjector
    abstract fun contributeMXHGateTinGameScreen() :MXHGateTinGameScreen

    @ContributesAndroidInjector
    abstract fun contributeMXHCamNangScreen() :MXHGateCamNangScreen

    @ContributesAndroidInjector
    abstract fun contributeMXHGateVideoScreen() :MXHGateVideoScreen

    @ContributesAndroidInjector
    abstract fun contributeMXHGateImageScreen() :MXHGateImageScreen

    @ContributesAndroidInjector(
            modules = [SearchModule::class]
    )
    abstract fun contributeSearchScreen(): SearchScreen

    @ContributesAndroidInjector
    abstract fun contributeReactionScreen(): ReactionScreen

    @ContributesAndroidInjector(
            modules = [UserDiscussionModule::class])
    abstract fun contributeGamerDiscussionActivity(): UserDiscussionScreen


    @ContributesAndroidInjector(
            modules = [GameDiscussionModule::class]
    )
    abstract fun contributeGameDiscussionActivity(): GameDiscussionScreen

    @ContributesAndroidInjector
    abstract fun contributeGalleryScreen(): GalleryScreen

//    @ContributesAndroidInjector(
//            modules = [ChannelModule::class, FragmentBuildersModule::class]
//    )
//    abstract fun contributeChannelActivity(): ChannelActivity
//
//    @ContributesAndroidInjector(
//            modules = [ReportGameModule::class]
//    )
//    abstract fun contributeReportGameActivity(): ReportGameActivity
//
//    @ContributesAndroidInjector(
//            modules = [ReportUserModule::class]
//    )
//    abstract fun contributeReportUserActivity(): ReportUserActivity
//
//    @ContributesAndroidInjector(
//            modules = [ReportPostModule::class]
//    )
//    abstract fun contributeReportPostActivity(): ReportPostActivity

    @Binds
    @IntoMap
    @ViewModelKey(NewFeedViewModel::class)
    abstract fun bindViewModel(newFeedViewModel: NewFeedViewModel): ViewModel

}