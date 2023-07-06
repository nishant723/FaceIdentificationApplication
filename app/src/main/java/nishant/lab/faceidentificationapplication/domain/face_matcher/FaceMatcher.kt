package nishant.lab.faceidentificationapplication.domain.face_matcher

import nishant.lab.faceidentificationapplication.domain.model.FaceMat
import nishant.lab.faceidentificationapplication.domain.model.Match
import nishant.lab.faceidentificationapplication.domain.model.MatchResult
import org.opencv.core.Core
import org.opencv.core.CvType.CV_32F
import org.opencv.core.Mat
import org.opencv.core.MatOfRect
import org.opencv.core.Size
import org.opencv.imgproc.Imgproc
import org.opencv.objdetect.CascadeClassifier

class FaceMatcher {

    private val faceDetector: CascadeClassifier = CascadeClassifier()

    fun matchFaces(registeredFaces: List<FaceMat>, capturedFace: Mat): MatchResult {
        val capturedFaceFeatures = extractFaceFeatures(capturedFace)
        val matchResults = mutableListOf<Match>()
        for (registeredFace in registeredFaces) {
            val registeredFaceFeatures = extractFaceFeatures(registeredFace.mat)
            val matchScore = calculateMatchScore(registeredFaceFeatures, capturedFaceFeatures)
            val match = Match(registeredFace, matchScore)
            matchResults.add(match)
        }
        matchResults.sortBy { it.matchScore }
        val bestMatch = matchResults.firstOrNull()
        return MatchResult(matchResults,bestMatch)
    }

    private fun extractFaceFeatures(face: Mat): Mat {
        val grayFace = Mat()
        Imgproc.cvtColor(face, grayFace, Imgproc.COLOR_BGR2GRAY)

        val faceRects = MatOfRect()
        val minFaceSize = Size(30.0, 30.0)
        val maxFaceSize = Size()
        val scaleFactor = 1.1
        val minNeighbors = 3
        faceDetector.detectMultiScale(
            grayFace,
            faceRects,
            scaleFactor,
            minNeighbors,
            0,
            minFaceSize,
            maxFaceSize
        )
        if (faceRects.empty()) {
            return Mat()
        }
        val faceRect = faceRects.toList()[0]
        val faceRegion = grayFace.submat(faceRect)
        val resizedFace = Mat()
        Imgproc.resize(faceRegion, resizedFace, Size(100.0, 100.0))
        return resizedFace
    }

    private fun calculateMatchScore(registeredFace: Mat, capturedFace: Mat): Double {
        val registeredFaceFloat = Mat()
        registeredFace.convertTo(registeredFaceFloat, CV_32F)
        val capturedFaceFloat = Mat()
        capturedFace.convertTo(capturedFaceFloat, CV_32F)
        val diff = Mat()
        Core.absdiff(registeredFaceFloat, capturedFaceFloat, diff)
        val meanDiff = Core.mean(diff)
        return meanDiff.`val`[0]
    }
}