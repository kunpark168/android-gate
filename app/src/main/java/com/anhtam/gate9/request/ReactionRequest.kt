package com.anhtam.gate9.request

import of.bum.network.v2.SocialService

sealed class Reaction(val id: Int,
                      val commentId: Int,
                      val userId: Int) {
    class LIKE(commentId: Int, userId: Int): Reaction(1, commentId, userId)
    class DISLIKE(commentId: Int, userId: Int): Reaction(2, commentId, userId)
    class LOVE(commentId: Int, userId: Int): Reaction(3, commentId, userId)
}