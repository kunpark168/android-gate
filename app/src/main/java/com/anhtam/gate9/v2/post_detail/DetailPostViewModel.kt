package com.anhtam.gate9.v2.post_detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import com.anhtam.domain.Base
import com.anhtam.domain.v2.Post
import com.anhtam.domain.v2.Userv1
import com.anhtam.domain.v2.wrap.WrapComments
import com.anhtam.gate9.repository.SocialRepository
import com.anhtam.gate9.restful.SocialService
import com.anhtam.gate9.session.SessionManager
import com.anhtam.gate9.vo.IllegalReturn
import com.anhtam.gate9.vo.Reaction
import of.bum.network.FetchBoundResource
import of.bum.network.helper.Resource
import java.lang.ref.WeakReference
import javax.inject.Inject
import kotlin.properties.Delegates

class DetailPostViewModel @Inject constructor(
        val socialService: SocialService,
        val repository: SocialRepository,
        val mSessionManager: SessionManager
) : ViewModel() {

    private var _commentId: Int  = 0

    var mPage = 0
    private val mComments = MediatorLiveData<Resource<WrapComments>>()
    val comments: LiveData<Resource<WrapComments>>
        get() = mComments

    fun initialize(id: Long) {
        _commentId = id.toInt()
    }


    fun getChildComment(){
        mPage = 0
        val source = repository.getChildComment(_commentId, mPage)
        mComments.addSource(source){
            mComments.value = it
        }
    }


    fun postComment(content: String? = null, imageUrl: String? = "") = object: FetchBoundResource<Base>(){
        override fun createCall() = socialService.postComment(_commentId.toLong(), content, imageUrl)
    }.asLiveData()

    /*
     * 1) Check is authenticate(Session Manager)
     *  a. Not -> Goto Login
     *  b.
     */
    fun react(value: Reaction, previous: Reaction): LiveData<Resource<Base>>{
        val params = hashMapOf<String, Int>()
        params["commentId"] = _commentId
        if (value == Reaction.None){
            params["type"] = Reaction.value(previous)
        } else {
            params["type"] = Reaction.value(value)
        }
        params["userId"] = mSessionManager.cachedUser.value?.data?.mId ?: 0
        return repository.react(params)
    }
}