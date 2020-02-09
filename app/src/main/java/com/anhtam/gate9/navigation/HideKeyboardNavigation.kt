package com.anhtam.gate9.navigation

import androidx.fragment.app.Fragment

class HideKeyboardNavigation(
        private val origin: Navigation
) : Navigation by origin {
    override fun changeFragment(fragment: Fragment, backStack: Boolean, tag: String?) {
        hideKeyboard()
        origin.changeFragment(fragment, backStack, tag)
    }

    override fun back() {
        hideKeyboard()
        origin.back()
    }

    private fun hideKeyboard() {
        // logic of hiding keyboard
    }
}