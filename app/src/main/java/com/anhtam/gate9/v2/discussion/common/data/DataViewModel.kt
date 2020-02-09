package com.anhtam.gate9.v2.discussion.common.data

import androidx.lifecycle.MediatorLiveData
import com.anhtam.domain.v2.Article
import com.anhtam.gate9.adapter.article.ArticleEntity
import com.anhtam.gate9.repository.SocialRepository
import com.anhtam.gate9.v2.discussion.common.CommonDiscussionViewModel
import of.bum.network.helper.Resource
import javax.inject.Inject

class DataViewModel @Inject constructor(private val socialRepository: SocialRepository) : CommonDiscussionViewModel<Article>() {
    val mData = MediatorLiveData<Resource<List<ArticleEntity>>>()
    override fun getNPHData(userId: Int) = socialRepository.getNPHData(userId, mCategory, mPage, 5)

    override fun getUserData(userId: Int) = socialRepository.getDataRelatedToUser(userId, mCategory, mPage, 5)
}