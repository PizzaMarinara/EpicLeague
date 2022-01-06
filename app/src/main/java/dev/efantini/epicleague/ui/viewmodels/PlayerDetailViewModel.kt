package dev.efantini.epicleague.ui.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.efantini.epicleague.data.datasources.DeckRepository
import dev.efantini.epicleague.data.datasources.PlayerRepository
import dev.efantini.epicleague.ui.states.PlayerDetailUiState
import javax.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
class PlayerDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val playerRepository = PlayerRepository.getInstance()
    private val deckRepository = DeckRepository.getInstance()

    var playerDetailContentUiState by mutableStateOf(PlayerDetailUiState())
        private set

    init {
        val playerId = savedStateHandle.get<Long>("playerId")
        viewModelScope.launch {
            playerId?.let {
                playerDetailContentUiState = playerDetailContentUiState.copy(
                    player = playerRepository.getPlayerById(playerId),
                    decks = deckRepository.getDecksForPlayer(playerId)
                )
            }
        }
    }
}
