package com.anhtam.gate9.v2.shared

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.anhtam.gate9.config.Config
import com.anhtam.gate9.navigation.FragmentResultListener
import com.anhtam.gate9.utils.FileUtils
import com.anhtam.gate9.utils.PermissionUtils
import com.anhtam.gate9.v2.main.DaggerNavigationFragment
import of.bum.network.helper.Resource
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File

abstract class AbstractGalleryFragment : DaggerNavigationFragment(), FragmentResultListener{

    private val viewModel: ShareViewModel by viewModels({requireActivity()}, {vmFactory})
    private var mIsMulti = false
    private val mMultiImages = arrayListOf<String>()
    open fun onSelectedImage(urls: List<String>){

    }

    override fun onFragmentResult(args: Bundle) {
        if (args.containsKey(Config.RESULT_CODE_SELECT_MULTI)) {
            val data = args.getStringArrayList(Config.RESULT_CODE_SELECT_MULTI)
            mMultiImages.clear()
            data?.let { mMultiImages.addAll(data) }
        }
        val media = createMedia(mMultiImages)
        if (media.isEmpty()) return
        showProgress()
        viewModel.uploadImage(media).observe(viewLifecycleOwner, Observer { source ->
            if (source is Resource.Success) {
                hideProgress()
                val urls = source.data  ?: return@Observer
                if (urls.isEmpty()) return@Observer
                onSelectedImage(urls)
            }
            if (source is Resource.Error) {
                hideProgress()
            }
        })
    }

    companion object{
        const val REQUEST_CODE_READ_EXTERNAL_STORAGE = 4
    }

    protected fun chooseImage(){
        mIsMulti = false
        val unwrapContext = context ?: return
        if (ContextCompat.checkSelfPermission(unwrapContext, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            PermissionUtils.requestPermissions(this, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), REQUEST_CODE_READ_EXTERNAL_STORAGE)
        } else {
            openSelectedImageGallery()
        }
    }

    protected fun selectedMultiImages(){
        mIsMulti = true
        val unwrapContext = context ?: return
        if (ContextCompat.checkSelfPermission(unwrapContext, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            PermissionUtils.requestPermissions(this, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), REQUEST_CODE_READ_EXTERNAL_STORAGE)
        } else {
            navigation?.addFragment(MultiChooseImageScreen.newInstance())
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
            REQUEST_CODE_READ_EXTERNAL_STORAGE -> {
                if (mIsMulti){
                    navigation?.addFragment(MultiChooseImageScreen.newInstance())
                } else {
                    openSelectedImageGallery()
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == Config.REQUEST_CODE_CHOOSE_IMAGE_FROM_CREATE_POST
                && resultCode == Activity.RESULT_OK
                && null != data) {
            val unwrapContext = context ?: return
            data.data?.let {
                val path = FileUtils.getPath(unwrapContext, it) ?: return
                val mImage = File(path)
                val reqFile = RequestBody.create(MediaType.parse("image/*"), mImage)
                val body = MultipartBody.Part.createFormData("files", mImage.name, reqFile)
                val mMedia = arrayListOf<MultipartBody.Part>()
                mMedia.add(body)
                viewModel.uploadImage(mMedia).observe(viewLifecycleOwner, Observer { source ->
                    if (source is Resource.Success) {
                        val urls = source.data  ?: return@Observer
                        if (urls.isEmpty()) return@Observer
                        onSelectedImage(urls)
                    }
                })
            }
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    private fun createMedia(paths: List<String>): ArrayList<MultipartBody.Part> {
        val mMedia = arrayListOf<MultipartBody.Part>()
        paths.forEach {
            val mImage = File(it)
            val reqFile = RequestBody.create(MediaType.parse("image/*"), mImage)
            val body = MultipartBody.Part.createFormData("files", mImage.name, reqFile)
            mMedia.add(body)
        }
        return mMedia
    }
}