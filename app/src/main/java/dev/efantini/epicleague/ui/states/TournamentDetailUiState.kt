package dev.efantini.epicleague.ui.states

import dev.efantini.epicleague.data.models.Player
import dev.efantini.epicleague.data.models.Tournament

data class TournamentDetailUiState(
    val tournament: Tournament? = null,
    val tournamentPlayers: List<TournamentPlayerItemUiState> = listOf(),
    val availablePlayers: List<Player> = listOf()
)
