package com.anhtam.gate9.v2.game_detail.du_lieu

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.anhtam.domain.v2.Article
import com.anhtam.gate9.repository.SocialRepository
import com.anhtam.gate9.v2.newfeed.PagingViewModel
import of.bum.network.helper.Resource
import javax.inject.Inject

class DuLieuViewModel @Inject constructor(
        private val repos : SocialRepository
) : PagingViewModel<Article>() {

    private var mId: Int = 0

    fun initialize(gameId: Int) {
        mId = gameId;
    }

    override fun fetchData() = repos.getDuLieuGame(mId, mPage)
}