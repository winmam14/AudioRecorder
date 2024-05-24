package at.winter.audioRecorder.utils.AudioRecorder

import java.io.File

interface AudioRecorder {
    fun start(outputFile: File)
    fun stop()
}