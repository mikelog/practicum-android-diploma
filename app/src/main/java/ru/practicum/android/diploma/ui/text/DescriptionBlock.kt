package ru.practicum.android.diploma.ui.text

import org.jsoup.Jsoup
import org.jsoup.nodes.Element

private const val H2_LEVEL = 2
private const val H3_LEVEL = 3

sealed interface DescriptionBlock {
    data class Heading(val level: Int, val html: String) : DescriptionBlock
    data class Paragraph(val html: String) : DescriptionBlock

    data class ListBlock(
        val ordered: Boolean,
        val items: List<String>,
    ) : DescriptionBlock
}

fun String.toDescriptionBlocks(): List<DescriptionBlock> =
    parseChildren(Jsoup.parseBodyFragment(this).body())

private fun parseChildren(parent: Element): List<DescriptionBlock> =
    parent.children().flatMap { element ->
        when (element.normalName()) {
            "h2" -> listOf(
                DescriptionBlock.Heading(H2_LEVEL, element.html()),
            )
            "h3" -> listOf(
                DescriptionBlock.Heading(H3_LEVEL, element.html()),
            )
            "p" -> listOf(
                DescriptionBlock.Paragraph(element.html()),
            )
            "ul", "ol" -> listOf(
                DescriptionBlock.ListBlock(
                    ordered = element.normalName() == "ol",
                    items = element.children()
                        .filter { it.normalName() == "li" }
                        .map(Element::html),
                ),
            )
            "section", "div", "article" -> parseChildren(element)
            else -> emptyList()
        }
    }
