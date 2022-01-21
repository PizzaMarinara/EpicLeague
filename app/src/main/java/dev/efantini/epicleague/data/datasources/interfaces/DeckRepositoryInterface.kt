package dev.efantini.epicleague.data.datasources.interfaces

import dev.efantini.epicleague.data.models.Deck

interface DeckRepositoryInterface : GenericRepositoryInterface<Deck> {
    suspend fun getDecksForPlayer(playerId: Long): List<Deck>
}
