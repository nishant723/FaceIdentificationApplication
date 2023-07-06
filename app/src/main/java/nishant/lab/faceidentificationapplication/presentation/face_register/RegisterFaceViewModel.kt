package nishant.lab.faceidentificationapplication.presentation.face_register

import android.content.Context
import android.net.Uri
import androidx.compose.runtime.*
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import nishant.lab.faceidentificationapplication.domain.model.Face
import nishant.lab.faceidentificationapplication.domain.use_case.RegisterFaceUseCase
import nishant.lab.faceidentificationapplication.presentation.util.CameraHelper
import javax.inject.Inject


@HiltViewModel
class RegisterFaceViewModel @Inject constructor(private val   registerFaceUseCase: RegisterFaceUseCase
,private val cameraHelper: CameraHelper) : ViewModel(){

   /* private val _registeredFaces by mutableStateOf(mutableStateListOf<FaceMat>())
    val registeredFaces: List<FaceMat> get() = _registeredFaces*/

    private val _faces = MutableLiveData<List<Face>>()
    val faces: LiveData<List<Face>> get() = _faces

    private var _currentFaces by mutableStateOf(emptyList<Face>())
    val currentFaces: List<Face> get() = _currentFaces

    private var _errorMessage by mutableStateOf("")
    val errorMessage: String get() = _errorMessage

    private val _imageUri = MutableLiveData<Uri?>()
    val imageUri: LiveData<Uri?> get() = _imageUri


    fun captureImage(context: Context) {
        if (cameraHelper.hasCameraPermission()) {
            val imageUri = cameraHelper.captureImageFromCamera()
            _imageUri.value = imageUri
        }
    }






    suspend fun registerFace(face: Face) {
        withContext(Dispatchers.IO) {
            try {
                registerFaceUseCase.registerFace(face)
            } catch (e: Exception) {
                _errorMessage = "Failed to register face: ${e.message}"
            }
        }
    }


}