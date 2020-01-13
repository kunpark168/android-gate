package com.anhtam.gate9.v2.report.post

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import com.anhtam.gate9.R
import com.anhtam.gate9.v2.report.game.ReportGameType
import com.anhtam.gate9.utils.debounceClick
import com.anhtam.gate9.v2.main.DaggerNavigationFragment
import kotlinx.android.synthetic.main.activity_report_post.*

class ReportPostActivity : DaggerNavigationFragment() {


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.activity_report_post, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initControls()
    }

    companion object{
        fun newInstance() = ReportPostActivity()
    }

    private fun initControls(){
        csGooglePlay.debounceClick { chooesTypeReport(ReportGameType.GOOGLE_PLAY.type)}
        csAPK.debounceClick { chooesTypeReport(ReportGameType.APK.type) }
        csAppStore.debounceClick { chooesTypeReport(ReportGameType.APP_STORE.type) }
        csIPA.debounceClick { chooesTypeReport(ReportGameType.IPA.type) }
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

        imgUnknow.setImageDrawable(if(type == ReportGameType.UNKNOW.type) resources.getDrawable(R.drawable.ic_checked_radio) else
            resources.getDrawable(R.drawable.ic_non_check))
    }
}
