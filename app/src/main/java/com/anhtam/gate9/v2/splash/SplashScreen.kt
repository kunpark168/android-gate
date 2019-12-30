package com.anhtam.gate9.v2.splash

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.anhtam.gate9.R
import com.anhtam.gate9.v2.main.DaggerNavigationFragment
import com.anhtam.gate9.v2.main.home.HomeFragment
import com.anhtam.gate9.v2.newfeed.NewFeedViewModel
import kotlinx.android.synthetic.main.splash_screen.*
import of.bum.network.helper.Resource

class SplashScreen : DaggerNavigationFragment() {

    private val mViewModel by viewModels<NewFeedViewModel> ({ requireNotNull(activity)}, {vmFactory})
    private var isAnimEnd = false
    private var isFetchEnd = false
    companion object {
        fun newInstance() = SplashScreen()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        activity?.window?.statusBarColor = ContextCompat.getColor(context!!, R.color.white)
        return inflater.inflate(R.layout.splash_screen, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mViewModel.loadNewFeed()
        startAnimation()
        // pre load data
        mViewModel.data.observe(viewLifecycleOwner, Observer {
            if (it is Resource.Success) {
                isFetchEnd = true
                if (isAnimEnd){
                    navigation?.newRootFragment(HomeFragment.newInstance())
                }
            }
        })
    }

    private fun startAnimation() {
        val animation = AnimationUtils.loadAnimation(context, R.anim.splash)
        imgLogo.startAnimation(animation)
        animation.setAnimationListener(object: Animation.AnimationListener{
            override fun onAnimationRepeat(p0: Animation?) {

            }

            override fun onAnimationStart(p0: Animation?) {

            }

            override fun onAnimationEnd(p0: Animation?) {
                isAnimEnd = true
                if (isFetchEnd){
                    navigation?.newRootFragment(HomeFragment.newInstance())
                }
            }
        })
    }
}