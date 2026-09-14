package ru.practicum.android.diploma.util

import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.util.Locale

/**
 * Форматирование суммы и валюты зарплаты.
 * Не зависит от Android Context, чтобы переиспользоваться и в UI-слое (Compose), и при необходимости в domain/data.
 */
object SalaryFormatter {

    private val CURRENCY_SYMBOLS = mapOf(
        "RUR" to "₽",
        "RUB" to "₽",
        "BYR" to "Br",
        "USD" to "$",
        "EUR" to "€",
        "KZT" to "₸",
        "UAH" to "₴",
        "AZN" to "₼",
        "UZS" to "UZS",
        "GEL" to "₾",
        "KGS" to "KGS"
    )

    private val amountFormat = DecimalFormat(
        "#,##0",
        DecimalFormatSymbols(Locale.US).apply { groupingSeparator = ' ' }
    )

    fun formatAmount(amount: Int): String = amountFormat.format(amount)

    fun currencySymbol(currency: String?): String =
        currency?.let { CURRENCY_SYMBOLS[it.uppercase(Locale.ROOT)] } ?: currency.orEmpty()

    fun formatValue(amount: Int, currency: String?): String {
        val symbol = currencySymbol(currency)
        return if (symbol.isEmpty()) formatAmount(amount) else "${formatAmount(amount)} $symbol"
    }
}
