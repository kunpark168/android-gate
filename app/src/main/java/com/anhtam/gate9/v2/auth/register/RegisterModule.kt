package com.anhtam.gate9.v2.auth.register

import androidx.lifecycle.ViewModel
import com.anhtam.gate9.di.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface RegisterModule {
    @Binds
    @IntoMap
    @ViewModelKey(RegisterViewModel::class)
    fun bindViewModel(registerViewModel: RegisterViewModel): ViewModel
}