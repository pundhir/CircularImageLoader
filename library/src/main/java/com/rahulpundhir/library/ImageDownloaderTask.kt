package com.rahulpundhir.library

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.AsyncTask
import java.net.URL

class ImageDownloaderTask(var listener: ImageDownloaderListener) : AsyncTask<String, Int, Bitmap>() {

    override fun doInBackground(vararg params: String): Bitmap? {
        return downloadImageBitmap(params[0])
    }

    override fun onPostExecute(bitmap: Bitmap?) {
        if (null != bitmap) {
            listener.onImageDownloaded(bitmap)
        } else {
            listener.onImageDownloadFailed()
        }
    }

    interface ImageDownloaderListener {
        fun onImageDownloaded(bitmap: Bitmap)
        fun onImageDownloadFailed()
    }

    /**
     * Downloading image from a given url.
     *
     * @param sUrl
     * @return
     */
    private fun downloadImageBitmap(sUrl: String): Bitmap? {
        var urlSplit = sUrl.split("/")
        var imageUrl = urlSplit.last()
        var bitmap: Bitmap? = ImageStorage.readImageFromStorage(imageUrl)
        if (bitmap == null) {
            try {
                val inputStream = URL(sUrl).openStream()   // Download Image from URL
                bitmap = BitmapFactory.decodeStream(inputStream)       // Decode Bitmap
                inputStream.close()
                ImageStorage.saveImageBitmap(bitmap, imageUrl)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        return bitmap
    }
}
