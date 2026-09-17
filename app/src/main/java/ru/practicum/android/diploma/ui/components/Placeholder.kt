package ru.practicum.android.diploma.ui.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import ru.practicum.android.diploma.ui.theme.Dimens

/**
 * Переиспользуемая заглушка-иллюстрация с подписью для пустых состояний и ошибок
 * (используется на экране поиска вакансий и других экранах со списками).
 */
@Composable
fun Placeholder(
    @DrawableRes image: Int,
    message: String?,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Image(
                painter = painterResource(image),
                contentDescription = null,
                modifier = Modifier.size(
                    width = Dimens.placeholderImageWidth,
                    height = Dimens.placeholderImageHeight
                )
            )
            if (message != null) {
                Text(
                    text = message,
                    modifier = Modifier.padding(
                        top = Dimens.spacingL,
                        start = Dimens.spacingXl,
                        end = Dimens.spacingXl
                    ),
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.onBackground,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}
