package at.winter.audioRecorder.utils

import at.winter.audioRecorder.data.Recording
import at.winter.audioRecorder.utils.AudioPlayer.AndroidAudioPlayer
import java.io.File

sealed interface RecordingEvent {
    data class StopRecording(val recording: Recording): RecordingEvent
    object StartRecording: RecordingEvent
    data class SortRecordings(val sortType: SortType): RecordingEvent
    data class DeleteRecording(val recording: Recording): RecordingEvent
    data class StartReplay(val file: File, val id: Int,  val audioPlayer: AndroidAudioPlayer): RecordingEvent
    data class StopReplay(val audioPlayer: AndroidAudioPlayer): RecordingEvent
}