package gen.test.android.playlistmaker

import java.text.SimpleDateFormat
import java.util.*

object DateTimeUtil {
    fun millisToMmSs(millis: Int): String {
       return SimpleDateFormat("mm:ss", Locale.getDefault()).format(millis)
    }
}