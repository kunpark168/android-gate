package com.anhtam.gate9.adapter.v2.du_lieu

import android.view.View
import androidx.constraintlayout.widget.Group
import com.anhtam.domain.v2.Article
import com.anhtam.gate9.R
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder


class Adapter: BaseQuickAdapter<Article, BaseViewHolder>(R.layout.mxh_gate_tin_game_item_layout, arrayListOf()){
    override fun convert(helper: BaseViewHolder?, item: Article?) {
        if (helper == null) return
        val article = item ?: return
        helper.getView<Group>(R.id.group)?.visibility = View.VISIBLE

    }
}