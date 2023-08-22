package ke.newsarticles.feature_news.data.mappers

import ke.newsarticles.core_database.data.entities.MultiMediaEntity
import ke.newsarticles.core_database.data.entities.NewsModelEntity
import ke.newsarticles.core_database.data.entities.UserEntity
import ke.newsarticles.feature_news.domain.models.MultiMedia
import ke.newsarticles.feature_news.domain.models.NewsModel
import ke.newsarticles.feature_news.domain.models.User

fun UserEntity.toUser(): User = User(
    name = name,
    profilepicture = profilepicture,
    userid = userid
)

fun User.toUserEntity(newsId: Int): UserEntity = UserEntity(
    name = name,
    profilepicture = profilepicture,
    userid = userid,
)

fun MultiMediaEntity.toMultiMedia(): MultiMedia = MultiMedia(
    createat = createat,
    description = description,
    id = id,
    name = name,
    title = title,
    url = url
)

fun MultiMedia.toMultiMediaEntity(newsId: Int): MultiMediaEntity = MultiMediaEntity(
    createat = createat,
    description = description,
    id = id,
    name = name,
    title = title,
    url = url,
)

fun NewsModelEntity.toNewsModel() = NewsModel(
    commentCount = commentCount,
    createdat = createdat,
    description = description,
    id = id,
    location = location,
    title = title,
    user = this.user?.toUser(),
    multiMedia = multiMedia?.map { k -> k?.toMultiMedia() },
)

fun NewsModel.toNewsModelEntity(newsId: Int): NewsModelEntity = NewsModelEntity(
    commentCount = commentCount,
    createdat = createdat,
    description = description,
    id = id,
    location = location,
    title = title,
    user = user?.toUserEntity(newsId),
    multiMedia = multiMedia?.map { k -> k?.toMultiMediaEntity(newsId) },
)