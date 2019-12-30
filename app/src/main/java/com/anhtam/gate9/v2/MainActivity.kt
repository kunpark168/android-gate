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
import com.anhtam.gate9.v2.auth.login.LoginScreen
import com.anhtam.gate9.v2.auth.register.RegisterScreen
import com.anhtam.gate9.v2.createimage.CreateImageScreen
import com.anhtam.gate9.v2.createpost.CreatePostScreen
import com.anhtam.gate9.v2.discussion.game.GameDiscussionScreen
import com.anhtam.gate9.v2.discussion.user.UserDiscussionScreen
import com.anhtam.gate9.v2.main.home.HomeFragment
import com.anhtam.gate9.v2.notification.NotificationFragment
import com.anhtam.gate9.v2.post.DetailPostScreen
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
        val behindFragment = supportFragmentManager.fragments.lastOrNull() as? OnFragmentListener
        behindFragment?.onFragmentResult()
        when(behindFragment) {
            is UserDiscussionScreen -> window?.statusBarColor = ContextCompat.getColor(this, R.color.color_main_blue)
            is GameDiscussionScreen -> window?.statusBarColor = ContextCompat.getColor(this, R.color.color_main_blue)
            is CreatePostScreen -> window?.statusBarColor = ContextCompat.getColor(this, R.color.color_main_blue)
            is CreateImageScreen -> window?.statusBarColor = ContextCompat.getColor(this, R.color.color_main_blue)
            is SplashScreen -> window?.statusBarColor = ContextCompat.getColor(this, R.color.color_main_blue)
            is HomeFragment -> window?.statusBarColor = ContextCompat.getColor(this, R.color.color_main_blue)
            is NotificationFragment -> window?.statusBarColor = ContextCompat.getColor(this, R.color.color_main_blue)
            is DetailPostScreen -> window?.statusBarColor = ContextCompat.getColor(this, R.color.color_main_blue)
            is LoginScreen -> window?.statusBarColor = ContextCompat.getColor(this, R.color.color_main_orange)
            is RegisterScreen -> window?.statusBarColor = ContextCompat.getColor(this, R.color.color_main_orange)
        }
    }
}