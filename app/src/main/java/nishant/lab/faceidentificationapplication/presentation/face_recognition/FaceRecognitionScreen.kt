package nishant.lab.faceidentificationapplication.presentation.face_recognition

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import nishant.lab.faceidentificationapplication.presentation.face_recognition.component.FaceRecognitionCameraPreview

import nishant.lab.faceidentificationapplication.presentation.util.Screen

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun FaceRecognitionScreen(navController: NavHostController,faceRecognitionViewModel: FaceRecognitionViewModel = hiltViewModel()) {
    val state = faceRecognitionViewModel.faceResult.value
    Box (modifier = Modifier.fillMaxSize()){
   /*     Box(
            Modifier
                // .fillMaxSize()
                .clip(CircleShape)
                .size(320.dp)
                .background(color = Color.Red)
                .align(Alignment.Center)

        ){*/
            Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
                Spacer(modifier = Modifier.size(16.dp))
                Column(modifier = Modifier.height(40.dp).fillMaxWidth()) {
                    state.imageString?.let {
                        println(it)
                        if(it!=null){
                            Text(text = it, textAlign = TextAlign.Center, modifier = Modifier.fillMaxWidth())
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

                }
                Spacer(modifier = Modifier.size(16.dp))
                FaceRecognitionCameraPreview(faceRecognitionViewModel,state)
                Spacer(modifier = Modifier.size(16.dp))
                if(state.isLoading){
                    CircularProgressIndicator(Modifier.size(20.dp))
                }
            }


       // }
        Column(modifier = Modifier
            .align(Alignment.BottomCenter)
            .padding(16.dp)) {
        
            Button(modifier = Modifier.fillMaxWidth(),
                onClick = {
                    navController.navigate(Screen.FaceDetectionScreen.route)
                }

            ) {
                Text(text = "Face Register")
            }

        }



        
        

       
    }
    }











