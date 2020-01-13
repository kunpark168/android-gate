package com.anhtam.gate9.v2.shared

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.anhtam.gate9.repository.SocialRepository
import of.bum.network.helper.Resource
import okhttp3.MultipartBody
import javax.inject.Inject

class ShareViewModel @Inject constructor(
        val repository: SocialRepository
): ViewModel(){

    fun uploadImage(files: List<MultipartBody.Part>): LiveData<Resource<List<String>>> {
        return repository.uploadImages(files)
    }
}