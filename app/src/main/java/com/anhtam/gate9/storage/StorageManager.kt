package com.anhtam.gate9.storage

import com.orhanobut.hawk.Hawk

internal enum class StorageKey(value: String) {
    ACCESS_TOKEN("ACCESS_TOKEN"),
}

class StorageManager {
    companion object {
        fun getAccessToken(): String {
            return Hawk.get(StorageKey.ACCESS_TOKEN.toString(),"")
        }
        fun setAccessToken(token: String) {
            Hawk.put(StorageKey.ACCESS_TOKEN.toString(), token)
        }

        fun deleteAll() {
            Hawk.delete(StorageKey.ACCESS_TOKEN.toString())
        }
    }
}