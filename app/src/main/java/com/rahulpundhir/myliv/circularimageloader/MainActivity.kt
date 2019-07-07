package com.rahulpundhir.myliv.circularimageloader

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Button
import android.widget.Toast
import com.rahulpundhir.library.CircularImageView

class MainActivity : AppCompatActivity() {

    val IMAGE_LOAD_URL = "https://thecatapi.com/api/images/get?format=src&size=med&type=jpg,png"
    var imageView: CircularImageView? = null
    var button: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        imageView = findViewById(R.id.circular_image_view)
        button = findViewById(R.id.change_image_button)


        button?.setOnClickListener({
            imageView?.setImage(object : CircularImageView.CircularImageListener {
                override fun onImageDownloadFailed() {
                    Toast.makeText(this@MainActivity,
                            getString(R.string.message_on_image_failed), Toast.LENGTH_LONG).show()
                }

            }, IMAGE_LOAD_URL)
        })
    }
}