package com.anhtam.gate9.v2.discussion.common.data

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.anhtam.domain.v2.Article
import com.anhtam.gate9.R
import com.anhtam.gate9.adapter.v2.ArticleAdapter
import com.anhtam.gate9.v2.chi_tiet_bai_viet.tin_game.ChiTietBaiVietHinhAnhScreen
import com.anhtam.gate9.v2.chi_tiet_bai_viet.tin_game.ChiTietBaiVietTinGameScreen
import com.anhtam.gate9.v2.chi_tiet_bai_viet.tin_game.ChiTietBaiVietVideoScreen
import com.anhtam.gate9.v2.discussion.common.CommonDiscussionFragment
import com.anhtam.gate9.v2.game_detail.DetailGameFragment

class DataFragment: CommonDiscussionFragment<Article, ArticleAdapter, DataViewModel>() {
    private var mUserId: Int = 0
    override val tabTitle: List<Int>
        get() = arrayListOf(R.string.news_category, R.string.tip_category, R.string.video_category, R.string.image_category)
    override val colorTextTab: Int = R.color.colorTabData
    override val mViewModel: DataViewModel by viewModels { vmFactory }
    companion object {
        fun newInstance(userId: Int) : DataFragment {
            val fragment = DataFragment()
            fragment.mUserId = userId
            return fragment
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initEvent()
    }

    private fun initEvent() {
        mAdapter.setOnItemChildClickListener { adapter, view, position ->
            when(view.id) {
                R.id.imgTinTuc, R.id.tvTinTuc -> {
                    val article = adapter.data[position] as? Article ?: return@setOnItemChildClickListener
                    val id = article.mArticleId ?: return@setOnItemChildClickListener
                    when(mCurrentCategory) {
                        1 -> navigation?.addFragment(ChiTietBaiVietTinGameScreen.newInstance(id, article, 0))
                        2 -> navigation?.addFragment(ChiTietBaiVietTinGameScreen.newInstance(id, article, 3))
                        3 -> navigation?.addFragment(ChiTietBaiVietVideoScreen.newInstance(id, article, 2))
                        4 -> navigation?.addFragment(ChiTietBaiVietHinhAnhScreen.newInstance(id, article, 4))
                        else -> {}
                    }
                }
                R.id.tvTitleGame, R.id.imgGame -> {
                    val id = (adapter.data[position] as? Article)?.mGame?.gameId ?: return@setOnItemChildClickListener
                    navigation?.addFragment(DetailGameFragment.newInstance(id))
                }
            }
        }
    }

}
