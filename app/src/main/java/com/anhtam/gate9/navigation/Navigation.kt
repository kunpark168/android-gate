package com.anhtam.gate9.navigation

import android.os.Bundle
import androidx.annotation.AnimRes
import androidx.annotation.AnimatorRes
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment

interface Navigation{
    fun addFragment(fragment: Fragment) = changeFragment(fragment, backStack = true)
    fun changeFragment(fragment: Fragment, backStack: Boolean)
    fun newRootFragment(fragment: Fragment)
    fun changeBackStack(vararg fragmentCreators: () -> Fragment)
    fun back()
    fun showDialog(dialog: DialogFragment, requestCode: Int)

    // pass data to previous fragment
    fun returnResult(args: Bundle)
}