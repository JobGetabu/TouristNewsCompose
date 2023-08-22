package ke.newsarticles.core_database.data.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "multi_media")
@kotlinx.serialization.Serializable
data class MultiMediaEntity (
    @PrimaryKey
    val id: Int?,
    val createat: String?,
    val description: String?,
    val name: String?,
    val title: String?,
    val url: String?
)