package ke.newsarticles.core_utils.navigation

sealed class UiEvent {
    data class OnNavigate(val route: String, val args: String? = null) : UiEvent()
}