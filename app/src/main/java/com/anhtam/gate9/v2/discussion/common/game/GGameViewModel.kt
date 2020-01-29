package com.anhtam.gate9.v2.discussion.common.game

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import com.anhtam.domain.v2.wrap.WrapGame
import com.anhtam.gate9.repository.SocialRepository
import of.bum.network.helper.Resource
import javax.inject.Inject

class GGameViewModel @Inject constructor(private val socialRepository: SocialRepository) : ViewModel() {
    val _game = MediatorLiveData<Resource<List<WrapGame>>>()

    private var mPage = 0
    var mCategory: GameCategory = GameCategory.OPEN
    val page: Int
        get() = mPage
    private var mType: Int = 0
    private var mUserId: Int = 0


    fun requestFirstPage(userId: Int,
                         category: GameCategory) {
        mPage = 0
        mUserId = userId
        mCategory = category
        mType = getCategory(category)
        _game.addSource(socialRepository.getGameRelatedToUser(mUserId, mType, mPage, 10 )){
            _game.value = it
        }
    }

    fun requestMore() {
        mPage++
        _game.addSource(socialRepository.getGameRelatedToUser(mUserId, mType, mPage, 10 )){
            _game.value = it
        }
    }

    private fun getCategory(category: GameCategory): Int {
        return when(category) {
            GameCategory.OPEN -> 1
            GameCategory.BETA -> 2
            GameCategory.COMING -> 3
            GameCategory.CLOSED -> 4
        }
    }
}