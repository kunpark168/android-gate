package com.anhtam.gate9.v2.search.result.article

import androidx.lifecycle.ViewModel
import com.anhtam.gate9.di.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface ArticleSearchModule {

    @Binds
    @IntoMap
    @ViewModelKey(ArticleSearchViewModel::class)
    fun bindViewModel(articleSearchViewModel: ArticleSearchViewModel): ViewModel
}