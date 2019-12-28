package com.anhtam.gate9.utils

import com.anhtam.reactlibs.adapter.AmazingAdapter
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import of.bum.network.helper.Resource

fun <D, A : BaseQuickAdapter<D, BaseViewHolder>> Resource<List<D>>.loadingToAdapter(adapter: A, page: Int) {
    when(this) {
        is Resource.Success -> {
            val data = this.data
            if (data.isNullOrEmpty()) {
                adapter.loadMoreEnd()
            } else {
                if (page == 1) {
                    adapter.setNewData(data)
                } else {
                    adapter.addData(data)
                }
                adapter.loadMoreComplete()
            }
        }
        is Resource.Loading -> {

        }
        else -> {
            adapter.loadMoreFail()
        }
    }
}

fun <D, A : AmazingAdapter<D>> Resource<List<D>>.loadingToAmazingAdapter(adapter: A, page: Int) {
    when(this) {
        is Resource.Success -> {
            val data = this.data ?: return
            if (data.isNotEmpty()) {
                if (page != 1) {
//                    adapter.hideLoading()
                }
                adapter.addList(data)
            }
        }
        is Resource.Loading -> {

        }
        else -> {
        }
    }
}