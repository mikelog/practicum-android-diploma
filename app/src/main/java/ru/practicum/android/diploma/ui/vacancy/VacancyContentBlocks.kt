package ru.practicum.android.diploma.ui.vacancy

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.domain.models.Contacts
import ru.practicum.android.diploma.domain.models.Phone
import ru.practicum.android.diploma.ui.theme.Dimens

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
            style = MaterialTheme.typography.titleLarge
        )

        skills.forEachIndexed { index, skill ->
            Text(
                text = skill,
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(top = if (index == 0) Dimens.spacingL else Dimens.spacingXs)
            )
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