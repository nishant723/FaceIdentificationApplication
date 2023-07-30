package nishant.lab.faceidentificationapplication.domain.model

import android.graphics.Bitmap
import androidx.camera.core.ImageProxy
import com.google.mlkit.vision.face.Face

data class FaceAnalysisResult (
    val imageBitMap : Bitmap? = null,
    val error : String = "",
    val isLoading: Boolean = false
)
