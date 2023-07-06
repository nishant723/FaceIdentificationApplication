package nishant.lab.faceidentificationapplication.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "face")
data class Face(@PrimaryKey (autoGenerate = true) val id: Int , val name: String, val image: String )
