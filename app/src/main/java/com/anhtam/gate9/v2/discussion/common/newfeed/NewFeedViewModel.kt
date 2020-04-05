package com.anhtam.gate9.v2.discussion.common.newfeed

import androidx.lifecycle.LiveData
import com.anhtam.domain.v2.Post
import com.anhtam.gate9.repository.SocialRepository
import com.anhtam.gate9.v2.discussion.common.CommonDiscussionViewModel
import of.bum.network.helper.Resource
import javax.inject.Inject

class NewFeedViewModel @Inject constructor(private val socialRepository: SocialRepository) : CommonDiscussionViewModel<Post>() {
    override fun getNPHData(userId: Int): LiveData<Resource<List<Post>>> = socialRepository.getNPHPost(userId, mCategory, mPage, 10)

    override fun getUserData(userId: Int): LiveData<Resource<List<Post>>> = socialRepository.getPostAndCommentByUser(userId, mCategory, mPage, 10)

}