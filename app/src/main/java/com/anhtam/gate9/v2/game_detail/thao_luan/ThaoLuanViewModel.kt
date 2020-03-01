package com.anhtam.gate9.v2.game_detail.thao_luan

import com.anhtam.domain.v2.Post
import com.anhtam.gate9.repository.SocialRepository
import com.anhtam.gate9.v2.newfeed.PagingViewModel
import javax.inject.Inject

class ThaoLuanViewModel @Inject constructor(
        private val repos: SocialRepository
) : PagingViewModel<Post>() {

    private var mGameId: Int = 0

    fun initialize(gameId: Int){
        mGameId = gameId
    }

    override fun fetchData() = repos.getThaoLuanGame(mGameId, mPage)
}