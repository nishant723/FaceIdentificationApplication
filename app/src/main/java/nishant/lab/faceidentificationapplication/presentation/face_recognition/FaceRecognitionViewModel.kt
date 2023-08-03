package nishant.lab.faceidentificationapplication.presentation.face_recognition

import android.annotation.SuppressLint
import android.app.Application
import android.graphics.Bitmap
import android.graphics.RectF
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.camera.core.ImageProxy
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import nishant.lab.faceidentificationapplication.domain.model.*

import nishant.lab.faceidentificationapplication.domain.use_case.FaceRecognitionUseCase
import nishant.lab.faceidentificationapplication.presentation.util.*

import javax.inject.Inject
@HiltViewModel
class FaceRecognitionViewModel @Inject constructor(private val faceRecognitionUseCase: FaceRecognitionUseCase, private val faceAnalyzer: FaceAnalyzer, private val app : Application) : MyProcessViewModel() {
    var _result = MutableLiveData<String?>("")
    private  val TAG = "FaceRecognitionViewMode"
  /*  private val _faceResult = MutableLiveData<FaceMatchingStatus>()
    val faceResult: LiveData<FaceMatchingStatus> get() = _faceResult*/

    val faceResult = mutableStateOf(FaceMatchingStatus())


    override fun analyzeFace(imageProxy: ImageProxy) {
        viewModelScope.launch(Dispatchers.IO) {
            faceRecognitionUseCase.performFaceMatching(liveFace = imageProxy)
                .collect { result ->
                    when (result) {

                        is Resource.Success -> {
                            println("viewModel : ${result.data}")
                            faceResult.value = (FaceMatchingStatus(imageString = result.data))
                        }
                        is Resource.Error -> {
                            println("viewModel : ${result.message}")
                            faceResult.value = (FaceMatchingStatus(error = result.message!!))
                        }
                        is Resource.Loading -> {
                            faceResult.value = FaceMatchingStatus(isLoading = true)

                        }
                    }
                }
        }
    }









}