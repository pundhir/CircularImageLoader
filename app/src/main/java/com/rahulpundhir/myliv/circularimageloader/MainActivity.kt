package com.rahulpundhir.myliv.circularimageloader

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.widget.Button
import android.widget.Toast
import com.rahulpundhir.library.CircularImageView

class MainActivity : AppCompatActivity(), ImageUrlTask.ImageUrlListener {

    val IMAGE_LOAD_URL = "https://api.thecatapi.com/v1/images/search"
    val REQUEST_CODE_WRITE_EXTERNAL_STORAGE_PERMISSION = 999
    var imageView: CircularImageView? = null
    var button: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        imageView = findViewById(R.id.circular_image_view)
        button = findViewById(R.id.change_image_button)
        requestWriteStoragePermission();

        button?.setOnClickListener {
            ImageUrlTask(this).execute(IMAGE_LOAD_URL)
        }

        /*  TODO - Code to remove once we have proper unit tests with mocked environment to test bitmap storage.
         val icon = BitmapFactory.decodeResource(resources, R.drawable.cat)
         ImageStorage.saveImageBitmap(icon, "cat12345.jpg")
         ImageStorage.readImageFromStorage("cat12345.jpg")
        */
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

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CODE_WRITE_EXTERNAL_STORAGE_PERMISSION) {
            val grantResultsLength = grantResults.size
            if (grantResultsLength > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(applicationContext, "You have granted write external storage permission.",
                        Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(applicationContext, "You haved denied write external storage permission.", Toast.LENGTH_LONG).show()
            }
        }
    }

    fun requestWriteStoragePermission() {
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            // Check whether this app has write external storage permission or not.
            val writeExternalStoragePermission = ContextCompat.checkSelfPermission(this@MainActivity,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)
            // If do not grant write external storage permission.
            if (writeExternalStoragePermission != PackageManager.PERMISSION_GRANTED) {
                // Request user to grant write external storage permission.
                ActivityCompat.requestPermissions(this@MainActivity, arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                        REQUEST_CODE_WRITE_EXTERNAL_STORAGE_PERMISSION)
            }
        }
    }
}