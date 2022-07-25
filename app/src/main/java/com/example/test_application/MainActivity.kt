package com.example.test_application

import android.Manifest
import android.app.Activity
import android.content.ContentValues
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.SystemClock
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Chronometer
import android.widget.ImageView
import android.widget.Toast

private const val REQUEST_CODE = 42
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // hello world button
        val myHelloBtn = findViewById<Button>(R.id.helloBtn)
        myHelloBtn.setOnClickListener {
            Log.v("hello World", "btn clicked!!")   // log.v is logcat->Verbose
            Toast.makeText(this, "Hello from Quan", Toast.LENGTH_SHORT).show()
        }

        // capture btn
        val myTakePicBtn = findViewById<Button>(R.id.btnTakePicture)
        myTakePicBtn.setOnClickListener {
            Log.v("capture_btn", "capture btn clicked!!")   // log.v is logcat->Verbose
            val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)

            if (takePictureIntent.resolveActivity(this.packageManager) != null){
                startActivityForResult(takePictureIntent, REQUEST_CODE)
            }
            else {
                Toast.makeText(this, "Unable to open camera", Toast.LENGTH_SHORT).show()
            }
        }

        // Chronometer
        val myChronometer2 = findViewById<Chronometer>(R.id.chronometer2)
        val myStartBtn = findViewById<Button>(R.id.start_btn)
        val myStopBtn = findViewById<Button>(R.id.stop_btn)
        val myResetBtn = findViewById<Button>(R.id.reset_btn)
        var stoptime: Long=0
        myStartBtn.setOnClickListener {
            myChronometer2.base = SystemClock.elapsedRealtime() + stoptime
            myChronometer2.start()
            myStartBtn.visibility = View.GONE
            myStopBtn.visibility = View.VISIBLE
        }
        myStopBtn.setOnClickListener {
            stoptime = myChronometer2.base - SystemClock.elapsedRealtime()
            myChronometer2.stop()
            myStopBtn.visibility = View.GONE
            myStartBtn.visibility = View.VISIBLE
        }
        myResetBtn.setOnClickListener {
            myChronometer2.base = SystemClock.elapsedRealtime()
            stoptime = 0
            myChronometer2.stop()
            myStopBtn.visibility = View.GONE
            myStartBtn.visibility = View.VISIBLE
        }
    }




    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == REQUEST_CODE && resultCode == Activity.RESULT_OK){
            val takenImage = data?.extras?.get("data") as Bitmap
            val myCapturedImageView = findViewById<ImageView>(R.id.capturedImageView)
            myCapturedImageView.setImageBitmap(takenImage)
        }
        else {
            super.onActivityResult(requestCode, resultCode, data)
        }

    }

}