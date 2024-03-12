package com.example.blur.Screen

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController

@Composable
fun CameraScreen(
    navController: NavHostController
) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current

    var imageBitmap by remember { mutableStateOf<Bitmap?>(null) }

    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult(),
        onResult = { result ->
            if (result.resultCode == android.app.Activity.RESULT_OK) {
                val data: Intent? = result.data
                val bitmap = data?.extras?.get("data") as Bitmap?
                if (bitmap != null) {
                    imageBitmap = bitmap
                }
            }
        })

    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (imageBitmap == null) {
            // 이미지가 없는 경우 촬영 버튼 표시
            ElevatedButton(
                onClick = {
                    dispatchTakePictureIntent(cameraLauncher, context)
                },
                modifier = Modifier.padding(16.dp)
            ) {
                Text("Take a Picture")
            }
        } else {
            // 이미지가 있는 경우 이미지 표시
            Image(
                bitmap = imageBitmap!!.asImageBitmap(),
                contentDescription = null,
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}

private fun dispatchTakePictureIntent(cameraLauncher: ActivityResultLauncher<Intent>, context: Context) {
    val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
    if (takePictureIntent.resolveActivity(context.packageManager) != null) {
        cameraLauncher.launch(takePictureIntent)
    }
}

@Preview
@Composable
fun CameraScreenPreview(){
    CameraScreen(rememberNavController())
}
