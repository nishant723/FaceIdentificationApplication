package nishant.lab.faceidentificationapplication.domain.model

data class FaceMatchingStatus(
    val imageString : String? = null,
    val error : String = "",
    val isLoading: Boolean = false
)
