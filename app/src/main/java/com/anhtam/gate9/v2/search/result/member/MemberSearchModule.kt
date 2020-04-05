package com.anhtam.gate9.v2.search.result.member

import androidx.lifecycle.ViewModel
import com.anhtam.gate9.di.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface MemberSearchModule  {
    @Binds
    @IntoMap
    @ViewModelKey(MemberSearchViewModel::class)
    fun bindViewModel(memberSearchViewModel: MemberSearchViewModel): ViewModel
}