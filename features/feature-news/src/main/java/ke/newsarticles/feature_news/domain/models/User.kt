package ke.newsarticles.feature_news.domain.models


import androidx.annotation.Keep
import kotlinx.serialization.SerialName

@Keep
@kotlinx.serialization.Serializable
data class User(
    @SerialName("name")
    val name: String?,
    @SerialName("profilepicture")
    val profilepicture: String?,
    @SerialName("userid")
    val userid: Int?
)