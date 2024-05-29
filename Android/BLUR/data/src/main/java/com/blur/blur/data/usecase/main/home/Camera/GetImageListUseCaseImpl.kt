package com.blur.blur.data.usecase.main.home.Camera

import android.content.ContentUris
import android.content.Context
import android.os.Build
import android.provider.MediaStore
import com.blur.blur.domain.model.Image
import com.blur.blur.domain.usecase.main.home.Camera.GetImageListUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

//https://developer.android.com/about/versions/14/changes/partial-photo-video-access?hl=ko

class GetImageListUseCaseImpl @Inject constructor(
    private val context: Context,
) : GetImageListUseCase {
    override suspend fun invoke(): List<Image> = withContext(Dispatchers.IO) {
        val contentResolver = context.contentResolver
        val projection = arrayOf(
            MediaStore.Images.Media._ID,
            MediaStore.Images.Media.DISPLAY_NAME,
            MediaStore.Images.Media.SIZE,
            MediaStore.Images.Media.MIME_TYPE,
        )

        val collectionUri = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            // Query all the device storage volumes instead of the primary only
            MediaStore.Images.Media.getContentUri(MediaStore.VOLUME_EXTERNAL)
        } else {
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        }

        val images = mutableListOf<Image>()

        contentResolver.query(
            collectionUri,
            projection,
            null,
            null,
            "${MediaStore.Images.Media.DATE_ADDED} DESC"
        )?.use { cursor ->
            val idColumn = cursor.getColumnIndexOrThrow(MediaStore.Images.Media._ID)
            val displayNameColumn = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DISPLAY_NAME)
            val sizeColumn = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.SIZE)
            val mimeTypeColumn = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.MIME_TYPE)

            while (cursor.moveToNext()) {
                val uri = ContentUris.withAppendedId(collectionUri, cursor.getLong(idColumn))
                val name = cursor.getString(displayNameColumn)
                val size = cursor.getLong(sizeColumn)
                val mimeType = cursor.getString(mimeTypeColumn)

                val image = Image(
                    uri = uri.toString(),
                    name = name,
                    size = size,
                    mimeType = mimeType
                )
                images.add(image)
            }
        }

        return@withContext images
    }

}
