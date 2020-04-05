package com.anhtam.gate9.v2.search.temp

import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import com.anhtam.domain.Game
import com.anhtam.gate9.R
import com.anhtam.gate9.utils.autoCleared
import com.anhtam.gate9.v2.main.DaggerNavigationFragment
import com.anhtam.gate9.v2.search.DataSource
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import kotlinx.android.synthetic.main.fragment_temp_search.*
import kotlinx.android.synthetic.main.layout_rating_game.view.*

/**
 * A simple [Fragment] subclass.
 */
class TempSearchFragment : DaggerNavigationFragment(R.layout.fragment_temp_search) {

    private var mRecentAdapter by autoCleared<Adapter>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val games = DataSource.getSampleGames()

        mRecentAdapter = Adapter()
        val dividerItemDecoration = DividerItemDecoration(context, LinearLayout.VERTICAL)
        val drawableDivider = context?.let {
            ContextCompat.getDrawable(it, R.drawable.light_divider_item_decorator)
        }
        drawableDivider?.let {
            dividerItemDecoration.setDrawable(drawableDivider)
        }
        rvRecent?.addItemDecoration(dividerItemDecoration)
        rvRecent?.adapter = mRecentAdapter
        rvRecent?.isNestedScrollingEnabled = false
//        mRecentAdapter.setNewData(games)
    }

    companion object {
        fun newInstance(): TempSearchFragment {
            return TempSearchFragment()
        }
    }

    class Adapter : BaseQuickAdapter<Game, BaseViewHolder>(R.layout.item_temp_search, emptyList()) {

        override fun convert(helper: BaseViewHolder?, item: Game?) {
            val unwrappedHelper = helper ?: return
            val unwrappedItem = item ?: return
            unwrappedHelper.itemView.apply {
                tvTitleGame.text = unwrappedItem.mTitle
            }
        }
    }
}
