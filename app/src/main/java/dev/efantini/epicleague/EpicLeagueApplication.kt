package dev.efantini.epicleague

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import dev.efantini.epicleague.data.ObjectBox

@HiltAndroidApp
class EpicLeagueApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        ObjectBox.init(this)
    }
}
