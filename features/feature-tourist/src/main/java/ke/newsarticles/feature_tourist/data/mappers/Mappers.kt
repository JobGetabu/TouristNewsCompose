package ke.newsarticles.feature_tourist.data.mappers

import ke.newsarticles.core_database.data.entities.TouristEntity
import ke.newsarticles.feature_tourist.domain.models.TouristModel

fun TouristEntity.toTouristModel(): TouristModel = TouristModel(
    createdat = createdat,
    id = id,
    touristEmail = touristEmail,
    touristLocation = touristLocation,
    touristName = touristName
)

fun TouristModel.toTouristEntity(): TouristEntity = TouristEntity(
    createdat = createdat,
    id = id,
    touristEmail = touristEmail,
    touristLocation = touristLocation,
    touristName = touristName
)