package com.anhtam.gate9.v2.search.result.article

import com.anhtam.domain.v2.Article
import com.anhtam.gate9.repository.SocialRepository
import com.anhtam.gate9.v2.newfeed.PagingViewModel
import javax.inject.Inject

class ArticleSearchViewModel @Inject constructor(
        private val repos: SocialRepository
) : PagingViewModel<Article>() {
    var mKey: String? = null

    override fun fetchData() =  repos.searchArticle(mKey ?: "", mPage)
}