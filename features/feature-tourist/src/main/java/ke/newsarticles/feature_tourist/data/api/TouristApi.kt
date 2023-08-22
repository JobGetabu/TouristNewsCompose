package ke.newsarticles.feature_tourist.data.api

import ke.newsarticles.feature_tourist.data.dto.TouristResponseDto
import retrofit2.http.GET
import retrofit2.http.Query


interface TouristApi {

    @GET("Tourist")
    suspend fun fetchTourists(
        @Query("page") page: Int = 1
    ): TouristResponseDto

}