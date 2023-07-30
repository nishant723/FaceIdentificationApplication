package nishant.lab.faceidentificationapplication.data.data_source

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import nishant.lab.faceidentificationapplication.domain.model.Face


@Database(entities = [Face::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract val  faceDao : FaceDao

    companion object {
        const val DATABASE_NAME = "face_db"
    }

    object DatabaseCallback : RoomDatabase.Callback() {
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            // Enable debug logging
            db.execSQL("PRAGMA wal_trace=ON")
            db.execSQL("PRAGMA foreign_keys=ON")
            // Add any other debug-related commands if needed
        }
    }
}