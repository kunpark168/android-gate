package com.anhtam.gate9.share.view

import androidx.annotation.LayoutRes
import com.anhtam.gate9.R
import com.chad.library.adapter.base.loadmore.LoadMoreView

class CustomLoadMoreView: LoadMoreView() {
    @LayoutRes
    override fun getLayoutId(): Int = R.layout.share_loading_load_more_view

    override fun getLoadingViewId(): Int = R.id.load_more_loading_view

    override fun getLoadEndViewId(): Int = R.id.load_more_load_end_view

    override fun getLoadFailViewId(): Int = R.id.load_more_load_fail_view
}