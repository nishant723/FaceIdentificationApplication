package nishant.lab.faceidentificationapplication.presentation.face_recognition.component

import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.graphics.Canvas

import android.graphics.Paint
import android.graphics.Rect
import android.graphics.RectF
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.camera.core.*
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.view.isNotEmpty
import nishant.lab.faceidentificationapplication.domain.model.FaceMatchingStatus
import nishant.lab.faceidentificationapplication.presentation.face_detection.FaceDetectionViewModel
import nishant.lab.faceidentificationapplication.presentation.face_recognition.FaceRecognitionViewModel
import nishant.lab.faceidentificationapplication.presentation.util.CustomLifecycleOwner
import nishant.lab.faceidentificationapplication.presentation.util.FaceAnalyzer
import java.util.concurrent.Executors



@SuppressLint("RestrictedApi")
@Composable
fun FaceRecognitionCameraPreview(faceRecognitionViewModel: FaceRecognitionViewModel
                                 , faceMatchingStatus: FaceMatchingStatus
) {
    val context = LocalContext.current
    val lifecycleOwner = remember { CustomLifecycleOwner() }
    val cameraProviderFuture = remember { ProcessCameraProvider.getInstance(context) }
    var cameraPreviewHeight by remember { mutableStateOf(0) }
    var cameraPreviewWith by remember { mutableStateOf(0) }
    val view = remember { PreviewView(context) }



    Box(modifier = Modifier
        .clip(CircleShape)
        .size(310.dp)
        .background(SetBackGroundColour(faceMatchingStatus = faceMatchingStatus))
        .wrapContentSize(Alignment.Center)){

        Box(
            modifier = Modifier
                .clip(CircleShape)
                .size(300.dp)
                .wrapContentSize(Alignment.Center)
        ) {

            DisposableEffect(cameraProviderFuture, lifecycleOwner) {
                val cameraProvider = cameraProviderFuture.get()

                val cameraSelector = CameraSelector.DEFAULT_FRONT_CAMERA
                val preview = Preview.Builder().build()
                val imageAnalyzer = ImageAnalysis.Builder().build()

                preview.setSurfaceProvider(view.surfaceProvider)
                imageAnalyzer.setAnalyzer(
                    Executors.newSingleThreadExecutor()
                ) { imageProxy ->
                    faceRecognitionViewModel.analyzeFace(imageProxy)
                }

                cameraProvider?.let {
                    cameraProvider.bindToLifecycle(
                        lifecycleOwner,
                        cameraSelector,
                        preview,
                        imageAnalyzer
                    )
                }

                onDispose {
                    // Unbind camera use cases when the Composable is removed from the UI
                    cameraProvider?.unbindAll()
                }
            }

            AndroidView(
                modifier = Modifier.fillMaxSize()
                    .onSizeChanged { size ->
                        cameraPreviewHeight = size.height
                        cameraPreviewWith = size.width
                    },
                factory = { view },
                update = { view -> }
            )




        }

    }






}