package nishant.lab.faceidentificationapplication.presentation.util


import android.annotation.SuppressLint
import android.graphics.RectF
import android.util.Log
import android.util.SparseIntArray
import android.view.Surface
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.face.FaceDetection
import com.google.mlkit.vision.face.FaceDetector
import com.google.mlkit.vision.face.FaceDetectorOptions
import nishant.lab.faceidentificationapplication.domain.model.FaceAnalysisResult


class FaceAnalyzer : ImageAnalysis.Analyzer {
    private val TAG = "FaceAnalyzer"
    private val faceDetector: FaceDetector

    init {
        // Create the face detection options
        val options = FaceDetectorOptions.Builder()
            .setPerformanceMode(FaceDetectorOptions.PERFORMANCE_MODE_ACCURATE)
            .setLandmarkMode(FaceDetectorOptions.LANDMARK_MODE_ALL)
            // .setContourMode(FaceDetectorOptions.CONTOUR_MODE_ALL)
            .setClassificationMode(FaceDetectorOptions.CLASSIFICATION_MODE_ALL)
            .build()
        faceDetector = FaceDetection.getClient(options)

    }

    var detectedFaceRect: List<RectF> = emptyList()

    @SuppressLint("UnsafeOptInUsageError")
    override fun analyze(image: ImageProxy) {

        val mediaImage = image.image ?: return
        val rotationDegrees = image.imageInfo.rotationDegrees
        val inputImage = InputImage.fromMediaImage(mediaImage, rotationDegrees)
        val yawThreshold = 7.0f // Adjust the threshold as needed
        if (inputImage != null) {
            faceDetector.process(inputImage)
                .addOnSuccessListener { faces ->
                    if(faces.size>=1){

                        detectedFaceRect = faces.map { face ->

                            val boundingBox = face.boundingBox
                            val left = boundingBox.left.toFloat()
                            val top = boundingBox.top.toFloat()
                            val right = boundingBox.right.toFloat()
                            val bottom = boundingBox.bottom.toFloat()
                            RectF(left, top, right, bottom)
                        }
                        Log.d(TAG, "faceAnalyzeClass: $detectedFaceRect")

                    }
                }
                .addOnFailureListener { exception ->
                    Log.e(TAG, "Face analysis failed: ${exception.message}", exception)
                }
                .addOnCompleteListener {
                    image.close()
                }
        }
    }


}
