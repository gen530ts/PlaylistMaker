package gen.test.android.playlistmaker.ui.settings.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewmodel.CreationExtras
import gen.test.android.playlistmaker.creator.Creator
import gen.test.android.playlistmaker.domain.settings.ThemeInteractor
import gen.test.android.playlistmaker.domain.settings.model.ThemeSettings
import gen.test.android.playlistmaker.domain.sharing.SharingInteractor

class SettingsViewModel(
    private val themeInteractor: ThemeInteractor,
    private val sharingInteractor: SharingInteractor
) : ViewModel() {
    companion object {
        fun getViewModelFactory(): ViewModelProvider.Factory =
            object : ViewModelProvider.Factory {
                @Suppress("UNCHECKED_CAST")
                override fun <T : ViewModel> create(
                    modelClass: Class<T>,
                    extras: CreationExtras
                ): T {
                    val application = checkNotNull(extras[APPLICATION_KEY])
                    return SettingsViewModel(
                        Creator.provideThemeInteractor(application),
                        Creator.provideSharingInteractor(application.applicationContext)
                    ) as T
                }
            }
    }

    private val themeLiveData = MutableLiveData<ThemeSettings>()
    fun getThemeLiveData(): LiveData<ThemeSettings> = themeLiveData


    fun getTheme()
    {
      themeLiveData.value=  themeInteractor.getTheme()
    }




    fun switchTheme(isDark:Boolean){
        themeInteractor.switchTheme(ThemeSettings(isDark))
        getTheme()
    }





    fun sharingApp(){
        sharingInteractor.shareApp()
    }
    fun openTerms(){
        sharingInteractor.openTerms()
    }
    fun openSupport(){
        sharingInteractor.openSupport()
    }
}