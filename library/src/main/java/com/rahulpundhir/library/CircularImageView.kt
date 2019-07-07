package com.rahulpundhir.library

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Handler
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory
import android.util.AttributeSet
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.RelativeLayout

class CircularImageView : RelativeLayout, ImageDownloaderTask.ImageDownloaderListener {

    private var imageView: ImageView? = null
    private var progressBar: ProgressBar? = null
    private var progressStatus: Int = 0
    internal val handler = Handler()
    private var listener: CircularImageListener? = null

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    override fun onFinishInflate() {
        super.onFinishInflate()
        View.inflate(context, R.layout.view_circular_image, this)
        imageView = findViewById(R.id.image_view_id)
        val icon = BitmapFactory.decodeResource(resources,
                R.mipmap.ic_placeholder_forground)
        setImageView(icon)
        progressBar = findViewById(R.id.progressbar)
        progressBar?.visibility = View.INVISIBLE
    }

    fun setImage(listener: CircularImageListener, url: String) {
        setListener(listener)
        showProgressBar()
        ImageDownloaderTask(this).execute(url)
    }

    override fun onImageDownloaded(bitmap: Bitmap) {
        setImageView(bitmap)
        hideProgressBar()
    }

    override fun onImageDownloadFailed() {
        hideProgressBar()
        listener?.onImageDownloadFailed()
    }

    private fun showProgressBar() {
        progressBar?.visibility = View.VISIBLE
        progressStatus = 0
        Thread(Runnable {
            while (progressStatus <= 100) {
                handler.post { progressBar?.progress = progressStatus }
                try {
                    Thread.sleep(20)
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                }

                progressStatus++
            }
        }).start()
    }

    private fun hideProgressBar() {
        progressBar?.visibility = View.INVISIBLE
    }

    private fun setImageView(bitmap: Bitmap) {
        val drawable = RoundedBitmapDrawableFactory.create(resources, bitmap)
        drawable.isCircular = true
        drawable.setAntiAlias(true)
        imageView?.setImageDrawable(drawable)
    }

    private fun setListener(listener: CircularImageListener) {
        this.listener = listener
    }

    interface CircularImageListener {
        fun onImageDownloadFailed()
    }
}
