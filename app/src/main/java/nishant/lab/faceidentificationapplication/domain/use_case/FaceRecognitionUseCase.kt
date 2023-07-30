package nishant.lab.faceidentificationapplication.domain.use_case

import android.app.Application
import android.util.Log
import androidx.camera.core.ImageProxy
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.withContext
import nishant.lab.faceidentificationapplication.domain.face_matcher.FaceMatcher
import nishant.lab.faceidentificationapplication.domain.model.Face
import nishant.lab.faceidentificationapplication.domain.model.Resource
import nishant.lab.faceidentificationapplication.domain.model.Result
import nishant.lab.faceidentificationapplication.domain.repository.FaceAnalyzerRepository
import nishant.lab.faceidentificationapplication.domain.repository.FaceRepository
import nishant.lab.faceidentificationapplication.presentation.util.FaceNetModel
import nishant.lab.faceidentificationapplication.presentation.util.ImageConverter
import javax.inject.Inject

class FaceRecognitionUseCase @Inject constructor(private val repository: FaceRepository,
private val faceNetModel: FaceNetModel,private val faceAnalyzerRepository : FaceAnalyzerRepository
) {
    val threshold = 0.5f
    private  val TAG = "FaceRecognitionUseCase"


    suspend fun performFaceMatching(
        liveFace: ImageProxy?
    ): Flow<Result<String>> = flow {
        // Check if liveFace is null or not
        if (liveFace == null) {
            emit(Result.Error("No face to match"))
            return@flow
        }

        // Perform face analysis asynchronously
        val faceAnalysisResult = faceAnalyzerRepository.analyzeFace(imageProxy = liveFace)

        faceAnalysisResult.collect { result ->
            when (result) {
                is Resource.Success -> {
                    // Check if there is a face to match
                    if (repository.getNameByFace() == null) {
                        emit(Result.Error("No face to match"))
                    } else {
                        println("Coroutine started in thread in usecase: ${Thread.currentThread().name}")
                        val convertImageBitMap =
                            ImageConverter().base64ToBitmap(repository.getNameByFace().image)
                        val result1 = faceNetModel.getFaceEmbeddingWithoutBBox(convertImageBitMap!!)
                        val result2 = faceNetModel.getFaceEmbeddingWithoutBBox(result.data!!)
                        val check = calculateSimilarityScore(result1, result2)
                        if (check >= 0.5f) {
                            emit(Result.Success(repository.getNameByFace().name))
                        } else {
                            emit(Result.Error("You are not registered"))
                        }
                    }
                }
                is Resource.Error -> emit(Result.Error(result.message ?: "Unknown error"))
               // is Resource.Loading -> emit(Result.Loading("Loading"))

            }
        }
    }.flowOn(Dispatchers.IO)


    private fun calculateSimilarityScore(embeddings1: FloatArray, embeddings2: FloatArray): Float {
        // Calculate the dot product of the two embedding vectors
        var dotProduct = 0f
        for (i in embeddings1.indices) {
            dotProduct += embeddings1[i] * embeddings2[i]
        }

        // Calculate the magnitudes of the embedding vectors
        var magnitude1 = 0f
        var magnitude2 = 0f
        for (i in embeddings1.indices) {
            magnitude1 += embeddings1[i] * embeddings1[i]
            magnitude2 += embeddings2[i] * embeddings2[i]
        }
        magnitude1 = Math.sqrt(magnitude1.toDouble()).toFloat()
        magnitude2 = Math.sqrt(magnitude2.toDouble()).toFloat()

        // Calculate the cosine similarity score
        val similarityScore = dotProduct / (magnitude1 * magnitude2)

        return similarityScore
    }









}