package com.anhtam.gate9.navigation

import android.content.Context
import com.anhtam.gate9.v2.OnFragmentListener
import dagger.android.support.DaggerFragment

open class NavigationFragment : DaggerFragment(), OnFragmentListener {

    protected var navigation: Navigation? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        navigation =
                (activity as? NavigationProvider)?.provideNavigation() ?:
                (activity as? Navigation) ?: throw IllegalArgumentException("Please make your activity implement NavigationProvider or Navigation")
    }

    override fun onBack() {
        //
    }

    override fun onFragmentResult() {
        //
    }
}