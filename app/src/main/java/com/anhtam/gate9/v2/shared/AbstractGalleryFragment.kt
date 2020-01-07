package com.anhtam.gate9.v2.shared

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.content.ContextCompat
import com.anhtam.gate9.config.Config
import com.anhtam.gate9.utils.FileUtils
import com.anhtam.gate9.utils.PermissionUtils
import com.anhtam.gate9.v2.main.DaggerNavigationFragment
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File

abstract class AbstractGalleryFragment : DaggerNavigationFragment(){

    private var mImage: File? = null
    protected var mMedia = arrayListOf<MultipartBody.Part>()

    open fun onSelectedImage(){

    }
    companion object{
        const val REQUEST_CODE_READ_EXTERNAL_STORAGE = 4
    }

    protected fun chooseImage(){
        val unwrapContext = context ?: return
        if (ContextCompat.checkSelfPermission(unwrapContext, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            PermissionUtils.requestPermissions(this, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), REQUEST_CODE_READ_EXTERNAL_STORAGE)
        } else {
            openSelectedImageGallery()
        }
    }

    private fun openSelectedImageGallery() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1) {
            startActivityForResult(Intent.createChooser(intent,"Select picture"), Config.REQUEST_CODE_CHOOSE_IMAGE_FROM_CREATE_POST)
        } else {
            startActivityForResult(intent, Config.REQUEST_CODE_CHOOSE_IMAGE_FROM_CREATE_POST)
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (grantResults.firstOrNull() != PackageManager.PERMISSION_GRANTED) return
        when (requestCode) {
            REQUEST_CODE_READ_EXTERNAL_STORAGE -> openSelectedImageGallery()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == Config.REQUEST_CODE_CHOOSE_IMAGE_FROM_CREATE_POST
                && resultCode == Activity.RESULT_OK
                && null != data) {
            val unwrapContext = context ?: return
            data.data?.let {
                val path = FileUtils.getPath(unwrapContext, it) ?: return
                mImage = File(path)
                onSelectedImage()
//                mGalleryAdapter.addData(path)
//                if (rvImage?.visibility == View.GONE) {
//                    rvImage?.visibility = View.VISIBLE
//                }
//                imgSend.visibility = View.VISIBLE
//                rightLayout.visibility = View.GONE
                val reqFile = RequestBody.create(MediaType.parse("image/*"), mImage!!)
                val body = MultipartBody.Part.createFormData("files", mImage!!.name, reqFile)
                mMedia.add(body)
            }
        }
        super.onActivityResult(requestCode, resultCode, data)
    }
}