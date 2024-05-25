package at.winter.audioRecorder.utils.AudioPlayer

import java.io.File

interface AudioPlayer {
    fun playMedia(file: File)
    fun stop()
}