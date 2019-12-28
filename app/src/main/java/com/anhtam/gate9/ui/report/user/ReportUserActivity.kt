package com.anhtam.gate9.ui.report.user

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.anhtam.gate9.R
import com.anhtam.gate9.ui.base.BaseActivity
import com.anhtam.gate9.ui.report.game.ReportGameType
import com.anhtam.gate9.utils.debounceClick
import kotlinx.android.synthetic.main.activity_report_game.*

class ReportUserActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_report_user)
        initControls()
    }

    companion object{
        fun start(context: Context?){
            val intent = Intent(context, ReportUserActivity::class.java)
            context?.startActivity(intent)
        }
    }

    private fun initControls(){
        csGooglePlay.debounceClick { chooesTypeReport(ReportGameType.GOOGLE_PLAY.type)}
        csAPK.debounceClick { chooesTypeReport(ReportGameType.APK.type) }
        csAppStore.debounceClick { chooesTypeReport(ReportGameType.APP_STORE.type) }
        csIPA.debounceClick { chooesTypeReport(ReportGameType.IPA.type) }
        csPC.debounceClick { chooesTypeReport(ReportGameType.PC.type) }
        csOtherReason.debounceClick { chooesTypeReport(ReportGameType.UNKNOW.type) }

        csBack.debounceClick { onBackPressed() }
    }

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
