package nishant.lab.faceidentificationapplication.data.data_source

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import nishant.lab.faceidentificationapplication.domain.model.Face


@Database(entities = [Face::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract val  faceDao : FaceDao

    companion object {
        const val DATABASE_NAME = "my_database"
    }
}