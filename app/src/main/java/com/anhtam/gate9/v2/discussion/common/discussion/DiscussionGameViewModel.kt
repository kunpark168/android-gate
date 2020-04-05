package com.anhtam.gate9.v2.discussion.common.discussion

import androidx.lifecycle.LiveData
import com.anhtam.domain.v2.Post
import com.anhtam.gate9.utils.AbsentLiveData
import com.anhtam.gate9.v2.discussion.common.CommonDiscussionViewModel
import of.bum.network.helper.Resource
import javax.inject.Inject

class DiscussionGameViewModel @Inject constructor() : CommonDiscussionViewModel<Post>() {
    override fun getNPHData(userId: Int): LiveData<Resource<List<Post>>> {
        return AbsentLiveData.create()
    }

    override fun getUserData(userId: Int): LiveData<Resource<List<Post>>> {
        return AbsentLiveData.create()
    }


}