package gen.test.android.playlistmaker.data.db.playlists

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Environment
import androidx.core.net.toUri
import java.io.File
import java.io.FileOutputStream

class ImageHandler(private val context: Context) {
    fun saveImage(uriStr: String, plist: PlistDB) {
        val filePath = File(
            context.getExternalFilesDir(
                Environment.DIRECTORY_PICTURES
            ), "playlistmaker_album"
        )
        if (!filePath.exists()) {
            filePath.mkdirs()
        }
        val fileName = plist.name + plist.description + ".jpg"
        val file = File(filePath, fileName)
        val inputStream = context.contentResolver.openInputStream(uriStr.toUri())
        val outputStream = FileOutputStream(file)
        BitmapFactory
            .decodeStream(inputStream)
            .compress(Bitmap.CompressFormat.JPEG, 30, outputStream)
    }
}