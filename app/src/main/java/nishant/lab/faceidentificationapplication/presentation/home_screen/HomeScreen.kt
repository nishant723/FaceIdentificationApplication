package nishant.lab.faceidentificationapplication.presentation.home_screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import nishant.lab.faceidentificationapplication.presentation.util.Screen

@Composable
fun  HomeScreen (navController: NavController) {

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(
            onClick = { navController.navigate(Screen.FaceRegister.route) },
            modifier = Modifier.padding(16.dp)
        ) {
            Text(text = "Face Register")
        }
        Button(
            onClick = { navController.navigate(Screen.FaceSelectore.route) },
            modifier = Modifier.padding(16.dp)
        ) {
            Text(text = "Face Identifier")
        }
    }
}