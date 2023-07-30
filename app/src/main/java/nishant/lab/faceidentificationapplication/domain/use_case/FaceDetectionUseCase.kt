package nishant.lab.faceidentificationapplication.domain.use_case

import dagger.hilt.android.lifecycle.HiltViewModel
import nishant.lab.faceidentificationapplication.data.repository.FaceRepositoryImpl
import nishant.lab.faceidentificationapplication.domain.model.Face
import nishant.lab.faceidentificationapplication.domain.repository.FaceRepository
import javax.inject.Inject

class FaceDetectionUseCase @Inject constructor(private val repository: FaceRepository) {
    suspend fun registerFace(face: Face) {
        repository.insertFace(face)
    }




}