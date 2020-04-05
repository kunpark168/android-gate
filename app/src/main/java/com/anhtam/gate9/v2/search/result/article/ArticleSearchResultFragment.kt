package com.anhtam.gate9.v2.search.result.article

import androidx.fragment.app.viewModels
import com.anhtam.domain.v2.Article
import com.anhtam.gate9.adapter.v2.ArticleAdapter
import com.anhtam.gate9.v2.search.AbstractTabSearchFragment

class ArticleSearchResultFragment : AbstractTabSearchFragment<Article, ArticleAdapter, ArticleSearchViewModel>() {
    override val mViewModel: ArticleSearchViewModel by viewModels { vmFactory }
    override fun setKey(key: String) {
        mViewModel.mKey = key
    }
}