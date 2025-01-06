package gen.test.android.playlistmaker.data.db.playlists

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Environment
import java.io.File
import java.io.FileOutputStream

class ImageHandler(private val context: Context) {
    fun saveImage(uri: Uri, plist: PlistDB) {
        val filePath = File(
            context.getExternalFilesDir(
                Environment.DIRECTORY_PICTURES
            ), "playlistmaker_album"
        )
        if (!filePath.exists()) {
            filePath.mkdirs()
        }
        //val fileName = plist.name + plist.description + ".jpg"
        val file = File(filePath, plist.imagePath)
        val inputStream = context.contentResolver.openInputStream(uri)
        val outputStream = FileOutputStream(file)
        BitmapFactory
            .decodeStream(inputStream)
            .compress(Bitmap.CompressFormat.JPEG, 30, outputStream)
    }
}