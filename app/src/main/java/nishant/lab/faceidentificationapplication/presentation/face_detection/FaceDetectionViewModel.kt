package nishant.lab.faceidentificationapplication.presentation.face_detection

import android.graphics.*
import androidx.camera.core.ImageProxy

import androidx.lifecycle.MutableLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import nishant.lab.faceidentificationapplication.domain.model.FaceAnalysisResult
import nishant.lab.faceidentificationapplication.domain.use_case.FaceDetectionUseCase
import android.graphics.Bitmap
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import nishant.lab.faceidentificationapplication.domain.model.Face
import nishant.lab.faceidentificationapplication.domain.model.FaceStatus
import nishant.lab.faceidentificationapplication.domain.use_case.FaceAnalyzerUseCase
import nishant.lab.faceidentificationapplication.presentation.util.ImageConverter
import nishant.lab.faceidentificationapplication.presentation.util.MyProcessViewModel
import javax.inject.Inject
import nishant.lab.faceidentificationapplication.domain.model.Result




@HiltViewModel
class FaceDetectionViewModel @Inject constructor(
    private val faceAnalyzerUseCase: FaceAnalyzerUseCase,
    private val faceDetectionUseCase: FaceDetectionUseCase) : MyProcessViewModel() {

    private val _faceResult = mutableStateOf(FaceStatus())
    val faceResult: State<FaceStatus> = _faceResult
    private val TAG = "RegisterFaceViewModel"

    override fun analyzeFace(imageProxy: ImageProxy) {
        viewModelScope.launch {
            try {
                when(val result = faceAnalyzerUseCase.analyzeFace(imageProxy = imageProxy)){
                    is  Result.Success -> {
                        _faceResult.value = FaceStatus(bitMapImage = result.data)
                    }
                     is Result.Error -> {
                         _faceResult.value = FaceStatus(error = result.message)
                     }
                   /* is Result.Loading -> {
                        _faceResult.value = FaceStatus(isLoading = true)
                    }*/
                 }
            }catch (e : Exception){
                //__faceResult.value = FaceStatus(error = result.message)
            }
        }

    }

    fun saveData(it: Bitmap, value: String) : Boolean {
        viewModelScope.launch(Dispatchers.IO){
            val convertImage = ImageConverter().convertBitmapToBase64(it)
            faceDetectionUseCase.registerFace(Face(0,value,convertImage))
        }
        return true


    }


}