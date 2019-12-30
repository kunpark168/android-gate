package com.anhtam.gate9.v2.mxh_gate.tin_game

import android.os.Bundle
import android.view.*
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DividerItemDecoration
import com.anhtam.gate9.R
import com.anhtam.gate9.navigation.NavigationFragment
import com.anhtam.gate9.utils.autoCleared
import com.anhtam.gate9.v2.main.DaggerNavigationFragment
import com.anhtam.gate9.v2.mxh_gate.MXHGateScreen
import com.anhtam.gate9.v2.mxh_gate.anh.MXHGateImageScreen
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import kotlinx.android.synthetic.main.mxh_gate_tin_game_screen.*

class MXHGateTinGameScreen : NavigationFragment() {

    companion object{
        fun newInstance() = MXHGateTinGameScreen()
    }

    private var mAdapter: Adapter by autoCleared()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.mxh_gate_tin_game_screen, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }
    private fun init(){
        mAdapter = Adapter()
        val data = arrayListOf<MXHGateScreen.EmptyData>()
        for(index in 1..10) data.add(MXHGateScreen.EmptyData())
        mAdapter.setNewData(data)
        rvTinGame?.adapter = mAdapter
        val unwrappedContext = context ?: return
        val dividerItemDecoration = DividerItemDecoration(unwrappedContext, LinearLayout.VERTICAL)
        val drawableDivider = ContextCompat.getDrawable(unwrappedContext, R.drawable.divider_item_decorator) ?: return
        dividerItemDecoration.setDrawable(drawableDivider)
        rvTinGame?.addItemDecoration(dividerItemDecoration)
    }

    class Adapter: BaseQuickAdapter<MXHGateScreen.EmptyData, BaseViewHolder>(R.layout.mxh_gate_tin_game_item_layout, arrayListOf()){
        override fun convert(helper: BaseViewHolder?, item: MXHGateScreen.EmptyData?) {

        }

    }
}