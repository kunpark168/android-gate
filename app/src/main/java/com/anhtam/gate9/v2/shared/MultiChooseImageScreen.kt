package com.anhtam.gate9.v2.shared

import android.os.Build
import android.os.Bundle
import android.view.*
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.anhtam.gate9.R
import com.anhtam.gate9.config.Config
import com.anhtam.gate9.v2.main.DaggerNavigationFragment
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import kotlinx.android.synthetic.main.multi_choose_image_fragment.*
import kotlinx.android.synthetic.main.multi_select_gallery_item_layout.view.*
import javax.inject.Inject

class MultiChooseImageScreen: DaggerNavigationFragment(){

    companion object{
        fun newInstance() = MultiChooseImageScreen()
    }

    private val mSelectedGallery = arrayListOf<String>()
    private val mViewModel: GalleryViewModel by viewModels { vmFactory }

    private var mDisplayCamera: Boolean = true

    private val mAdapter: ChooseGalleryAdapter = ChooseGalleryAdapter()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.multi_choose_image_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    override fun menuRes() =  R.menu.camera_menu

    private fun init(){
        loadData()
        initRecyclerView()
        observer()
    }

    private fun observer(){
        mViewModel.getImages().observe(viewLifecycleOwner, Observer {
            if (!it.isNullOrEmpty()){
                mAdapter.setNewData(it)
            }
        })
    }

    private fun loadData(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            mViewModel.getAllImages()
        }
    }

    private fun initRecyclerView(){
        mAdapter.setSelectedListener(object : ChooseGalleryAdapter.SelectedListener{
            override fun onSelectedChange(data: List<String>) {
                mSelectedGallery.clear()
                mSelectedGallery.addAll(data)
                mDisplayCamera = mSelectedGallery.isEmpty()
                activity?.invalidateOptionsMenu()
            }
        })
        rvImages?.adapter = mAdapter
        rvImages?.layoutManager = GridLayoutManager(context, 3)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        val itemCamera = menu.findItem(R.id.menu_camera)
        val itemNext = menu.findItem(R.id.menu_next)
        if (mDisplayCamera){
            itemCamera?.isVisible = true
            itemNext?.isVisible = false
        } else {
            itemCamera?.isVisible = false
            itemNext?.isVisible = true
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.menu_camera -> {

            }
            R.id.menu_next -> {
                val arg = Bundle()
                arg.putStringArrayList(Config.RESULT_CODE_SELECT_MULTI, mSelectedGallery)
                navigation?.returnResult(arg)
            }

        }
        return super.onOptionsItemSelected(item)
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
            view.itemView?.onSelected(mSelectedItem.contains(path))
            helper.addOnClickListener(R.id.itemView)
        }
    }
}