package com.anhtam.gate9.v2.shared.gallery

import android.content.Context
import android.os.Bundle
import android.os.Environment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.anhtam.gate9.R
import java.io.File
import java.io.FileFilter



class CustomGalleryFragment : Fragment(){
    companion object{
        fun newInstance() = CustomGalleryFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.activity_test, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setGridAdapter(Environment.getExternalStorageDirectory().absolutePath)
    }

    private fun setGridAdapter(path: String){
        createGridItems(path)
    }

    private fun createGridItems(directoryPath: String){
        val rootFile = context?.filesDir
        val dir = context?.getDir("abc", Context.MODE_PRIVATE)
        val cachDir = context?.cacheDir
        val rootExternal = context?.getExternalFilesDir("images/")
        val files = File(directoryPath).listFiles(ImageFileFilter())
        val file = File(Environment.getExternalStorageDirectory().absolutePath)
        val x = getFile(file)
    }

    private class ImageFileFilter : FileFilter{
        override fun accept(file: File?): Boolean {
            if (file == null) return false
            if (file.isDirectory) return true
            else if (isImageFile(file.absolutePath)){
                return true
            }
            return false
        }

        private fun isImageFile(filePath: String): Boolean{
            if (filePath.endsWith(".jpg") || filePath.endsWith(".png")){
                return true
            }
            return false
        }
    }

    fun getFile(dir: File): ArrayList<String> {
        val fileList = arrayListOf<String>()
        val listFile = dir.listFiles()
        if (listFile != null && listFile.isNotEmpty()) {
            for (file in listFile) {
                if (file.isDirectory) {
                    getFile(file)
                } else {
                    if (file.name.endsWith(".png")
                            || file.name.endsWith(".jpg")
                            || file.name.endsWith(".jpeg")
                            || file.name.endsWith(".gif")
                            || file.name.endsWith(".bmp")
                            || file.name.endsWith(".webp")) {
                        val temp = file.path.substring(0, file.path.lastIndexOf('/'))
                        if (!fileList.contains(temp))
                            fileList.add(temp)
                    }
                }
            }
        }
        return fileList
    }
}