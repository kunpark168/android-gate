package com.anhtam.gate9.v2.search.result.member

import androidx.lifecycle.Transformations
import com.anhtam.domain.v2.protocol.User
import com.anhtam.gate9.repository.SocialRepository
import com.anhtam.gate9.v2.newfeed.PagingViewModel
import of.bum.network.helper.Resource
import javax.inject.Inject

class MemberSearchViewModel @Inject constructor(
        private val repos: SocialRepository
) : PagingViewModel<User>(){
    var mKey: String? = null
    override fun fetchData() = Transformations.map(repos.searchMember(mKey ?: "", mPage))  {
        val game: List<User>? = it.data
        when (it) {
            is Resource.Success -> Resource.Success(game)
            is Resource.Error -> Resource.Error(it.message ?: "", game)
            else -> Resource.Loading()
        }
    }

}