package com.anhtam.gate9.v2.game_detail.download

import android.content.Intent
import android.net.Uri
import com.anhtam.domain.v2.protocol.Game
import com.anhtam.gate9.R
import com.anhtam.gate9.v2.main.DaggerNavigationFragment
import kotlinx.android.synthetic.main.layout_download_game.*

class DownloadGameFragment constructor(
        private val mGame: Game
) : DaggerNavigationFragment(R.layout.layout_download_game) {

    companion object{
        fun newInstance(game: Game) = DownloadGameFragment(game)
    }

    init {
        initEvent()
    }

    private fun initEvent() {
        btDownload?.setOnClickListener {
            openGooglePlay()
        }
        apkLayout?.setOnClickListener {
            downloadApk()
        }
    }

    private fun openGooglePlay() {
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = Uri.parse(mGame.googlePlay)
        startActivity(intent)
    }

    private fun openAppStore() {

    }

    private fun downloadApk() {
        val intent = Intent(Intent.ACTION_VIEW)
//        intent.data = Uri.parse(mGa)
        startActivity(intent)
    }


}