package at.winter.audioRecorder.utils.AudioPlayer

interface AudioPlayer {
    fun playMedia(media: ByteArray)
    fun stop()
}