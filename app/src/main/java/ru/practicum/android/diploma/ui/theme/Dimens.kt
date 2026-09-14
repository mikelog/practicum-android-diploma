package ru.practicum.android.diploma.ui.theme

import androidx.compose.ui.unit.dp

/**
 * Единые отступы и размеры компонентов.
 * Экраны должны обращаться к этим константам вместо хардкода `.dp`.
 */
object Dimens {

    // Базовая сетка отступов
    val spacingXxs = 2.dp
    val spacingXs = 4.dp
    val spacingS = 8.dp
    val spacingM = 12.dp
    val spacingL = 16.dp
    val spacingXl = 24.dp
    val spacingXxl = 32.dp

    // Размеры компонентов
    val topBarHeight = 64.dp
    val bottomBarHeight = 57.dp
    val dividerThickness = 1.dp
    val elevationNone = 0.dp

    // Карточка вакансии в списке (по макету Figma: List Item / Picture image+Text)
    val vacancyCardVerticalPadding = 9.dp
    val vacancyLogoSize = 48.dp
    val vacancyLogoCornerRadius = 12.dp
    val vacancyLogoBorderWidth = 1.dp

    // Поле поиска (по макету Figma: Search Stack)
    val searchFieldHeight = 56.dp
    val searchFieldCornerRadius = 12.dp
    val searchFieldIconButtonSize = 48.dp

    // Счётчик найденных вакансий (по макету Figma: Chip)
    val chipCornerRadius = 12.dp
    val chipHorizontalPadding = 12.dp
    val chipVerticalPadding = 4.dp

    // Иллюстрации-заглушки состояний экрана (Idle/Empty/ошибки)
    val placeholderImageWidth = 328.dp
    val placeholderImageHeight = 223.dp
}
