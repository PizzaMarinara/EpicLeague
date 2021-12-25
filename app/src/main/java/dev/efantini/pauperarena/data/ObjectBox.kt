package dev.efantini.pauperarena.data

import android.content.Context
import dev.efantini.pauperarena.data.models.MyObjectBox
import io.objectbox.BoxStore
import java.lang.Exception

object ObjectBox {
    private var boxStore: BoxStore? = null
    fun init(context: Context) {
        boxStore = MyObjectBox.builder()
            .androidContext(context.applicationContext)
            .build()
    }

    fun get(): BoxStore {
        return boxStore ?: throw Exception("BoxStore not initialized")
    }
}
