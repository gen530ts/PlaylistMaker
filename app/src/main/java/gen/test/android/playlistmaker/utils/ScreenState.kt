package gen.test.android.playlistmaker.utils

sealed class ScreenState<out T> {
    data class Success<T>(val data: T) : ScreenState<T>()
    object Warning: ScreenState<Nothing>()
}