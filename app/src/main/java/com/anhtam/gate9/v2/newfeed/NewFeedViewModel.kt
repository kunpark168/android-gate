package com.anhtam.gate9.v2.newfeed

import androidx.lifecycle.ViewModel
import com.anhtam.domain.v2.WrappedHome
import com.anhtam.gate9.v2.InfoService
import of.bum.network.FetchBoundResource
import of.bum.network.v2.SocialService
import javax.inject.Inject

/*
 * Common:
 *  - Banner
 *  - Group banners
 *  - User info load Storage ->
 */

class NewFeedViewModel @Inject constructor(
        val socialService: SocialService,
        val infoService: InfoService) : ViewModel() {

    fun getListingPost() = object : FetchBoundResource<WrappedHome>(){
        override fun createCall() = socialService.getListPosts()
    }.asLiveData()

    fun getInfoUser() = infoService.getInfo()
    fun react(params: Map<String, Int>) = infoService.react(params)
}