package dev.efantini.epicleague.ui.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
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
) : ViewModel() {

    private val tournamentRepository = TournamentRepository.getInstance()
    private val tournamentPlayerRepository = TournamentPlayerRepository.getInstance()

    var tournamentDetailContentUiState by mutableStateOf(TournamentDetailUiState())
        private set

    val tournamentId = savedStateHandle.get<Long>("tournamentId")

    init {
        viewModelScope.launch {
            tournamentId?.let {
                tournamentDetailContentUiState = tournamentDetailContentUiState.copy(
                    tournament = tournamentRepository.getElementById(tournamentId),
                    tournamentPlayers = tournamentPlayerRepository
                        .getPlayersForTournament(tournamentId)
                        .map { TournamentPlayerItemUiState(it) },
                    availablePlayers = tournamentRepository.getPlayersNotInTournament(tournamentId)
                )
            }
        }
    }

    private fun getTournamentPlayers() {
        viewModelScope.launch {
            tournamentId?.let {
                tournamentDetailContentUiState = tournamentDetailContentUiState.copy(
                    tournamentPlayers = tournamentPlayerRepository
                        .getPlayersForTournament(tournamentId)
                        .map { TournamentPlayerItemUiState(it) }
                )
            }
        }
    }

    fun putTournamentPlayers(items: List<TournamentPlayer>) {
        viewModelScope.launch {
            tournamentPlayerRepository.putItems(items)
            getTournamentPlayers()
        }
    }

    private fun refreshPlayers() {
        viewModelScope.launch {
            tournamentId?.let {
                tournamentDetailContentUiState = tournamentDetailContentUiState.copy(
                    availablePlayers = tournamentRepository.getPlayersNotInTournament(tournamentId)
                )
            }
        }
    }
}
