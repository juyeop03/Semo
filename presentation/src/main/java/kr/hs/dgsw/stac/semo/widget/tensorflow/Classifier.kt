package kr.hs.dgsw.stac.semo.widget.tensorflow

import android.graphics.Bitmap

interface Classifier {

    fun recognizeImage(bitmap: Bitmap): List<Recognition>
    fun close()

    class Recognition(val id: String, val title: String, val confidence: Float)
}