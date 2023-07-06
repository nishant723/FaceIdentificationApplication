package nishant.lab.faceidentificationapplication.presentation.util

sealed class Screen(val route : String){
    object HomeScreen : Screen ("home_screen")
    object FaceRegister: Screen("face_register")
    object FaceSelectore: Screen("face_selectore")

}

