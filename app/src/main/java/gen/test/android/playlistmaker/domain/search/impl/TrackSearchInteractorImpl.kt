package gen.test.android.playlistmaker.domain.search.impl

import gen.test.android.playlistmaker.data.search.SearchRepository
import gen.test.android.playlistmaker.domain.search.TrackSearchInteractor
import gen.test.android.playlistmaker.domain.search.model.TrackSearch
import gen.test.android.playlistmaker.utils.Resource
import java.util.concurrent.Executors

class TrackSearchInteractorImpl(private val repository: SearchRepository) :
    TrackSearchInteractor {
    private val executor = Executors.newCachedThreadPool()
    override fun searchTrack(expression: String, consumer: TrackSearchInteractor.TrackSearchConsumer) {
        executor.execute{
            when(val resource = repository.searchTrack(expression))
            {
                is Resource.Success -> {
                    consumer.consume(resource.data as ArrayList<TrackSearch>, false)
                }
                else -> {consumer.consume(null, true)}
            }
        }
    }

}


