package com.anhtam.gate9.v2.mxh_gate.video

import android.os.Bundle
import android.view.*
import com.anhtam.gate9.R
import com.anhtam.gate9.navigation.NavigationFragment
import com.anhtam.gate9.utils.autoCleared
import com.anhtam.gate9.v2.main.DaggerNavigationFragment
import com.anhtam.gate9.v2.mxh_gate.MXHGateScreen
import com.anhtam.gate9.v2.mxh_gate.anh.MXHGateImageScreen
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import kotlinx.android.synthetic.main.mxh_gate_video_screen.*

class MXHGateVideoScreen : NavigationFragment() {

    companion object{
        fun newInstance() = MXHGateVideoScreen()
    }
    private var mAdapter: Adapter by autoCleared()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.mxh_gate_video_screen, container, false)
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
        rvVideo?.adapter = mAdapter
    }

    class Adapter: BaseQuickAdapter<MXHGateScreen.EmptyData, BaseViewHolder>(R.layout.mxh_gate_video_item_layout, arrayListOf()){
        override fun convert(helper: BaseViewHolder?, item: MXHGateScreen.EmptyData?) {

        }

    }
}