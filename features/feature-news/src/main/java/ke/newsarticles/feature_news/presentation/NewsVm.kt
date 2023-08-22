package ke.newsarticles.feature_news.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import ke.newsarticles.core_network.data.ResponseState
import ke.newsarticles.feature_news.domain.models.NewsModel
import ke.newsarticles.feature_news.domain.repositories.NewsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class NewsUiState(
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val news: List<NewsModel> = emptyList()
)

@HiltViewModel
class NewsVm @Inject constructor(private val newsRepository: NewsRepository) : ViewModel() {

    private val _newsUiState = MutableStateFlow(NewsUiState())
    val newsUiState = _newsUiState.asStateFlow()

    init {
        fetchNews()
        listenToDbUpdates()
    }

    private fun fetchNews(){
        viewModelScope.launch{
            _newsUiState.update { it.copy(isLoading = true) }
            when(newsRepository.getNewsArticles()){
                is ResponseState.Success -> {}
                is ResponseState.Error -> {
                    _newsUiState.update { it.copy(isLoading = true, errorMessage = it.errorMessage) }
                }
            }
        }
    }

    private fun listenToDbUpdates() {
        viewModelScope.launch {
            newsRepository.listenForNewsArticles().collectLatest { news ->
                if (news.isNotEmpty()) {
                    _newsUiState.update {
                        it.copy(isLoading = false, errorMessage = null, news = news)
                    }
                }
            }
        }
    }

}