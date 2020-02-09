package com.anhtam.gate9.v2.discussion.common.rating

import com.anhtam.domain.v2.Post
import com.anhtam.domain.v2.Rating
import com.anhtam.gate9.repository.SocialRepository
import com.anhtam.gate9.utils.AbsentLiveData
import com.anhtam.gate9.v2.discussion.common.CommonDiscussionViewModel
import of.bum.network.helper.Resource
import javax.inject.Inject

class RatingViewModel @Inject constructor(private val repos: SocialRepository) : CommonDiscussionViewModel<Rating>() {
    override fun getNPHData(userId: Int) = repos.getNPHRating(userId, mCategory, mPage, 5)
    override fun getUserData(userId: Int) = repos.getUserRating(userId, mCategory, mPage, 5)
}