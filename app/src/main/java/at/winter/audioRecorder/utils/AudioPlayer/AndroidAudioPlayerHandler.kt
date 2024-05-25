package at.winter.audioRecorder.utils.AudioPlayer

import android.content.Context
import android.util.Log
import at.winter.audioRecorder.data.Recording
import at.winter.audioRecorder.utils.RecordingEvent
import java.io.File
import java.io.FileOutputStream

private val TAG = "AndroidAudioPlayerHandler"
class AndroidAudioPlayerHandler(private var applicationContext: Context) {
    private var player: AndroidAudioPlayer = AndroidAudioPlayer(applicationContext)
    fun toggle(isReplaying: Boolean, currentRecording: Int, recording: Recording ): RecordingEvent {
        return if (isReplaying && currentRecording == recording.id) {
            Log.i(TAG, "Stop playing...")
            RecordingEvent.StopReplay(player)
        } else {
            Log.i(TAG, "Start playing...")
            RecordingEvent.StartReplay(writeTempFile(recording.file),recording.id,player)
        }
    }

    private fun writeTempFile(media: ByteArray): File {
        val file = File(applicationContext.cacheDir, "replay.pm3")
        FileOutputStream(file).write(media)
        return file
    }
}