package ru.practicum.android.diploma.ui.theme

import androidx.compose.ui.unit.dp

/**
 * Единые отступы и размеры компонентов.
 * Экраны должны обращаться к этим константам вместо хардкода `.dp`.
 */
object Dimens {

    // Базовая сетка отступов
    val spacingXxs = 1.dp
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

    // Карточка вакансии в списке (используется на экранах поиска, избранного и команды —
    // по макету Figma: List Item / Picture image+Text)
    val vacancyCardVerticalPadding = 9.dp
    val vacancyLogoSize = 48.dp
    val vacancyLogoCornerRadius = 12.dp
    val vacancyLogoBorderWidth = 1.dp

    // Поле "ожидаемая зарплата"
    val salaryFilterFieldCornerRadius = 12.dp
    val salaryFilterField = 51.dp

    // Основная кнопка
    val primaryButton = 59.dp
    val primaryButtonCornerRadius = 12.dp

    // Кнопка сброса
    val resetButton = 59.dp
    val resetButtonCornerRadius = 12.dp
}
