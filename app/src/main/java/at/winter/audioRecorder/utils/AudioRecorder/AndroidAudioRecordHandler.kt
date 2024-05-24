package at.winter.audioRecorder.utils.AudioRecorder

import android.content.Context
import android.util.Log
import java.io.File

const val TAG = "AndroidAudioRecordHandler"

enum class ActiveState{
    INACTIVE, ACTIVE
}

class AndroidAudioRecordHandler(private var applicationContext: Context){
    private var state: ActiveState = ActiveState.INACTIVE
    private var recorder: AndroidAudioRecorder = AndroidAudioRecorder(applicationContext)

    fun toggle(){
        when(state){
            ActiveState.ACTIVE -> {
                Log.i(TAG, "Stop Recording...")
                state = ActiveState.INACTIVE
                recorder.stop()
            }
            ActiveState.INACTIVE -> {
                Log.i(TAG, "Start Recording...")
                state = ActiveState.ACTIVE
                recorder.start(createFile())
            }
        }
    }

    private fun createFile(): File {
        val filename = "record_${System.currentTimeMillis()}.mp3"
        Log.i(TAG, "Create cache file with name: $filename")
        return File(applicationContext.cacheDir, filename)
    }
}