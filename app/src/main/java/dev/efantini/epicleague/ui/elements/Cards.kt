package dev.efantini.epicleague.ui.elements

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import dev.efantini.epicleague.ui.states.PlayerItemUiState

@Composable
fun PlayerCard(
    item: PlayerItemUiState,
) {
    Card(
        elevation = 4.dp,
        shape = RoundedCornerShape(15.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Spacer(Modifier.width(16.dp))
            Column(Modifier.padding(top = 0.dp, bottom = 0.dp)) {
                Text(
                    text = item.player.fullName,
                    style = MaterialTheme.typography.h6.copy(color = Color.Black)
                )
            }
        }
    }
}

@Composable
fun DialogCard(
    onDismissRequest: () -> Unit,
    properties: DialogProperties = DialogProperties(),
    content: @Composable () -> Unit,
) {
    Dialog(
        onDismissRequest = onDismissRequest,
        properties = properties
    ) {
        Card(
            elevation = 4.dp,
            shape = RoundedCornerShape(15.dp)
        ) {
            Surface(modifier = Modifier.padding(15.dp)) {
                content()
            }
        }
    }
}
