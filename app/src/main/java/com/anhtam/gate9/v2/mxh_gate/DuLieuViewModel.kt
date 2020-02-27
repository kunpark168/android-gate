package com.anhtam.gate9.v2.mxh_gate

import com.anhtam.domain.v2.Article
import com.anhtam.gate9.repository.SocialRepository
import com.anhtam.gate9.v2.newfeed.PagingViewModel
import javax.inject.Inject

class DuLieuViewModel @Inject constructor(
        private val repos: SocialRepository
) : PagingViewModel<Article>(){

    private var mCategory: Int = 1

    fun setCategory(category: Int){
        mCategory = category
    }
    override fun fetchData() = repos.getDataRelatedToUser(0, mCategory, mPage, 10)

}