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


  suspend fun analyzeFace(imageProxy : ImageProxy) : Result<Bitmap> {
       return when (val result = faceAnalyzerRepository.analyzeFace(imageProxy = imageProxy).first()){
           is Resource.Success -> Result.Success(result.data!!)
           is Resource.Error -> Result.Error(result.message?:"unknown error ")
          // is Resource.Loading -> Result.Loading("Loading")
       }

   }








}