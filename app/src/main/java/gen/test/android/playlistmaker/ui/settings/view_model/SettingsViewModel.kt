package gen.test.android.playlistmaker.ui.settings.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import gen.test.android.playlistmaker.domain.settings.ThemeInteractor
import gen.test.android.playlistmaker.domain.settings.model.ThemeSettings
import gen.test.android.playlistmaker.domain.sharing.SharingInteractor

class SettingsViewModel(
    private val themeInteractor: ThemeInteractor,
    private val sharingInteractor: SharingInteractor
) : ViewModel() {


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


