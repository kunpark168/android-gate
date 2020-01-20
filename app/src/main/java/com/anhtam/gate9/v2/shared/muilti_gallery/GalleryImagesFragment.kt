package com.anhtam.gate9.v2.shared.muilti_gallery

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import com.anhtam.gate9.R
import com.anhtam.gate9.config.Config
import com.anhtam.gate9.share.view.CustomLoadMoreView
import com.anhtam.gate9.v2.main.DaggerNavigationFragment
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import kotlinx.android.synthetic.main.gallery_images_fragment.*
import kotlinx.android.synthetic.main.multi_select_gallery_item_layout.view.*
import javax.inject.Inject

class GalleryImagesFragment private constructor(): DaggerNavigationFragment(){

    companion object{
        private const val NUM_GALLERY_PER_PAGE = 18
        fun newInstance() = GalleryImagesFragment()
    }

    private val mImages = arrayListOf<String>()
    private var mPage = 0

    private var mAdapter: ChooseGalleryAdapter? = null
    private var mViewModel: GalleryViewModel? = null
    private val mSelectedGallery = arrayListOf<String>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.gallery_images_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init(){
        mViewModel = ViewModelProviders.of(requireParentFragment())[GalleryViewModel::class.java]
        initRecyclerView()
        observerImagesChange()
        initEvents()
    }

    private fun initEvents(){
        btnChoose?.setOnClickListener{
            val arg = Bundle()
            arg.putStringArrayList(Config.RESULT_CODE_SELECT_MULTI, mSelectedGallery)
            navigation?.returnResult(arg)
        }
    }

    private fun observerImagesChange(){
        mViewModel?.getImages()?.observe(viewLifecycleOwner, Observer {
            mPage = 0
            mAdapter?.setNewData(null)
            if (it.isNullOrEmpty()) {
                return@Observer
            }
            mImages.addAll(it)
            loadMoreAdapter(load(mPage))
        })
    }

    private fun load(page: Int): List<String>{
        return if (mImages.size < 4)
            mImages
        else {
            mImages.subList(0, 4)
        }
    }

    private fun loadMoreAdapter(data: List<String>){
        if (data.isNullOrEmpty()) {
            mAdapter?.loadMoreEnd()
        } else {
            if (mPage == 0) {
                mAdapter?.setNewData(data)
            } else {
//                mAdapter?.addData(data)
            }
            mAdapter?.loadMoreComplete()
        }
    }

    private fun initRecyclerView(){
        mAdapter = ChooseGalleryAdapter()
        mAdapter?.setSelectedListener(object : ChooseGalleryAdapter.SelectedListener{
            override fun onSelectedChange(data: List<String>) {
                mSelectedGallery.clear()
                mSelectedGallery.addAll(data)
            }
        })
        mAdapter?.setLoadMoreView(CustomLoadMoreView())
        mAdapter?.setOnLoadMoreListener({
            mPage++
            loadMoreAdapter(load(mPage))
        }, rvImages)
        rvImages?.adapter = mAdapter
        rvImages?.layoutManager = GridLayoutManager(context, 2)
    }
    class ChooseGalleryAdapter @Inject constructor(): BaseQuickAdapter<String, BaseViewHolder>(R.layout.multi_select_gallery_item_layout, mutableListOf()) {

        private val mSelectedItem = mutableListOf<String>()
        private var mListener: SelectedListener? = null

        interface SelectedListener{
            fun onSelectedChange(data: List<String>)
        }

        fun setSelectedListener(listener: SelectedListener){
            mListener = listener
        }

        init {
            setOnItemChildClickListener { _, view, position ->
                when(view.id){
                    R.id.itemView -> {
                        val item = data[position] ?: return@setOnItemChildClickListener
                        if (mSelectedItem.contains(item)){
                            mSelectedItem.remove(item)
                        } else {
                            mSelectedItem.add(item)
                        }
                        mListener?.onSelectedChange(mSelectedItem)
                    }
                    else -> {}
                }
                notifyItemChanged(position)
            }
        }

        override fun convert(helper: BaseViewHolder?, item: String?) {
            val path = item ?: return
            val view = helper?.itemView ?: return
            view.itemView?.setImage(path)
            view.itemView?.onSelected(mSelectedItem.contains(item))
            helper.addOnClickListener(R.id.itemView)
        }
    }
}