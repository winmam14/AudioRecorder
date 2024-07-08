package at.winter.audioRecorder.utils.bluetooth.data

import android.annotation.SuppressLint
import android.bluetooth.BluetoothDevice
import at.winter.audioRecorder.utils.bluetooth.domain.record.BluetoothDeviceDomain

@SuppressLint("MissingPermission")
fun BluetoothDevice.toBluetoothDeviceDomain(): BluetoothDeviceDomain{
    return BluetoothDeviceDomain(
        name = name,
        address = address
    )
}