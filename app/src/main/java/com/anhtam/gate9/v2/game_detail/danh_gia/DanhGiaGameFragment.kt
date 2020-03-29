package com.anhtam.gate9.v2.game_detail.danh_gia

import android.os.Bundle
import androidx.fragment.app.viewModels
import com.anhtam.gate9.adapter.v2.RatingGameAdapter
import com.anhtam.gate9.navigation.FragmentResultListener
import com.anhtam.gate9.v2.danh_gia.RatingFragment
import com.anhtam.gate9.v2.game_detail.AbstractTabGameFragment
import com.anhtam.gate9.vo.Rating
import com.anhtam.gate9.vo.model.Category
import kotlinx.android.synthetic.main.danh_gia_game_header_view.view.*
import of.bum.network.helper.RestResponse

class DanhGiaGameFragment : AbstractTabGameFragment<Rating, RatingGameAdapter, DanhGiaViewModel>(){
    override fun initViewModel(id: Int) {
        mViewModel.initialize(id)
    }



    override val mViewModel: DanhGiaViewModel by viewModels { vmFactory }
    private lateinit var mHeaderView: DanhGiaGameHeaderView

    override fun setUpAdapter() {
        super.setUpAdapter()
        mHeaderView = DanhGiaGameHeaderView(context)
        mHeaderView.navigateToRatingFragment{ rating, content ->
            navigation?.addFragment(RatingFragment.newInstance(mGameId, isUser = false, rating = rating , content = content, callback = Runnable { mViewModel.loadData(refresh = true) }))
        }
        mAdapter.addHeaderView(mHeaderView)
    }

    override fun onResponseSuccess(response: RestResponse<*>?) {
        super.onResponseSuccess(response)
        val rate = response?.mMeta
        val point1 = rate?.get("point1") as? Double ?: 0.0
        val point2 = rate?.get("point2") as? Double ?: 0.0
        val point3 = rate?.get("point3") as? Double ?: 0.0
        val point4 = rate?.get("point4") as? Double ?: 0.0
        val point5 = rate?.get("point5") as? Double ?: 0.0

        val userRating = response?.mMeta?.get("urate") as? Map<String, Any>
        val point = userRating?.get("point") as? Double ?: 0.0
        val content = userRating?.get("content") as? String
        mHeaderView.ratingComponent?.ratingInfo(Category.Member, point, content)
        mHeaderView.ratingComponent?.initView(arrayOf(point1, point2, point3, point4, point5))
    }

    companion object{
        fun newInstance() = DanhGiaGameFragment()
    }
}