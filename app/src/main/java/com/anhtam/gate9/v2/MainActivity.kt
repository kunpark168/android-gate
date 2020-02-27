package com.anhtam.gate9.v2

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.anhtam.gate9.R
import com.anhtam.gate9.navigation.HideKeyboardNavigation
import com.anhtam.gate9.navigation.Navigation
import com.anhtam.gate9.navigation.NavigationDispatcher
import com.anhtam.gate9.navigation.NavigationProvider
import com.anhtam.gate9.v2.splash.SplashScreen
import dagger.android.support.DaggerAppCompatActivity


class MainActivity : DaggerAppCompatActivity(), NavigationProvider {

    companion object {
        fun start(context: Context?) {
            val intent = Intent(context, MainActivity::class.java)
            context?.startActivity(intent)
        }
    }

    private val mNavigation: Navigation by lazy {
        HideKeyboardNavigation(
            NavigationDispatcher(this,
                    R.id.container)
        )
    }

    override fun provideNavigation() = mNavigation

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.single_activity)
        mNavigation.newRootFragment(SplashScreen.newInstance())
    }

    override fun onBackPressed() {
        val visibleFragment = supportFragmentManager.fragments.lastOrNull() as? OnFragmentListener
        visibleFragment?.onBack()
        super.onBackPressed()
        val fragment = if(supportFragmentManager.fragments.size == 2) {
            supportFragmentManager.fragments.firstOrNull()
        } else {
            supportFragmentManager.fragments.lastOrNull()
        }
        val behindFragment = fragment as? OnFragmentListener
        behindFragment?.onFragmentResult()
    }
}