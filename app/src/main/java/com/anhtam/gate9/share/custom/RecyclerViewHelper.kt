package com.anhtam.gate9.ui.shared

import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.anhtam.gate9.R
import com.anhtam.gate9.utils.ifNotNull
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder

abstract class BindingRecyclerViewAdapter<T, D: ViewDataBinding>(@LayoutRes layoutResId: Int, data: List<T?>? ): BaseQuickAdapter<T?, CustomViewHolder>(layoutResId, data) {

    private var mBinding: D? = null

    @Suppress("UNCHECKED_CAST")
    override fun convert(helper: CustomViewHolder?, item: T?) {
        ifNotNull(helper, item) {
            holder, data ->
            run {
                mBinding = holder.getBinding() as? D
                bind(holder, data, mBinding)
            }
        }
    }

    abstract fun bind(holder: CustomViewHolder, data: T, binding: D?)

    override fun getItemView(layoutResId: Int, parent: ViewGroup?): View {
        val binding = DataBindingUtil.inflate<ViewDataBinding>(mLayoutInflater, layoutResId, parent, false)
        return if (binding == null) {
            super.getItemView(layoutResId, parent)

        } else {
            val view = binding.root
            view.setTag(R.id.BaseQuickAdapter_databinding_support, binding)
            return view
        }
    }
}

class CustomViewHolder(view: View?): BaseViewHolder(view) {
    fun getBinding(): ViewDataBinding? {
        return itemView.getTag(R.id.BaseQuickAdapter_databinding_support) as ViewDataBinding?
    }
}

