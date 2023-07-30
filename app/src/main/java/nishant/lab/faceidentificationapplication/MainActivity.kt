package nishant.lab.faceidentificationapplication

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.os.StrictMode
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import androidx.core.content.ContextCompat
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import nishant.lab.faceidentificationapplication.presentation.face_detection.FaceDetectionScreen


import nishant.lab.faceidentificationapplication.presentation.face_recognition.FaceRecognitionScreen

import nishant.lab.faceidentificationapplication.presentation.util.Screen
import nishant.lab.faceidentificationapplication.ui.theme.FaceIdentificationApplicationTheme


@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val CAMERA_PERMISSION_REQUEST_CODE = 101

    private val cameraPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                // Camera permission granted, do something here if needed
            } else {
                // Camera permission not granted, handle this case (e.g., show a message)
            }
        }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        if (BuildConfig.DEBUG) {
            StrictMode.enableDefaults();
        }
        super.onCreate(savedInstanceState)
        setContent {


         //   CoroutineScope(Dispatchers.Default).launch {
                // Initialize OpenCV asynchronously
         //       OpenCVLoader.initAsync(OpenCVLoader.OPENCV_VERSION_3_4_0, this@MainActivity, mLoaderCallback)
         //   }


            FaceIdentificationApplicationTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                   val navController = rememberNavController()
                    NavHost(navController = navController, startDestination = Screen.FaceRecognitionScreen.route){

                     composable(route = Screen.FaceRecognitionScreen.route){
                         FaceRecognitionScreen(navController = navController)
                     }

                        composable(route = Screen.FaceDetectionScreen.route){
                            FaceDetectionScreen(navController = navController)
                        }


                    }
                }
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            CAMERA_PERMISSION_REQUEST_CODE -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Camera permission granted, do something here if needed
                } else {
                    // Camera permission not granted, handle this case (e.g., show a message)
                }
            }
            // Handle other permission request codes here if needed
        }
    }

    // Function to check and request camera permission
    private fun checkAndRequestCameraPermission() {
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.CAMERA
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // Request camera permission from the user
            cameraPermissionLauncher.launch(Manifest.permission.CAMERA)
        } else {
            // Camera permission is already granted, do something here if needed
        }
    }

    // Call checkAndRequestCameraPermission in your onStart or onResume method
    override fun onStart() {
        super.onStart()
        checkAndRequestCameraPermission()
    }
}



/*
@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    FaceIdentificationApplicationTheme {
        Greeting("Android")
    }
}*/
