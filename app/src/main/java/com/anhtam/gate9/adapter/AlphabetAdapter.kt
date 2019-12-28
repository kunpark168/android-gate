package com.anhtam.gate9.adapter

import android.content.Context
import android.graphics.Color
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.anhtam.gate9.R
import com.anhtam.gate9.utils.customOnClickHolder
import kotlinx.android.synthetic.main.alphabet_item_layout.view.*

class AlphabetAdapter(val context: Context?, private val mIChangeAlphabetIndex: IChangeAlphabetIndex) : RecyclerView.Adapter<AlphabetAdapter.ViewHolder>() {

    private val alphabet = mutableListOf(
            context?.resources?.getString(R.string.all) ?: "",
            "#",
            "A",
            "B",
            "C",
            "D",
            "E",
            "F",
            "G",
            "H",
            "I",
            "J",
            "K",
            "L",
            "M",
            "N",
            "O",
            "P",
            "Q",
            "R",
            "S",
            "T",
            "U",
            "V",
            "W",
            "X",
            "Y",
            "Z"
    )
    private var selected = 0

    override fun onCreateViewHolder(viewGroup: ViewGroup, p1: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.alphabet_item_layout, viewGroup, false))
    }

    override fun getItemCount(): Int {
        return alphabet.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (position == 0) {
            holder.itemView.tvDigit.setTextSize(TypedValue.COMPLEX_UNIT_SP, 13f)
        } else {
            holder.itemView.tvDigit.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16f)
        }
        if (position == selected) {
            holder.itemView.viewSelected.visibility = View.VISIBLE
            holder.itemView.tvDigit.setTextColor(Color.parseColor("#fa5a36"))
            holder.itemView.item_alphabet.setBackgroundColor(Color.WHITE)
        } else {
            holder.itemView.viewSelected.visibility = View.GONE
            holder.itemView.tvDigit.setTextColor(Color.parseColor("#777777"))
            holder.itemView.item_alphabet.setBackgroundColor(Color.parseColor("#f8f8f8"))
        }
        holder.itemView.tvDigit.text = alphabet[position]
        holder.itemView.customOnClickHolder {
            setSelected(position)
        }
    }

    private fun setSelected(selected: Int) {
        this.selected = selected
        alphabet.add("")
        alphabet.removeAt(alphabet.size - 1)
        notifyDataSetChanged()
        mIChangeAlphabetIndex.onChangeAlphabetIndex(alphabet[selected])
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
    interface IChangeAlphabetIndex{
        fun onChangeAlphabetIndex(letter: String)
    }
}