package ke.newsarticles.core_database.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tourists")
@kotlinx.serialization.Serializable
data class TouristEntity(
    @PrimaryKey
    val id: Int?,
    val createdat: String?,
    val touristEmail: String?,
    val touristLocation: String?,
    val touristName: String?
)
