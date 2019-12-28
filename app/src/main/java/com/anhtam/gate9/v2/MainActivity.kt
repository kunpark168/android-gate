package com.anhtam.gate9.v2

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.core.content.ContextCompat
import com.anhtam.gate9.R
import com.anhtam.gate9.navigation.HideKeyboardNavigation
import com.anhtam.gate9.navigation.Navigation
import com.anhtam.gate9.navigation.NavigationDispatcher
import com.anhtam.gate9.navigation.NavigationProvider
import com.anhtam.gate9.v2.splash.SplashScreen
import dagger.android.support.DaggerAppCompatActivity

/*
 *  - Status bar
 *  - Toolbar bar
 */

class MainActivity : DaggerAppCompatActivity(), NavigationProvider {

    companion object {
        var mColor = ColorStatus.BLUE
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
        val behindFragment = supportFragmentManager.fragments.lastOrNull() as? OnFragmentListener
        behindFragment?.onFragmentResult()
        when(mColor) {
            ColorStatus.BLUE -> {
                window?.statusBarColor = ContextCompat.getColor(this, R.color.color_main_blue)
            }
            ColorStatus.ORANGE -> {
                window?.statusBarColor = ContextCompat.getColor(this, R.color.color_main_orange)
            }
        }
    }


    enum class ColorStatus{
        BLUE, ORANGE
    }
}