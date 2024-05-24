package at.winter.audioRecorder.utils

import at.winter.audioRecorder.data.Recording

data class RecordingState (
    val recordings: List<Recording> = emptyList(),
    val name: String = "",
    val size: Int = 0,
    val file: ByteArray = ByteArray(0),
    val duration: Long = 0L,
    val unixTimestamp: Long = 0L,
    val isRecording: Boolean = false,
    val sortType: SortType = SortType.TIMESTAMP_DESC
)