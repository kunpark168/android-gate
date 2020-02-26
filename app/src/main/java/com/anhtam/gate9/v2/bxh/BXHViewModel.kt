package com.anhtam.gate9.v2.bxh

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import androidx.viewpager.widget.PagerAdapter
import com.anhtam.domain.v2.Userv1
import com.anhtam.domain.v2.protocol.User
import com.anhtam.gate9.config.Config
import com.anhtam.gate9.repository.SocialRepository
import com.anhtam.gate9.v2.discussion.common.CommonDiscussionViewModel
import com.anhtam.gate9.v2.newfeed.PagingViewModel
import com.anhtam.gate9.v2.shared.PageViewModel
import of.bum.network.helper.Resource
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