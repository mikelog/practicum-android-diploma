package ru.practicum.android.diploma.ui.vacancy

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.fromHtml
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.domain.models.Contacts
import ru.practicum.android.diploma.domain.models.Phone
import ru.practicum.android.diploma.ui.text.DescriptionBlock
import ru.practicum.android.diploma.ui.text.H2_LEVEL
import ru.practicum.android.diploma.ui.text.H3_LEVEL
import ru.practicum.android.diploma.ui.text.toDescriptionBlocks
import ru.practicum.android.diploma.ui.theme.Dimens

private val HeadingToParagraphSpacing: Dp = 16.dp
private val HeadingToListSpacing: Dp = 4.dp
private val ParagraphSpacing: Dp = 0.dp
private val BlockSpacing: Dp = 16.dp
private val ListItemSpacing: Dp = 0.dp
private val ListMarkerWidth: Dp = 24.dp

@Composable
fun VacancyDescription(
    description: String?,
    modifier: Modifier = Modifier
) {
    if (description.isNullOrBlank()) {
        return
    }

    val blocks = remember(description) {
        description.toDescriptionBlocks()
    }

    Column(modifier = modifier) {
        blocks.forEachIndexed { index, block ->
            val previousBlock = blocks.getOrNull(index - 1)

            if (previousBlock != null) {
                Spacer(
                    modifier = Modifier.height(spacingBetween(previousBlock, block)),
                )
            }

            DescriptionBlockContent(block)
        }
    }
}

private fun spacingBetween(previous: DescriptionBlock, current: DescriptionBlock): Dp = when {
    previous is DescriptionBlock.Heading &&
        previous.level == H2_LEVEL &&
        current is DescriptionBlock.Paragraph -> HeadingToParagraphSpacing

    previous is DescriptionBlock.Heading &&
        previous.level == H3_LEVEL &&
        current is DescriptionBlock.ListBlock -> HeadingToListSpacing

    previous is DescriptionBlock.Paragraph &&
        current is DescriptionBlock.Paragraph -> ParagraphSpacing

    else -> BlockSpacing
}

@Composable
private fun DescriptionBlockContent(block: DescriptionBlock) {
    when (block) {
        is DescriptionBlock.Heading -> HtmlText(
            html = block.html,
            style = if (block.level == H2_LEVEL) {
                MaterialTheme.typography.titleLarge
            } else {
                MaterialTheme.typography.titleMedium
            },
        )

        is DescriptionBlock.Paragraph -> HtmlText(
            html = block.html,
            style = MaterialTheme.typography.bodyLarge,
        )

        is DescriptionBlock.ListBlock -> DescriptionList(block)
    }
}

@Composable
private fun HtmlText(
    html: String,
    style: TextStyle,
    modifier: Modifier = Modifier
) {
    val text = remember(html) {
        AnnotatedString.fromHtml(html)
    }

    Text(
        text = text,
        style = style,
        modifier = modifier,
    )
}

@Composable
private fun DescriptionList(block: DescriptionBlock.ListBlock) {
    val listStyle = MaterialTheme.typography.bodyLarge

    Column(
        verticalArrangement = Arrangement.spacedBy(ListItemSpacing),
    ) {
        block.items.forEachIndexed { itemIndex, itemHtml ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.Top,
            ) {
                Text(
                    text = if (block.ordered) {
                        "${itemIndex + 1}."
                    } else {
                        "•"
                    },
                    style = listStyle,
                    modifier = Modifier.width(ListMarkerWidth),
                )

                HtmlText(
                    html = itemHtml,
                    style = listStyle,
                    modifier = Modifier.weight(1f),
                )
            }
        }
    }
}

@Composable
fun SkillsSection(
    skills: List<String>,
    modifier: Modifier = Modifier
) {
    if (skills.isEmpty()) {
        return
    }

    Column(modifier = modifier.fillMaxWidth()) {
        Text(
            text = stringResource(R.string.key_skills),
            style = MaterialTheme.typography.titleMedium
        )

        skills.forEachIndexed { index, skill ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        top = if (index == 0) {
                            Dimens.spacingXs
                        } else {
                            0.dp
                        },
                    ),
                verticalAlignment = Alignment.Top,
            ) {
                Text(
                    text = "•",
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.width(ListMarkerWidth),
                )

                Text(
                    text = skill,
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.weight(1f),
                )
            }
        }
    }
}

@Composable
fun ContactsSection(
    contacts: Contacts?,
    modifier: Modifier = Modifier
) {
    if (contacts == null || contacts.isEmpty()) {
        return
    }

    val context = LocalContext.current

    Column(modifier = modifier.fillMaxWidth().padding(bottom = Dimens.spacingS)) {
        Text(
            text = stringResource(R.string.contacts),
            style = MaterialTheme.typography.titleMedium
        )

        if (contacts.name.isNotBlank()) {
            Text(
                text = contacts.name,
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(top = Dimens.spacingXs)
            )
        }

        contacts.phones.forEach { phone ->
            PhoneRow(
                phone = phone,
                modifier = Modifier.padding(top = Dimens.spacingXs),
                onClick = { callPhone(context, phone.formatted) }
            )
        }

        if (contacts.email.isNotBlank()) {
            Text(
                text = contacts.email,
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier
                    .padding(top = Dimens.spacingXs)
                    .clickable { openEmail(context, contacts.email) }
            )
        }
    }
}

@Composable
private fun PhoneRow(
    phone: Phone,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(modifier = modifier.fillMaxWidth().clickable(onClick = onClick)) {
        Text(
            text = phone.formatted,
            style = MaterialTheme.typography.bodyLarge
        )

        if (!phone.comment.isNullOrBlank()) {
            Text(
                text = phone.comment,
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(start = Dimens.spacingS)
            )
        }
    }
}

private fun Contacts.isEmpty(): Boolean {
    return name.isBlank() && email.isBlank() && phones.isEmpty()
}
