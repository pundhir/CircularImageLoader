package com.rahulpundhir.library

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Environment
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream


object ImageStorage {

    fun saveImageBitmap(bitmap: Bitmap, fileName: String) {
        if (isExternalStorageWritable()) {
            saveImage(bitmap, fileName)
        }
    }

    private fun saveImage(finalBitmap: Bitmap, fileName: String) {
        val root = Environment.getExternalStorageDirectory().toString()
        val myDir = File(root + "/download")
        myDir.mkdirs()

        val file = File(myDir, fileName)
        if (file.exists()) file.delete()
        file.createNewFile()
        try {
            val out = FileOutputStream(file)
            finalBitmap.compress(Bitmap.CompressFormat.JPEG, 100, out)
            out.flush()
            out.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun readImageFromStorage(fileName: String): Bitmap? {
        var bitmap: Bitmap? = null
        val root = Environment.getExternalStorageDirectory().toString()
        val directory = File(root + "/download")
        val file = File(directory, fileName) //or any other format supported

        if (file.exists()) {
            val streamIn = FileInputStream(file)
            bitmap = BitmapFactory.decodeStream(streamIn) //This gets the image
            streamIn.close()
        }
        return bitmap
    }

    /* Checks if external storage is available for read and write */
    private fun isExternalStorageWritable(): Boolean {
        val state = Environment.getExternalStorageState()
        return Environment.MEDIA_MOUNTED == state
    }
}