package dev.efantini.pauperarena.data

import androidx.room.Database
import androidx.room.RoomDatabase
import dev.efantini.pauperarena.data.dao.CardDao
import dev.efantini.pauperarena.data.dao.DeckCardRefDao
import dev.efantini.pauperarena.data.dao.DeckDao
import dev.efantini.pauperarena.data.models.Card
import dev.efantini.pauperarena.data.models.Deck
import dev.efantini.pauperarena.data.models.DeckCardRef

@Database(
    entities = [
        Deck::class, DeckCardRef::class, Card::class
    ],
    version = 1
)
abstract class AbstractAppDatabase : RoomDatabase() {
    abstract fun getDeckDao(): DeckDao
    abstract fun getCardDao(): CardDao
    abstract fun getDeckCardRefDao(): DeckCardRefDao
}
