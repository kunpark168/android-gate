package com.anhtam.gate9.v2.createpost

import android.Manifest
import android.app.Activity.RESULT_OK
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.core.content.ContextCompat
import com.anhtam.gate9.R
import com.anhtam.gate9.adapter.v2.ChooseGalleryAdapter
import com.anhtam.gate9.config.Config
import com.anhtam.gate9.utils.FileUtils
import com.anhtam.gate9.utils.PermissionUtils
import com.anhtam.gate9.v2.main.DaggerNavigationFragment
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import kotlinx.android.synthetic.main.create_post_screen.*
import kotlinx.android.synthetic.main.select_photo_item_layout.view.*
import of.bum.network.v2.MediaService
import of.bum.network.v2.SocialService
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber
import java.io.File
import javax.inject.Inject
import javax.inject.Named

class CreatePostScreen : DaggerNavigationFragment() {

    private var mImage: File? = null
    @Inject lateinit var mediaService: MediaService
    @Inject lateinit var socialService: SocialService
    private var mPhotos = arrayListOf<String>()
    @Inject lateinit var mAdapter: ChooseGalleryAdapter
    private var mMedia = arrayListOf<MultipartBody.Part>()

    @field:Named("banner") @Inject lateinit var bannerOptions: RequestOptions

    companion object {
        fun newInstance()= CreatePostScreen()
        const val REQUEST_CODE_READ_EXTERNAL_STORAGE = 4
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        activity?.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
        return inflater.inflate(R.layout.create_post_screen, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        eventClicks()
        initView()
    }

    private fun initView() {
        mAdapter?.setOnItemChildClickListener { _, _, position ->
            mAdapter?.remove(position)
            mMedia.removeAt(position)
            if (mAdapter?.data?.isEmpty() == true){
                imageRecyclerView.visibility = View.GONE
            }
        }
        imageRecyclerView?.adapter = mAdapter
    }

    private fun eventClicks() {
        tvCancel?.setOnClickListener {
            navigation?.back()
        }
        imgPhoto?.setOnClickListener {
            val unwrapContext = context ?: return@setOnClickListener
            if (ContextCompat.checkSelfPermission(unwrapContext, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                PermissionUtils.requestPermissions(this, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), REQUEST_CODE_READ_EXTERNAL_STORAGE)
            } else {
                openSelectedImageGallery()
            }
        }
        tvPost?.setOnClickListener {
            if(!mMedia.isNullOrEmpty()) {
                showProgress()
                uploadImage()
            } else {
                showProgress()
                postForum()
            }
        }
    }

    private fun postForum() {
        val params = HashMap<String, Any>()
        params["appKey"] = "0mg0y5suxqa1vkaufens"
        params["content"] = edtPostContent?.text?.toString() ?: ""
        params["domain"] = "https://www.9gate.net/"
        params["domainCrc"] = "3891665734"
        params["gameId"] = 0
        params["link"] = "https://www.9gate.net/social"
        params["linkCrc"] = "3891665734"
        params["parentId"] = 0
        params["photos"] = mPhotos
        params["title"] = "Tao new post"

        mediaService.updatePostForum(params).enqueue(object: Callback<ResponseBody>{
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Timber.d("")
                hideProgress()
            }

            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                hideProgress()
                if (response.isSuccessful && response.code() == 200) {
                    navigation?.returnResult(Bundle())
                }
            }
        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == Config.REQUEST_CODE_CHOOSE_IMAGE_FROM_CREATE_POST
                && resultCode == RESULT_OK
                && null != data) {
            val unwrapContext = context ?: return
            data.data?.let {
                val path = FileUtils.getPath(unwrapContext, it) ?: return
                mImage = File(path)
                mAdapter?.addData(path)
                if (imageRecyclerView?.visibility == View.GONE) {
                    imageRecyclerView?.visibility = View.VISIBLE
                }
                val reqFile = RequestBody.create(MediaType.parse("image/*"), mImage)
                val body = MultipartBody.Part.createFormData("files", mImage!!.name, reqFile)
                mMedia.add(body)
            }
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    private fun uploadImage() {
        mediaService.upload(mMedia).enqueue(object: Callback<List<String>> {
            override fun onFailure(call: Call<List<String>>, t: Throwable) {
                hideProgress()
                Timber.d("loi")
            }

            override fun onResponse(call: Call<List<String>>, response: Response<List<String>>) {
                Timber.d("thanh cong ne")
                if (response.isSuccessful
                        && response.code() == 200
                        && response.body() != null) {
                    mPhotos.addAll(response.body()!!)
                    postForum()
                } else {
                    hideProgress()
                }
            }
        })
    }


    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (grantResults.firstOrNull() != PackageManager.PERMISSION_GRANTED) return
        when (requestCode) {
            REQUEST_CODE_READ_EXTERNAL_STORAGE -> openSelectedImageGallery()
        }
    }

    private fun openSelectedImageGallery() {
        val intent = Intent()
        intent.type = "image/*"
//        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
        intent.action = Intent.ACTION_GET_CONTENT
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1) {
            startActivityForResult(Intent.createChooser(intent,"Select picture"), Config.REQUEST_CODE_CHOOSE_IMAGE_FROM_CREATE_POST)
        } else {
            startActivityForResult(intent, Config.REQUEST_CODE_CHOOSE_IMAGE_FROM_CREATE_POST)
        }
    }
}