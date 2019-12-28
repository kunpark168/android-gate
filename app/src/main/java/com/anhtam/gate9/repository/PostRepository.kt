package com.anhtam.gate9.repository

import androidx.lifecycle.LiveData
import com.anhtam.domain.Post
import of.bum.network.FetchBoundResource
import of.bum.network.helper.ApiResponse
import of.bum.network.helper.Resource
import of.bum.network.helper.RestResponse
import of.bum.network.service.PostService
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PostRepository @Inject constructor(
        private val postService: PostService
) {

    fun getPosts(link: String, page: Int): LiveData<Resource<List<Post>>> {
        return object: FetchBoundResource<List<Post>>() {
            override fun createCall(): LiveData<ApiResponse<RestResponse<List<Post>>>> {
                val map = mutableMapOf<String, String?>()
                map["link"] = link
                return postService.getAllPostInGame(map, page)
            }
        }.asLiveData()
    }

    fun getAllPosts(page: Int): LiveData<Resource<List<Post>>> {
        return object: FetchBoundResource<List<Post>>() {
            override fun createCall(): LiveData<ApiResponse<RestResponse<List<Post>>>> {
                return postService.getAllPosts(page)
            }
        }.asLiveData()
    }

    fun getPostsOfUser(userId: String, page: String?,
                       type: String?) : LiveData<Resource<List<Post>>> {
        return object : FetchBoundResource<List<Post>>() {
            override fun createCall(): LiveData<ApiResponse<RestResponse<List<Post>>>> {
                return postService.getPostByUser(userId, page, type)
            }

        }.asLiveData()
    }
}