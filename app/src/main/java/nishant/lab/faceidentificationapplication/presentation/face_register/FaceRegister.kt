package nishant.lab.faceidentificationapplication.presentation.face_register

import android.graphics.Bitmap
import android.net.Uri
import android.util.Base64
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.launch
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import nishant.lab.faceidentificationapplication.domain.model.Face
import nishant.lab.faceidentificationapplication.presentation.util.CameraPreview
import java.io.ByteArrayOutputStream


@Composable
fun FaceRegister(navController: NavHostController, viewModel: RegisterFaceViewModel = hiltViewModel()) {
    val context = LocalContext.current
    val nameState = remember { mutableStateOf("") }
    val nameErrorState = remember { mutableStateOf(false) }

    var showError by remember { mutableStateOf(false) }
    var bitmap by remember {
        mutableStateOf<Bitmap?>(value = null) }
    val launcher = rememberLauncherForActivityResult(contract = ActivityResultContracts.TakePicturePreview()){
        bitmap = it
    }

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(vertical = 20.dp, horizontal = 20.dp))
    {
        OutlinedTextField(
            value = nameState.value,
            onValueChange = { value ->
                nameState.value = value
                nameErrorState.value = false
            },
            label = { Text("Enter Name") },
            isError = nameErrorState.value,
            modifier = Modifier.fillMaxWidth()
        )
        if (showError) {
            Text(
                text = "Field cannot be empty",
                style = MaterialTheme.typography.body2,
                color = MaterialTheme.colors.error,
                modifier = Modifier.padding(top = 8.dp)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))
        CameraPreview()
        Spacer(modifier = Modifier.height(16.dp))

       /* bitmap?.let {
            Image(bitmap = bitmap?.asImageBitmap()!!,
                contentDescription = "", modifier = Modifier.size(200.dp))
            Spacer(modifier = Modifier.height(20.dp))
            GlobalScope.launch {
                val byteArrayOutputStream = ByteArrayOutputStream()
                it.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream)
                val byteArray = byteArrayOutputStream.toByteArray()
                val base64String = Base64.encodeToString(byteArray, Base64.DEFAULT)
                viewModel.registerFace(Face(0,"name",base64String))
            }


        }*/
        Button(onClick = {
            if (nameState.value.isBlank()) {
                showError = true
            } else {
                //launcher.launch()
               // comp
            }
        }) {
            Text(text = "Save")

        }
    }
}












