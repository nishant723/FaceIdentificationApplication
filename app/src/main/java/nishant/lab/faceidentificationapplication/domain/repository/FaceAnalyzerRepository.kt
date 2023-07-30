package nishant.lab.faceidentificationapplication.domain.repository

import android.graphics.Bitmap
import androidx.camera.core.ImageProxy
import com.google.mlkit.vision.face.Face
import kotlinx.coroutines.flow.Flow
import nishant.lab.faceidentificationapplication.domain.model.FaceAnalysisResult
import nishant.lab.faceidentificationapplication.domain.model.Resource

import javax.security.auth.callback.Callback

interface FaceAnalyzerRepository {
     fun analyzeFace(imageProxy : ImageProxy ) : Flow<Resource<Bitmap>>
}