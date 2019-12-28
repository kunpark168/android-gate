package com.anhtam.gate9.v2.thong_tin.user

import androidx.lifecycle.ViewModel
import com.anhtam.gate9.di.ViewModelKey
import com.anhtam.gate9.ui.discussion.DiscussionViewModel
import com.anhtam.gate9.ui.discussion.common.discussion.DiscussionGameViewModel
import com.anhtam.gate9.ui.discussion.common.newfeed.NewFeedFragment
import com.anhtam.gate9.ui.discussion.common.newfeed.NewFeedModule
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

@Module
abstract class ThongTinNguoiDungModule {

    @ContributesAndroidInjector(
            modules = [NewFeedModule::class]
    )
    abstract fun contributeNewFeedFragment(): NewFeedFragment

    @Binds
    @IntoMap
    @ViewModelKey(DiscussionViewModel::class)
    abstract fun bindViewModel(discussionViewModel: DiscussionViewModel): ViewModel
}