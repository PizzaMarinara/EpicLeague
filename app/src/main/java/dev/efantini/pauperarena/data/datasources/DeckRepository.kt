package dev.efantini.pauperarena.data.datasources

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

class DeckRepository private constructor(
    private val defaultDispatcher: CoroutineDispatcher
) {
    companion object {
        @Volatile
        private var INSTANCE: DeckRepository? = null
        fun getInstance(
            defaultDispatcher: CoroutineDispatcher = Dispatchers.IO
        ): DeckRepository = INSTANCE ?: synchronized(this) {
            INSTANCE ?: DeckRepository(defaultDispatcher).also { INSTANCE = it }
        }
    }
}
