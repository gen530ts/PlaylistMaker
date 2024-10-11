package gen.test.android.playlistmaker.domain.search.impl

import gen.test.android.playlistmaker.data.search.SearchRepository
import gen.test.android.playlistmaker.domain.search.TrackSearchInteractor
import gen.test.android.playlistmaker.domain.search.model.TrackSearch
import gen.test.android.playlistmaker.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class TrackSearchInteractorImpl(private val repository: SearchRepository) : TrackSearchInteractor {

    override fun searchTrack(expression: String): Flow<Pair<List<TrackSearch>?, Boolean>> {
        return repository.searchTrack(expression).map { result ->
            when (result) {
                is Resource.Success -> {
                    Pair(result.data, false)
                }
                is Resource.NotFound -> {
                    Pair(ArrayList(), false)
                }
                else -> {Pair(null, true)}
            }
        }
    }
}



// private val executor = Executors.newCachedThreadPool()
/*executor.execute{
    when(val resource = repository.searchTrack(expression))
    {
        is Resource.Success -> {
            consumer.consume(resource.data as ArrayList<TrackSearch>, false)
        }
        is Resource.NotFound -> {
            consumer.consume(ArrayList(), false)
        }
        else -> {consumer.consume(null, true)}
    }
}*/


