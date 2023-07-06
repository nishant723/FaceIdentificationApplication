package nishant.lab.faceidentificationapplication.presentation.util

import android.content.ContentValues.TAG
import android.util.Log
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageCapture
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView

import java.util.concurrent.Executors

@Composable
fun   CameraPreview(){

    val context = LocalContext.current
    val lifecycleOwner = remember { CustomLifecycleOwner() }

    val cameraProviderFuture = remember { ProcessCameraProvider.getInstance(context) }
    val imageCapture = remember { ImageCapture.Builder().build() }

    val hasFace = remember { mutableStateOf(true) }

    Box(modifier = Modifier.fillMaxWidth().height(700.dp)) {
        AndroidView(
            modifier = Modifier.fillMaxSize(),
            factory = { ctx ->
                val cameraProvider = cameraProviderFuture.get()
                val cameraSelector = CameraSelector.DEFAULT_FRONT_CAMERA
                val view = androidx.camera.view.PreviewView(ctx).apply {
                    implementationMode = androidx.camera.view.PreviewView.ImplementationMode.COMPATIBLE
                    val preview = androidx.camera.core.Preview.Builder().build()
                    val imageAnalyzer = ImageAnalysis.Builder().build()

                    preview.setSurfaceProvider(surfaceProvider)
                    imageAnalyzer.setAnalyzer(Executors.newSingleThreadExecutor()) { imageProxy ->
                        // analyzeImage(imageProxy, hasFace)
                    }

                    try {
                        cameraProvider.unbindAll()
                        cameraProvider.bindToLifecycle(lifecycleOwner, cameraSelector, preview, imageCapture, imageAnalyzer)
                    } catch (exception: Exception) {
                        Log.e(TAG, "Use case binding failed: ${exception.message}")
                    }
                }

                view
            },
            update = { view ->
                // Update camera view if needed
            }
        )

        if (!hasFace.value) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
              //  DrawRedCircle()
            }
        }
    }
}