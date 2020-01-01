package com.anhtam.gate9.v2.discussion.common.discussion

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import com.anhtam.domain.Post
import com.anhtam.gate9.repository.PostRepository
import com.anhtam.gate9.v2.discussion.common.newfeed.PostCategory
import com.anhtam.gate9.utils.setValueDiff
import of.bum.network.helper.Resource
import javax.inject.Inject

class DiscussionGameViewModel @Inject constructor(private val postRepository: PostRepository) : ViewModel() {

    private val mPosts: MediatorLiveData<Resource<List<Post>>> = MediatorLiveData()
    val posts: LiveData<Resource<List<Post>>>
        get() = mPosts

    private var mPage = 1
    val page: Int
        get() = mPage
    private var mType: String? = null
    var mCategory: PostCategory = PostCategory.BOTH
    private var mLink: String = ""


    fun requestFirstPage(link: String,
                         category: PostCategory) {
        mPage = 1
        mLink = link
        mType = getCategory(category)
        mCategory = category
        val newData = postRepository.getPosts(link, mPage)
        mPosts.addSource(newData) {
            it?.let { mPosts.setValueDiff(it) }
        }
    }

    fun requestMore() {
        mPage++
        val newData = postRepository.getPostsOfUser(mLink, mPage.toString(), mType)
        mPosts.addSource(newData) {
            it?.let { mPosts.setValueDiff(it) }
        }
    }

    private fun getCategory(category: PostCategory): String? {
        return when(category) {
            PostCategory.BOTH -> {
                null
            }
            PostCategory.POST -> {
                "post"
            }
            PostCategory.COMMENT -> {
                "reply"
            }
        }
    }

}