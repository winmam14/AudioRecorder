package at.winter.audioRecorder.utils.bluetooth

import at.winter.audioRecorder.utils.bluetooth.domain.record.BluetoothDevice
import at.winter.audioRecorder.utils.bluetooth.domain.record.ConnectionResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import java.sql.Connection

interface BluetoothController {
    val isConnected: StateFlow<Boolean>
    val scannedDevices: StateFlow<List<BluetoothDevice>>
    val pairedDevices: StateFlow<List<BluetoothDevice>>
    val errors: SharedFlow<String>

    fun startDiscovery()
    fun stopDiscovery()

    fun release()

    fun startServer(): Flow<ConnectionResult>
    fun connectToDevice(device: BluetoothDevice): Flow<ConnectionResult>
    fun closeConnection()
}