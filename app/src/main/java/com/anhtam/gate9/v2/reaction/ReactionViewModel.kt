package com.anhtam.gate9.v2.reaction

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import com.anhtam.domain.v2.Userv1
import com.anhtam.gate9.repository.SocialRepository
import of.bum.network.helper.Resource
import javax.inject.Inject
import kotlin.properties.Delegates

class ReactionViewModel @Inject constructor(
        val repository: SocialRepository
): ViewModel(){

    var mPage = 0
    private var mCommentId by Delegates.notNull<Int>()
    private var mTab by Delegates.notNull<Int>()
    private val mMembers = MediatorLiveData<Resource<List<Userv1>>>()
    val members: LiveData<Resource<List<Userv1>>>
        get() = mMembers
    fun requestFirst(commentId: Int, tab: Int){
        mCommentId = commentId
        mTab = tab
        mPage = 0
        val source = repository.getSocialContact(mCommentId, mTab, mPage)
        mMembers.addSource(source){
            mMembers.value = it
        }
    }

    fun requestMore(){
        mPage++
        val source = repository.getSocialContact(mCommentId, mTab, mPage)
        mMembers.addSource(source){
            mMembers.value = it
        }
    }
}