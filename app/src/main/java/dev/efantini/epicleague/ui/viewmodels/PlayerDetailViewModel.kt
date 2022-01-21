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
import dev.efantini.epicleague.data.models.Deck
import dev.efantini.epicleague.ui.states.DeckItemUiState
import dev.efantini.epicleague.ui.states.PlayerDetailUiState
import javax.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
class PlayerDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val playerRepository: PlayerRepository,
    private val deckRepository: DeckRepository
) : ViewModel() {

    var playerDetailContentUiState by mutableStateOf(PlayerDetailUiState())
        private set

    val playerId = savedStateHandle.get<Long>("playerId")

    init {
        viewModelScope.launch {
            playerId?.let {
                playerRepository.getElementById(playerId)?.let { player ->
                    playerDetailContentUiState = playerDetailContentUiState.copy(
                        player = player
                    )
                    getDecks()
                }
            }
        }
    }

    private suspend fun getDecks() {
        playerDetailContentUiState = playerDetailContentUiState.copy(
            decks = deckRepository.getDecksForPlayer(playerDetailContentUiState.player.id)
                .map { DeckItemUiState(it) }
        )
    }

    fun putDecks(items: List<Deck>) {
        viewModelScope.launch {
            playerDetailContentUiState.player.decks.addAll(items)
            playerRepository.putItems(listOf(playerDetailContentUiState.player))
            getDecks()
        }
    }
}
