package ke.newsarticles.core_database.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
@kotlinx.serialization.Serializable
data class UserEntity (
    @PrimaryKey
    val userid: Int?,
    val name: String?,
    val profilepicture: String?,
)