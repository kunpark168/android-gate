package com.anhtam.gate9.navigation

import android.os.Bundle
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment

interface Navigation{
    fun addFragment(fragment: Fragment, tag: String? = null) = changeFragment(fragment, backStack = true, tag = tag)
    fun changeFragment(fragment: Fragment, backStack: Boolean, tag: String? = null)
    fun newRootFragment(fragment: Fragment)
    fun changeBackStack(vararg fragmentCreators: () -> Fragment)
    fun back()
    fun showDialog(dialog: DialogFragment, requestCode: Int)

    // pass data to previous fragment
    fun returnResult(args: Bundle)
}