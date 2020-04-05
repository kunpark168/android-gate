package com.anhtam.gate9.v2.messenger

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import com.anhtam.domain.v2.Userv1
import com.anhtam.domain.v2.protocol.User
import com.anhtam.gate9.repository.SocialRepository
import com.anhtam.gate9.utils.AbsentLiveData
import com.anhtam.gate9.v2.newfeed.PagingViewModel
import com.anhtam.gate9.vo.Letter
import of.bum.network.helper.Resource
import java.util.*
import javax.inject.Inject

class ChannelViewModel @Inject constructor(
        private val mRepo: SocialRepository
) : PagingViewModel<Letter>() {

    private var mUser: User? = null
    var mIsFilter: Boolean = false
    private var mKeyword: String? = null
    private var mStartDate: String? = null
    private var mEndDate: String? = null

    override fun fetchData()  = if (mUser?.mId == null) AbsentLiveData.create() else mRepo.getLetterByUser(mUser?.mId!!)

    fun getLetterByUser(user: User) {
        mUser = user
        loadData(refresh = true)
    }

    fun filterDate(keyword: String?, startDate: String?, endDate: String?) {
        if (!mIsFilter) mIsFilter = true
        mKeyword = keyword
        mStartDate = startDate
        mEndDate = endDate
        mPage = 0
        val source = mRepo.filterLetter(keyword ?: "", startDate ?: "", endDate ?: "",1, mPage)
        _data.addSource(source){
            _data.value = it
            when(it){
                is Resource.Success, is Resource.Error -> {
                    _data.removeSource(source)
                }
            }
        }
    }

    fun loadMore() {
        mPage++
        val source = mRepo.filterLetter(mKeyword ?: "", mStartDate ?: "", mEndDate ?: "",1, mPage)
        _data.addSource(source){
            _data.value = it
            when(it){
                is Resource.Success, is Resource.Error -> {
                    _data.removeSource(source)
                }
            }
        }
    }
}