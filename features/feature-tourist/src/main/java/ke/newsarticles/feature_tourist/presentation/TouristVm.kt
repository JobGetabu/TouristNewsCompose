package ke.newsarticles.feature_tourist.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import ke.newsarticles.core_network.data.ResponseState
import ke.newsarticles.feature_tourist.domain.models.TouristModel
import ke.newsarticles.feature_tourist.domain.repositories.TouristRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


data class NewsUiState(
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val tourist: List<TouristModel> = emptyList()
)

@HiltViewModel
class TouristVm  @Inject constructor(private val touristRepository: TouristRepository): ViewModel() {
    private val _touristUiState = MutableStateFlow(NewsUiState())
    val touristUiState = _touristUiState.asStateFlow()

    init {
        fetchTourists()
        listenToDbUpdates()
    }

    private fun fetchTourists(){
        viewModelScope.launch{
            _touristUiState.update { it.copy(isLoading = true) }
            when(touristRepository.getTourists()){
                is ResponseState.Success -> {}
                is ResponseState.Error -> {
                    _touristUiState.update { it.copy(isLoading = false, errorMessage = it.errorMessage) }
                }
            }
        }
    }

    private fun listenToDbUpdates() {
        viewModelScope.launch {
            touristRepository.listenForTourists().collectLatest { tourist ->
                if (tourist.isNotEmpty()) {
                    _touristUiState.update {
                        it.copy(isLoading = false, errorMessage = null, tourist = tourist)
                    }
                }
            }
        }
    }
}