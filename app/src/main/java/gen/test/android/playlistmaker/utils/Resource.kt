package gen.test.android.playlistmaker.utils

sealed class Resource<T>(val data: T? = null, val message: String? = null)
{
    class Success<T>(data: T): Resource<T>(data)
    class NotFound<T> : Resource<T>()
    class ComProblem<T> : Resource<T>()
    
    
}