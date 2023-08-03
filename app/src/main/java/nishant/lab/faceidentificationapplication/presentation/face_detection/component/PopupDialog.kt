package nishant.lab.faceidentificationapplication.presentation.face_detection.component

import android.graphics.Bitmap
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavController
import androidx.navigation.compose.DialogHost
import nishant.lab.faceidentificationapplication.presentation.face_detection.FaceDetectionViewModel

@Composable
fun PopupDialog(dismissPopup: () -> Unit,viewModel: FaceDetectionViewModel,bitMapImage : Bitmap,navController: NavController
) {
    // Define the state for the text input
    val textState = remember { mutableStateOf("") }
    // val bestFaceBitmap by viewModel.getMutableLiveDataInstance().observeAsState()
    Dialog(onDismissRequest = dismissPopup) {
        Card(
            modifier = Modifier.padding(16.dp),
            elevation = 4.dp
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                // Display the image using an ImageView
                Image (
                    bitmap = bitMapImage!!.asImageBitmap(),
                    //  painter = painterResource(id = R.drawable.ic_launcher_background),
                    contentDescription = null,
                    modifier = Modifier.size(100.dp)
                )
                Spacer(modifier = Modifier.padding(7.dp))

                // Add a text input field for editing text
                OutlinedTextField (
                    value = textState.value,
                    onValueChange = { textState.value = it },
                    label = { Text("Enter Name") }
                )
                Spacer(modifier = Modifier.padding(7.dp))
                Button(
                    onClick = {

                      val result = viewModel.saveData(bitMapImage,textState.value)
                        if (result){
                            navController.popBackStack()
                        }

                    }
                ) {
                    Text("Save")
                }
            }
        }
    }


}