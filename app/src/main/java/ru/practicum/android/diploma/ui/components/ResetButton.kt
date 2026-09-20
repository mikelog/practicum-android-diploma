package ru.practicum.android.diploma.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import ru.practicum.android.diploma.ui.theme.Dimens

@Composable
fun ResetButton(
    text: String,
    isVisible: Boolean = true,
    onClickAction: (() -> Unit)? = null,
    modifier: Modifier = Modifier
) {
    if (isVisible) {
        Button(
            onClick = { onClickAction?.invoke() },
            modifier = modifier
                .height(Dimens.resetButton)
                .fillMaxWidth(),
            shape = RoundedCornerShape(Dimens.resetButtonCornerRadius),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.background,
                contentColor = MaterialTheme.colorScheme.error
            ),
        ) {
            Text(
                text = text,
                style = MaterialTheme.typography.titleMedium
            )
        }
    }
}
