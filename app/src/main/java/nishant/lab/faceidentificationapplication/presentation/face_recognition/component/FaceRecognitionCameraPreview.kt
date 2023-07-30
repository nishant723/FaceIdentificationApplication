package nishant.lab.faceidentificationapplication.presentation.face_recognition.component

import android.annotation.SuppressLint
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.camera.core.*
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
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
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import nishant.lab.faceidentificationapplication.presentation.face_detection.FaceDetectionViewModel
import nishant.lab.faceidentificationapplication.presentation.face_recognition.FaceRecognitionViewModel
import nishant.lab.faceidentificationapplication.presentation.util.CustomLifecycleOwner
import java.util.concurrent.Executors

@RequiresApi(Build.VERSION_CODES.O)
@SuppressLint("RestrictedApi")
@Composable
fun FaceRecognitionCameraPreview( registeringFace: FaceRecognitionViewModel
                                  , onImageCapture: (ImageProxy)->Unit) {
    val context = LocalContext.current
    val lifecycleOwner = remember { CustomLifecycleOwner() }
    val cameraProviderFuture = remember { ProcessCameraProvider.getInstance(context) }
    val imageCapture = remember { ImageCapture.Builder().build() }
    val hasFace = remember { mutableStateOf(true) }
    var cameraPreviewHeight by remember { mutableStateOf(0) }
    var cameraPreviewWith by remember { mutableStateOf(0) }

    Box(modifier = Modifier.clip(CircleShape)
        .size(310.dp)
        //.background(color = Color.Red)
        .wrapContentSize(Alignment.Center)){

        Box(
            modifier = Modifier
                .clip(CircleShape)
                .size(300.dp)
                .wrapContentSize(Alignment.Center)
        ) {
            AndroidView(
                modifier = Modifier
                    .fillMaxSize()
                    .onSizeChanged { size ->
                        cameraPreviewHeight = size.height
                        cameraPreviewWith = size.width
                    },
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
                        registeringFace.analyzeFace(imageProxy)

                        // imageProxy.close()
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

                    //val lifecycleOwner = LocalLifecycleOwner.current
                    view
                },
                update = { view ->

                }
            )



            /*  if(faceError!=null){
                  if(toastMessage == true){
                      Toast.makeText(context, toastMessage.toString(), Toast.LENGTH_SHORT).show()
                  }else{
                      Toast.makeText(context, toastMessage.toString(), Toast.LENGTH_SHORT).show()
                  }
              }else{
                  Toast.makeText(context, faceError, Toast.LENGTH_SHORT).show()
              }*/
        }

    }






}