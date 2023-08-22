package ke.newsarticles.feature_tourist.data.api

import ke.newsarticles.feature_tourist.data.dto.TouristResponseDto
import retrofit2.http.GET


interface TouristApi {

    @GET("Tourist?page=2")
    suspend fun fetchTourists(): TouristResponseDto

}