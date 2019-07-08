package com.rahulpundhir.myliv.circularimageloader

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Button
import android.widget.Toast
import com.rahulpundhir.library.CircularImageView

class MainActivity : AppCompatActivity(), ImageUrlTask.ImageUrlListener {


    val IMAGE_LOAD_URL = "https://api.thecatapi.com/v1/images/search"
    var imageView: CircularImageView? = null
    var button: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        imageView = findViewById(R.id.circular_image_view)
        button = findViewById(R.id.change_image_button)


        button?.setOnClickListener {
            ImageUrlTask(this).execute(IMAGE_LOAD_URL)
        }
    }

    override fun onImageReceived(url: String) {
        imageView?.setImage(object : CircularImageView.CircularImageListener {
            override fun onImageDownloadFailed() {
                Toast.makeText(this@MainActivity,
                        getString(R.string.message_on_image_failed), Toast.LENGTH_LONG).show()
            }

        }, url)
    }

    override fun onImageUrlFailed() {
        Toast.makeText(this@MainActivity,
                getString(R.string.message_on_image_failed), Toast.LENGTH_LONG).show()
    }
}