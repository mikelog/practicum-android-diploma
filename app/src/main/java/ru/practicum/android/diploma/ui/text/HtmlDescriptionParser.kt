package ru.practicum.android.diploma.ui.text

import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.fromHtml

fun String.toAnnotatedDescription(): AnnotatedString = AnnotatedString.fromHtml(this)