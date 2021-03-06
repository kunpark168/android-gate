package com.anhtam.gate9.di

import androidx.lifecycle.ViewModel
import com.anhtam.gate9.v2.auth.login.LoginModule
import com.anhtam.gate9.v2.auth.login.LoginScreen
import com.anhtam.gate9.v2.auth.register.RegisterModule
import com.anhtam.gate9.v2.auth.register.RegisterScreen
import com.anhtam.gate9.v2.bxh.BXHModule
import com.anhtam.gate9.v2.bxh.BXHScreen
import com.anhtam.gate9.v2.ca_nhan.CaNhanScreen
import com.anhtam.gate9.v2.categories.CategoryModule
import com.anhtam.gate9.v2.categories.FeatureScreen
import com.anhtam.gate9.v2.chi_tiet_bai_viet.ChiTietBaiVietModule
import com.anhtam.gate9.v2.chi_tiet_bai_viet.tin_game.ChiTietBaiVietHinhAnhScreen
import com.anhtam.gate9.v2.chi_tiet_bai_viet.tin_game.ChiTietBaiVietTinGameScreen
import com.anhtam.gate9.v2.chi_tiet_bai_viet.tin_game.ChiTietBaiVietVideoScreen
import com.anhtam.gate9.v2.createimage.CreateImageScreen
import com.anhtam.gate9.v2.createpost.CreatePostModule
import com.anhtam.gate9.v2.createpost.CreatePostScreen
import com.anhtam.gate9.v2.danh_gia.RatingFragment
import com.anhtam.gate9.v2.discussion.UserDiscussionModule
import com.anhtam.gate9.v2.follow.FollowModule
import com.anhtam.gate9.v2.follow.FollowScreen
import com.anhtam.gate9.v2.gallery.GalleryScreen
import com.anhtam.gate9.v2.game_detail.DetailGameFragment
import com.anhtam.gate9.v2.game_detail.DetailGameModule
import com.anhtam.gate9.v2.game_detail.download.DownloadGameFragment
import com.anhtam.gate9.v2.main.home.HomeFragment
import com.anhtam.gate9.v2.main.home.HomeModule
import com.anhtam.gate9.v2.member.MemberFragment
import com.anhtam.gate9.v2.member.MemberModule
import com.anhtam.gate9.v2.member.all.MemberListFragment
import com.anhtam.gate9.v2.member.all.MemberListModule
import com.anhtam.gate9.v2.messenger.ChannelModule
import com.anhtam.gate9.v2.messenger.chat.ChatFragment
import com.anhtam.gate9.v2.messenger.chat.ChatModule
import com.anhtam.gate9.v2.messenger.inbox.ChannelLetterFragment
import com.anhtam.gate9.v2.messenger.inbox.CreateLetterFragment
import com.anhtam.gate9.v2.mxh_game.MXHGameModule
import com.anhtam.gate9.v2.mxh_game.MXHGameScreen
import com.anhtam.gate9.v2.mxh_gate.DuLieuModule
import com.anhtam.gate9.v2.mxh_gate.MXHGateScreen
import com.anhtam.gate9.v2.newfeed.NewFeedViewModel
import com.anhtam.gate9.v2.notification.NotificationFragment
import com.anhtam.gate9.v2.notification.NotificationModule
import com.anhtam.gate9.v2.nph_detail.DetailNPHFragment
import com.anhtam.gate9.v2.post_detail.DetailPostModule
import com.anhtam.gate9.v2.post_detail.DetailPostScreen
import com.anhtam.gate9.v2.reaction.ReactionModule
import com.anhtam.gate9.v2.reaction.ReactionScreen
import com.anhtam.gate9.v2.report.game.ReportGameActivity
import com.anhtam.gate9.v2.report.game.ReportGameModule
import com.anhtam.gate9.v2.report.post.ReportPostActivity
import com.anhtam.gate9.v2.report.post.ReportPostModule
import com.anhtam.gate9.v2.report.user.ReportUserActivity
import com.anhtam.gate9.v2.report.user.ReportUserModule
import com.anhtam.gate9.v2.search.SearchModule
import com.anhtam.gate9.v2.search.SearchScreen
import com.anhtam.gate9.v2.shared.muilti_gallery.GalleryModule
import com.anhtam.gate9.v2.shared.muilti_gallery.MultiChooseImageScreen
import com.anhtam.gate9.v2.splash.SplashScreen
import com.anhtam.gate9.v2.user_detail.DetailUserFragment
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

@Module
@Suppress("unused")
abstract class FragmentBuildersModule {

    @ContributesAndroidInjector(
            modules = [CreatePostModule::class]
    )
    abstract fun contributeCreatePostScreen(): CreatePostScreen

    @ContributesAndroidInjector
    abstract fun contributeCreateImageScreen(): CreateImageScreen

    @ContributesAndroidInjector
    abstract fun contributeSplashScreen(): SplashScreen

    @ContributesAndroidInjector(
            modules = [LoginModule::class]
    )
    abstract fun contributeLoginScreen(): LoginScreen

    @ContributesAndroidInjector(
            modules = [RegisterModule::class]
    )
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
    abstract fun contributeMemberHomeFragment(): MemberFragment


    @ContributesAndroidInjector(
            modules = [MemberListModule::class]
    )
    abstract fun contributeMemberListFragment(): MemberListFragment

    @ContributesAndroidInjector(
            modules = [ChannelModule::class]
    )
    abstract fun contributeMessengerFragment(): ChannelLetterFragment

    @ContributesAndroidInjector
    abstract fun contributeInboxFragment(): CreateLetterFragment

    @ContributesAndroidInjector(
            modules = [ChatModule::class]
    )
    abstract fun contributeChatFragment(): ChatFragment


    @ContributesAndroidInjector(
            modules = [BXHModule::class]
    )
    abstract fun contributeChartsActivity(): BXHScreen


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

    @ContributesAndroidInjector(
            modules = [DuLieuModule::class]
    )
    abstract fun contributeMXHGateScreen() :MXHGateScreen

    @ContributesAndroidInjector(
            modules = [SearchModule::class]
    )
    abstract fun contributeSearchScreen(): SearchScreen

    @ContributesAndroidInjector(
            modules = [ReactionModule::class]
    )
    abstract fun contributeReactionScreen(): ReactionScreen

    @ContributesAndroidInjector(
            modules = [UserDiscussionModule::class])
    abstract fun contributeDetailUserActivity(): DetailUserFragment

    @ContributesAndroidInjector(
            modules = [UserDiscussionModule::class])
    abstract fun contributeDetailNPHActivity(): DetailNPHFragment


    @ContributesAndroidInjector(
            modules = [DetailGameModule::class]
    )
    abstract fun contributeDetailGameFragment(): DetailGameFragment

    @ContributesAndroidInjector
    abstract fun contributeGalleryScreen(): GalleryScreen

    @ContributesAndroidInjector(
            modules = [GalleryModule::class]
    )
    abstract fun contributeMultiChooseImageScreen(): MultiChooseImageScreen

    @ContributesAndroidInjector(
            modules = [FollowModule::class]
    )
    abstract fun contributeFollowScreen(): FollowScreen

//    @ContributesAndroidInjector(
//            modules = [ChannelModule::class, FragmentBuildersModule::class]
//    )
//    abstract fun contributeChannelActivity(): ChannelActivity
//
    @ContributesAndroidInjector(
            modules = [ReportGameModule::class]
    )
    abstract fun contributeReportGameActivity(): ReportGameActivity

    @ContributesAndroidInjector(
            modules = [ReportUserModule::class]
    )
    abstract fun contributeReportUserActivity(): ReportUserActivity

    @ContributesAndroidInjector(
            modules = [ReportPostModule::class]
    )
    abstract fun contributeReportPostActivity(): ReportPostActivity

    @ContributesAndroidInjector(
            modules = [ChiTietBaiVietModule::class]
    )
    abstract fun contributeChiTietBaiVietTin(): ChiTietBaiVietTinGameScreen

    @ContributesAndroidInjector(
            modules = [ChiTietBaiVietModule::class]
    )
    abstract fun contributeChiTietBaiVietAnh(): ChiTietBaiVietHinhAnhScreen

    @ContributesAndroidInjector(
            modules = [ChiTietBaiVietModule::class]
    )
    abstract fun contributeChiTietBaiVietVideo(): ChiTietBaiVietVideoScreen

    @ContributesAndroidInjector
    abstract fun contributeUploadRating(): RatingFragment

    @ContributesAndroidInjector
    abstract fun contributeDownloadGame(): DownloadGameFragment

    @Binds
    @IntoMap
    @ViewModelKey(NewFeedViewModel::class)
    abstract fun bindViewModel(newFeedViewModel: NewFeedViewModel): ViewModel

}