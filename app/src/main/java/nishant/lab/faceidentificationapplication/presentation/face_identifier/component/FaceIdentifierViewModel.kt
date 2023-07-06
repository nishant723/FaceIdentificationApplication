package nishant.lab.faceidentificationapplication.presentation.face_identifier.component

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import nishant.lab.faceidentificationapplication.domain.use_case.FaceIdentifierUseCase
import org.opencv.core.Mat
import org.opencv.core.MatOfByte
import org.opencv.imgcodecs.Imgcodecs
import javax.inject.Inject
@HiltViewModel
class FaceIdentifierViewModel @Inject constructor(private val faceIdentifierUseCase: FaceIdentifierUseCase) : ViewModel() {

    private var _errorMessage by mutableStateOf("")
    val errorMessage: String get() = _errorMessage
    private  var list = ArrayList<FaceMat>()



     fun getFunction(base64String: String) {

        viewModelScope.launch(Dispatchers.IO) {
            try{
                val faceList = faceIdentifierUseCase.getImageByName()
                val v = faceList.size
            }catch (e : Exception){
                val error = e.message
            }

            try {
                val faceList = faceIdentifierUseCase.getImageByName()
                val result = convertListOfStringImagesToMats(faceList.map { it.image })
                for (i in result) {
                    list.add(FaceMat(i, "name"))
                }
                val singleMat = convertStringToMat(base64String)
                val face = FaceMat(singleMat, "name")
                val name = faceIdentifierUseCase.performFaceMatching(list, face)
            }catch (e : Exception){
                val error = e.message
            }
        }
    }
    private  fun convertListOfStringImagesToMats(imageStrings: List<String>): List<Mat> {
        val mats = mutableListOf<Mat>()
        for (imageString in imageStrings) {
            val byteArray = android.util.Base64.decode(imageString, android.util.Base64.DEFAULT)
            val matOfByte = MatOfByte(*byteArray)
            val mat = Imgcodecs.imdecode(matOfByte, Imgcodecs.IMREAD_COLOR)
            mats.add(mat)
        }

        return mats
    }

    private  fun convertStringToMat(image : String) : Mat {
        val byteArray = android.util.Base64.decode(image, android.util.Base64.DEFAULT)
        val matOfByte = MatOfByte(*byteArray)
        return Imgcodecs.imdecode(matOfByte, Imgcodecs.IMREAD_COLOR)
    }





}