package ru.practicum.android.diploma.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import ru.practicum.android.diploma.ui.theme.Dimens

@Composable
fun PrimaryButton(
    text: String,
    isVisible: Boolean = true,
    onClickAction: (() -> Unit)? = null,
    modifier: Modifier = Modifier
) {
    if (isVisible) {
        Button(
            onClick = { onClickAction?.invoke() },
            modifier = modifier
                .height(Dimens.primaryButton)
                .fillMaxWidth(),
            shape = RoundedCornerShape(Dimens.primaryButtonCornerRadius)
        ) {
            Text(
                text = text,
                style = MaterialTheme.typography.titleMedium
            )
        }
    }
}
