package com.anhtam.gate9.v2.shared.muilti_gallery

import androidx.lifecycle.ViewModel
import com.anhtam.gate9.di.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

@Module
interface GalleryModule {
    @ContributesAndroidInjector
    fun contributeGalleryImagesFragment(): GalleryImagesFragment

    @Binds
    @IntoMap
    @ViewModelKey(GalleryViewModel::class)
    fun bindViewModel(galleryViewModel: GalleryViewModel): ViewModel
}