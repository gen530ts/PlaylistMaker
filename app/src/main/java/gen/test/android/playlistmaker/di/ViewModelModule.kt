package gen.test.android.playlistmaker.di

import gen.test.android.playlistmaker.ui.createplaylist.CreatePlayListViewModel
import gen.test.android.playlistmaker.ui.favtracks.FavTracksViewModel
import gen.test.android.playlistmaker.ui.playlists.PlayListsViewModel
import gen.test.android.playlistmaker.ui.player.view_model.PlayerViewModel
import gen.test.android.playlistmaker.ui.search.view_model.SearchViewModel
import gen.test.android.playlistmaker.ui.settings.view_model.SettingsViewModel
import org.koin.dsl.module
import org.koin.core.module.dsl.*

val viewModelModule = module {

    viewModel {
        PlayerViewModel(get(),get(),get(),get())
    }

    viewModel {
        SearchViewModel(get(),get())
    }

    viewModel {
        SettingsViewModel(get(),get())
    }

    viewModel {
        FavTracksViewModel(get())
    }

    viewModel {
        PlayListsViewModel(get())
    }

    viewModel {
        CreatePlayListViewModel(get())
    }
}