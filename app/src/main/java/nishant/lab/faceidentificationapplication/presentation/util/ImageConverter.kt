package nishant.lab.faceidentificationapplication.presentation.util

import android.annotation.SuppressLint
import android.graphics.*
import android.media.Image
import android.util.Base64
import androidx.camera.core.ImageProxy
import java.io.ByteArrayOutputStream


class ImageConverter {

     fun imageProxyToByteArray(image: ImageProxy): ByteArray {
        val planes = image.planes
        val bufferSize = image.width * image.height * ImageFormat.getBitsPerPixel(ImageFormat.YUV_420_888) / 8
        val byteArray = ByteArray(bufferSize)

        var bufferOffset = 0
        var pixelStride: Int
        var rowStride: Int
        var rowPadding: Int

        for (planeIndex in 0 until planes.size) {
            val buffer = planes[planeIndex].buffer
            pixelStride = planes[planeIndex].pixelStride
            rowStride = planes[planeIndex].rowStride
            rowPadding = rowStride - pixelStride * image.width

            val rowSize = image.width * pixelStride

            val numRows = if (planeIndex == 0) {
                image.height
            } else {
                image.height / 2
            }

            val planeBuffer = ByteArray(buffer.remaining())
            buffer.get(planeBuffer)

            var bufferOffsetIndex = 0
            for (row in 0 until numRows) {
                System.arraycopy(
                    planeBuffer,
                    bufferOffsetIndex,
                    byteArray,
                    bufferOffset,
                    rowSize
                )
                bufferOffset += rowSize
                bufferOffsetIndex += rowStride
            }
        }

        return byteArray
    }



       @SuppressLint("UnsafeOptInUsageError")
       fun convertImageToBitMap(imageProxy: ImageProxy)  : Bitmap? {
           val planes: Array<out ImageProxy.PlaneProxy> = imageProxy.getPlanes()
           val yBuffer = planes[0].buffer
           val uBuffer = planes[1].buffer
           val vBuffer = planes[2].buffer

           val ySize = yBuffer.remaining()
           val uSize = uBuffer.remaining()
           val vSize = vBuffer.remaining()

           val nv21 = ByteArray(ySize + uSize + vSize)
           //U and V are swapped
           //U and V are swapped
           yBuffer[nv21, 0, ySize]
           vBuffer[nv21, ySize, vSize]
           uBuffer[nv21, ySize + vSize, uSize]

           val yuvImage =
               YuvImage(nv21, ImageFormat.NV21, imageProxy.getWidth(), imageProxy.getHeight(), null)
           val out = ByteArrayOutputStream()
           yuvImage.compressToJpeg(Rect(0, 0, yuvImage.width, yuvImage.height), 75, out)

           val imageBytes = out.toByteArray()
           return BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)

   }


     fun toBitmap(image: Image): Bitmap? {
        val planes = image.planes
        val yBuffer = planes[0].buffer
        val uBuffer = planes[1].buffer
        val vBuffer = planes[2].buffer
        val ySize = yBuffer.remaining()
        val uSize = uBuffer.remaining()
        val vSize = vBuffer.remaining()
        val nv21 = ByteArray(ySize + uSize + vSize)
        //U and V are swapped
        yBuffer[nv21, 0, ySize]
        vBuffer[nv21, ySize, vSize]
        uBuffer[nv21, ySize + vSize, uSize]
        val yuvImage = YuvImage(nv21, ImageFormat.NV21, image.width, image.height, null)
        val out = ByteArrayOutputStream()
        yuvImage.compressToJpeg(Rect(0, 0, yuvImage.width, yuvImage.height), 75, out)
        val imageBytes = out.toByteArray()
        return BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
    }


    fun bitmapToByteArray(bitmap: Bitmap): ByteArray {
        val width = bitmap.width
        val height = bitmap.height

        val pixels = IntArray(width * height)
        bitmap.getPixels(pixels, 0, width, 0, 0, width, height)

        val byteArray = ByteArray(width * height)

        for (i in 0 until width * height) {
            val pixel = pixels[i]
            val alpha = (pixel shr 24 and 0xFF) // Get the alpha component
            val red = (pixel shr 16 and 0xFF)   // Get the red component
            val green = (pixel shr 8 and 0xFF)  // Get the green component
            val blue = (pixel and 0xFF)         // Get the blue component

            // Determine whether the pixel is considered "on" or "off"
            val isOn = (alpha > 0) && (red > 127 || green > 127 || blue > 127)
            byteArray[i] = if (isOn) 1 else 0
        }

        return byteArray
    }


    fun convertBitmapToBase64(bitmap: Bitmap): String {

        val outputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
        val bitmapData: ByteArray = outputStream.toByteArray()
        val base64String: String = Base64.encodeToString(bitmapData, Base64.DEFAULT)

        return base64String
    }

    fun byteArrayToBitmap(byteArray: ByteArray): Bitmap? {
        return try {
            BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    fun base64ToBitmap(base64String: String): Bitmap? {
        return try {
            val decodedBytes = Base64.decode(base64String, Base64.DEFAULT)
            BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.size)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }




    fun increaseBitmapSize(bitmap: Bitmap, targetSize: Int): Bitmap {
        val currentSize = bitmap.byteCount

        if (currentSize >= targetSize) {
            // No need to increase the size, return the original bitmap
            return bitmap
        }

        val width = bitmap.width
        val height = bitmap.height
        val scaleFactor = Math.sqrt(targetSize.toDouble() / currentSize.toDouble())

        val newWidth = (width * scaleFactor).toInt()
        val newHeight = (height * scaleFactor).toInt()

        val resizedBitmap = Bitmap.createScaledBitmap(bitmap, newWidth, newHeight, true)

        // Pad the bitmap to match the exact target size
        val paddedBitmap = Bitmap.createBitmap(targetSize, 1, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(paddedBitmap)
        canvas.drawBitmap(resizedBitmap, (targetSize - newWidth) / 2f, 0f, null)

        return paddedBitmap
    }


    fun resizeBitmap(bitmap: Bitmap, width: Int, height: Int): Bitmap {
        return Bitmap.createScaledBitmap(bitmap, width, height, true)
    }





}