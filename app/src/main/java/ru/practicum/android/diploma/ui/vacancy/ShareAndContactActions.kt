package ru.practicum.android.diploma.ui.vacancy

import android.content.Context
import android.content.Intent
import android.net.Uri

fun shareVacancy(
    context: Context,
    url: String
) {
    val sendIntent = Intent(Intent.ACTION_SEND).apply {
        type = "text/plain"
        putExtra(Intent.EXTRA_TEXT, url)
    }
    context.startActivity(Intent.createChooser(sendIntent, null))
}

fun openEmail(
    context: Context,
    email: String
) {
    val emailIntent = Intent(Intent.ACTION_SENDTO).apply {
        data = Uri.parse("mailto:$email")
    }
    context.startActivity(emailIntent)
}

fun callPhone(
    context: Context,
    formattedNumber: String
) {
    val dialIntent = Intent(Intent.ACTION_DIAL).apply {
        data = Uri.parse("tel:${formattedNumber.phoneDigitsOnly()}")
    }
    context.startActivity(dialIntent)
}

private fun String.phoneDigitsOnly(): String {
    return filter { it.isDigit() || it == PHONE_PLUS_SIGN }
}

private const val PHONE_PLUS_SIGN = '+'