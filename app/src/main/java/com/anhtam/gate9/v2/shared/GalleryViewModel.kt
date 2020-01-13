package com.anhtam.gate9.v2.shared

import android.app.Application
import android.database.Cursor
import android.net.Uri
import android.provider.MediaStore
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class GalleryViewModel @Inject constructor(private val application: Application) : ViewModel(), CoroutineScope{
    private val job = Job()
    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.Main

    private val imagesLiveData = MutableLiveData<List<String>>()
    fun getImages(): LiveData<List<String>> = imagesLiveData

    private fun loadImagesFromSdCard(): List<String>{

        val projection = arrayOf(MediaStore.Images.Media.BUCKET_DISPLAY_NAME, MediaStore.Images.Media.DATA)

        val cursor = application.contentResolver.query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                projection,
                null,
                null,
                MediaStore.Images.Media.DATE_ADDED)
                ?: return emptyList()

        val bucketNamesTemp = ArrayList<String>(cursor.count)
        val bitmapList = ArrayList<String>(cursor.count)
        val albumSet = hashSetOf<String>()
        var file: File
        if (cursor.moveToLast()){
            do {
                if (Thread.interrupted()) return emptyList()
                val album = cursor.getString(cursor.getColumnIndexOrThrow(projection[0]))
                val image = cursor.getString(cursor.getColumnIndexOrThrow(projection[1]))
                file = File(image)
                if (file.exists() && !albumSet.contains(album)){
                    bucketNamesTemp.add(album)
                    bitmapList.add(image)
                    albumSet.add(album)
                }
            } while (cursor.moveToPrevious())
        }
        bucketNamesTemp.forEach{
            getPicturesBucket(it)
        }
        cursor.close()
        return emptyList()
    }

    private fun getPicturesBucket(bucket: String){
        val projection = arrayOf(MediaStore.Images.Media.DISPLAY_NAME, MediaStore.Images.Media.DATA)
        val cursor = application.contentResolver.query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                projection,
                MediaStore.Images.Media.BUCKET_DISPLAY_NAME+" =?",
                arrayOf(bucket),
                MediaStore.Images.Media.DATE_ADDED)
                ?: return
        val images = ArrayList<String>(cursor.count)
        val albumSet = hashSetOf<String>()
        var file: File
        if (cursor.moveToLast()){
            do {
                if (Thread.interrupted()) return
                val path = cursor.getString(cursor.getColumnIndex(projection[1]))
                file = File(path)
                if (file.exists() && !albumSet.contains(path)){
                    images.add(path)
                    albumSet.add(path)
                }
            } while (cursor.moveToPrevious())
        }
        cursor.close()
    }

    fun getAllImages(){
        launch(Dispatchers.Main){
            imagesLiveData.value = with(Dispatchers.IO){
                loadImagesFromSdCard()
            }
        }
    }
}