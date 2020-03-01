package com.anhtam.gate9.fcm

enum class NotificationType(type: Int) {
    RATING(1), COMMENT(2), MESSAGE(3), CHAT(4), UNKNOW(-1);

    private val type: Int?= -1

    companion object{
        fun getNotificationType(type: Int) : NotificationType{
            for(notificatonType in values()){
                if(notificatonType.type == type){
                    return notificatonType
                }
            }

            return UNKNOW
        }
    }

}