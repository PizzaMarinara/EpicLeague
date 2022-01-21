package dev.efantini.epicleague.ui.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.efantini.epicleague.data.datasources.TournamentRepository
import dev.efantini.epicleague.data.models.Tournament
import dev.efantini.epicleague.ui.states.TournamentItemUiState
import dev.efantini.epicleague.ui.states.TournamentListUiState
import javax.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
class TournamentListViewModel @Inject constructor(
    private val tournamentRepository: TournamentRepository
) : ViewModel() {

    var tournamentListContentUiState by mutableStateOf(TournamentListUiState())
        private set

    init {
        getTournaments()
    }

    private fun getTournaments() {
        viewModelScope.launch {
            val items = tournamentRepository.getItems()
            tournamentListContentUiState = tournamentListContentUiState.copy(
                tournamentItems = items.map {
                    TournamentItemUiState(tournament = it)
                }
            )
        }
    }

    fun putTournaments(items: List<Tournament>) {
        viewModelScope.launch {
            tournamentRepository.putItems(items)
            getTournaments()
        }
    }

    fun deleteTournaments(items: List<Tournament>) {
        viewModelScope.launch {
            tournamentRepository.deleteItems(items)
            getTournaments()
        }
    }
}
