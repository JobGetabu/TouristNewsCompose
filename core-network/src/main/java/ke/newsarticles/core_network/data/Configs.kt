package ke.newsarticles.core_network.data

sealed class ResponseState<T> {
    data class Success<T>(val data: T) : ResponseState<T>()
    data class Error<T>(val error: String) : ResponseState<T>()
}