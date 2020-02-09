package com.anhtam.gate9.di

import androidx.lifecycle.ViewModel
import com.anhtam.gate9.navigation.HideKeyboardNavigation
import com.anhtam.gate9.navigation.Navigation
import com.anhtam.gate9.v2.BackgroundViewModel
import com.anhtam.gate9.v2.shared.ShareViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface NavigationModule {
    @Binds
    fun bindNavigation(navigation: HideKeyboardNavigation): Navigation

    @Binds
    @IntoMap
    @ViewModelKey(ShareViewModel::class)
    fun bindShareViewModel(shareViewModel: ShareViewModel): ViewModel
}