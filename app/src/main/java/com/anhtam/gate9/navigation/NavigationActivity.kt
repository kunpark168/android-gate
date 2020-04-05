package com.anhtam.gate9.navigation

import androidx.appcompat.app.AppCompatActivity
import com.anhtam.gate9.R

/*
 * provide way to animation TODO Feature
 */
abstract class NavigationActivity : AppCompatActivity()
        , NavigationProvider {

    protected open val containerId = R.id.container

    private val dispatcher: NavigationDispatcher by lazy {
        NavigationDispatcher(this, containerId)
    }

    override fun provideNavigation() = HideKeyboardNavigation(dispatcher)

    override fun onBackPressed() {

        if (!dispatcher.onBackPressed())
        super.onBackPressed()
    }
}