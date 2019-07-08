package com.rahulpundhir.myliv.circularimageloader

import android.os.AsyncTask

class ImageUrlTask(var listener: ImageUrlListener) : AsyncTask<String, Int, String>() {

    override fun doInBackground(vararg params: String): String? {
        return NetworkRequest.getImageUrl(params[0])
    }

    override fun onPostExecute(url: String?) {
        if (null != url) {
            listener.onImageReceived(url)
        } else {
            listener.onImageUrlFailed()
        }
    }

    interface ImageUrlListener {
        fun onImageReceived(url: String)
        fun onImageUrlFailed()
    }
}
