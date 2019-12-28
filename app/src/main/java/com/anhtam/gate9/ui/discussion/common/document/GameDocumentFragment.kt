package com.anhtam.gate9.ui.discussion.common.document

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.anhtam.gate9.R
import com.anhtam.gate9.ui.base.BaseFragment
import com.anhtam.gate9.v2.main.DaggerNavigationFragment
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import kotlinx.android.synthetic.main.game_document_fragment.*

class GameDocumentFragment : DaggerNavigationFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.game_document_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init() {
        val adapter = TypeGame()
        adapter.setNewData(typeGame())
//        rvTypeGame?.adapter = adapter
//        rvTypeGame?.addItemDecoration(DividerItemDecoration(context, RecyclerView.HORIZONTAL))
    }

    companion object {
        fun newInstance(gameId: String): GameDocumentFragment {
            return GameDocumentFragment()
        }
        fun typeGame(): List<String> {
            return arrayListOf("Nhập vai", "Hành Động", "3D", "Võ Hiệp", "Trung Quốc")
        }
    }

    class TypeGame : BaseQuickAdapter<String, BaseViewHolder>(R.layout.item_type_of_game) {

        override fun convert(helper: BaseViewHolder?, item: String?) {
            val holder = helper ?: return
            val type = item ?: return
            val tvType = holder.itemView as? TextView
            tvType?.text = type
        }

    }
}