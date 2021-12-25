package dev.efantini.pauperarena

import android.app.Application
import com.google.android.play.core.missingsplits.MissingSplitsManagerFactory
import dev.efantini.pauperarena.data.ObjectBox

class PauperArenaApplication : Application() {

    override fun onCreate() {
        if (MissingSplitsManagerFactory.create(this).disableAppIfMissingRequiredSplits()) {
            return
        }
        super.onCreate()
        ObjectBox.init(this)
    }
}
