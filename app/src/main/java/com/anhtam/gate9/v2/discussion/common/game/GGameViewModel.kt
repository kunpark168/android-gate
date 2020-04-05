package com.anhtam.gate9.v2.discussion.common.game

import androidx.lifecycle.Transformations
import com.anhtam.domain.v2.protocol.Game
import com.anhtam.domain.v2.wrap.WrapGame
import com.anhtam.gate9.repository.SocialRepository
import com.anhtam.gate9.v2.discussion.common.CommonDiscussionViewModel
import of.bum.network.helper.Resource
import javax.inject.Inject

class GGameViewModel @Inject constructor(private val socialRepository: SocialRepository) : CommonDiscussionViewModel<Game>() {
    override fun getNPHData(userId: Int) = Transformations.map(socialRepository.getNPHGame(userId, mCategory, mPage, 10)) {
        val game: List<Game>? = it.data?.map { wrap -> wrap.wrap }
        when (it) {
            is Resource.Success -> Resource.Success(game)
            is Resource.Error -> Resource.Error(it.message ?: "", game)
            else -> Resource.Loading()
        }

    }

    override fun getUserData(userId: Int) = Transformations.map(socialRepository.getGameRelatedToUser(userId, mCategory, mPage, 10)) {
        val game: List<Game>? = it.data?.map { wrap -> wrap.wrap }
        when (it) {
            is Resource.Success -> Resource.Success(game)
            is Resource.Error -> Resource.Error(it.message ?: "", game)
            else -> Resource.Loading()
        }
    }
}