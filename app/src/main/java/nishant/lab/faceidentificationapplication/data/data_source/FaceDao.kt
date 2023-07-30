package nishant.lab.faceidentificationapplication.data.data_source

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import nishant.lab.faceidentificationapplication.domain.model.Face

@Dao
interface FaceDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFace(face : Face?)

    @Query("SELECT * FROM Face")
    suspend fun getAllFaces(): Face

   /* @Query("select * from face where id = id")
    suspend fun getIdByFace( id : Int) : Face*/

   /* @Query("SELECT * FROM Face WHERE image =:imageByteArray LIMIT 1")
    suspend fun getFaceByImage(imageByteArray: String): Face?*/
}