package com.anhtam.gate9.v2.game_detail.download

import com.anhtam.domain.v2.protocol.Game
import com.anhtam.gate9.R
import com.anhtam.gate9.v2.main.DaggerNavigationFragment

class DownloadGameFragment constructor(
        private val mGame: Game
) : DaggerNavigationFragment(R.layout.layout_download_game) {

    companion object{
        fun newInstance(game: Game) = DownloadGameFragment(game)
    }

    private fun openGooglePlay() {

    }

    private fun openAppStore() {

    }

    private fun downloadApk() {

    }


}