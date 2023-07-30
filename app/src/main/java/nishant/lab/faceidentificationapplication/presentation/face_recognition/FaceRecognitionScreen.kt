package nishant.lab.faceidentificationapplication.presentation.face_recognition

import android.graphics.Bitmap
import android.os.Build
import androidx.compose.ui.graphics.Color
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight.Companion.Black
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import nishant.lab.faceidentificationapplication.presentation.face_recognition.component.FaceRecognitionCameraPreview

import nishant.lab.faceidentificationapplication.presentation.util.Screen

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun FaceRecognitionScreen(navController: NavHostController,faceIdentifierViewModel: FaceRecognitionViewModel = hiltViewModel()) {


    val state = faceIdentifierViewModel.faceResult.value
    Box (modifier = Modifier.fillMaxSize()){
        Box(
            Modifier
                .align(Alignment.Center)
                //.clip(CircleShape)
                //.size(300.dp)
        ){

            FaceRecognitionCameraPreview(faceIdentifierViewModel){}
        }
        Column(modifier = Modifier
            .align(Alignment.BottomCenter)
            .padding(16.dp)) {
            state.imageString?.let {
                println(it)
                if(it!=null){
                    Text(text = it)
                }

            }
            state.error?.let {
                if(it.isNotEmpty()){
                    println(it)
                    Text(
                        text = it,
                        color = MaterialTheme.colors.error,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                            //.padding(horizontal = 10.dp)


                    )

                }
            }
            Button(modifier = Modifier.padding(10.dp),
                onClick = {
                    navController.navigate(Screen.FaceDetectionScreen.route)
                }

            ) {
                Text(text = "Face Register")
            }

        }

       /* if(state.isLoading){
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        }*/

        
        

       
    }
    }











