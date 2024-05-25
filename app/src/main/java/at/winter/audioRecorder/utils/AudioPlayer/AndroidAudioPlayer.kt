package at.winter.audioRecorder.utils.AudioPlayer

import android.content.Context
import android.media.MediaPlayer
import android.util.Log
import androidx.core.net.toUri
import java.io.File

private val TAG = "AndroidAudioPlayer"
class AndroidAudioPlayer(private var context: Context): AudioPlayer {

    private var player: MediaPlayer? = null

    override fun playMedia(file: File) {
        Log.i(TAG, "Start media player...")
        if(player != null){
            stop()
        }
        Log.i(TAG, "Creating Media Player...")
        MediaPlayer.create(context, file.toUri()).apply {
            player = this
            start()
        }
        file.delete()
    }

    override fun stop() {
        player?.stop()
        player?.release()
        player = null
    }
}