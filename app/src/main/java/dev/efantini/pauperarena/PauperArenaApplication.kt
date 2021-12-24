package dev.efantini.pauperarena

import android.app.Application
import com.google.android.play.core.missingsplits.MissingSplitsManagerFactory
import dev.efantini.pauperarena.data.AppDatabase

class PauperArenaApplication : Application() {

    override fun onCreate() {
        if (MissingSplitsManagerFactory.create(this).disableAppIfMissingRequiredSplits()) {
            return
        }
        super.onCreate()
        AppDatabase.init(this)
    }
}
