package com.anhtam.gate9.v2.main.member.all

import androidx.lifecycle.ViewModel
import com.anhtam.gate9.di.ViewModelKey
import com.anhtam.gate9.v2.main.member.MemberDefaultViewModel
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