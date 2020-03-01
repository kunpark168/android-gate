package com.anhtam.gate9.v2.chi_tiet_bai_viet

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.anhtam.domain.v2.Article
import com.anhtam.domain.v2.wrap.WrapArticle
import com.anhtam.gate9.repository.SocialRepository
import com.anhtam.gate9.utils.AbsentLiveData
import of.bum.network.helper.Resource
import javax.inject.Inject

class ChiTietBaiVietViewModel @Inject constructor(
        repos: SocialRepository
): ViewModel(){

    val mId = MutableLiveData<Int>()

    val mArticle: LiveData<Resource<WrapArticle>> = Transformations.switchMap(mId){id ->
        if (id == null || id <= 0) {
            AbsentLiveData.create()
        } else {
            repos.getChiTietBaiViet(id, 0)
        }
    }
}