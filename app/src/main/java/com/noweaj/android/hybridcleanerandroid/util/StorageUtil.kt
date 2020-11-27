package com.noweaj.android.hybridcleanerandroid.util

import android.content.Context
import android.os.Environment
import java.io.File

object StorageUtil {

    fun getManualFile(context: Context, filename: String): File {
        val finalFile = File("${context.filesDir}/$filename")
        if(!finalFile.exists()){
            val assetFile = File("${context.assets}/$filename")
            copyFileFromAssets(assetFile, finalFile)
        }

        return finalFile
    }

    private fun copyFileFromAssets(assetFile: File, finalFile: File){
        
    }

    fun copyFileFromAssetsToExternalStorage(){

    }

    fun copyFileFromAssetsToMediaStoreDownloads(
        context: Context,
        filePath: String,
        displayName: String,
        mediaType: String
    ){
        val relativeLocation = Environment.DIRECTORY_DOCUMENTS

    }

}