package nishant.lab.faceidentificationapplication.domain.repository

import nishant.lab.faceidentificationapplication.domain.model.Face

interface FaceRepository {

    suspend fun insertFace(face: Face)
    suspend fun getNameByFace() : List<Face>
    }
