package nishant.lab.faceidentificationapplication.presentation.util

import android.annotation.SuppressLint
import android.graphics.Rect
import android.util.Log
import androidx.camera.core.*
import androidx.camera.lifecycle.ProcessCameraProvider

import androidx.compose.foundation.layout.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.camera.view.PreviewView
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.*


import androidx.compose.ui.Alignment

import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.getValue
import kotlinx.coroutines.flow.observeOn


import nishant.lab.faceidentificationapplication.presentation.face_detection.FaceDetectionViewModel


import java.util.concurrent.Executors

@SuppressLint("RestrictedApi", "UnrememberedMutableState")
@Composable
fun FaceDetectionCameraPreview(
    registeringFace: FaceDetectionViewModel
    , onImageCapture: (ImageProxy)->Unit) {
    val context = LocalContext.current
    val lifecycleOwner = remember { CustomLifecycleOwner() }
    val cameraProviderFuture = remember { ProcessCameraProvider.getInstance(context) }
    var detectedFaceRect by remember {
        mutableStateOf<Rect?>(null)
    }


    Box(
        modifier = Modifier
            .clip(CircleShape)
            .size(300.dp)
            .wrapContentSize(Alignment.Center)
    ) {

        AndroidView(
            modifier = Modifier
                .fillMaxSize()
                .onSizeChanged { size -> },
            factory = { ctx ->
                val cameraProvider = cameraProviderFuture.get()
                val cameraSelector = CameraSelector.DEFAULT_FRONT_CAMERA
                val view = PreviewView(ctx)
                val preview = Preview.Builder().build()
                val imageAnalyzer = ImageAnalysis.Builder().build()

                preview.setSurfaceProvider(view.surfaceProvider)
                imageAnalyzer.setAnalyzer(
                    Executors.newSingleThreadExecutor()
                ) { imageProxy ->
                   // detectedFaceRect = registeringFace.analyzeFace(imageProxy = imageProxy)
                registeringFace.analyzeFace(imageProxy)
                }

                try {
                    cameraProvider.unbindAll()
                    cameraProvider.bindToLifecycle(
                        lifecycleOwner,
                        cameraSelector,
                        preview,
                        imageAnalyzer
                    )
                } catch (exception: Exception) {
                    Log.d(CameraXThreads.TAG, "Use case binding failed: ${exception.message}")
                }
                view
            },
            update = { view ->

            }
        )
    }



}







