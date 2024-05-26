package at.winter.audioRecorder.utils.AudioRecorder

import android.content.Context
import android.util.Log
import at.winter.audioRecorder.data.Recording
import at.winter.audioRecorder.utils.RecordingEvent
import java.io.File

private val TAG = "AndroidAudioRecordHandler"

class AndroidAudioRecordHandler(private var applicationContext: Context) {
    private var recorder: AndroidAudioRecorder = AndroidAudioRecorder(applicationContext)
    private var file: File? = null

    fun toggle(durationMs: Long, isRecording: Boolean): RecordingEvent {
        if (isRecording) {
            Log.i(TAG, "Stop Recording...")
            recorder.stop()
            return if (file != null) {
                val recording = Recording(
                    name = file!!.nameWithoutExtension,
                    size = file!!.readBytes().size,
                    file = file!!.readBytes(),
                    duration = durationMs,
                    unixTimestamp = System.currentTimeMillis()
                )
                file!!.delete()
                RecordingEvent.StopRecording(recording)
            } else {
                RecordingEvent.StopRecording(Recording("", 0, ByteArray(0), 0L, 0L))
            }
        } else {
            Log.i(TAG, "Start Recording...")
            file = createFile()
            recorder.start(file!!)
            return RecordingEvent.StartRecording
        }
    }

    private fun createFile(): File {
        val filename = "R${System.currentTimeMillis()}.mp3"
        Log.i(TAG, "Create cache file with name: $filename")
        return File(applicationContext.cacheDir, filename)
    }
}