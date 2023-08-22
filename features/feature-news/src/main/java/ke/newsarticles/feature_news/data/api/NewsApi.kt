package ke.newsarticles.feature_news.data.api

import ke.newsarticles.feature_news.data.dto.NewsResponseDto
import kotlinx.serialization.json.JsonObject
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST


interface NewsApi {

    @GET("Feed/GetNewsFeed")
    suspend fun fetchNews(): NewsResponseDto

}