package com.anhtam.reactlibs.adapter

import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.AsyncDifferConfig
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.*
import com.anhtam.domain.Base

/**
 *  Adapter for loading Rv
 *  - Differ -> optimized NotifyDataSetChanged
 *  - Recycle view holder
 *  - Async -> Run optimized in background
 *  - addData -> run in background with coroutines
 *
 *  - Glide load
 */
abstract class AmazingAdapter<T: Base>(
        @LayoutRes private val layoutRes: Int,
        diffUtil: DiffUtil.ItemCallback<T>
) : ListAdapter<T, RecyclerView.ViewHolder>(
        AsyncDifferConfig.Builder<T>(diffUtil).build()
){

    private val job = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + job)
    private var mIsLoading = false

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when(viewType) {
            LOADING_TYPE -> {
                EmptyHolder.from(parent)
            }
            else -> {
                ViewTypeHolder.from(parent, layoutRes)
            }

        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (getItemViewType(position) == VIEW_TYPE) {
            (holder as ViewTypeHolder).bind(getItem(position))
        } else {

        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (currentList[position].isLoading){
            LOADING_TYPE
        } else {
            VIEW_TYPE
        }
    }

    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
        super.onDetachedFromRecyclerView(recyclerView)
        job.cancel()
    }

    fun addList(data: List<T>) {
        uiScope.launch {
            val list = mutableListOf<T>()
            withContext(Dispatchers.Default) {
                list.addAll(currentList)
                if (mIsLoading) {
                    list.removeAt(list.size - 1)
                    mIsLoading = false
                }
                list.addAll(data)
            }
            submitList(list)
        }
    }

    fun displayLoading() {
        if (!mIsLoading) {
            mIsLoading = true
            uiScope.launch {
                val list = mutableListOf<T>()
                withContext(Dispatchers.Default) {
                    list.addAll(currentList)
                    list.add(loadingItem())
                }
                submitList(list)
            }
        }
    }

    abstract fun ViewTypeHolder.bind(item: T?)
    abstract fun loadingItem(): T
    companion object {
        private const val LOADING_TYPE = 0
        private const val VIEW_TYPE = 1
    }

}



