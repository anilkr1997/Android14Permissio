package com.mrappbuilder.android14permission

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatImageView
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
   lateinit var image:AppCompatImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        image=findViewById(R.id.img_selected)
this.findViewById<AppCompatButton>(R.id.selected).setOnClickListener {

    launchNewPhotoPicker()
}




    }
    private fun launchNewPhotoPicker(){
        newPiker.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
    }







    val newPiker=registerForActivityResult(ActivityResultContracts.PickVisualMedia()){ uri ->
        if (uri != null) {
image.setImageURI(uri)
            Log.d("PhotoPicker", "Selected URI: ${uri}")
        } else {
            Log.d("PhotoPicker", "No media selected")
        }



    }

}


