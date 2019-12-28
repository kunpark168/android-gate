package com.anhtam.gate9.v2.thong_tin.user

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.anhtam.gate9.R
import com.anhtam.gate9.ui.discussion.common.newfeed.NewFeedFragment
import com.anhtam.gate9.v2.main.DaggerNavigationFragment
import javax.inject.Inject

class ThongTinNguoiDungScreen constructor(
        private val _userId: Int
) : DaggerNavigationFragment(){

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.thong_tin_nguoi_dung_screen, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init(){
        childFragmentManager.beginTransaction()
                .replace(R.id.container, NewFeedFragment(_userId))
                .commit()
    }
}