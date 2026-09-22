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
import ru.practicum.android.diploma.ui.text.toDescriptionBlocks
import ru.practicum.android.diploma.ui.theme.Dimens

@Suppress("CognitiveComplexMethod")
@Composable
fun VacancyDescription(
    description: String?,
    modifier: Modifier = Modifier
) {
    val h2Style: TextStyle = MaterialTheme.typography.titleLarge
    val h3Style: TextStyle = MaterialTheme.typography.titleMedium
    val paragraphStyle: TextStyle = MaterialTheme.typography.bodyLarge
    val listStyle: TextStyle = MaterialTheme.typography.bodyLarge

    val headingToParagraphSpacing: Dp = 16.dp
    val headingToListSpacing: Dp = 4.dp
    val paragraphSpacing: Dp = 0.dp
    val blockSpacing: Dp = 16.dp
    val listItemSpacing: Dp = 0.dp

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
                val spacing = when {
                    previousBlock is DescriptionBlock.Heading &&
                        previousBlock.level == 2 &&
                        block is DescriptionBlock.Paragraph -> {
                        headingToParagraphSpacing
                    }

                    previousBlock is DescriptionBlock.Heading &&
                        previousBlock.level == 3 &&
                        block is DescriptionBlock.ListBlock -> {
                        headingToListSpacing
                    }

                    previousBlock is DescriptionBlock.Paragraph &&
                        block is DescriptionBlock.Paragraph -> {
                        paragraphSpacing
                    }

                    else -> {
                        blockSpacing
                    }
                }

                Spacer(
                    modifier = Modifier.height(spacing),
                )
            }

            when (block) {
                is DescriptionBlock.Heading -> {
                    val text = remember(block.html) {
                        AnnotatedString.fromHtml(block.html)
                    }

                    Text(
                        text = text,
                        style = if (block.level == 2) {
                            h2Style
                        } else {
                            h3Style
                        },
                    )
                }

                is DescriptionBlock.Paragraph -> {
                    val text = remember(block.html) {
                        AnnotatedString.fromHtml(block.html)
                    }

                    Text(
                        text = text,
                        style = paragraphStyle,
                    )
                }

                is DescriptionBlock.ListBlock -> {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(listItemSpacing),
                    ) {
                        block.items.forEachIndexed { itemIndex, itemHtml ->
                            val itemText = remember(itemHtml) {
                                AnnotatedString.fromHtml(itemHtml)
                            }

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
                                    modifier = Modifier.width(24.dp),
                                )

                                Text(
                                    text = itemText,
                                    style = listStyle,
                                    modifier = Modifier.weight(1f),
                                )
                            }
                        }
                    }
                }
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
                    modifier = Modifier.width(24.dp),
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
