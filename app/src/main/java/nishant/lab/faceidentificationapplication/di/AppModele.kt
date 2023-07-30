package nishant.lab.faceidentificationapplication.di

import android.app.Application
import android.content.Context

import androidx.room.Room
import com.google.mlkit.vision.face.FaceDetection
import com.google.mlkit.vision.face.FaceDetector
import com.google.mlkit.vision.face.FaceDetectorOptions
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import nishant.lab.faceidentificationapplication.data.data_source.AppDatabase
import nishant.lab.faceidentificationapplication.data.repository.FaceAnalyzerRepositoryImpl
import nishant.lab.faceidentificationapplication.data.repository.FaceRepositoryImpl
import nishant.lab.faceidentificationapplication.domain.repository.FaceAnalyzerRepository
import nishant.lab.faceidentificationapplication.domain.repository.FaceRepository
import nishant.lab.faceidentificationapplication.domain.use_case.FaceAnalyzerUseCase
import nishant.lab.faceidentificationapplication.domain.use_case.FaceRecognitionUseCase
import nishant.lab.faceidentificationapplication.domain.use_case.FaceUseCase
import nishant.lab.faceidentificationapplication.domain.use_case.FaceDetectionUseCase
import nishant.lab.faceidentificationapplication.presentation.util.FaceAnalyzer
import nishant.lab.faceidentificationapplication.presentation.util.FaceNetModel
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun providerAppDatabase(app: Application): AppDatabase {
        return Room.databaseBuilder(
            app, AppDatabase::class.java,
            AppDatabase.DATABASE_NAME

        )
            .addCallback(AppDatabase.DatabaseCallback)
            .build()
    }

    @Provides
    @Singleton
    fun provideFaceDetector(): FaceDetector {
        val options = FaceDetectorOptions.Builder()
            .setPerformanceMode(FaceDetectorOptions.PERFORMANCE_MODE_ACCURATE)
            .setLandmarkMode(FaceDetectorOptions.LANDMARK_MODE_ALL)
            .setClassificationMode(FaceDetectorOptions.CLASSIFICATION_MODE_ALL)
            .build()
        return FaceDetection.getClient(options)
    }

    @Provides
    @Singleton
    fun provideFaceRepository(db: AppDatabase): FaceRepository {
        return FaceRepositoryImpl(db.faceDao)
    }

    @Provides
    @Singleton
    fun providerFaceAnalysisRepository(faceDetector: FaceDetector) : FaceAnalyzerRepository{
        return FaceAnalyzerRepositoryImpl(faceDetector)
    }

    @Provides
    @Singleton
    fun providerFaceUseCase(repository: FaceRepository,faceAnalyzerRepository: FaceAnalyzerRepository,
    faceNetModel: FaceNetModel): FaceUseCase {
        return FaceUseCase(
            faceRecognitionUseCase = FaceRecognitionUseCase(repository,faceNetModel,faceAnalyzerRepository),
            faceFaceRecognitionUseCase = FaceDetectionUseCase(repository)
        )
    }

    @Provides
    @Singleton
    fun providerFaceAnalysisUseCase(repository: FaceAnalyzerRepository): FaceAnalyzerUseCase{
        return FaceAnalyzerUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideFaceAnalyzer(): FaceAnalyzer {
        return FaceAnalyzer()
    }

    @Provides
    @Singleton
    fun providerTensorFlow(app: Application) : FaceNetModel {
        return FaceNetModel(app)
    }

   /* @Provides
    fun provideContext(application: Application): Context = application.applicationContext*/




}
