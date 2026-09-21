package ru.practicum.android.diploma.ui.team

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.navigation.NavController
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.ui.theme.Dimens

private data class TeamMember(
    val nameRes: Int,
    val roleRes: Int,
    val initialsRes: Int
)

private val teamMembers = listOf(
    TeamMember(
        nameRes = R.string.team_member_name_logins,
        roleRes = R.string.team_member_role_logins,
        initialsRes = R.string.team_member_initials_logins
    ),
    TeamMember(
        nameRes = R.string.team_member_name_bitkin,
        roleRes = R.string.team_member_role_bitkin,
        initialsRes = R.string.team_member_initials_bitkin
    ),
    TeamMember(
        nameRes = R.string.team_member_name_matuskin,
        roleRes = R.string.team_member_role_matuskin,
        initialsRes = R.string.team_member_initials_matuskin
    )
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TeamScreen(navController: NavController) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(R.string.team)) },
                expandedHeight = Dimens.topBarHeight
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            Text(
                text = stringResource(R.string.team_header),
                style = MaterialTheme.typography.headlineLarge,
                color = MaterialTheme.colorScheme.onBackground,
                maxLines = 2,
                overflow = TextOverflow.Clip,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = Dimens.spacingL)
                    .height(Dimens.teamHeaderHeight)
            )

            LazyColumn(
                modifier = Modifier.fillMaxSize()
            ) {
                items(teamMembers.size) { index ->
                    TeamMemberItem(member = teamMembers[index])
                }
            }
        }
    }
}

@Composable
private fun TeamMemberItem(member: TeamMember) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = Dimens.spacingL, vertical = Dimens.vacancyCardVerticalPadding),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(Dimens.teamAvatarSize)
                .background(MaterialTheme.colorScheme.primaryContainer, CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = stringResource(member.initialsRes),
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onPrimaryContainer
            )
        }

        Column(
            modifier = Modifier
                .weight(1f)
                .padding(start = Dimens.spacingM)
        ) {
            Text(
                text = stringResource(member.nameRes),
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurface,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            Text(
                text = stringResource(member.roleRes),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}