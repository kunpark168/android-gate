package com.anhtam.gate9.utils

import android.annotation.SuppressLint
import android.content.ContentUris
import android.content.Context
import android.database.Cursor
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.media.ExifInterface
import android.net.Uri
import android.os.AsyncTask
import android.os.Build
import android.os.Environment
import android.provider.DocumentsContract
import android.provider.MediaStore
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.OutputStream
import java.text.SimpleDateFormat
import java.util.*

class FileUtils {

    interface OnRotateImageFileListener {
        fun onFinish()
    }

    companion object {
        @Throws(IOException::class)
        fun convertBitmapToFile(bitmap: Bitmap): File {
            val file = createImageFile()
            val os: OutputStream

            os = FileOutputStream(file)
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, os)
            os.flush()
            os.close()
            return file
        }

        fun rotateImageFile(file: File, listener: OnRotateImageFileListener?) {
            AsyncTask.execute(Runnable {
                try {
                    val exifInterface = ExifInterface(file.absolutePath)
                    val orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_UNDEFINED)

                    val bmOptions = BitmapFactory.Options()
                    val bitmap = BitmapFactory.decodeFile(file.absolutePath, bmOptions)
                    val rotatedBitmap: Bitmap
                    when (orientation) {
                        ExifInterface.ORIENTATION_ROTATE_90 -> rotatedBitmap = rotateBitmap(bitmap, 90f)
                        ExifInterface.ORIENTATION_ROTATE_180 -> rotatedBitmap = rotateBitmap(bitmap, 180f)
                        ExifInterface.ORIENTATION_ROTATE_270 -> rotatedBitmap = rotateBitmap(bitmap, 270f)
                        ExifInterface.ORIENTATION_NORMAL -> {
                            listener?.onFinish()
                            return@Runnable
                        }
                        else -> {
                            listener?.onFinish()
                            return@Runnable
                        }
                    }

                    val fos = FileOutputStream(file.absolutePath)
                    rotatedBitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos)
                    fos.flush()
                    fos.close()
                    listener?.onFinish()
                } catch (e: IOException) {
                    e.printStackTrace()
                    listener!!.onFinish()
                }
            })
        }

        private fun rotateBitmap(bitmap: Bitmap, angle: Float): Bitmap {
            val matrix = Matrix()
            matrix.postRotate(angle)
            return Bitmap.createBitmap(bitmap, 0, 0, bitmap.width, bitmap.height, matrix, true)
        }

        @Throws(IOException::class)
        fun createImageFile(): File {
            val timeStamps = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
            val imgeFileName = "JPEG_" + timeStamps + "_"
            val storageDir = Environment.getExternalStorageDirectory()
            return File.createTempFile(imgeFileName, ".jpg", storageDir)
        }

        @SuppressLint("NewApi")
        fun getPath(context: Context, uri: Uri): String? {

            val isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT

            if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {

                if (isExternalStorageDocument(uri)) {
                    val docId = DocumentsContract.getDocumentId(uri)
                    val split = docId.split(":".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
                    val type = split[0]

                    if ("primary".equals(type, ignoreCase = true)) {
                        return (Environment.getExternalStorageDirectory().toString() + "/"
                                + split[1])
                    }
                } else if (isDownloadsDocument(uri)) {

                    val id = DocumentsContract.getDocumentId(uri)
                    val contentUri = ContentUris.withAppendedId(
                            Uri.parse("content://downloads/public_downloads"),
                            java.lang.Long.valueOf(id))

                    return getDataColumn(context, contentUri, null, null)
                } else if (isMediaDocument(uri)) {
                    val docId = DocumentsContract.getDocumentId(uri)
                    val split = docId.split(":".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
                    val type = split[0]

                    var contentUri: Uri? = null
                    if ("image" == type) {
                        contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                    } else if ("video" == type) {
                        contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI
                    } else if ("audio" == type) {
                        contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
                    }

                    val selection = "_id=?"
                    val selectionArgs = arrayOf(split[1])

                    return getDataColumn(context, contentUri, selection,
                            selectionArgs)
                }
            } else if ("content".equals(uri.scheme, ignoreCase = true)) {

                return if (isGooglePhotosUri(uri)) uri.lastPathSegment else getDataColumn(context, uri, null, null)

            } else if ("file".equals(uri.scheme, ignoreCase = true)) {
                return uri.path
            }

            return null
        }

        private fun getDataColumn(context: Context, uri: Uri?,
                                  selection: String?, selectionArgs: Array<String>?): String? {

            var cursor: Cursor? = null
            val column = "_data"
            val projection = arrayOf(column)

            try {
                cursor = context.contentResolver.query(uri!!, projection,
                        selection, selectionArgs, null)
                if (cursor != null && cursor.moveToFirst()) {
                    val index = cursor.getColumnIndexOrThrow(column)
                    return cursor.getString(index)
                }
            } finally {
                cursor?.close()
            }
            return null
        }

        private fun isExternalStorageDocument(uri: Uri): Boolean {
            return "com.android.externalstorage.documents" == uri
                    .authority
        }

        private fun isDownloadsDocument(uri: Uri): Boolean {
            return "com.android.providers.downloads.documents" == uri
                    .authority
        }

        private fun isMediaDocument(uri: Uri): Boolean {
            return "com.android.providers.media.documents" == uri
                    .authority
        }

        private fun isGooglePhotosUri(uri: Uri): Boolean {
            return "com.google.android.apps.photos.content" == uri
                    .authority
        }
    }

}
