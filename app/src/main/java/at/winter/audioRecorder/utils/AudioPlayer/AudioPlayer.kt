package at.winter.audioRecorder.utils.AudioPlayer

import java.io.File

interface AudioPlayer {
    fun playFile(file: File)
    fun stop()
}