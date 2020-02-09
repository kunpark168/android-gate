package com.anhtam.gate9.v2.newfeed

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import com.anhtam.domain.v2.Post
import com.anhtam.gate9.repository.SocialRepository
import com.anhtam.gate9.v2.discussion.common.CommonDiscussionViewModel
import of.bum.network.helper.Resource
import javax.inject.Inject

/*
 * Common:
 *  - Banner
 *  - Group banners
 *  - User info load Storage ->
 */

class NewFeedViewModel @Inject constructor(
        val repository: SocialRepository) : PagingViewModel<Post>() {
    override fun fetchData() = repository.getListingPost(mPage, 10)


    fun loadFirstPage(tab: Int){
        mPage = 0
        val fetchData = repository.getFollowingInfo(tab, mPage)
        _data.addSource(fetchData){
            _data.value = it
        }
    }

    fun loadMore(tab: Int){
        mPage++
        val fetchData = repository.getFollowingInfo(tab, mPage)
        _data.addSource(fetchData){
            _data.value = it
        }
    }

    fun follow(){
        repository.follow()
    }

}