package com.anhtam.gate9.v2.splash

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import com.anhtam.gate9.R
import com.anhtam.gate9.v2.main.DaggerNavigationFragment
import com.anhtam.gate9.v2.main.home.HomeFragment
import kotlinx.android.synthetic.main.splash_screen.*

class SplashScreen : DaggerNavigationFragment() {

    companion object {
        fun newInstance() = SplashScreen()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.splash_screen, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mSessionManager.initialize()
        startAnimation()
    }

    override fun statusColor() = R.color.white

    private fun startAnimation() {
        val animation = AnimationUtils.loadAnimation(context, R.anim.splash)
        imgLogo.startAnimation(animation)
        animation.setAnimationListener(object: Animation.AnimationListener{
            override fun onAnimationRepeat(p0: Animation?) {

            }

            override fun onAnimationStart(p0: Animation?) {

            }

            override fun onAnimationEnd(p0: Animation?) {
                navigation?.newRootFragment(HomeFragment.newInstance())
            }
        })
    }
}