package ru.practicum.android.diploma.util

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch

/** Задержка перед автопоиском после остановки ввода, мс (ТЗ экрана поиска — 2 секунды). */
const val SEARCH_DEBOUNCE_DELAY_MILLIS = 2000L

/** Задержка для защиты от повторных нажатий (клики по элементам списка и т.п.), мс. */
const val CLICK_DEBOUNCE_DELAY_MILLIS = 1000L

/**
 * Debounce для потока значений: значение проходит дальше, только если в течение [debounceMillis]
 * после него не пришло новое. Подряд идущие одинаковые значения отбрасываются.
 *
 * Для поля поиска и поиска по регионам/отраслям — везде, где «печатает → ждём паузу → запрос».
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
 * Возвращает функцию, откладывающую вызов [action] на [delayMillis] мс — замена
 * `Handler.postDelayed` для императивных случаев (антидабл-клик, ручной перезапуск поиска).
 *
 * @param useLastParam `true` — новый вызов до срабатывания отменяет предыдущий, выполнится
 * только последний; `false` — «побеждает» первый вызов, остальные в течение задержки игнорируются.
 */
fun <T> debounce(
    coroutineScope: CoroutineScope,
    delayMillis: Long = CLICK_DEBOUNCE_DELAY_MILLIS,
    useLastParam: Boolean = true,
    action: (T) -> Unit
): (T) -> Unit {
    var debounceJob: Job? = null
    return { param: T ->
        if (useLastParam) {
            debounceJob?.cancel()
        }
        if (debounceJob?.isActive != true) {
            debounceJob = coroutineScope.launch {
                delay(delayMillis)
                action(param)
            }
        }
    }
}

/** Вариант [debounce] без параметра — для простого антидабл-клика. */
fun debounce(
    coroutineScope: CoroutineScope,
    delayMillis: Long = CLICK_DEBOUNCE_DELAY_MILLIS,
    action: () -> Unit
): () -> Unit {
    val debounced = debounce<Unit>(coroutineScope, delayMillis, useLastParam = false) { action() }
    return { debounced(Unit) }
}
