package com.anhtam.gate9.v2.post

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.anhtam.domain.Base
import com.anhtam.domain.Parent
import com.anhtam.domain.Post
import com.anhtam.domain.v2.WrapComments
import com.anhtam.gate9.repository.SocialRepository
import of.bum.network.FetchBoundResource
import of.bum.network.helper.ApiResponse
import of.bum.network.helper.RestResponse
import of.bum.network.service.PostService
import of.bum.network.v2.SocialService
import javax.inject.Inject

class DetailPostViewModel @Inject constructor(
        private val postService: PostService,
        val socialService: SocialService,
        private val repository: SocialRepository) : ViewModel() {

    fun commentByPost(id: String) = object: FetchBoundResource<Post>(){
        override fun createCall() = postService.getDetailPost(id)
    }.asLiveData()

    fun getDetailPost(id: String) = object: FetchBoundResource<WrapComments>(){
        override fun createCall() = socialService.getDetailPosts(id)
    }.asLiveData()

    fun postComment(parent: Long, content: String? = null, imageUrl: String? = "") = object: FetchBoundResource<Base>(){
        override fun createCall() = socialService.postComment(parent, content, imageUrl)
    }.asLiveData()

    fun react(params: Map<String, Int>) = repository.react(params)
}