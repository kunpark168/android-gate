package com.anhtam.gate9.v2.shared

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import com.anhtam.gate9.R
import com.anhtam.gate9.vo.model.Category
import kotlinx.android.synthetic.main.rating_view_header.view.*
import kotlinx.android.synthetic.main.rating_view_header01.view.*

class RatingComponent @JvmOverloads constructor(
        context: Context?,
        attributeSet: AttributeSet? = null,
        defStyleAttr: Int = 0
) : ConstraintLayout(context, attributeSet, defStyleAttr) {

    private val progresses by lazy {
        arrayOf(oneStarProgress, twoStarProgress, threeStarProgress, fourStarProgress, fiveStarProgress)
    }


    init {
        View.inflate(context, R.layout.rating_view_header, this)
        rtReview?.setOnRatingBarChangeListener { _, rating, _ ->
            when (rating.toInt()) {
                in 0..1 -> {
                    tvStatusRating?.text = "Poor"
                }
                else -> {
                    tvStatusRating?.text = "Good"
                }
            }
        }
        txtPoint?.text = "9.1"
    }

    fun initView(percent: Array<Float>) {
        var params: LayoutParams?
        for (index in progresses.indices) {
            params = progresses[index].layoutParams as? LayoutParams
            params?.matchConstraintPercentWidth = percent[index]
            progresses[index].layoutParams = params
        }

    }

    fun ratingInfo(mType: Category) {
        when (mType) {
            Category.Member -> descriptionTextView?.text = "Bạn viết bài rất hay!"
            Category.Publisher -> descriptionTextView?.text = resources?.getString(R.string.ban_nghi_gi_ve_nph_nay) ?: ""
        }

    }
}