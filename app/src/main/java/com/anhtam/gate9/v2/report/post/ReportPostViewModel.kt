package com.anhtam.gate9.v2.report.post

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import com.anhtam.domain.v2.wrap.WrapComments
import com.anhtam.gate9.repository.SocialRepository
import of.bum.network.helper.Resource
import javax.inject.Inject

class ReportPostViewModel @Inject constructor(val repos: SocialRepository) : ViewModel() {

    private val mComments = MediatorLiveData<Resource<WrapComments>>()
    val comments: LiveData<Resource<WrapComments>>
        get() = mComments
    fun getDetailPost(id: Long){
        val source = repos.getChildComment(id.toInt(), 0)
        mComments.addSource(source){
            mComments.value = it
        }
    }
    fun report(id: Long, note: String) = repos.report(id, 1, note)
}