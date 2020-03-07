package com.anhtam.gate9.v2

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.anhtam.domain.v2.Post
import com.anhtam.gate9.R
import com.anhtam.gate9.config.Config
import com.anhtam.gate9.fcm.NotificationType
import com.anhtam.gate9.navigation.HideKeyboardNavigation
import com.anhtam.gate9.navigation.Navigation
import com.anhtam.gate9.navigation.NavigationDispatcher
import com.anhtam.gate9.navigation.NavigationProvider
import com.anhtam.gate9.v2.post_detail.DetailPostScreen
import com.anhtam.gate9.v2.splash.SplashScreen
import dagger.android.support.DaggerAppCompatActivity
import timber.log.Timber


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

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)

        intent?.let {
            handleOnClickPushNotification(it)
        }
    }

    private fun handleOnClickPushNotification(intent: Intent?) {
        val code = intent?.getIntExtra(Config.NOTIFICATION_TYPE, -1)?: -1
        when (NotificationType.getNotificationType(code)){
            NotificationType.COMMENT -> {
                val post = intent?.getSerializableExtra(Config.COMMENT) as? Post ?: return
                mNavigation.addFragment(DetailPostScreen.newInstance(post, DetailPostScreen.Detail.POST, null))
            }
        }
    }
}