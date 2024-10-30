package gen.test.android.playlistmaker.di

import gen.test.android.playlistmaker.ui.media.view_model.FavTracksViewModel
import gen.test.android.playlistmaker.ui.media.view_model.PlayListsViewModel
import gen.test.android.playlistmaker.ui.player.view_model.PlayerViewModel
import gen.test.android.playlistmaker.ui.search.view_model.SearchViewModel
import gen.test.android.playlistmaker.ui.settings.view_model.SettingsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {

    viewModel {
        PlayerViewModel(get(),get(),get())
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
        PlayListsViewModel()
    }
}