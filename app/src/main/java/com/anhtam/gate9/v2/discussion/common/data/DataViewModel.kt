package com.anhtam.gate9.v2.discussion.common.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import com.anhtam.domain.v2.Article
import com.anhtam.domain.v2.wrap.WrapArticle
import com.anhtam.domain.v2.wrap.WrapGames
import com.anhtam.gate9.repository.SocialRepository
import com.anhtam.gate9.v2.discussion.common.CommonDiscussionViewModel
import of.bum.network.helper.Resource
import javax.inject.Inject

class DataViewModel @Inject constructor(private val socialRepository: SocialRepository) : CommonDiscussionViewModel<Article>() {
    override fun getNPHData(userId: Int) = socialRepository.getNPHData(userId, mCategory, mPage, 10)

    override fun getUserData(userId: Int) =  socialRepository.getNPHData(userId, mCategory, mPage, 10)
}