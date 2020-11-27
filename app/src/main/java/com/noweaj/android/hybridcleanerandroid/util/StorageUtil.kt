package com.noweaj.android.hybridcleanerandroid.util

import android.content.Context
import android.os.Environment

object StorageUtil {
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