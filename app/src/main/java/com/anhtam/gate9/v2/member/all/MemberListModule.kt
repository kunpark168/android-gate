package com.anhtam.gate9.v2.member.all

import androidx.lifecycle.ViewModel
import com.anhtam.gate9.di.ViewModelKey
import com.anhtam.gate9.v2.member.MemberDefaultViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class MemberListModule {

    /* Member */
    @Binds
    @IntoMap
    @ViewModelKey(MemberDefaultViewModel::class)
    abstract fun bindMemberDefaultViewModel(memberViewModel: MemberDefaultViewModel): ViewModel
}