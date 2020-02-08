package com.anhtam.gate9.v2.discussion.common.rating

import com.anhtam.domain.v2.Post
import com.anhtam.gate9.utils.AbsentLiveData
import com.anhtam.gate9.v2.discussion.common.CommonDiscussionViewModel
import of.bum.network.helper.Resource
import javax.inject.Inject

class RatingViewModel @Inject constructor() : CommonDiscussionViewModel<Post>() {
    override fun getNPHData(userId: Int) = AbsentLiveData.create<Resource<List<Post>>>()
    override fun getUserData(userId: Int) = AbsentLiveData.create<Resource<List<Post>>>()
}