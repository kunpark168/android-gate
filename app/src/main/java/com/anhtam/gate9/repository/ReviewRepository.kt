package com.anhtam.gate9.repository

import androidx.lifecycle.LiveData
import com.anhtam.domain.ReviewDTO
import of.bum.network.FetchBoundResource
import of.bum.network.helper.ApiResponse
import of.bum.network.helper.Resource
import of.bum.network.helper.RestResponse
import of.bum.network.service.ReviewService
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ReviewRepository @Inject constructor(
        private val reviewService: ReviewService
) {
    fun getReviewUser(id: Int, page: Int): LiveData<Resource<List<ReviewDTO>>> {
        return object: FetchBoundResource<List<ReviewDTO>>() {
            override fun createCall(): LiveData<ApiResponse<RestResponse<List<ReviewDTO>>>> {
                return reviewService.getReviewUser(id, page)
            }
        }.asLiveData()
    }
}