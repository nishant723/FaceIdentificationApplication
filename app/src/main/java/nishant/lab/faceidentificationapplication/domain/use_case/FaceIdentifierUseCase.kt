package nishant.lab.faceidentificationapplication.domain.use_case

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import nishant.lab.faceidentificationapplication.domain.face_matcher.FaceMatcher
import nishant.lab.faceidentificationapplication.domain.model.Face
import nishant.lab.faceidentificationapplication.domain.model.FaceMat
import nishant.lab.faceidentificationapplication.domain.repository.FaceRepository
import javax.inject.Inject

class FaceIdentifierUseCase @Inject constructor(private val repository: FaceRepository) {

    private lateinit var v: List<FaceMat>

   suspend fun getImageByName () : List<Face>{
       return repository.getNameByFace()

   }

    suspend fun performFaceMatching(
        registeredFaces: ArrayList<FaceMat>,
        capturedFace: FaceMat
    ): String? {
        return withContext(Dispatchers.IO) {
            try {
                val capturedMat = capturedFace.mat
                val faceMatcher = FaceMatcher()
                val matchResult = faceMatcher.matchFaces(registeredFaces, capturedMat)
                val bestMatch = matchResult.bestMatch
                bestMatch?.registeredFace?.name
            } catch (e: Exception) {
               // _errorMessage = "Face matching failed: ${e.message}"
                null
            }
        }
    }









}