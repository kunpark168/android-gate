package com.anhtam.gate9.v2.notification

import androidx.lifecycle.ViewModel
import com.anhtam.gate9.di.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

@Module
abstract class NotificationModule {

    @ContributesAndroidInjector
    abstract fun contributeAllNotificationFragment(): AllNotificationFragment

    @ContributesAndroidInjector
    abstract fun contributeSystemNotificationFragment(): SystemNotificationFragment

    @Binds
    @IntoMap
    @ViewModelKey(NotificationViewModel::class)
    abstract fun bindNotificationViewModel(notificationViewModel: NotificationViewModel): ViewModel
}