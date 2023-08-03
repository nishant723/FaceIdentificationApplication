package nishant.lab.faceidentificationapplication.presentation.face_recognition.component

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import nishant.lab.faceidentificationapplication.domain.model.FaceMatchingStatus

@Composable
fun SetBackGroundColour(faceMatchingStatus: FaceMatchingStatus) : Color{
    return when {
        faceMatchingStatus.isLoading -> Color.Gray
        faceMatchingStatus.error.isNotEmpty() -> Color.Red
        faceMatchingStatus.imageString != null -> Color.Green
        else -> Color.Transparent
    }

}