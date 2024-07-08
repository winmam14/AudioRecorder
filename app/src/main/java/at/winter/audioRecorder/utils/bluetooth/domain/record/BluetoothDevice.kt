package at.winter.audioRecorder.utils.bluetooth.domain.record

typealias BluetoothDeviceDomain = BluetoothDevice

data class BluetoothDevice(
    val name: String?,
    val address: String
)
