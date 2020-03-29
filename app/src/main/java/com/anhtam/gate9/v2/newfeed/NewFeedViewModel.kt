package com.anhtam.gate9.v2.newfeed

import com.anhtam.domain.v2.Post
import com.anhtam.gate9.repository.SocialRepository
import javax.inject.Inject

/*
 * Common:
 *  - Banner
 *  - Group banners
 *  - User info load Storage ->
 */

class NewFeedViewModel @Inject constructor(
        val repository: SocialRepository) : PagingViewModel<Post>() {
    override fun fetchData() = repository.getListingPost(mPage, 5)


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