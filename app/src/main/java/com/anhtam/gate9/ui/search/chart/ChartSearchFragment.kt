package com.anhtam.gate9.ui.search.chart


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import com.anhtam.gate9.adapter.BannerGameAdapter
import com.anhtam.domain.dto.Game
import com.anhtam.gate9.R
import com.anhtam.gate9.ui.base.BaseFragment
import com.anhtam.gate9.ui.search.DataSource
import com.anhtam.gate9.utils.autoCleared
import com.beloo.widget.chipslayoutmanager.ChipsLayoutManager
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import kotlinx.android.synthetic.main.fragment_chart_search.*
import kotlinx.android.synthetic.main.item_recently_search.view.*

/**
 * A simple [Fragment] subclass.
 */
class ChartSearchFragment : BaseFragment() {

    private var mAdapter by autoCleared<BannerGameAdapter>()
    private var mRecentAdapter by autoCleared<Adapter>()


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_chart_search, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val games = DataSource.getSampleGames()

        mAdapter = BannerGameAdapter()
        rvTagGame?.layoutManager = ChipsLayoutManager.newBuilder(context)
                .build()
        rvTagGame?.adapter = mAdapter
        mAdapter.setNewData(games)

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
        mRecentAdapter.setNewData(games)


        mRequestManager.load(games[0].mBanner).into(imgBanner01)
        mRequestManager.load(games[1].mBanner).into(imgBanner02)
        mRequestManager.load(games[2].mBanner).into(imgBanner03)
        mRequestManager.load(games[3].mBanner).into(imgBanner04)
    }

    companion object {
        fun newInstance(): ChartSearchFragment {
            return ChartSearchFragment()
        }
    }

    class Adapter : BaseQuickAdapter<Game, BaseViewHolder>(R.layout.item_recently_search, emptyList()) {

        override fun convert(helper: BaseViewHolder?, item: Game?) {
            val unwrappedHelper = helper ?: return
            val unwrappedItem = item ?: return
            unwrappedHelper.itemView.apply {
                tvTitleGame.text = unwrappedItem.mTitle
            }
        }
    }
}
