package com.anhtam.gate9.v2.search.result.article

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.anhtam.domain.v2.Article
import com.anhtam.gate9.R
import com.anhtam.gate9.adapter.v2.ArticleAdapter
import com.anhtam.gate9.v2.chi_tiet_bai_viet.tin_game.ChiTietBaiVietTinGameScreen
import com.anhtam.gate9.v2.game_detail.DetailGameFragment
import com.anhtam.gate9.v2.game_detail.download.DownloadGameFragment
import com.anhtam.gate9.v2.search.AbstractTabSearchFragment

class ArticleSearchResultFragment : AbstractTabSearchFragment<Article, ArticleAdapter, ArticleSearchViewModel>() {
    override val mViewModel: ArticleSearchViewModel by viewModels { vmFactory }
    override fun setKey(key: String) {
        mViewModel.mKey = key
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mAdapter.setOnItemChildClickListener setOnItemClickListener@{ _, childView, position ->
            when (childView.id) {
                R.id.tvTitleGame, R.id.imgGame -> {
                    val id = mAdapter.data[position]?.mGame?.gameId ?: return@setOnItemClickListener
                    navigation?.addFragment(DetailGameFragment.newInstance(id))
                }
                R.id.imgTinTuc, R.id.tvTinTuc -> {
                    val id = mAdapter.getItem(position)?.mArticleId ?: return@setOnItemClickListener
                    val article = mAdapter.getItem(position)
                    val type = article?.mArticleType ?: return@setOnItemClickListener
                    navigation?.addFragment(ChiTietBaiVietTinGameScreen.newInstance(id, article, type))
                }
                R.id.tvDownload -> {
                    val game = mAdapter.data[position].mGame ?: return@setOnItemClickListener
                    navigation?.addFragment(DownloadGameFragment.newInstance(game))
                }
                else -> {
                }
            }
        }
    }
}