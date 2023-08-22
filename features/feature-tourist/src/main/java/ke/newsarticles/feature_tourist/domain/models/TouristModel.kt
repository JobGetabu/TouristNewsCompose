package ke.newsarticles.feature_tourist.domain.models
import androidx.annotation.Keep
import ke.newsarticles.core_database.data.entities.TouristEntity
import kotlinx.serialization.SerialName

@Keep
@kotlinx.serialization.Serializable
data class TouristModel(
    @SerialName("createdat")
    val createdat: String?,
    @SerialName("id")
    val id: Int?,
    @SerialName("tourist_email")
    val touristEmail: String?,
    @SerialName("tourist_location")
    val touristLocation: String?,
    @SerialName("tourist_name")
    val touristName: String?
)

