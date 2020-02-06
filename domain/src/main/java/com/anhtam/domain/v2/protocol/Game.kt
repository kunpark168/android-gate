package com.anhtam.domain.v2.protocol

import com.anhtam.domain.Base
import com.anhtam.domain.Type

interface Game {
    var gameId: Int?
    var name: String?
    var avatar: String?
    var background: String?
    var description: String?
    var point: String?
    var createdDate: String?
    var createdUserId: Int?
    var heDieuHanh: String?
    var numViewed: String?
    var numDownloaded: String?
    var numLiked: String?
    var numDisliked: String?
    var capacity: String?
    var gameTypes: List<Type>?
    var gameType: Type?
    var gameTypeStr: String?
    var fanPage: String?
    var homepage: String?
    var photos: String?
    var follower: String?
    var following: String?
    var follow: Boolean?
    var post: String?
    var imgCover: String?
}