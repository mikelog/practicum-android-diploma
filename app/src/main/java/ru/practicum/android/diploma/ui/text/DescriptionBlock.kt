package ru.practicum.android.diploma.ui.text

import org.jsoup.Jsoup
import org.jsoup.nodes.Element

sealed interface DescriptionBlock {
    data class Heading(
        val level: Int,
        val html: String,
    ) : DescriptionBlock

    data class Paragraph(
        val html: String,
    ) : DescriptionBlock

    data class ListBlock(
        val ordered: Boolean,
        val items: List<String>,
    ) : DescriptionBlock
}

fun String.toDescriptionBlocks(): List<DescriptionBlock> {
    val body = Jsoup.parseBodyFragment(this).body()

    return parseChildren(body)
}

private fun parseChildren(parent: Element): List<DescriptionBlock> {
    return parent.children().flatMap { element ->
        when (element.normalName()) {
            "h2" -> {
                listOf(DescriptionBlock.Heading(level = 2, html = element.html()))
            }

            "h3" -> {
                listOf(DescriptionBlock.Heading(level = 3, html = element.html()))
            }

            "p" -> {
                listOf(DescriptionBlock.Paragraph(html = element.html()))
            }

            "ul", "ol" -> {
                val items = element.children()
                    .filter { it.normalName() == "li" }
                    .map { it.html() }

                listOf(
                    DescriptionBlock.ListBlock(
                        ordered = element.normalName() == "ol",
                        items = items,
                    ),
                )
            }

            "section", "div", "article" -> {
                parseChildren(element)
            }

            else -> {
                emptyList()
            }
        }
    }
}
