package gen.test.android.playlistmaker.ui.playlist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import gen.test.android.playlistmaker.domain.db.PlistInteractor
import gen.test.android.playlistmaker.domain.models.PlistViewScreen
import gen.test.android.playlistmaker.domain.sharing.SharingInteractor
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class PlistViewViewModel(
    private val playlistId: Long,
    private val plistInteractor: PlistInteractor,
    private val sharingInteractor: SharingInteractor
) : ViewModel() {

    private var playlistStateLiveData = MutableLiveData<PlistViewScreen>()
    fun observePlist(): LiveData<PlistViewScreen> = playlistStateLiveData


    init {
        viewModelScope.launch {
            plistInteractor.getPlistViewDataFlow(playlistId).collect {
                playlistStateLiveData.postValue(it)
            }
        }
    }

    fun delTrackInPlist(idTrack: Int) {
        viewModelScope.launch {
            plistInteractor.delTrackInPlist(playlistId, idTrack)
        }
    }

    @OptIn(DelicateCoroutinesApi::class)
    fun delPlist() {
        GlobalScope.launch {
            val plist = playlistStateLiveData.value
            if ((plist != null)&&(plist.id!=null)) {
              //  Log.d("mytag", "del plist name=${plist.name}")
                plistInteractor.delPlistById(plist.id)
            }
        }
    }

    fun sharePlist() {
        val plist = playlistStateLiveData.value
        if (plist != null) {
            sharingInteractor.sharePlist(plist)
        }
    }
}


// playlistStateLiveData.postValue(plistInteractor.getPlistViewData(playlistId))
/*    private var tracksStateLiveData = MutableLiveData<List<Track>>()
    fun observeTracks(): LiveData<List<Track>> = tracksStateLiveData

    private var durationTracksLiveData = MutableLiveData("")
    fun observeDurationTracks(): LiveData<String> = durationTracksLiveData*/
/*Log.d("mytag", "Start")
val num1: Deferred<Int> = async {
    delay(4000)
    return@async 1
}
val num2: Deferred<Int> = async {
    delay(2000)
    return@async 2
}
val res2= num2.await()
Log.d("mytag", "await2")
val res1= num1.await()
Log.d("mytag", "await1")

val res3=res1+res2
Log.d("mytag", "res3=$res3")*/


// playlistStateLiveData.value = Plist(name = "NamePlist")
/*
plistInteractor.getTracksByPlistId(playlistId).collect {
    tracksStateLiveData.postValue(it)
    var durationTracksMillis = 0
    it.forEach { drt ->
        Log.d("mytag", "drt.trackTimeMillis=${drt.trackTimeMillis}")
        durationTracksMillis += drt.trackTimeMillis

    }
    Log.d("mytag", "durationTracksMillis = $durationTracksMillis")
    durationTracksLiveData.postValue(
        SimpleDateFormat("mm", Locale.getDefault()).format
            (durationTracksMillis)
    )
}
playlistStateLiveData.postValue(plistInteractor.getPlistById(playlistId))*/
