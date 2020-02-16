package com.anhtam.gate9.v2.discussion.game.thao_luan

import androidx.lifecycle.LiveData
import com.anhtam.domain.v2.Post
import com.anhtam.gate9.repository.SocialRepository
import com.anhtam.gate9.v2.discussion.common.CommonDiscussionViewModel
import com.anhtam.gate9.v2.newfeed.PagingViewModel
import of.bum.network.helper.Resource
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