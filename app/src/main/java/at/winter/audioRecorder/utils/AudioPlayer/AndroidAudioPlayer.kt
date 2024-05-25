package at.winter.audioRecorder.utils.AudioPlayer

import android.content.Context
import android.media.MediaPlayer
import android.net.Uri
import android.util.Log
import androidx.core.net.toUri
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream

class AndroidAudioPlayer(private var context: Context): AudioPlayer {

    private var player: MediaPlayer? = null

    override fun playMedia(media: ByteArray) {
        if(player != null){
            stop()
        }
        val file = createTempFile(media)
        MediaPlayer.create(context, file.toUri()).apply {
            player = this
            start()
        }
    }

    override fun stop() {
        player?.stop()
        player?.release()
        player = null
    }

    private fun createTempFile(media: ByteArray): File {
        val file = File(context.cacheDir, "replay.pm3")
        FileOutputStream(file).write(media)
        return file
    }

}