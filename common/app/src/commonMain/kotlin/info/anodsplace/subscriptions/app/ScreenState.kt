package info.anodsplace.subscriptions.app

sealed interface ScreenState {
    object Initial : ScreenState
    object Loading : ScreenState
    object Ready: ScreenState
    data class Error(val message: String): ScreenState
}