package com.anhtam.gate9.v2.createpost

import androidx.lifecycle.ViewModel
import com.anhtam.gate9.repository.SocialRepository
import javax.inject.Inject

class CreatePostViewModel @Inject constructor(
        val repository: SocialRepository): ViewModel() {
}