package nishant.lab.faceidentificationapplication.presentation.util

import android.app.Activity
import android.app.Application
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Environment
import android.provider.MediaStore
import androidx.activity.result.ActivityResultLauncher
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import java.io.File
import java.util.jar.Manifest
import javax.inject.Inject

class CameraHelper @Inject constructor(private val application: Application) {

    companion object {
        private const val CAMERA_PERMISSION_REQUEST_CODE = 100
        private const val IMAGE_CAPTURE_REQUEST_CODE = 101
    }

    private lateinit var imageCaptureLauncher: ActivityResultLauncher<Uri>
    private var currentImageUri: Uri? = null

    fun setActivityResultLauncher(launcher: ActivityResultLauncher<Uri>) {
        imageCaptureLauncher = launcher
    }

    fun hasCameraPermission() : Boolean {
        return ContextCompat.checkSelfPermission(application,
            android.Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED
    }

    fun requestCameraPermission(activity: Activity) {
        ActivityCompat.requestPermissions(
            activity,
            arrayOf(android.Manifest.permission.CAMERA),
            CAMERA_PERMISSION_REQUEST_CODE
        )
    }

    fun captureImageFromCamera(): Uri {
        val imageFileName = "face_image.jpg"
        val storageDir = application.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        val imageFile = File.createTempFile(imageFileName, null, storageDir)
        val imageUri = FileProvider.getUriForFile(
            application,
            "${application.packageName}.fileprovider",
            imageFile
        )

        val captureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        captureIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri)

        val packageManager = application.packageManager
        if (captureIntent.resolveActivity(packageManager) != null) {
            // Start the camera activity from the context
            imageCaptureLauncher.launch(imageUri)
        }

        return imageUri
    }

    fun convertImageToString(imageUri: Uri): String {
        // Convert the image file to a string representation (e.g., Base64 encoding)
        // Implement the conversion logic here
        return ""
    }
}