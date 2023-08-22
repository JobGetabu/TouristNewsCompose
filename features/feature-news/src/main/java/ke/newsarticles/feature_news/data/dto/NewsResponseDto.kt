package ke.newsarticles.feature_news.data.dto

import androidx.annotation.Keep
import ke.newsarticles.feature_news.domain.models.NewsModel
import kotlinx.serialization.SerialName



@Keep
@kotlinx.serialization.Serializable
data class NewsResponseDto(
    @SerialName("data")
    val `data`: List<NewsModel?>?,
    @SerialName("page")
    val page: Int?,
    @SerialName("per_page")
    val perPage: Int?,
    @SerialName("total_pages")
    val totalPages: Int?,
    @SerialName("totalrecord")
    val totalrecord: Int?
)
