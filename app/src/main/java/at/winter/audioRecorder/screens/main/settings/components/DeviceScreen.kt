package at.winter.audioRecorder.screens.main.settings.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import at.winter.audioRecorder.utils.bluetooth.domain.record.BluetoothDevice
import at.winter.audioRecorder.utils.bluetooth.presentation.BluetoothUiState

@Composable
fun DeviceScreen(
    state: BluetoothUiState,
    onStartScan: () -> Unit,
    onStopScan: () -> Unit,
    onDeviceClicked: (BluetoothDevice) -> Unit,
    onStartServer: () -> Unit
) {
    Column(modifier = Modifier.fillMaxSize()) {
        BluetoothDeviceList(pairedDevices = state.pairedDevices, scannedDevices = state.scannedDevices, onClick = onDeviceClicked, modifier = Modifier.fillMaxWidth().weight(1f))
        Row(modifier = Modifier.fillMaxWidth().padding(vertical = 16.dp), horizontalArrangement = Arrangement.SpaceAround) {
            Button(onClick = onStartScan) {
                Text(text = "Start Scan")
            }
            Button(onClick = onStopScan) {
                Text(text = "Stop Scan")
            }
            Button(onClick = onStartServer) {
                Text(text = "Start Server")
            }
        }
    }
}

@Composable
fun BluetoothDeviceList(
    pairedDevices: List<BluetoothDevice>,
    scannedDevices: List<BluetoothDevice>,
    onClick: (BluetoothDevice) -> Unit,
    modifier: Modifier
) {
    LazyColumn(modifier) {
        item {
            Text(
                text = "Paired Devices",
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp,
                modifier = Modifier.padding(16.dp)
            )
        }
        items(pairedDevices) { device ->
            Text(device.name ?: "(No name)", modifier = Modifier
                .fillMaxWidth()
                .clickable { onClick(device) }
                .padding(16.dp))
        }
        item {
            Text(
                text = "Scanned Devices",
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp,
                modifier = Modifier.padding(16.dp)
            )
        }
        items(scannedDevices) { device ->
            Text(device.name ?: "(No name)", modifier = Modifier
                .fillMaxWidth()
                .clickable { onClick(device) }
                .padding(16.dp))
        }
    }
}