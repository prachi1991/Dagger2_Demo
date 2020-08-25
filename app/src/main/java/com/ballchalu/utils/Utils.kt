package com.ballchalu.utils

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.util.Base64
import android.widget.ImageView
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.graphics.drawable.toBitmap
import com.ballchalu.R
import java.io.ByteArrayOutputStream


object Utils {

    fun getDrawableToBitmap(drawable: Drawable): Bitmap {
        val bitmap = drawable.toBitmap(drawable.intrinsicWidth, drawable.intrinsicHeight)
        return bitmap
    }

    fun getFileToByte(drawable: Drawable?): String? {
        return try {
            val bitmap = drawable?.toBitmap(drawable.intrinsicWidth, drawable.intrinsicHeight)
            val baos = ByteArrayOutputStream()
            bitmap?.compress(Bitmap.CompressFormat.JPEG, 100, baos) //bm is the bitmap object
            val byteArrayImage = baos.toByteArray()
            var imgString = Base64.encodeToString(byteArrayImage, Base64.NO_WRAP)
            val image = StringBuilder("data:image/png;base64,")
            image.append(imgString).toString()
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
    fun showPassword(
        passwordView: AppCompatEditText,
        eyeView: ImageView,
        isShow: Boolean){
        if(isShow){
            passwordView.transformationMethod= HideReturnsTransformationMethod.getInstance()
            eyeView.setImageResource(R.drawable.ic_hideeye)
        }
        else{
            passwordView.transformationMethod= PasswordTransformationMethod.getInstance()
            eyeView.setImageResource(R.drawable.ic_eye)
        }
        val text = passwordView.text
        passwordView.setSelection(text.toString().length)

    }
}