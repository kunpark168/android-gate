package com.anhtam.gate9.v2.main.member

import com.anhtam.gate9.vo.EUser

interface INavigator {
    fun navigateToListFragment(type: EUser)
    fun navigateToMemberFragment()
    fun navigateToMemberDetail(idUser: String?, type: String)
}