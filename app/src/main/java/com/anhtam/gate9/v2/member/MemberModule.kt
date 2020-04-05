package com.anhtam.gate9.v2.member

import androidx.lifecycle.ViewModel
import com.anhtam.gate9.di.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

@Module
abstract class MemberModule {

    @ContributesAndroidInjector
    abstract fun contributeMemberFragment(): MemberTabFragment

    /* Member */
    @Binds
    @IntoMap
    @ViewModelKey(MemberDefaultViewModel::class)
    abstract fun bindMemberDefaultViewModel(memberViewModel: MemberDefaultViewModel): ViewModel

}