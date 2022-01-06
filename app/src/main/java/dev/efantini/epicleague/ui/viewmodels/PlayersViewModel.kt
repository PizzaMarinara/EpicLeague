package dev.efantini.epicleague.ui.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.efantini.epicleague.data.datasources.PlayerRepository
import dev.efantini.epicleague.data.models.Player
import dev.efantini.epicleague.ui.states.PlayerItemUiState
import dev.efantini.epicleague.ui.states.PlayersListUiState
import javax.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
class PlayersViewModel @Inject constructor() : ViewModel() {

    private val playerRepository = PlayerRepository.getInstance()

    var playersListContentUiState by mutableStateOf(PlayersListUiState())
        private set

    init {
        getPlayers()
    }

    private fun getPlayers() {
        viewModelScope.launch {
            val items = playerRepository.getItems()
            playersListContentUiState = playersListContentUiState.copy(
                playerItems = items.map {
                    PlayerItemUiState(player = it)
                }
            )
        }
    }

    fun putPlayers(items: List<Player>) {
        viewModelScope.launch {
            playerRepository.putItems(items)
            getPlayers()
        }
    }

    fun deletePlayers(items: List<Player>) {
        viewModelScope.launch {
            playerRepository.deleteItems(items)
            getPlayers()
        }
    }
}
