package nishant.lab.faceidentificationapplication.presentation.face_detection

import android.content.Context
import android.graphics.Bitmap
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.MutableLiveData
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import kotlinx.coroutines.launch

import nishant.lab.faceidentificationapplication.presentation.face_recognition.FaceRecognitionScreen
import nishant.lab.faceidentificationapplication.presentation.util.FaceDetectionCameraPreview
import nishant.lab.faceidentificationapplication.presentation.util.Screen


@Composable
fun FaceDetectionScreen(navController: NavHostController, viewModel: FaceDetectionViewModel = hiltViewModel()) {
    val textState = remember { mutableStateOf("") }
    val state = viewModel.faceResult.value
    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()
    var lazyValue : Bitmap? = null
    var intValue : Int = 0

    
    Scaffold(scaffoldState = scaffoldState) {
        Box(modifier = Modifier.fillMaxSize()){
            Column(modifier = Modifier
                .fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally
            )
            {
                Spacer(modifier = Modifier.height(16.dp))
                if(state.error.isNotEmpty()){
                    Text(
                        text = state.error,
                        color = MaterialTheme.colors.error,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 20.dp)


                    )

                }
                Spacer(modifier = Modifier.height(16.dp))
                FaceDetectionCameraPreview(viewModel){}
                Spacer(modifier = Modifier.height(16.dp))


            }

            state.bitMapImage?.let {
                if(it!=null){
                    Column(modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .padding(16.dp)) {
                        Text(text = "Now u can save")
                        Spacer(modifier = Modifier.height(10.dp))
                        Image(bitmap = it.asImageBitmap(), contentDescription = "")
                        Spacer(modifier = Modifier.height(16.dp))
                        OutlinedTextField(value = textState.value, onValueChange = {textState.value = it},
                            label = { Text(text = "Enter name")})
                        Spacer(modifier = Modifier.height(16.dp))
                        Button(onClick = {

                            val v = viewModel.saveData(it!!,textState.value)
                            if(v){
                               /* scope.launch {
                                    scaffoldState.snackbarHostState.showSnackbar(message = "Save")
                                }*/
                                navController.navigate(Screen.FaceRecognitionScreen.route,navOptions = NavOptions.Builder()
                                    .setPopUpTo(Screen.FaceRecognitionScreen.route, true)
                                    .build())
                            }


                        }, modifier = Modifier
                            .fillMaxWidth()) {
                            Text(text = "Save")
                        }
                    }
                }




            }

          /*  if(state.isLoading){
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }*/





        }
    }





}


















