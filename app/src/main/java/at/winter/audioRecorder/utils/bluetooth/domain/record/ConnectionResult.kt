package at.winter.audioRecorder.utils.bluetooth.domain.record

sealed interface ConnectionResult {
    object ConnectionEstablished: ConnectionResult
    data class Error(val message: String): ConnectionResult
}