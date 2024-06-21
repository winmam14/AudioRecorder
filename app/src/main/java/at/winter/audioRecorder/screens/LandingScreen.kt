package at.winter.audioRecorder.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import at.winter.audioRecorder.R
import at.winter.audioRecorder.utils.RecordingState

@Composable
fun LandingScreen(
    state: RecordingState,
    onOpenMainScreen: () -> Unit
) {
    Box(modifier = Modifier
        .fillMaxSize()
        .clickable {
            onOpenMainScreen()
        }) {
        Text(
            state.joke,
            style = MaterialTheme.typography.headlineSmall,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .align(Alignment.Center)
                .padding(horizontal = 16.dp)
        )

        Text(
            stringResource(id = R.string.tab_to_start),
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier
                .align(Alignment.Center)
                .offset(y = 128.dp)

        )
    }


}