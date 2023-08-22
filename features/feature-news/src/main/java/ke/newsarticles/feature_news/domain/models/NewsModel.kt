package ke.newsarticles.feature_news.domain.models

import androidx.annotation.Keep
import kotlinx.serialization.SerialName

@Keep
@kotlinx.serialization.Serializable
data class NewsModel(
    @SerialName("commentCount")
    val commentCount: Int?,
    @SerialName("createdat")
    val createdat: String?,
    @SerialName("description")
    val description: String?,
    @SerialName("id")
    val id: Int?,
    @SerialName("location")
    val location: String?,
    @SerialName("multiMedia")
    val multiMedia: List<MultiMedia?>?,
    @SerialName("title")
    val title: String?,
    @SerialName("user")
    val user: User?
)