package dev.efantini.epicleague.data

import android.content.Context
import android.util.Log
import dev.efantini.epicleague.BuildConfig
import dev.efantini.epicleague.data.models.MyObjectBox
import io.objectbox.BoxStore
import io.objectbox.android.AndroidObjectBrowser
import java.lang.Exception

object ObjectBox {
    private var boxStore: BoxStore? = null
    fun init(context: Context) {
        boxStore = MyObjectBox.builder()
            .androidContext(context.applicationContext)
            .build()
        if (BuildConfig.DEBUG) {
            val started = AndroidObjectBrowser(boxStore).start(context.applicationContext)
            Log.i("ObjectBrowser", "Started: $started")
        }
    }

    fun get(): BoxStore {
        return boxStore ?: throw Exception("BoxStore not initialized")
    }
}
