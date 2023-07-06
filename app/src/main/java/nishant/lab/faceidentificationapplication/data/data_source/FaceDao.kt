package nishant.lab.faceidentificationapplication.data.data_source

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import nishant.lab.faceidentificationapplication.domain.model.Face

@Dao
interface FaceDao {
    @Insert
    suspend fun insertFace(face : Face)

    @Query("SELECT * FROM face")
    suspend fun getAllFaces(): List<Face>

    @Query("SELECT * FROM face WHERE image =:imageByteArray LIMIT 1")
    suspend fun getFaceByImage(imageByteArray: ByteArray): Face?
}