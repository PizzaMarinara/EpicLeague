package dev.efantini.pauperarena.ui.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.efantini.pauperarena.data.datasources.PlayerRepository
import dev.efantini.pauperarena.data.models.Player
import dev.efantini.pauperarena.ui.states.PlayerItemUiState
import dev.efantini.pauperarena.ui.states.PlayerListUiState
import javax.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
class PlayerViewModel @Inject constructor() : ViewModel() {

    private val playerRepository = PlayerRepository.getInstance()

    init {
        getPlayers()
    }

    var playersListContentUiState by mutableStateOf(PlayerListUiState())
        private set

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
