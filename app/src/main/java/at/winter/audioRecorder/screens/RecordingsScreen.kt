package at.winter.audioRecorder.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import at.winter.audioRecorder.R
import at.winter.audioRecorder.data.Recording
import at.winter.audioRecorder.utils.AudioPlayer.AndroidAudioPlayerHandler
import at.winter.audioRecorder.utils.RecordingEvent
import at.winter.audioRecorder.utils.RecordingState
import at.winter.audioRecorder.utils.SimpleDateFormatter
import at.winter.audioRecorder.utils.SortType

@Composable
fun RecordingsScreen(state: RecordingState, onEvent: (RecordingEvent) -> Unit) {
    val applicationContext = LocalContext.current

    val audioPlayer = remember {
        AndroidAudioPlayerHandler(applicationContext)
    }


    LazyColumn(
        contentPadding = PaddingValues(dimensionResource(id = R.dimen.medium_padding)),
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(
            dimensionResource(id = R.dimen.medium_padding)
        )
    ) {
        item {
            Text(
                stringResource(id = R.string.recordings_page_title),
                style = MaterialTheme.typography.titleLarge
            )
            Text(
                stringResource(id = R.string.recordings_page_description),
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(
                    bottom = dimensionResource(
                        id = R.dimen.large_padding
                    )
                )
            )
            Text(text = stringResource(id = R.string.order_by))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState()),
                verticalAlignment = Alignment.CenterVertically
            ) {
                SortType.entries.forEach { sortType ->
                    Row(
                        modifier = Modifier.clickable {
                            onEvent(RecordingEvent.SortRecordings(sortType))
                        }, verticalAlignment = Alignment.CenterVertically
                    ) {
                        RadioButton(selected = state.sortType == sortType,
                            onClick = { onEvent(RecordingEvent.SortRecordings(sortType)) })
                        Text(text = stringResource(id = sortType.resourceID))
                    }
                }
            }
        }
        items(state.recordings) { recording ->
            RecordingItem(
                recording = recording,
                onStartReplay = {
                    onEvent(
                        audioPlayer.toggle(
                            state.isReplaying,
                            state.currentRecording,
                            recording
                        )
                    )
                },
                onDeleteRecording = {
                    onEvent(RecordingEvent.DeleteRecording(recording = recording))
                }
            )
        }
    }
}

@Composable
fun RecordingItem(recording: Recording, onStartReplay: () -> Unit, onDeleteRecording: () -> Unit) {
    Card {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(dimensionResource(id = R.dimen.small_padding)),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier
                .weight(1f)
                .clickable {
                    onStartReplay()
                }) {
                Text(recording.name, style = MaterialTheme.typography.titleLarge, modifier = Modifier.padding(bottom = dimensionResource(
                    id = R.dimen.small_padding
                )))
                Text(
                    stringResource(id = R.string.recording_item_attribute_size) + String.format(" %.2f kb",recording.size / 1024.0), style = MaterialTheme.typography.bodyMedium
                )
                Text(
                    text = stringResource(id = R.string.recording_item_attribute_duration) + String.format(" %.2f sec",recording.duration / 1000.0),
                    style = MaterialTheme.typography.bodyMedium
                )
                Text(
                    text = stringResource(id = R.string.recording_item_attribute_date) + " ${
                        SimpleDateFormatter.formatTime(
                            recording.unixTimestamp, "YYYY-MM-dd HH:mm"
                        )
                    }", style = MaterialTheme.typography.bodyMedium
                )

            }
            IconButton(onClick = { onDeleteRecording() }) {
                Icon(
                    imageVector = Icons.Default.Delete, contentDescription = "Delete recording"
                )
            }
        }
    }
}