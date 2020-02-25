package com.anhtam.domain.v2.protocol

import com.anhtam.domain.v2.Type

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
    var follower: Int?
    var following: Int?
    var follow: Boolean?
    var post: Int?
    var imgCover: String?
    var country: String?
    var email: String?
    var phone: String?
    var group: String?
    var mRating: Double?
    var mNumRating: Int?
}