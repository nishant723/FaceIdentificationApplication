package nishant.lab.faceidentificationapplication.presentation.util

import androidx.camera.core.ImageProxy
import androidx.lifecycle.ViewModel

abstract class MyProcessViewModel : ViewModel() {
    abstract fun analyzeFace(imageProxy: ImageProxy)
}