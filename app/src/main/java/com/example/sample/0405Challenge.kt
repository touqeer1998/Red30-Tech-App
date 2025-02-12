package com.example.sample

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.IconToggleButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.red30.compose.ui.component.SessionTags
import com.example.red30.compose.ui.theme.Red30TechTheme
import com.example.red30.data.SessionInfo

//private class FavoritesViewModel: ViewModel() {
//    private val _sessionInfo = MutableStateFlow<SessionInfo>(SessionInfo.fake())
//    val sessionInfo: StateFlow<SessionInfo> = _sessionInfo
//
//    fun toggleFavorite() {
//        // TODO: update the sessionInfo with the new favorite value
//    }
//}

@Composable
private fun SessionItem(
    modifier: Modifier = Modifier,
    sessionInfo: SessionInfo,
    onFavoriteClick: () -> Unit = {}
) {
    ElevatedCard(
        modifier = modifier.padding(16.dp),
        shape = RoundedCornerShape(0.dp)
    ) {
        with(sessionInfo) {
            Row {
                Column(modifier = Modifier.weight(1f)) {
                    SessionTags(
                        track = session.track,
                        roomName = session.roomName
                    )
                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp, horizontal = 16.dp),
                        text = session.name,
                        style = MaterialTheme.typography.titleLarge
                    )
                }

                IconToggleButton(
                    checked = sessionInfo.isFavorite,
                    onCheckedChange = { onFavoriteClick() }
                ) {
                    if (sessionInfo.isFavorite) {
                        Icon(Icons.Filled.Favorite, contentDescription = "un-favorite session")
                    } else {
                        Icon(Icons.Outlined.FavoriteBorder, contentDescription = "favorite session")
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun Challenge0405Preview() {
    // TODO: Create the needed variables

    Red30TechTheme {
        Surface {
//            SessionItem(
//                sessionInfo = ,
//                onFavoriteClick = {  }
//            )
        }
    }
}
