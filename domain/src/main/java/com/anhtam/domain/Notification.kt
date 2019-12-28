package com.anhtam.domain

data class Notification(val title: String?, val timestamp: String?,
                        val image: String?) : Base() {
    companion object {
        fun loading(): Notification {
            val item = Notification(null, null, null)
            item.isLoading = true
            return item
        }
    }
}