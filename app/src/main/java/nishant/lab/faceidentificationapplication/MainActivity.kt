package nishant.lab.faceidentificationapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint

import nishant.lab.faceidentificationapplication.presentation.face_identifier.FaceSelectore
import nishant.lab.faceidentificationapplication.presentation.face_register.FaceRegister
import nishant.lab.faceidentificationapplication.presentation.home_screen.HomeScreen
import nishant.lab.faceidentificationapplication.presentation.util.Screen
import nishant.lab.faceidentificationapplication.ui.theme.FaceIdentificationApplicationTheme
@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FaceIdentificationApplicationTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                   val navController = rememberNavController()
                    NavHost(navController = navController, startDestination = Screen.HomeScreen.route){
                        composable(route = Screen.HomeScreen.route){
                            HomeScreen(navController = navController)
                        }
                     composable(route = Screen.FaceSelectore.route){
                         FaceSelectore(navController = navController)
                     }

                        composable(route = Screen.FaceRegister.route){
                            FaceRegister(navController = navController)
                        }


                    }
                }
            }
        }
    }
}

/*
@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    FaceIdentificationApplicationTheme {
        Greeting("Android")
    }
}*/
