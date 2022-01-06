package dev.efantini.epicleague.ui.elements

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.material.ExtendedFloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import dev.efantini.epicleague.R
import dev.efantini.epicleague.ui.theme.BOTTOMNAVBAR_HEIGHT
import dev.efantini.epicleague.ui.theme.DEFAULT_FLOATING_BUTTON_HEIGHT
import dev.efantini.epicleague.ui.theme.FONT_MEDIUM

@Composable
fun AddFloatingActionButton(
    onClick: () -> Unit,
) {
    ExtendedFloatingActionButton(
        modifier = Modifier
            .height(DEFAULT_FLOATING_BUTTON_HEIGHT)
            .offset(
                x = 0.dp,
                y = BOTTOMNAVBAR_HEIGHT.value
                    .times(-1)
                    .plus(5).dp
            ),
        onClick = onClick,
        icon = { Icon(Icons.Default.Add, "") },
        text = {
            Text(
                text = stringResource(id = R.string.add),
                fontSize = FONT_MEDIUM,
                maxLines = 1
            )
        }
    )
}
