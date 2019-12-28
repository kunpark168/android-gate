package com.anhtam.gate9.adapter.navigator

import android.content.Context
import com.anhtam.domain.v2.PostEntity

interface IPostNavigator {

    fun navigateToMemberDiscussion(userId: Int)
    fun navigateToPostDetail(context: Context?, postEntity: PostEntity)
}