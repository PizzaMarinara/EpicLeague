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
abstract class AppDatabase : RoomDatabase() {
    abstract fun deckDao(): DeckDao
    abstract fun cardDao(): CardDao
    abstract fun deckCardRefDao(): DeckCardRefDao
}
