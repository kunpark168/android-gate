package com.anhtam.gate9.v2.categories

import androidx.lifecycle.ViewModel
import com.anhtam.gate9.di.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

@Module
abstract class CategoryModule {
    @ContributesAndroidInjector
    abstract fun contributeCategoryFragment(): FeatureChildrenFragment

    @Binds
    @IntoMap
    @ViewModelKey(CategoryViewmodel::class)
    abstract fun bindCategoryModule(categoryViewModel: CategoryViewmodel): ViewModel
}