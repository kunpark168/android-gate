package com.anhtam.gate9.v2.main.member

interface INavigator {
    fun navigateToListFragment(type: String)
    fun navigateToMemberFragment()
    fun navigateToMemberDetail(idUser: String?, type: String)
}