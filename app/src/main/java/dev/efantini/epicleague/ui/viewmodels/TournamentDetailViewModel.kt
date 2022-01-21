package dev.efantini.epicleague.ui.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.efantini.epicleague.data.datasources.PlayerRepository
import dev.efantini.epicleague.data.datasources.TournamentPlayerRepository
import dev.efantini.epicleague.data.datasources.TournamentRepository
import dev.efantini.epicleague.data.models.TournamentPlayer
import dev.efantini.epicleague.ui.states.TournamentDetailUiState
import dev.efantini.epicleague.ui.states.TournamentPlayerItemUiState
import javax.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
class TournamentDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val tournamentRepository: TournamentRepository,
    private val tournamentPlayerRepository: TournamentPlayerRepository,
    private val playerRepository: PlayerRepository
) : ViewModel() {

    var tournamentDetailContentUiState by mutableStateOf(TournamentDetailUiState())
        private set

    val tournamentId = savedStateHandle.get<Long>("tournamentId")

    init {
        viewModelScope.launch {
            tournamentId?.let {
                tournamentRepository.getElementById(tournamentId)?.let { tournament ->
                    tournamentDetailContentUiState = tournamentDetailContentUiState.copy(
                        tournament = tournament,
                    )
                }
            }
            getTournamentPlayers()
        }
    }

    private suspend fun getTournamentPlayers() {
        tournamentDetailContentUiState = tournamentDetailContentUiState.copy(
            tournamentPlayers = tournamentPlayerRepository
                .getPlayersForTournament(tournamentDetailContentUiState.tournament.id)
                .map { TournamentPlayerItemUiState(it) },
            availablePlayers = playerRepository
                .getPlayersNotInTournament(tournamentDetailContentUiState.tournament.id)
        )
    }

    fun putTournamentPlayers(items: List<TournamentPlayer>) {
        viewModelScope.launch {
            tournamentDetailContentUiState
                .tournament.tournamentPlayers.addAll(items)
            tournamentRepository.putItems(
                listOf(
                    tournamentDetailContentUiState
                        .tournament
                )
            )
            getTournamentPlayers()
        }
    }
}
