package com.anhtam.gate9.v2.report.user

import android.os.Bundle
import android.view.View
import com.anhtam.gate9.R
import com.anhtam.gate9.utils.debounceClick
import com.anhtam.gate9.v2.main.DaggerNavigationFragment
import com.anhtam.gate9.v2.report.game.ReportGameType
import kotlinx.android.synthetic.main.activity_report_game.*

class ReportUserActivity : DaggerNavigationFragment(R.layout.activity_report_user) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initControls()
    }

    companion object{
        fun newInstance() = ReportUserActivity()
    }

    private fun initControls(){
        csGooglePlay.debounceClick { chooesTypeReport(ReportGameType.GOOGLE_PLAY.type)}
        csAPK.debounceClick { chooesTypeReport(ReportGameType.APK.type) }
        csAppStore.debounceClick { chooesTypeReport(ReportGameType.APP_STORE.type) }
        csIPA.debounceClick { chooesTypeReport(ReportGameType.IPA.type) }
        csPC.debounceClick { chooesTypeReport(ReportGameType.PC.type) }
        csOtherReason.debounceClick { chooesTypeReport(ReportGameType.UNKNOW.type) }
    }

    override fun menuRes() = R.menu.chat_avatar_report_menu

    private fun chooesTypeReport(type: Int){
        imgGooglePlay.setImageDrawable(if(type == ReportGameType.GOOGLE_PLAY.type) resources.getDrawable(R.drawable.ic_checked_radio) else
            resources.getDrawable(R.drawable.ic_non_check))

        imgAPK.setImageDrawable(if(type == ReportGameType.APK.type) resources.getDrawable(R.drawable.ic_checked_radio) else
            resources.getDrawable(R.drawable.ic_non_check))

        imgAppStore.setImageDrawable(if(type == ReportGameType.APP_STORE.type) resources.getDrawable(R.drawable.ic_checked_radio) else
            resources.getDrawable(R.drawable.ic_non_check))

        imgIPA.setImageDrawable(if(type == ReportGameType.IPA.type) resources.getDrawable(R.drawable.ic_checked_radio) else
            resources.getDrawable(R.drawable.ic_non_check))

        imgPC.setImageDrawable(if(type == ReportGameType.PC.type) resources.getDrawable(R.drawable.ic_checked_radio) else
            resources.getDrawable(R.drawable.ic_non_check))

        imgUnknow.setImageDrawable(if(type == ReportGameType.UNKNOW.type) resources.getDrawable(R.drawable.ic_checked_radio) else
            resources.getDrawable(R.drawable.ic_non_check))
    }
}
