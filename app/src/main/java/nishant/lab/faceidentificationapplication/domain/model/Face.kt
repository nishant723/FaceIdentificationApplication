package nishant.lab.faceidentificationapplication.domain.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
//data class Face(@PrimaryKey (autoGenerate = true) val id: Int? = null , val name: String,@ColumnInfo(typeAffinity = ColumnInfo.BLOB) val image: ByteArray )
data class Face(  @PrimaryKey val id: Int? = 1,
                  val name: String,
                  val image: String )
class InvalidFaceException(message:String):Exception(message)