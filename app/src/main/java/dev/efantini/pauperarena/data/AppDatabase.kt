package dev.efantini.pauperarena.data

import android.content.Context
import androidx.room.Room
import java.lang.Exception

object AppDatabase {
    private var appDatabase: AbstractAppDatabase? = null
    fun init(context: Context) {
        appDatabase = Room.databaseBuilder(
            context.applicationContext,
            AbstractAppDatabase::class.java,
            "pauper-arena-db"
        ).build()
    }

    fun get(): AbstractAppDatabase {
        return appDatabase ?: throw Exception("Database not initialized")
    }
}
