package nishant.lab.faceidentificationapplication.data.repository

import nishant.lab.faceidentificationapplication.data.data_source.FaceDao
import nishant.lab.faceidentificationapplication.domain.model.Face
import nishant.lab.faceidentificationapplication.domain.repository.FaceRepository
import javax.inject.Inject

class FaceRepositoryImpl @Inject constructor(private val faceDao: FaceDao)  : FaceRepository {
    override suspend fun insertFace(face: Face){
      return  faceDao.insertFace(face)
    }

    override suspend fun getNameByFace(): Face {
        return faceDao.getAllFaces()
    }




}