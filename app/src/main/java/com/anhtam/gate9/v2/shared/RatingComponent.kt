package com.anhtam.gate9.v2.shared

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import com.anhtam.gate9.R
import com.anhtam.gate9.vo.model.Category
import kotlinx.android.synthetic.main.layout_rating.view.*
import kotlinx.android.synthetic.main.rating_view_header.view.*
import kotlinx.android.synthetic.main.rating_view_header01.view.*
import java.math.RoundingMode
import java.text.DecimalFormat

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
    }

    fun initView(percent: Array<Double>) {
        var params: LayoutParams?
        val sum = percent.mapIndexed {  index, item ->
            (index + 1) * item
        }.sum()
        val num = percent.sum()
        val arg = sum / num
        for (index in progresses.indices) {
            params = progresses[index].layoutParams as? LayoutParams
            params?.matchConstraintPercentWidth = percent[index].toFloat() * (index + 1) * 0.4f / sum.toFloat()
            progresses[index].layoutParams = params
        }
        txtPoint?.text = String.format("%.1f", arg)
        ratingBar?.rating = arg.toFloat()
    }

    fun ratingInfo(mType: Category) {
        when (mType) {
            Category.Member -> descriptionTextView?.text = "Bạn viết bài rất hay!"
            Category.Publisher -> descriptionTextView?.text = resources?.getString(R.string.ban_nghi_gi_ve_nph_nay) ?: ""
        }

    }
}