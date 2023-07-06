package nishant.lab.faceidentificationapplication.presentation.face_identifier

import android.content.ContentValues.TAG
import android.graphics.Bitmap
import android.media.FaceDetector
import android.nfc.Tag
import android.util.Base64
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.launch
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageCapture
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.NavHostController
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import nishant.lab.faceidentificationapplication.presentation.face_identifier.component.FaceIdentifierViewModel
import nishant.lab.faceidentificationapplication.presentation.util.CameraPreview
import nishant.lab.faceidentificationapplication.presentation.util.CustomLifecycleOwner
import java.io.ByteArrayOutputStream
import java.util.concurrent.Executor
import java.util.concurrent.ExecutorService

@Composable
fun FaceSelectore(navController: NavHostController,faceIdentifierViewModel: FaceIdentifierViewModel = hiltViewModel()) {
    Box (modifier = Modifier.fillMaxSize()){
        CameraPreview()
    }

}









