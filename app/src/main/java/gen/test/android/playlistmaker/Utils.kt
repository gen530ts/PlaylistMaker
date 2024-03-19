package gen.test.android.playlistmaker

import android.content.Context
import android.util.TypedValue
import java.text.SimpleDateFormat
import java.util.*

object Utils {
    fun millisToMmSs(millis: Int): String {
       return SimpleDateFormat("mm:ss", Locale.getDefault()).format(millis)
    }
    fun dpToPx(dp: Float, context: Context): Int {
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            dp,
            context.resources.displayMetrics).toInt()
    }
}