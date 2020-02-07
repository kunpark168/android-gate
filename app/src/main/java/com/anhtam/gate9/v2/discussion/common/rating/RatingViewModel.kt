package com.anhtam.gate9.v2.discussion.common.rating

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import com.anhtam.domain.ReviewDTO
import of.bum.network.helper.Resource
import javax.inject.Inject

class RatingViewModel @Inject constructor() : ViewModel() {

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
    }
}