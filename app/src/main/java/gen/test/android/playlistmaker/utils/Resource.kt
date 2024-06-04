package gen.test.android.playlistmaker.utils

sealed class Resource<T>(val data: T? = null, val message: String? = null)
{
    class Success<T>(data: T): Resource<T>(data)
    class NotFound<T> : Resource<T>()
    class ComProblem<T> : Resource<T>()
    /*object NotFound: Resource<T>(val data: T? = null, val message: String? = null)
    object ComProblem: Resource<T>()*/
    //class Error<T>(message: String, data: T? = null): Resource<T>(data, message)
}