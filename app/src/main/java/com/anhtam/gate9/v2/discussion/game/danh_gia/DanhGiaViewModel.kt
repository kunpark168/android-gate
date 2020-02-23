package com.anhtam.gate9.v2.discussion.game.danh_gia

import androidx.lifecycle.LiveData
import com.anhtam.gate9.repository.SocialRepository
import com.anhtam.gate9.utils.AbsentLiveData
import com.anhtam.gate9.v2.newfeed.PagingViewModel
import com.anhtam.gate9.vo.Rating
import of.bum.network.helper.Resource
import javax.inject.Inject

class DanhGiaViewModel @Inject constructor(
        private val repos: SocialRepository
): PagingViewModel<Rating>(){

    private var mGameId: Int = 0

    fun initialize(gameId: Int){
        mGameId = gameId
    }

    override fun fetchData() = repos.getGameRating(mGameId, mPage)

}