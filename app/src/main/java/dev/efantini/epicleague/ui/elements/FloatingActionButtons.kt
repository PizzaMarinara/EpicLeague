package dev.efantini.epicleague.ui.elements

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import dev.efantini.epicleague.R
import dev.efantini.epicleague.ui.theme.BOTTOMNAVBAR_HEIGHT
import dev.efantini.epicleague.ui.theme.DEFAULT_FLOATING_BUTTON_HEIGHT
import dev.efantini.epicleague.ui.theme.GreyC
import dev.efantini.epicleague.ui.theme.GreyG
import dev.efantini.epicleague.ui.theme.LocalFontSize
import dev.efantini.epicleague.ui.theme.LocalSpacing

@Composable
fun AddFloatingActionButton(
    onClick: () -> Unit,
) {
    OutlinedButton(
        modifier = Modifier
            .height(DEFAULT_FLOATING_BUTTON_HEIGHT)
            .offset(
                x = 0.dp,
                y = BOTTOMNAVBAR_HEIGHT.value
                    .times(-1)
                    .plus(5).dp
            )
            .wrapContentWidth(),
        onClick = onClick,
        border = BorderStroke(1.dp, GreyG),
        shape = RoundedCornerShape(10.dp),
        colors = ButtonDefaults.outlinedButtonColors(
            backgroundColor = MaterialTheme.colors.secondary,
            contentColor = MaterialTheme.colors.onSecondary,
            disabledContentColor = GreyC
        )
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(Icons.Default.Add, "")
            Spacer(modifier = Modifier.width(LocalSpacing.current.default))
            Text(
                text = stringResource(id = R.string.add),
                fontSize = LocalFontSize.current.medium,
                maxLines = 1
            )
        }
    }
}
