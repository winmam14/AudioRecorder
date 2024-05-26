package at.winter.audioRecorder.screens

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.updateTransition
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.LargeFloatingActionButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Snackbar
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import at.winter.audioRecorder.R
import at.winter.audioRecorder.utils.AudioRecorder.AndroidAudioRecordHandler
import at.winter.audioRecorder.utils.RecordingEvent
import at.winter.audioRecorder.utils.RecordingState
import at.winter.audioRecorder.utils.StopWatch
import java.util.Timer
import kotlin.concurrent.schedule

private val TAG = "MainScreen"

@Composable
fun MainScreen(
    onOpenRecordings: () -> Unit,
    state: RecordingState,
    activity: Activity,
    onEvent: (RecordingEvent) -> Unit
) {
    val applicationContext = LocalContext.current

    val recorder = remember {
        AndroidAudioRecordHandler(applicationContext)
    }
    var showSnackbar by remember {
        mutableStateOf(false)
    }


    Box {

        AnimatedVisibility(
            visible = !state.isRecording,
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
            Text(stringResource(id = R.string.main_screen_title), style = MaterialTheme.typography.displaySmall, textAlign = TextAlign.Center)
        }

        RecordElement(
            recordingStarted = state.isRecording,
            onPermissionDenied = {
                showSnackbar = true
                Timer().schedule(3000L) {
                    showSnackbar = false
                }
            },
            onClick = { duration ->
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
                onClick = {
                    onOpenRecordings()
                }, modifier = Modifier
                    .wrapContentSize()

            ) {
                Text(stringResource(id = R.string.show_recordings), style = MaterialTheme.typography.labelLarge)
            }
        }


        AnimatedVisibility(
            visible = showSnackbar,
            enter = scaleIn() + fadeIn(),
            exit = scaleOut() + fadeOut(),
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(dimensionResource(id = R.dimen.outline_padding))
        ) {
            Snackbar(
                modifier = Modifier.padding(
                    vertical = dimensionResource(
                        id = R.dimen.outline_padding
                    )
                )
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Text(stringResource(id = R.string.permission_snackbar), modifier = Modifier.weight(1f))
                    TextButton(onClick = {
                        Intent(
                            Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                            Uri.fromParts("package", "at.winter.audioRecorder", null)
                        ).also { activity.startActivity(it) }
                    }) {
                        Text(stringResource(id = R.string.grant_permission))
                    }
                }
            }
        }
    }
}

@Composable
fun RecordElement(
    recordingStarted: Boolean,
    onPermissionDenied: () -> Unit,
    onClick: (time: Long) -> Unit,
    modifier: Modifier = Modifier
) {
    val stopWatch = remember { StopWatch() }
    val transition = updateTransition(targetState = recordingStarted, label = "boxTransition")
    val scale by transition.animateFloat(
        transitionSpec = { spring(stiffness = 50f) }, label = "scaleAnimation"
    ) { isRecording ->
        if (isRecording) 0.6f else 1f
    }
    var recordPermissionAllowed by remember {
        mutableStateOf(false)
    }
    val recordingPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { isGranted ->
            recordPermissionAllowed = isGranted
            Log.i(TAG, "Permission granted: $isGranted")
            if (isGranted) {
                stopWatch.start()
                onClick(stopWatch.timeMillis)
            } else {
                onPermissionDenied()
            }
        }
    )



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
                    if (!recordPermissionAllowed){
                        recordingPermissionLauncher.launch(Manifest.permission.RECORD_AUDIO)
                    } else {
                        stopWatch.start()
                        onClick(stopWatch.timeMillis)
                    }
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