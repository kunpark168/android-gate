package com.anhtam.gate9.v2.discussion.common.game

import com.anhtam.domain.v2.wrap.WrapGame
import com.anhtam.gate9.repository.SocialRepository
import com.anhtam.gate9.v2.discussion.common.CommonDiscussionViewModel
import javax.inject.Inject

class GGameViewModel @Inject constructor(private val socialRepository: SocialRepository) : CommonDiscussionViewModel<WrapGame>() {
    override fun getNPHData(userId: Int) = socialRepository.getNPHGame(userId, mCategory, mPage, 10)

    override fun getUserData(userId: Int) = socialRepository.getGameRelatedToUser(userId, mCategory, mPage, 10 )
}