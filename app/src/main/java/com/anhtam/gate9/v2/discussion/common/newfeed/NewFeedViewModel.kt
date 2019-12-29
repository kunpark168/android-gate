package com.anhtam.gate9.v2.discussion.common.newfeed

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import com.anhtam.domain.v2.wrap.WrapListing
import com.anhtam.gate9.repository.SocialRepository
import of.bum.network.helper.Resource
import javax.inject.Inject

class NewFeedViewModel @Inject constructor(private val socialRepository: SocialRepository) : ViewModel() {

    val _post = MediatorLiveData<Resource<WrapListing>>()
    private var mPage = 0
    val page: Int
        get() = mPage
    private var mType: Int = 1
    var mCategory: PostCategory = PostCategory.BOTH
    private var mUserId: Int = 0


    fun requestFirstPage(userId: Int,
                         category: PostCategory) {
        mPage = 0
        mUserId = userId
        mType = getCategory(category)
        mCategory = category
        _post.addSource(socialRepository.getPostAndCommentByUser(
                userId,mType, mPage, 10)){
            _post.value = it
        }
    }

    fun requestMore(){
        mPage++
        _post.addSource(socialRepository.getPostAndCommentByUser(
                mUserId,mType, mPage, 10)){
            _post.value = it
        }
    }

    fun requestMore(userId: Int){
        if(userId == 0) {
            mUserId = userId
        }
        mPage++
        _post.addSource(socialRepository.getPostAndCommentByUser(
                mUserId,mType, mPage, 10)){
            _post.value = it
        }
    }

    private fun getCategory(category: PostCategory): Int {
        return when(category) {
            PostCategory.BOTH -> {
                1
            }
            PostCategory.POST -> {
                2
            }
            PostCategory.COMMENT -> {
                3
            }
        }
    }

}