package com.anhtam.gate9.v2.discussion.common.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.anhtam.domain.v2.Article
import com.anhtam.gate9.adapter.article.ArticleEntity
import com.anhtam.gate9.repository.SocialRepository
import com.anhtam.gate9.v2.discussion.common.CommonDiscussionViewModel
import of.bum.network.helper.Resource
import javax.inject.Inject

class DataViewModel @Inject constructor(private val socialRepository: SocialRepository) : CommonDiscussionViewModel<ArticleEntity>() {
    val mData = MediatorLiveData<Resource<List<ArticleEntity>>>()
    override fun getNPHData(userId: Int) : LiveData<Resource<List<ArticleEntity>>> {
        mData.value = Resource.Loading()
        val source = socialRepository.getNPHData(userId, mCategory, mPage, 5)
        addSource(source)
        return mData
    }

    private fun addSource(source: LiveData<Resource<List<Article>>>){
        mData.addSource(source){
            when(it){
                is Resource.Success -> {
                    mData.removeSource(source)
                    val data = it.data?.map { article -> ArticleEntity(article, mCategory) }
                    mData.value = Resource.Success(data, it.mResponse)
                }
                is Resource.Error -> {
                    mData.removeSource(source)
                    mData.value = Resource.Error(it.message ?: "Unknown error")
                }
            }
        }
    }


    override fun getUserData(userId: Int) : LiveData<Resource<List<ArticleEntity>>> {
        mData.value = Resource.Loading()
        val source = socialRepository.getDataRelatedToUser(userId, mCategory, mPage, 5)
        addSource(source)
        return mData
    }
}