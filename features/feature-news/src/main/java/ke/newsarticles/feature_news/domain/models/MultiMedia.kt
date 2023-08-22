package ke.newsarticles.feature_news.domain.models

import androidx.annotation.Keep
import kotlinx.serialization.SerialName


@Keep
@kotlinx.serialization.Serializable
data class MultiMedia(
    @SerialName("createat")
    val createat: String?,
    @SerialName("description")
    val description: String?,
    @SerialName("id")
    val id: Int?,
    @SerialName("name")
    val name: String?,
    @SerialName("title")
    val title: String?,
    @SerialName("url")
    val url: String?
)