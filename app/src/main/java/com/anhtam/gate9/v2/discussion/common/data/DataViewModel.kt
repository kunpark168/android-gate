package com.anhtam.gate9.v2.discussion.common.data

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import com.anhtam.domain.v2.wrap.WrapGames
import com.anhtam.gate9.repository.SocialRepository
import of.bum.network.helper.Resource
import javax.inject.Inject

class DataViewModel @Inject constructor(private val socialRepository: SocialRepository) : ViewModel() {
    val _articles = MediatorLiveData<Resource<WrapGames>>()

    private var mPage = 1
    var mCategory: DataCategory = DataCategory.NEWS
    val page: Int
        get() = mPage
    private var mType: Int = 1
    private var mUserId: Int = 0


    fun requestFirstPage(userId: Int,
                         category: DataCategory) {
        mPage = 1
        mUserId = userId
        mCategory = category
        mType = getCategory(category)
        _articles.addSource(socialRepository.getDataRelatedToUser(mUserId, mType, mPage, 10)){
            _articles.value = it
        }
    }

    fun requestMore() {
        mPage++
        _articles.addSource(socialRepository.getDataRelatedToUser(mUserId, mType, mPage, 10)){
            _articles.value = it
        }
    }

    private fun getCategory(category: DataCategory): Int {
        return when(category) {
            DataCategory.NEWS -> 1
            DataCategory.TIP -> 2
            DataCategory.VIDEO -> 3
            DataCategory.IMAGE -> 4
        }
    }
}