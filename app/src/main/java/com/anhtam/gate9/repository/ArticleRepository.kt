package com.anhtam.gate9.repository

import androidx.lifecycle.LiveData
import com.anhtam.domain.Article
import of.bum.network.FetchBoundResource
import of.bum.network.helper.ApiResponse
import of.bum.network.helper.Resource
import of.bum.network.helper.RestResponse
import of.bum.network.service.ArticleService
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ArticleRepository @Inject constructor(private val articleService: ArticleService) {

    fun getDataOfUser(userId: String, page: String?, type: String?): LiveData<Resource<List<Article>>> {
        return object: FetchBoundResource<List<Article>>() {
            override fun createCall(): LiveData<ApiResponse<RestResponse<List<Article>>>> {
                return articleService.getDataBelowType(userId, page, type)
            }
        }.asLiveData()
    }
}