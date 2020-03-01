package com.anhtam.gate9.v2.shared.muilti_gallery

import android.app.Application
import android.provider.MediaStore
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.anhtam.domain.v2.Folder
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

    private val imagesLiveData = MediatorLiveData<List<Folder>>()
    private val mImage = MutableLiveData<List<String>>()

    fun getFolders(): LiveData<List<Folder>> = imagesLiveData
    fun getImages(): LiveData<List<String>> = mImage

    private fun loadImagesFromSdCard(): List<Folder>{
        val projection = arrayOf(MediaStore.Images.Media.BUCKET_DISPLAY_NAME, MediaStore.Images.Media.DATA)

        val cursor = application.contentResolver.query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                projection,
                null,
                null,
                MediaStore.Images.Media.DATE_ADDED)
                ?: return emptyList()

        val folders = ArrayList<Folder>(cursor.count)
        val albumSet = hashSetOf<String>()
        var file: File
        if (cursor.moveToLast()){
            do {
                if (Thread.interrupted()) return emptyList()
                val album = cursor.getString(cursor.getColumnIndexOrThrow(projection[0]))
                val image = cursor.getString(cursor.getColumnIndexOrThrow(projection[1]))
                file = File(image)
                if (file.exists() && !albumSet.contains(album)){
                    val folder = Folder(album, image)
                    folders.add(folder)
                    albumSet.add(album)
                }
            } while (cursor.moveToPrevious())
        }
        cursor.close()
        return folders
    }

    private fun getPicturesBucket(bucket: String): List<String>{
        val projection = arrayOf(MediaStore.Images.Media.DISPLAY_NAME, MediaStore.Images.Media.DATA)
        val cursor = application.contentResolver.query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                projection,
                MediaStore.Images.Media.BUCKET_DISPLAY_NAME +" =?",
                arrayOf(bucket),
                MediaStore.Images.Media.DATE_ADDED)
                ?: return emptyList()
        val images = ArrayList<String>(cursor.count)
        val albumSet = hashSetOf<String>()
        var file: File
        if (cursor.moveToLast()){
            do {
                if (Thread.interrupted()) return emptyList()
                val path = cursor.getString(cursor.getColumnIndex(projection[1]))
                file = File(path)
                if (file.exists() && !albumSet.contains(path)){
                    images.add(path)
                    albumSet.add(path)
                }
            } while (cursor.moveToPrevious())
        }
        cursor.close()
        return images
    }

    fun getAllImages(){
        launch(coroutineContext){
            imagesLiveData.value = with(Dispatchers.IO){
                loadImagesFromSdCard()
            }
        }
    }

    fun getImageByFolder(folder: Folder){
        launch(coroutineContext) {
            mImage.value = with(Dispatchers.IO){
                getPicturesBucket(folder.bucketDisplayName)
            }
        }
    }
}