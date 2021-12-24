package dev.efantini.pauperarena.data.datasources

import dev.efantini.pauperarena.data.models.Deck

interface DeckRepository {
    suspend fun getDecks(): List<Deck>
}
