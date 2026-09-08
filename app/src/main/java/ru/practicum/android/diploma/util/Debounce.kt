package ru.practicum.android.diploma.util

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch

/** Задержка перед автопоиском после остановки ввода, мс (ТЗ экрана поиска — 2 секунды). */
const val SEARCH_DEBOUNCE_DELAY_MILLIS = 2000L

/** Окно блокировки повторных нажатий, мс. */
const val CLICK_DEBOUNCE_DELAY_MILLIS = 1000L

/**
 * Debounce для потока значений: значение проходит дальше, только если в течение [debounceMillis]
 * после него не пришло новое. Подряд идущие одинаковые значения отбрасываются.
 *
 * Модель «печатает → пауза → запрос»: для поля поиска и поиска по регионам/отраслям.
 *
 * ```
 * queryFlow
 *     .searchDebounce()
 *     .onEach(::search)
 *     .launchIn(viewModelScope)
 * ```
 */
@OptIn(FlowPreview::class)
fun <T> Flow<T>.searchDebounce(
    debounceMillis: Long = SEARCH_DEBOUNCE_DELAY_MILLIS
): Flow<T> = debounce(debounceMillis).distinctUntilChanged()

/**
 * Throttle для кликов: первый вызов выполняется сразу, последующие игнорируются в течение
 * [delayMillis]. Замена `Handler.postDelayed` для защиты от даблтапа и повторной навигации.
 *
 * ```
 * private val onVacancyClick = clickDebounce(viewModelScope) { openVacancy(it) }
 * ```
 */
fun <T> clickDebounce(
    coroutineScope: CoroutineScope,
    delayMillis: Long = CLICK_DEBOUNCE_DELAY_MILLIS,
    action: (T) -> Unit
): (T) -> Unit {
    var isClickAllowed = true
    return { param: T ->
        if (isClickAllowed) {
            isClickAllowed = false
            action(param)
            coroutineScope.launch {
                delay(delayMillis)
                isClickAllowed = true
            }
        }
    }
}

/** Вариант [clickDebounce] без параметра — для кнопок и прочих кликов без данных. */
fun clickDebounce(
    coroutineScope: CoroutineScope,
    delayMillis: Long = CLICK_DEBOUNCE_DELAY_MILLIS,
    action: () -> Unit
): () -> Unit {
    val debounced = clickDebounce<Unit>(coroutineScope, delayMillis) { action() }
    return { debounced(Unit) }
}
