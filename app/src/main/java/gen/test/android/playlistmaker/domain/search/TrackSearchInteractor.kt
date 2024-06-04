package gen.test.android.playlistmaker.domain.search

import gen.test.android.playlistmaker.domain.search.model.TrackSearch

interface TrackSearchInteractor {
    fun searchTrack(expression: String, consumer: TrackSearchConsumer)
    interface TrackSearchConsumer
    {
        fun consume(foundMovies: ArrayList<TrackSearch>?, isError: Boolean)
    }
}