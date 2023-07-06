package nishant.lab.faceidentificationapplication.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import dagger.Component
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import nishant.lab.faceidentificationapplication.MainActivity
import nishant.lab.faceidentificationapplication.data.data_source.AppDatabase
import nishant.lab.faceidentificationapplication.data.data_source.FaceDao
import nishant.lab.faceidentificationapplication.data.repository.FaceRepositoryImpl
import nishant.lab.faceidentificationapplication.domain.repository.FaceRepository
import nishant.lab.faceidentificationapplication.domain.use_case.FaceIdentifierUseCase
import nishant.lab.faceidentificationapplication.domain.use_case.FaceUseCase
import nishant.lab.faceidentificationapplication.domain.use_case.RegisterFaceUseCase
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule  {
    @Provides
    @Singleton
    fun providerAppDatabase(app : Application) : AppDatabase {
        return Room.databaseBuilder(app,AppDatabase::class.java,
        AppDatabase.DATABASE_NAME).build()
    }

    @Provides
    @Singleton
    fun provideFaceRepository(db:AppDatabase) : FaceRepository{
        return FaceRepositoryImpl(db.faceDao)
    }
    @Provides
    @Singleton
   fun providerFaceUseCase(repository: FaceRepository) : FaceUseCase{
       return FaceUseCase(faceIdentifierUseCase = FaceIdentifierUseCase(repository),
       faceRegisterFaceUseCase = RegisterFaceUseCase(repository)
       )
   }
}