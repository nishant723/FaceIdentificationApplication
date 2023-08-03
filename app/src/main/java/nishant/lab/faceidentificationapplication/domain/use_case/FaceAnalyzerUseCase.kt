package nishant.lab.faceidentificationapplication.domain.use_case

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.graphics.Matrix
import android.graphics.Rect
import androidx.camera.core.ImageProxy
import com.google.mlkit.vision.face.Face
import kotlinx.coroutines.flow.*
import nishant.lab.faceidentificationapplication.domain.model.FaceAnalysisResult
import nishant.lab.faceidentificationapplication.domain.model.Resource
import nishant.lab.faceidentificationapplication.domain.repository.FaceAnalyzerRepository
import nishant.lab.faceidentificationapplication.presentation.util.ImageConverter
import java.io.IOException
import javax.inject.Inject
import nishant.lab.faceidentificationapplication.domain.model.Result

class FaceAnalyzerUseCase @Inject constructor(private val faceAnalyzerRepository: FaceAnalyzerRepository) {


    @SuppressLint("UnsafeOptInUsageError")
    suspend fun analyzeFace(imageProxy: ImageProxy): Resource<Bitmap> {
        return try {
            val resourceList = faceAnalyzerRepository.analyzeFace(imageProxy = imageProxy)
                .toList()

            if (resourceList.isEmpty()) {
                Resource.Error("No face detected")
            } else {
                // Get the last emitted element as it represents the latest result
                val lastResource = resourceList.last()
                when (lastResource) {
                    is Resource.Success -> Resource.Success(lastResource.data!!)
                    is Resource.Error -> Resource.Error(lastResource.message ?: "Unknown error")
                    is Resource.Loading -> Resource.Loading()
                }
            }
        } catch (e: Throwable) {
            Resource.Error(e.message ?: "An error occurred")
        }
    }













}