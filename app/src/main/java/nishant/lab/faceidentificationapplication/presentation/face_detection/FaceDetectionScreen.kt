package nishant.lab.faceidentificationapplication.presentation.face_detection

import android.graphics.Bitmap
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import kotlinx.coroutines.launch
import nishant.lab.faceidentificationapplication.presentation.face_detection.component.PopupDialog

import nishant.lab.faceidentificationapplication.presentation.util.FaceDetectionCameraPreview


@Composable
fun FaceDetectionScreen(navController: NavHostController, viewModel: FaceDetectionViewModel = hiltViewModel()) {
    var state = viewModel.faceResult.value
    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()
    var lazyValue : Bitmap? = null
    var isPopupShowing by remember { mutableStateOf(false) }
    var capturedBitmap by remember { mutableStateOf<Bitmap?>(null) }


    
    Scaffold(scaffoldState = scaffoldState) {
        Box(modifier = Modifier.fillMaxSize()){
            Column(modifier = Modifier
                .fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally)
            { Spacer(modifier = Modifier.height(16.dp))
                Column(modifier = Modifier
                    .fillMaxWidth()
                    .height(40.dp)) {
                    if(state.error.isNotEmpty()){
                            Text(
                                text = state.error,
                                color = MaterialTheme.colors.error,
                                textAlign = TextAlign.Center,
                                modifier = Modifier
                                    .fillMaxWidth()

                            )


                    }
                }

                Spacer(modifier = Modifier.height(16.dp))
                FaceDetectionCameraPreview(viewModel){}


            }
            Column(modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(16.dp)) {
                Button(onClick = {
                    if(state.error.isNotEmpty()){
                        scope.launch {
                            scaffoldState.snackbarHostState.showSnackbar(state.error)
                        }
                    } else {
                        capturedBitmap = state.bitMapImage
                        println("bitMap $lazyValue")
                        isPopupShowing = true
                    }



                                 }, modifier = Modifier
                    .fillMaxWidth()) {
                        Text(text = "Capture")
                    }
                }



          /*  if(state.isLoading){
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }*/
            if (isPopupShowing) {
                if(capturedBitmap!=null){
                    PopupDialog(dismissPopup = { isPopupShowing = false },viewModel,
                        bitMapImage = capturedBitmap!!, navController = navController)
                }
                /*else{
                    scope.launch {
                        scaffoldState.snackbarHostState.showSnackbar(message = state.error)

                    }


                }*/


            }





        }
    }





}


















