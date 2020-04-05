package com.anhtam.gate9.v2.notification

import androidx.lifecycle.LiveData
import com.anhtam.gate9.repository.SocialRepository
import com.anhtam.gate9.restful.entities.Notification
import com.anhtam.gate9.v2.newfeed.PagingViewModel
import com.anhtam.gate9.v2.shared.PageViewModel
import of.bum.network.helper.Resource
import javax.inject.Inject

class NotificationViewModel @Inject constructor(
        private val repos: SocialRepository
): PagingViewModel<Notification>() {
    override fun fetchData() = repos.getNotificationList(mPage)
}