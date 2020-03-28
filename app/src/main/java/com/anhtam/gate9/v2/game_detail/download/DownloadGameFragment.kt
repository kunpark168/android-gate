package com.anhtam.gate9.v2.game_detail.download

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.anhtam.domain.v2.protocol.Game
import com.anhtam.gate9.R
import com.anhtam.gate9.v2.main.DaggerNavigationFragment
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.layout_download_game.*

class DownloadGameFragment constructor(
        private val mGame: Game
) : DaggerNavigationFragment(R.layout.layout_download_game) {

    private var mCurrentRadioButton: Int = -1

    companion object{
        fun newInstance(game: Game) = DownloadGameFragment(game)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initEvent()
    }

    private fun initElement(link: String?, noneTextView: TextView?, imageView: ImageView, index: Int){
        if (link.isNullOrEmpty()) {
            noneTextView?.visibility = View.VISIBLE
            imageView.visibility = View.GONE
        } else {
            noneTextView?.visibility = View.GONE
            imageView.visibility = View.VISIBLE
            if (mCurrentRadioButton == -1) {
                mCurrentRadioButton = index
                Glide.with(this).load(R.drawable.ic_checked_radio).into(imageView)
            }
        }
    }

    private fun initView() {
        initElement(mGame.googlePlay, googlePlayTextView, googlePlayRadioButton, 0)
        initElement(mGame.appleStore, appStoreTextView, appStoreRadioButton, 1)
        initElement(mGame.androidLink, apkTextView, apkRadioButton,2)
        initElement(mGame.iosLink, ipaTextView, ipaRadioButton,3)
        initElement(mGame.windowPhoneLink, windowPhoneTextView, windowPhoneRadioButton,5)
        initElement(mGame.pcLink, pcTextView, pcRadioButton,4)
        btDownload?.setOnClickListener { openLink() }
    }

    private fun initEvent() {
        googlePlayLayout?.setOnClickListener { eventElement(mGame.googlePlay, googlePlayRadioButton, 0) }
        appStoreLayout?.setOnClickListener { eventElement(mGame.appleStore, appStoreRadioButton, 1) }
        apkLayout?.setOnClickListener { eventElement(mGame.androidLink, apkRadioButton, 2) }
        ipaLayout?.setOnClickListener { eventElement(mGame.iosLink, ipaRadioButton, 3) }
        windowPhoneLayout?.setOnClickListener { eventElement(mGame.windowPhoneLink, windowPhoneRadioButton, 4) }
        pcLayout?.setOnClickListener { eventElement(mGame.pcLink, pcRadioButton, 5) }
    }

    private fun eventElement(link: String?, imageView: ImageView, index: Int) {
        if (link.isNullOrEmpty() || mCurrentRadioButton == index || mCurrentRadioButton == -1) return
        when(mCurrentRadioButton) {
            0 -> Glide.with(this).load(R.drawable.ic_non_check).into(googlePlayRadioButton)
            1 -> Glide.with(this).load(R.drawable.ic_non_check).into(appStoreRadioButton)
            2 -> Glide.with(this).load(R.drawable.ic_non_check).into(apkRadioButton)
            3 -> Glide.with(this).load(R.drawable.ic_non_check).into(ipaRadioButton)
            4 -> Glide.with(this).load(R.drawable.ic_non_check).into(windowPhoneRadioButton)
            5 -> Glide.with(this).load(R.drawable.ic_non_check).into(pcRadioButton)
        }
        mCurrentRadioButton = index
        Glide.with(this).load(R.drawable.ic_checked_radio).into(imageView)
    }

    private fun openLink() {
        val link = when(mCurrentRadioButton) {
            0 -> mGame.googlePlay
            1 -> mGame.appleStore
            2 -> mGame.androidLink
            3 -> mGame.iosLink
            4 -> mGame.windowPhoneLink
            5 -> mGame.pcLink
            else -> null
        } ?: return
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = Uri.parse(link)
        startActivity(intent)
    }

}