package gen.test.android.playlistmaker.creator

object Creator {
 /*   val getTrack: GetTrackUseCase = GetTrackUseCaseImpl(GetTrackRepositoryImpl())

    fun providePlayerInteractor(): PlayerInteractor {
        return PlayerInteractorImpl(providePlayer())
    }

    private fun providePlayer(): MediaPlayerWrapper {
        return MediaPlayerWrapperImpl(MediaPlayer())
    }

    private fun provideExternalNavigator(context: Context): ExternalNavigator {
        return ExternalNavigatorImpl(context)
    }

    private fun provideSharingRepository(context: Context): SharingRepository {
        return SharingRepositoryImpl(context)
    }

    fun provideSharingInteractor(
        context: Context,
       
       
    ): SharingInteractor {
        return SharingInteractorImpl(
            provideExternalNavigator(context),
            provideSharingRepository(context)
        )
    }
    fun provideThemeInteractor(application:Application):ThemeInteractor{
        return ThemeInteractorImpl(ThemeRepositoryImpl(application))
    }*/

    /*private fun getSearchRepository(context: Context): SearchRepository {
        val baseUrl = "https://itunes.apple.com"
        val retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val musicService = retrofit.create(ItunesAppleApi::class.java)
        return SearchRepositoryImpl(RetrofitNetworkClient(context,musicService))
    }

    fun provideSearchInteractor(context: Context): TrackSearchInteractor {
        return TrackSearchInteractorImpl(getSearchRepository(context))
    }*/

    /*private fun getHistoryRepository(shPref:SharedPreferences): HistoryRepository {
        return HistoryRepositoryImpl(HistoryManagerImpl(shPref, Gson()))
    }

    fun provideHistoryInteractor(shPref:SharedPreferences): HistoryInteractor {
        return HistoryInteractorImpl(getHistoryRepository(shPref))
    }*/
}
