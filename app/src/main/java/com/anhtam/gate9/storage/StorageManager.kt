package com.anhtam.gate9.storage

import com.orhanobut.hawk.Hawk

internal enum class StorageKey(value: String) {
    ACCESS_TOKEN("ACCESS_TOKEN"),
    USER_ID("USER_ID"),
    USER_AVATAR("USER_AVATAR")
}
class StorageManager {
    companion object {
        fun getAccessToken(): String {
            return Hawk.get(StorageKey.ACCESS_TOKEN.toString(),"")
        }
        fun setAccessToken(token: String) {
            Hawk.put(StorageKey.ACCESS_TOKEN.toString(), token)
        }
        fun getUserId(): String {
            return Hawk.get(StorageKey.USER_ID.toString(), "")
        }
        fun setUserId(userId: String?) {
            Hawk.put(StorageKey.USER_ID.toString(), userId)
        }

        fun getUserAvatar(): String {
            return Hawk.get(StorageKey.USER_AVATAR.toString(), "")
        }
        fun setUserAvatar(userAvatar: String?) {
            Hawk.put(StorageKey.USER_AVATAR.toString(), userAvatar)
        }

        fun deleteAll() {
            Hawk.delete(StorageKey.ACCESS_TOKEN.toString())
            Hawk.delete(StorageKey.USER_ID.toString())
            Hawk.delete(StorageKey.USER_AVATAR.toString())
        }
    }
}