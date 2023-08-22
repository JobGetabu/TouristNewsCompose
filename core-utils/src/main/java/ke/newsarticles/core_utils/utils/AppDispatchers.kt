package ke.newsarticles.utils

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

interface AppDispatchers {
    fun io(): CoroutineDispatcher
    fun main(): CoroutineDispatcher
    fun defaul(): CoroutineDispatcher
}

class AppDispatchersImpl : AppDispatchers {
    override fun io(): CoroutineDispatcher = Dispatchers.IO
    override fun main(): CoroutineDispatcher = Dispatchers.Main
    override fun defaul(): CoroutineDispatcher = Dispatchers.Default
}
