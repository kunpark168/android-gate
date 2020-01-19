package com.anhtam.gate9.v2.shared.muilti_gallery

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.anhtam.domain.v2.Folder
import com.anhtam.gate9.R
import com.anhtam.gate9.v2.main.DaggerNavigationFragment
import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import kotlinx.android.synthetic.main.gallery_folder_fragment.*
import kotlinx.android.synthetic.main.gallery_folder_item_layout.view.*

class GalleryFolderFragment private constructor(val mListener: (Int)->Unit) : DaggerNavigationFragment(){

    companion object{
        fun newInstance(listener: (Int)->Unit) = GalleryFolderFragment(mListener = listener)
    }

    private var mViewModel: GalleryViewModel? = null
    private var mAdapter: GalleryFolderAdapter? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.gallery_folder_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mViewModel = ViewModelProviders.of(requireParentFragment())[GalleryViewModel::class.java]
        initGalleryRecyclerView()
        observerFolderChange()
    }

    private fun initGalleryRecyclerView(){
        mAdapter = GalleryFolderAdapter()
        mAdapter?.setOnItemClickListener { _, _, position ->
            mListener(position)
        }
        rvFolder?.adapter = mAdapter
        rvFolder?.layoutManager = LinearLayoutManager(context)
    }

    private fun observerFolderChange(){
        mViewModel?.getFolders()?.observe(viewLifecycleOwner, Observer {
            mAdapter?.setNewData(it)
        })
    }

    private class GalleryFolderAdapter: BaseQuickAdapter<Folder, BaseViewHolder>(R.layout.gallery_folder_item_layout, mutableListOf()){
        override fun convert(helper: BaseViewHolder?, item: Folder?) {
            val folder = item ?: return
            val view = helper?.itemView ?: return
            view.tvFolder?.text = folder.bucketDisplayName
            Glide.with(mContext)
                    .load(folder.representFile)
                    .centerCrop()
                    .into(view.imgThumbnail)
        }
    }
}