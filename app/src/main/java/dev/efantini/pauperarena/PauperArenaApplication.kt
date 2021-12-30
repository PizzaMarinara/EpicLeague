package dev.efantini.pauperarena

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import dev.efantini.pauperarena.data.ObjectBox

@HiltAndroidApp
class PauperArenaApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        ObjectBox.init(this)
    }
}
