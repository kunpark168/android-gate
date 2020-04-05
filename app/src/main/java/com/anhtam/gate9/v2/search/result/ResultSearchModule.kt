package com.anhtam.gate9.v2.search.result

import androidx.lifecycle.ViewModel
import com.anhtam.gate9.di.ViewModelKey
import com.anhtam.gate9.v2.search.SearchViewModel
import com.anhtam.gate9.v2.search.result.article.ArticleSearchModule
import com.anhtam.gate9.v2.search.result.article.ArticleSearchResultFragment
import com.anhtam.gate9.v2.search.result.game.GameSearchModule
import com.anhtam.gate9.v2.search.result.game.GameSearchResultFragment
import com.anhtam.gate9.v2.search.result.member.MemberSearchModule
import com.anhtam.gate9.v2.search.result.member.MemberSearchResultFragment
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

@Module
interface ResultSearchModule {

    @ContributesAndroidInjector(
            modules = [GameSearchModule::class]
    )
    fun contributeGameSearchFragment(): GameSearchResultFragment

    @ContributesAndroidInjector(
            modules = [MemberSearchModule::class]
    )
    fun contributeMemberSearchFragment(): MemberSearchResultFragment

    @ContributesAndroidInjector(
            modules = [ArticleSearchModule::class]
    )
    fun contributeArticleSearchFragment(): ArticleSearchResultFragment
}