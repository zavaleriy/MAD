package com.example.mad

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.util.Log
import androidx.core.net.toUri
import java.io.File
import java.io.FileOutputStream
import java.util.UUID

class Repository(val context: Context) {
    fun getImages(): List<Uri> {
        val dir = File(context.filesDir, DIR_IMAGES)
        if (!dir.exists()) {
            dir.mkdirs()
            return emptyList()
        }
        return dir.listFiles()?.map { file -> file.path.toUri() } ?: emptyList()
    }

    fun saveImage(bitmap: Bitmap): Uri? {
        val dir = File(context.filesDir, DIR_IMAGES)
        if (!dir.exists()) dir.mkdirs()
        return try {
            val file = File(dir, UUID.randomUUID().toString())
            val fileOutputStream = FileOutputStream(file)
            val resizedBitmap = resizeBitmap(bitmap, MAX_WIDTH, MAX_HEIGHT)
            resizedBitmap.compress(Bitmap.CompressFormat.PNG, 90 ,fileOutputStream)
            fileOutputStream.flush()
            fileOutputStream.close()
            file.path.toUri()
        } catch (e: Exception) {
            Log.e("Repository saveImage", e.message.toString())
            null
        }
    }

    fun deleteImage(uri: Uri): Boolean {
        return try {
            val file = File(uri.path!!)
            file.delete()
        } catch (e: Exception) {
            Log.e("Repository deleteImage", e.message.toString())
            false
        }
    }

    private fun resizeBitmap(bitmap: Bitmap, maxWidth: Int, maxHeight: Int): Bitmap {
        val width = bitmap.width
        val height = bitmap.height

        val aspectRatio = width.toFloat() / height.toFloat()
        val newWidth: Int
        val newHeight: Int

        if (width > height) {
            newWidth = maxWidth
            newHeight = (maxWidth / aspectRatio).toInt()
        } else {
            newHeight = maxHeight
            newWidth = (maxHeight * aspectRatio).toInt()
        }

        return Bitmap.createScaledBitmap(bitmap, newWidth, newHeight, true)
    }

    companion object RepositoryHelper {
        const val DIR_IMAGES = "images"
        const val MAX_WIDTH = 2048
        const val MAX_HEIGHT = 2048
    }

}