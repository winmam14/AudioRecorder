package at.winter.audioRecorder.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.updateTransition
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.LargeFloatingActionButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import at.winter.audioRecorder.R
import at.winter.audioRecorder.utils.AudioRecorder.AndroidAudioRecordHandler
import at.winter.audioRecorder.utils.RecordingEvent
import at.winter.audioRecorder.utils.RecordingState
import at.winter.audioRecorder.utils.StopWatch
import java.util.Timer
import kotlin.concurrent.schedule

const val TAG = "MainScreen"

@Composable
fun MainScreen(onOpenRecordings: () -> Unit, state: RecordingState, onEvent: (RecordingEvent) -> Unit) {
    val applicationContext = LocalContext.current

    val recorder = remember {
        AndroidAudioRecordHandler(applicationContext)
    }

    Box {

        RecordElement(
            recordingStarted = state.isRecording,
            onClick = {duration ->
                onEvent(recorder.toggle(duration, state.isRecording))
            },
            modifier = Modifier
                .align(Alignment.Center)
        )

        AnimatedVisibility(
            visible = !state.isRecording,
            enter = scaleIn() + fadeIn(),
            exit = scaleOut() + fadeOut(),
            modifier = Modifier
                .align(Alignment.Center)
                .padding(
                    top = dimensionResource(id = R.dimen.record_button_size) + dimensionResource(
                        id = R.dimen.extra_large_padding
                    )
                )
        ) {
            OutlinedButton(
                onClick = { onOpenRecordings()
//                    player.playFile(audioFile ?: return@OutlinedButton)
                }, modifier = Modifier
                    .wrapContentSize()

            ) {
                Text("Show Records", style = MaterialTheme.typography.labelLarge)
            }
        }


    }


}

@Composable
fun RecordElement(recordingStarted: Boolean, onClick: (time: Long) -> Unit, modifier: Modifier = Modifier) {
    val stopWatch = remember { StopWatch() }
    val transition = updateTransition(targetState = recordingStarted, label = "boxTransition")
    val scale by transition.animateFloat(
        transitionSpec = { spring(stiffness = 50f) }, label = "scaleAnimation"
    ) { isRecording ->
        if (isRecording) 0.6f else 1f
    }



    Box(modifier = modifier) {
        AnimatedVisibility(
            visible = recordingStarted,
            enter = scaleIn() + fadeIn(),
            exit = scaleOut() + fadeOut(),
            modifier = Modifier
                .align(Alignment.Center)
                .padding(
                    bottom = dimensionResource(id = R.dimen.record_button_size) + dimensionResource(
                        id = R.dimen.extra_large_padding
                    )
                )
        ) {
            Text(stopWatch.formattedTime, style = MaterialTheme.typography.displayLarge)
        }

        LargeFloatingActionButton(
            onClick = {
                if (recordingStarted) {
                    stopWatch.pause()
                    Timer().schedule(1000L) {
                        onClick(stopWatch.timeMillis)
                        stopWatch.reset()
                    }
                } else {
                    onClick(stopWatch.timeMillis)
                    stopWatch.start()
                }
            },
            shape = CircleShape,
            modifier = modifier
                .size(dimensionResource(id = R.dimen.record_button_size))
                .scale(scale)
                .align(Alignment.Center)
        ) {
            Icon(
                painterResource(id = R.drawable.record_icon),
                "Record Button",
                modifier = Modifier.size(
                    dimensionResource(id = R.dimen.record_button_icon_size)
                )
            )
        }
    }
}