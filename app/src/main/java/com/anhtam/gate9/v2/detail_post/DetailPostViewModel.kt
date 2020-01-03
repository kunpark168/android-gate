package com.anhtam.gate9.v2.detail_post

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.anhtam.domain.Base
import com.anhtam.domain.Post
import com.anhtam.domain.v2.PostEntity
import com.anhtam.domain.v2.WrapComments
import com.anhtam.gate9.repository.SocialRepository
import com.anhtam.gate9.storage.StorageManager
import com.anhtam.gate9.vo.IllegalReturn
import com.anhtam.gate9.vo.Reaction
import of.bum.network.FetchBoundResource
import of.bum.network.service.PostService
import of.bum.network.v2.SocialService
import java.lang.ref.WeakReference
import javax.inject.Inject
import kotlin.properties.Delegates

class DetailPostViewModel @Inject constructor(
        private val postService: PostService,
        val socialService: SocialService,
        private val repository: SocialRepository) : ViewModel() {

    private val _react = MutableLiveData<Reaction>()
    var _userId: Int by Delegates.notNull()
    var _gameId: Int by Delegates.notNull()
    private var _commentId: Int by Delegates.notNull()
    private lateinit var navigation: WeakReference<INavigator>

    fun initialize(post: PostEntity, navigator: INavigator){
        val value = try {
            post.like?.toInt() ?: 0
        } catch (e: NumberFormatException){
            throw IllegalReturn()
        }
        navigation = WeakReference(navigator)
        _react.value = Reaction.react(value)
        _userId = post.user?.mId ?: throw IllegalReturn()
        _gameId = try {
            post.game?.gameId?.toInt() ?: 0
        } catch (e: NumberFormatException){
            throw IllegalReturn()
        }
        _commentId = post.commentId?.toInt() ?: throw IllegalReturn()
    }

    fun commentByPost(id: String) = object: FetchBoundResource<Post>(){
        override fun createCall() = postService.getDetailPost(id)
    }.asLiveData()

    fun getDetailPost(id: String) = object: FetchBoundResource<WrapComments>(){
        override fun createCall() = socialService.getDetailPosts(id)
    }.asLiveData()

    fun postComment(content: String? = null, imageUrl: String? = "") = object: FetchBoundResource<Base>(){
        override fun createCall() = socialService.postComment(_commentId.toLong(), content, imageUrl)
    }.asLiveData()

    fun react(params: Map<String, Int>) = repository.react(params)

    /*
     * 1) Check is authenticate(Session Manager)
     *  a. Not -> Goto Login
     *  b.
     */
    fun react(value: Reaction){
        when(StorageManager.getAccessToken()){
            "" -> navigation.get()?.toLogin()// navigate to Login
            else -> { // auth
                when{
                    _react.value == value -> _react.value = Reaction.None
                    _react.value == Reaction.None -> _react.value = value
                    else ->{
                        _react.value = value
                    }
                }
                postReact()
            }
        }
    }

    private fun postReact(){
        val react = _react.value ?: return
        val params = hashMapOf<String, Int>()
        params["commentId"] = _commentId
        params["type"] = Reaction.value(react)
        params["userId"] = _userId
        socialService.react(params)
    }
}