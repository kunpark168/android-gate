package com.anhtam.gate9.v2.search.result

import com.anhtam.gate9.v2.search.game.GameSearchFragment
import com.anhtam.gate9.v2.search.result.game.GameSearchModule
import com.anhtam.gate9.v2.search.result.game.GameSearchResultFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
interface ResultSearchModule {

    @ContributesAndroidInjector(
            modules = [GameSearchModule::class]
    )
    fun contributeGameSearchFragment(): GameSearchResultFragment
}