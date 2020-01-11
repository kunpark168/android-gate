package com.anhtam.gate9.v2.shared

import android.annotation.SuppressLint
import android.database.Cursor
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.GridLayoutManager
import com.anhtam.gate9.R
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
        initRecyclerView()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            mAdapter.setNewData(loadImagesFromSDCard())
        }
    }

    private fun initRecyclerView(){
        rvImages?.adapter = mAdapter
        rvImages?.layoutManager = GridLayoutManager(context, 3)
    }


    @SuppressLint("InlinedApi", "Recycle")
    @RequiresApi(Build.VERSION_CODES.O)
    private fun loadImagesFromSDCard(): ArrayList<String> {
        val uri: Uri = android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        val cursor: Cursor?
        val column_index_data: Int
        val column_index_folder_name: Int
        val listOfAllImages = ArrayList<String>()
        var absolutePathOfImage: String?

        val projection = arrayOf(MediaStore.MediaColumns.DATA, MediaStore.Images.Media.BUCKET_DISPLAY_NAME)

        cursor = activity?.contentResolver?.query(uri, projection, null, null)

        column_index_data = cursor!!.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA)
        column_index_folder_name = cursor
                .getColumnIndexOrThrow(MediaStore.Images.Media.BUCKET_DISPLAY_NAME)
        while (cursor.moveToNext()) {
            absolutePathOfImage = cursor.getString(column_index_data)
            listOfAllImages.add(absolutePathOfImage)
        }
        return listOfAllImages
    }

    class ChooseGalleryAdapter @Inject constructor(): BaseQuickAdapter<String, BaseViewHolder>(R.layout.multi_select_gallery_item_layout, mutableListOf()) {
        override fun convert(helper: BaseViewHolder?, item: String?) {
            val path = item ?: return
            val view = helper?.itemView ?: return
            view.itemView?.setImage(path)
        }
    }
}