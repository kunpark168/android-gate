package com.anhtam.gate9.v2.discussion.common.rating

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import com.anhtam.domain.ReviewDTO
import com.anhtam.gate9.repository.ReviewRepository
import com.anhtam.gate9.utils.setValueDiff
import of.bum.network.helper.Resource
import javax.inject.Inject

class RatingViewModel @Inject constructor(private val reviewRepository: ReviewRepository) : ViewModel() {

    private val mReviews: MediatorLiveData<Resource<List<ReviewDTO>>> = MediatorLiveData()
    val reviews: LiveData<Resource<List<ReviewDTO>>>
        get() = mReviews

    private var mPage = 1
    val page: Int
        get() = mPage
    private var mUserId: Int = 0

    fun requestFirst(userId: Int) {
        mPage = 1
        mUserId = userId
        val newData =  reviewRepository.getReviewUser(mUserId, mPage)
        mReviews.addSource(newData) {
            it?.let { mReviews.setValueDiff(it) }
        }
    }
}