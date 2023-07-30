package nishant.lab.faceidentificationapplication.presentation.util

sealed class Screen(val route : String){
    object RegistrationScreen : Screen ("registration_screen")
    object FaceDetectionScreen: Screen("face_register")
    object FaceRecognitionScreen: Screen("face_selectore")

}

