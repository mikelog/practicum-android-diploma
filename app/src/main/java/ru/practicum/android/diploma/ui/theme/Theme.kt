package ru.practicum.android.diploma.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

private val LightColorScheme = lightColorScheme(
    // Кнопки, активные элементы, курсор
    primary = Blue,
    onPrimary = White,

    primaryContainer = LightGray,
    onPrimaryContainer = Blue,

    secondary = Blue,
    onSecondary = White,

    secondaryContainer = LightGray,
    onSecondaryContainer = Blue,

    // Фон приложения
    background = White,
    onBackground = Black,

    // Поверхности: панели, карточки, модальные окна
    surface = White,
    onSurface = Black,

    // Вариативные поверхности, например фон текстового поля
    surfaceVariant = Gray,
    onSurfaceVariant = Black,

    // Неактивные элементы и границы
    outline = Gray,
    outlineVariant = LightGray,

    // Ошибки
    error = Red,
    onError = White,

    // Затемнение позади ModalBottomSheet
    scrim = Scrim
)

private val DarkColorScheme = darkColorScheme(
    // Кнопки, активные элементы, курсор
    primary = Blue,
    onPrimary = White,

    primaryContainer = LightGray,
    onPrimaryContainer = Blue,

    secondary = Blue,
    onSecondary = White,

    secondaryContainer = LightGray,
    onSecondaryContainer = Blue,

    // Для тёмной темы фон должен быть тёмным
    background = Black,
    onBackground = White,

    surface = Black,
    onSurface = White,

    // Фон текстового поля
    surfaceVariant = Gray,
    onSurfaceVariant = White,

    // Неактивные элементы и границы
    outline = Gray,
    outlineVariant = LightGray,

    // Ошибки
    error = Red,
    onError = White,

    // Затемнение позади ModalBottomSheet
    scrim = Scrim
)

@Composable
fun AppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) {
        DarkColorScheme
    } else {
        LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = AppTypography
    ) {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = colorScheme.background,
            contentColor = colorScheme.onBackground,
            content = content
        )
    }
}
