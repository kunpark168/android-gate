package com.anhtam.gate9.v2.notification

import androidx.lifecycle.ViewModel
import com.anhtam.domain.Notification
import javax.inject.Inject

class NotificationViewModel @Inject constructor(): ViewModel() {
    fun notifications(): List<Notification> {
        val notifications = mutableListOf<Notification>()
        notifications.add(Notification(null, null, null))
        notifications.add(Notification(null, null, null))
        notifications.add(Notification(null, null, null))
        notifications.add(Notification(null, null, null))
        notifications.add(Notification(null, null, null))
        notifications.add(Notification(null, null, null))
        notifications.add(Notification(null, null, null))
        notifications.add(Notification(null, null, null))
        notifications.add(Notification(null, null, null))
        notifications.add(Notification(null, null, null))
        return notifications
    }
}