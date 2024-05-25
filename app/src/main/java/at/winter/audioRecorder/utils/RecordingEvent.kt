package at.winter.audioRecorder.utils

import at.winter.audioRecorder.data.Recording
import at.winter.audioRecorder.utils.AudioPlayer.AndroidAudioPlayer

sealed interface RecordingEvent {
    data class StopRecording(val recording: Recording): RecordingEvent
    object StartRecording: RecordingEvent
    data class SortRecordings(val sortType: SortType): RecordingEvent
    data class DeleteRecording(val recording: Recording): RecordingEvent
    data class StartReplay(val recording: Recording, val audioPlayer: AndroidAudioPlayer): RecordingEvent
}