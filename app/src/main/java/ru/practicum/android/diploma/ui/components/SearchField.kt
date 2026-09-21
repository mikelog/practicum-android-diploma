package ru.practicum.android.diploma.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.ui.theme.Dimens

/**
 * Переиспользуемое поле поиска с hint-ом и кнопкой очистки
 * (используется на экране поиска вакансий, выборе отрасли и т.п.).
 */
@Composable
fun SearchField(
    query: String,
    onQueryChange: (String) -> Unit,
    onClearQuery: () -> Unit,
    modifier: Modifier = Modifier
) {
    val shape = RoundedCornerShape(Dimens.searchFieldCornerRadius)
    Box(
        modifier = modifier
            .height(Dimens.searchFieldHeight)
            .clip(shape)
            .background(MaterialTheme.colorScheme.tertiary, shape)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = Dimens.spacingL),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier.weight(1f),
                contentAlignment = Alignment.CenterStart
            ) {
                if (query.isEmpty()) {
                    Text(
                        text = stringResource(R.string.search_hint),
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onTertiary
                    )
                }
                BasicTextField(
                    value = query,
                    onValueChange = onQueryChange,
                    singleLine = true,
                    textStyle = MaterialTheme.typography.bodyLarge.copy(
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    ),
                    cursorBrush = SolidColor(MaterialTheme.colorScheme.primary),
                    modifier = Modifier.fillMaxWidth()
                )
            }

            if (query.isEmpty()) {
                Box(
                    modifier = Modifier.size(Dimens.searchFieldIconButtonSize),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        painter = painterResource(R.drawable.ic_search_24dp),
                        contentDescription = stringResource(R.string.cd_search_icon),
                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            } else {
                IconButton(
                    onClick = onClearQuery,
                    modifier = Modifier.size(Dimens.searchFieldIconButtonSize)
                ) {
                    Icon(
                        painter = painterResource(R.drawable.ic_close_24dp),
                        contentDescription = stringResource(R.string.cd_clear_query),
                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }
    }
}
