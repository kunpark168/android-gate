package com.anhtam.gate9.navigation

import android.os.Bundle
import androidx.annotation.IdRes
import androidx.fragment.app.*
import com.anhtam.gate9.R


/*
 * back stack != fragments exist
 * clear back stack and replace == new root
 */
class NavigationDispatcher(
        private val activity: FragmentActivity,
        @IdRes private val containerId: Int
) : Navigation, BackListener {

    companion object{
        private val EMPTY_BACK_LISTENER = object : BackListener {
            override fun onBackPressed() = true
        }
    }

    override fun changeBackStack(vararg fragmentCreators: () -> Fragment) {
        if (fragmentCreators.size == 1) {
            newRootFragment(fragmentCreators[0])
        } else if (fragmentCreators.size > 1) {
            activity.supportFragmentManager.run {
                clearBackStack()
                fragmentCreators
                        .map { it.invoke() }
                        .forEach { addFragment(it) }
                executePendingTransactions()
            }
        }
    }

    private fun newRootFragment(fragmentCreator: () -> Fragment) = newRootFragment(fragmentCreator())

    override fun newRootFragment(fragment: Fragment) {
        activity.supportFragmentManager.clearBackStack()
        changeFragmentWithoutAnimation(fragment)
    }

    private fun changeFragmentWithoutAnimation(fragment: Fragment) = activity.run {
        supportFragmentManager.commitTransaction {
            replace(containerId, fragment)
        }
    }

    override fun changeFragment(fragment: Fragment, backStack: Boolean, tag: String?) = activity.run {
        supportFragmentManager.commitTransaction {
            if (backStack) placeToBackStack(tag)
            setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right)
            add(containerId, fragment, tag)
        }
    }

    override fun back() = activity.onBackPressed()

    override fun showDialog(dialog: DialogFragment, requestCode: Int) {
        dialog.setTargetFragment(null, requestCode)
        dialog.show(activity.supportFragmentManager, null)
    }

    private fun FragmentManager.clearBackStack(){
        if (!isBackStackEmpty()) {
            val first = getBackStackEntryAt(0)
            popBackStackImmediate(first.id, FragmentManager.POP_BACK_STACK_INCLUSIVE)
        }
    }

    private fun FragmentManager.isBackStackEmpty() = backStackEntryCount == 0

    private fun FragmentTransaction.placeToBackStack(tag: String?) {
        addToBackStack(tag)
        setTransition(FragmentTransaction.TRANSIT_NONE)
    }

    private fun FragmentManager.commitTransaction(block: FragmentTransaction.() -> Unit) {
        beginTransaction().apply { block() }.commit()
    }

    override fun returnResult(args: Bundle) {
        back()
        // get current fragment and call
        (getCurrentFragment() as? FragmentResultListener)
                ?.onFragmentResult(args)
    }

    override fun onBackPressed() = (getCurrentFragment() as? BackListener ?: EMPTY_BACK_LISTENER).onBackPressed()

    override fun clear(upToTag: String, includeMatch: Boolean) {
        activity.supportFragmentManager.popBackStack(upToTag, if (includeMatch) FragmentManager.POP_BACK_STACK_INCLUSIVE else 0)
    }

    private fun getCurrentFragment() =
            activity.supportFragmentManager.findFragmentById(containerId)
}