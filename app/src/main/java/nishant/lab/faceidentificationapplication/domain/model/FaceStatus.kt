package nishant.lab.faceidentificationapplication.domain.model

import android.graphics.Bitmap

data class FaceStatus(
    val bitMapImage: Bitmap? = null,
    val error: String = "",
    val isLoading : Boolean = false
)
