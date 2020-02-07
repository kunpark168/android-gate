package com.anhtam.domain

import com.squareup.moshi.Json

data class Post(
        @Json(name = "comment_id") val commentId: String?,
        @Json(name = "avatar") val avatar: String?,
        @Json(name = "name") val name: String?,
        @Json(name = "user_id") val user_id: String?,
        @Json(name = "link") val link: String?,
        @Json(name = "content") val content: String?,
        @Json(name = "photo") val photo: List<String>?,
        @Json(name = "like") val like: String?,
        @Json(name = "created_date") val createdDate: String?,
        @Json(name = "title") val title: String?,
        @Json(name = "total_like") val totalLike: Long?,
        @Json(name = "total_dislike") val totalDislike: Long?,
        @Json(name = "total_reply") val totalReply: Long?,
        @Json(name = "game") val game: Game?,
//        @Json(name = "user") val user: User?,
        @Json(name = "parent") val parent: Parent?,
        @Json(name = "list_like") val listLike: ListLike? = null
): Base() {
    companion object {
        fun loading(): Post {
            val item = Post(null, null, null, null, null, null,
                    null, null, null, null, null, null,
                    null, null, null, null)
            item.isLoading = true
            return item
        }
    }
}


data class Parent(
        @Json(name = "total")
        val total: Int?,
        @Json(name = "comments")
        val comments: List<Post>?
)

data class ListLike(
        @Json(name = "total_like")
        val totalLike: Int?,
        @Json(name = "total_dislike")
        val totalDislike: Int?
)

