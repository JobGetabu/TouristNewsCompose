package ke.newsarticles.core_database.data.entities

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import ke.newsarticles.core_database.data.converters.MultiMediaListTypeConverter

@Entity( tableName = "news")
@kotlinx.serialization.Serializable
data class NewsModelEntity(
    @PrimaryKey
    val id: Int?,
    val commentCount: Int?,
    val createdat: String?,
    val description: String?,
    val location: String?,
    @TypeConverters(MultiMediaListTypeConverter::class)
    val multiMedia: List<MultiMediaEntity?>? = arrayListOf(),
    val title: String?,
    @Embedded(prefix = "user_")
    val user: UserEntity?
)