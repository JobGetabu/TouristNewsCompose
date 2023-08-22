package ke.newsarticles.core_database.data.converters

import androidx.room.TypeConverter
import ke.newsarticles.core_database.data.entities.MultiMediaEntity
import kotlinx.serialization.json.Json
import kotlinx.serialization.encodeToString
import kotlinx.serialization.decodeFromString

class MultiMediaListTypeConverter {

    @TypeConverter
    fun fromMultiMediaList(list: List<MultiMediaEntity?>?): String {
        return Json.encodeToString(list)
    }

    @TypeConverter
    fun toMultiMediaList(json: String?): List<MultiMediaEntity?> {
        return Json.decodeFromString(json ?: "[]")
    }
}