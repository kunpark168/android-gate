package com.anhtam.gate9.v2.bxh

import com.anhtam.domain.v2.Userv1
import com.anhtam.gate9.repository.SocialRepository
import com.anhtam.gate9.v2.newfeed.PagingViewModel
import javax.inject.Inject

class BXHViewModel @Inject constructor(
        private var mRepository: SocialRepository
) : PagingViewModel<Userv1>(){

    private var mUserId: Int = 4
    fun setId(id: Int){
        mUserId = id
    }

    override fun fetchData() = mRepository.getListRanking(mUserId, mPage)
}