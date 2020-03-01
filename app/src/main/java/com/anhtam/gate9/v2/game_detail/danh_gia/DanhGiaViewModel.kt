package com.anhtam.gate9.v2.game_detail.danh_gia

import com.anhtam.gate9.repository.SocialRepository
import com.anhtam.gate9.v2.newfeed.PagingViewModel
import com.anhtam.gate9.vo.Rating
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