package com.anhtam.gate9.v2.discussion.common.rating

import androidx.lifecycle.ViewModel
import javax.inject.Inject

class RatingViewModel @Inject constructor() : ViewModel() {

    private var mPage = 1
    val page: Int
        get() = mPage
}