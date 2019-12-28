package com.anhtam.gate9.v2.messenger

import androidx.lifecycle.ViewModel
import com.anhtam.gate9.repository.UserRepository
import javax.inject.Inject

class ChannelViewModel @Inject constructor(private val userRepository: UserRepository) : ViewModel() {

    val users = userRepository.requestMemberList(1, "All", "member")

}