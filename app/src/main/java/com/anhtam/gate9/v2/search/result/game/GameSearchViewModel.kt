package com.anhtam.gate9.v2.search.result.game

import androidx.lifecycle.Transformations
import com.anhtam.domain.v2.protocol.Game
import com.anhtam.gate9.repository.SocialRepository
import com.anhtam.gate9.v2.newfeed.PagingViewModel
import of.bum.network.helper.Resource
import javax.inject.Inject

class GameSearchViewModel @Inject constructor(val repos: SocialRepository): PagingViewModel<Game>(){
    var mKey: String? = null
    override fun fetchData() = Transformations.map(repos.searchGame(mKey ?: "", mPage)) {
        val game: List<Game>? = it.data
        when (it) {
            is Resource.Success -> Resource.Success(game)
            is Resource.Error -> Resource.Error(it.message ?: "", game)
            else -> Resource.Loading()
        }
    }

}