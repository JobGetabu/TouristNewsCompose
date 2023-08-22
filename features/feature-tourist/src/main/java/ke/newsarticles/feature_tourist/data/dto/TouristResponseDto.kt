package ke.newsarticles.feature_tourist.data.dto
import androidx.annotation.Keep
import ke.newsarticles.feature_tourist.domain.models.TouristModel
import kotlinx.serialization.SerialName

@Keep
@kotlinx.serialization.Serializable
data class TouristResponseDto(
    @SerialName("data")
    val `data`: List<TouristModel?>?,
    @SerialName("page")
    val page: Int?,
    @SerialName("per_page")
    val perPage: Int?,
    @SerialName("total_pages")
    val totalPages: Int?,
    @SerialName("totalrecord")
    val totalrecord: Int?
)
