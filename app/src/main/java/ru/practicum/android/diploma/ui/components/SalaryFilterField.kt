package ru.practicum.android.diploma.ui.components

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.input.InputTransformation
import androidx.compose.foundation.text.input.TextFieldLineLimits
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.delete
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TextFieldLabelPosition
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.ui.theme.Dimens

// Поле "ожидаемая зарплата"
private val salaryFilterFieldCornerRadius = 12.dp

@Composable
fun SalaryTextField(
    state: TextFieldState,
    labelText: String,
    placeholderText: String,
    modifier: Modifier = Modifier,
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isFocused by interactionSource.collectIsFocusedAsState()

    TextField(
        state = state,
        modifier = modifier.fillMaxWidth(),
        interactionSource = interactionSource,
        label = {
            Text(
                labelText,
                style = MaterialTheme.typography.labelMedium,
            )
        },
        labelPosition = TextFieldLabelPosition.Attached(
            alwaysMinimize = true,
        ),
        placeholder = {
            Text(
                placeholderText,
                style = MaterialTheme.typography.bodyLarge,
            )
        },
        trailingIcon = {
            if (state.text.isNotEmpty()) {
                IconButton(
                    onClick = {
                        state.edit {
                            delete(0, length)
                        }
                    },
                    modifier = Modifier.padding(end = Dimens.spacingXs),
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_close_24dp),
                        contentDescription = null,
                    )
                }
            }
        },
        lineLimits = TextFieldLineLimits.SingleLine,
        inputTransformation = InputTransformation {
            if (!asCharSequence().all(Char::isDigit)) {
                revertAllChanges()
            }
        },
        shape = RoundedCornerShape(salaryFilterFieldCornerRadius),
        colors = TextFieldDefaults.colors(
            focusedLabelColor = MaterialTheme.colorScheme.onPrimaryContainer,
            unfocusedLabelColor = if (state.text.isNotEmpty() && !isFocused) {
                MaterialTheme.colorScheme.onSurfaceVariant
            } else {
                MaterialTheme.colorScheme.onTertiary
            },
            focusedTextColor = MaterialTheme.colorScheme.onSurfaceVariant,
            unfocusedTextColor = MaterialTheme.colorScheme.onSurfaceVariant,
            focusedContainerColor = MaterialTheme.colorScheme.tertiary,
            unfocusedContainerColor = MaterialTheme.colorScheme.tertiary,
            focusedPlaceholderColor = MaterialTheme.colorScheme.onTertiary,
            unfocusedPlaceholderColor = MaterialTheme.colorScheme.onTertiary,
            cursorColor = MaterialTheme.colorScheme.onPrimaryContainer,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
        )
    )
}
