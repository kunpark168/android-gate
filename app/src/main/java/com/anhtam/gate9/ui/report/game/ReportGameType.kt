package com.anhtam.gate9.ui.report.game

enum class ReportGameType (var type: Int) {
    GOOGLE_PLAY(0), APK(1), APP_STORE(2), IPA(3), WINDOWS_PHONE(4), PC(5), UNKNOW(6);
    companion object{
        fun getTypeReportGame(type: Int): ReportGameType{
            for(reportGameType in values()){
                if(reportGameType.type == type)
                    return reportGameType
            }
            return UNKNOW
        }
    }
}