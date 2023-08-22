package ke.newsarticles.core_utils.utils

import java.text.SimpleDateFormat
import java.util.*

object TimeUtils {
    fun formatDate(date: String?, originFormat: String, toFormat: String): String? {
        return try {
            val parsedDate = SimpleDateFormat(originFormat, Locale.getDefault()).parse(date)
            SimpleDateFormat(toFormat, Locale.getDefault()).format(parsedDate)
        } catch (e: Exception) {
            date
        }
    }
}