package dev.efantini.pauperarena.data.datasources

import dev.efantini.pauperarena.data.models.Deck

interface DeckDatasource {
    fun getDecks(): List<Deck>
    // fun putDecks(decks: List<Deck>)
    // fun deleteDecks(decks: List<Deck>)
}
