package com.anhtam.gate9.v2.ca_nhan

import androidx.lifecycle.ViewModel
import com.anhtam.gate9.di.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface CaNhanModule {
    @Binds
    @IntoMap
    @ViewModelKey(UserViewModel::class)
    fun bindViewModel(userViewModel: UserViewModel): ViewModel
}