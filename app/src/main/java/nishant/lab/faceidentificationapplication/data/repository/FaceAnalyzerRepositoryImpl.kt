package nishant.lab.faceidentificationapplication.data.repository

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.graphics.Matrix
import android.graphics.Rect
import android.util.Log
import androidx.camera.core.ImageProxy
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.face.Face
import com.google.mlkit.vision.face.FaceDetection
import com.google.mlkit.vision.face.FaceDetector
import com.google.mlkit.vision.face.FaceDetectorOptions
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import nishant.lab.faceidentificationapplication.domain.model.Resource

import nishant.lab.faceidentificationapplication.domain.repository.FaceAnalyzerRepository
import nishant.lab.faceidentificationapplication.presentation.util.ImageConverter
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine
import kotlin.math.abs


class FaceAnalyzerRepositoryImpl @Inject constructor(var  faceDetector: FaceDetector) : FaceAnalyzerRepository{

    private val TAG = "FaceAnalyzerRepositoryImpl"


    /*init {
        // Create the face detection options
        val options = FaceDetectorOptions.Builder()
            .setPerformanceMode(FaceDetectorOptions.PERFORMANCE_MODE_ACCURATE)
            .setLandmarkMode(FaceDetectorOptions.LANDMARK_MODE_ALL)
            .setClassificationMode(FaceDetectorOptions.CLASSIFICATION_MODE_ALL)
            .build()
        faceDetector = FaceDetection.getClient(options)
    }*/

    @SuppressLint("UnsafeOptInUsageError")
    override fun analyzeFace(imageProxy: ImageProxy): Flow<Resource<Bitmap>> = flow {
       val mediaImage = imageProxy.image ?: kotlin.run {
           emit(Resource.Error("No face available"))
           return@flow
       }
        val rotationDegree = imageProxy.imageInfo.rotationDegrees
        val inputImage = InputImage.fromMediaImage(mediaImage,rotationDegree)
        try {
            val faces = suspendCoroutine<List<Face>> { continuation ->
                faceDetector.process(inputImage)
                    .addOnSuccessListener { faces ->
                        continuation.resume(faces)
                    }
                    .addOnFailureListener { exception ->
                        continuation.resumeWithException(exception)
                    }
            }
            println("Coroutine started in thread in analyzer repository: ${Thread.currentThread().name}")
              emit(Resource.Loading<Bitmap>())
            if(faces.size>1) {
                println("face size : ${faces.size}")
                emit(Resource.Error<Bitmap>("Multiple face detected"))
            }else if (faces.isEmpty()){
                emit(Resource.Error<Bitmap>("No face detected"))
            }else if (!isFaceCentered(face = faces[0], image = imageProxy)){
                emit(Resource.Error<Bitmap>("Face is not in center"))
            }else {
                val bitmapResult = ImageConverter().toBitmap(image = imageProxy.image!!)
                if (bitmapResult == null) {
                    emit(Resource.Error<Bitmap>("Error in converting bitmap"))
                    return@flow
                }
                val boundingBox = faces[0].boundingBox
                if (boundingBox == null) {
                    emit(Resource.Error<Bitmap>("Bounding box is null"))
                    return@flow
                }
                if(boundingBox == null && bitmapResult == null && rotationDegree == null){
                    emit(Resource.Error<Bitmap>("Ensure entire face is within the circle"))
                }else{
                    val cropImage = cropToBBox(bitmapResult, boundingBox, rotationDegree)
                    if (cropImage == null) {
                        emit(Resource.Error<Bitmap>("Ensure entire face is within the circle"))
                        return@flow
                    }else {
                        emit(Resource.Success<Bitmap>(cropImage))
                    }
                }


            }
        }
        catch (e : Exception) {
            emit(Resource.Error<Bitmap>(e.message?:"An error occurred "))
        }

        catch (e : NullPointerException) {
            emit(Resource.Error<Bitmap>(e.message?:"An error occurred "))
        }catch (e : Throwable){
           emit(Resource.Error<Bitmap>(e.message?:"An error occurred "))
            println("error throwable ${e.message}")

        } finally {
                imageProxy.close()
            }

    } .flowOn(Dispatchers.IO)


    private fun isFaceCentered(face: Face, image: ImageProxy): Boolean {
        /*val frameCenterX = image.width / 2
        val frameCenterY = image.height / 2

        val faceCenterX = (face.boundingBox.left + face.boundingBox.right) / 2
        val faceCenterY = (face.boundingBox.top + face.boundingBox.bottom) / 2

        val offsetX = Math.abs(faceCenterX - frameCenterX)
        val offsetY = Math.abs(faceCenterY - frameCenterY)

        val thresholdX = 0.4f * image.width // Adjust the percentage or value as needed
        val thresholdY = 0.4f * image.height
        offsetX <= thresholdX && offsetY <= thresholdY*/
       // face.headEulerAngleY>1 && face.headEulerAngleZ>1
        return abs(face.headEulerAngleY) <=15 && abs(face.headEulerAngleZ) <=15 && abs(face.headEulerAngleX)<=15
    }

    private fun cropToBBox(image: Bitmap, boundingBox: Rect, rotation: Int): Bitmap? {
        var croppedImage = image
        try {
            if (rotation != 0) {
                val matrix = Matrix()
                matrix.postRotate(rotation.toFloat())
                croppedImage = Bitmap.createBitmap(
                    image,
                    0,
                    0,
                    image.width,
                    image.height,
                    matrix,
                    true
                )
            }

            if (boundingBox.top >= 0 && boundingBox.bottom <= croppedImage.height &&
                boundingBox.top + boundingBox.height() <= croppedImage.height &&
                boundingBox.left >= 0 && boundingBox.left + boundingBox.width() <= croppedImage.width
            ) {
                return Bitmap.createBitmap(
                    croppedImage,
                    boundingBox.left,
                    boundingBox.top,
                    boundingBox.width(),
                    boundingBox.height()
                )
            }
        } catch (e: Exception) {
            // Handle any exceptions that occur during the cropping process
            e.printStackTrace()
        }

        return null
    }


}