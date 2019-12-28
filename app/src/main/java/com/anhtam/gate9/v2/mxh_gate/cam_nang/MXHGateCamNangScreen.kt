package com.anhtam.gate9.v2.mxh_gate.cam_nang

import android.os.Bundle
import android.view.*
import com.anhtam.gate9.R
import com.anhtam.gate9.v2.main.DaggerNavigationFragment

class MXHGateCamNangScreen : DaggerNavigationFragment() {

    companion object{
        fun newInstance() = MXHGateCamNangScreen()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.mxh_gate_cam_nang_screen, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }
    private fun init(){

    }
}