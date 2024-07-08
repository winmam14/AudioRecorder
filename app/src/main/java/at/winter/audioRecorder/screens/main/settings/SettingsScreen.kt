package at.winter.audioRecorder.screens.main.settings

import android.bluetooth.BluetoothManager
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import at.winter.audioRecorder.screens.main.settings.components.DeviceScreen
import at.winter.audioRecorder.utils.bluetooth.data.AndroidBluetoothController
import at.winter.audioRecorder.viewModels.BluetoothViewModel

@Composable
fun SettingsScreen(){
    val context = LocalContext.current
    val bluetoothManager by lazy {
        // returns null if device does not have Bluetooth
        context.getSystemService(BluetoothManager::class.java)
    }
    // contains functionality
    val bluetoothAdapter by lazy {
        bluetoothManager?.adapter
    }

    val isBluetoothEnabled by lazy {
        bluetoothAdapter?.isEnabled == true
    }

    val bluetoothController = AndroidBluetoothController(LocalContext.current)
    val viewModel = viewModel<BluetoothViewModel>(
        factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return BluetoothViewModel(bluetoothController) as T
            }
        }
    )
    val state by viewModel.state.collectAsState()

    LaunchedEffect(key1 = state.errorMessage){
        state.errorMessage?.let { message ->
            Toast.makeText(context, message, Toast.LENGTH_LONG).show()
        }
    }

    LaunchedEffect(key1 = state.isConnected){
        if(state.isConnected) {
            Toast.makeText(context, "You are connected!", Toast.LENGTH_LONG).show()
        }
    }

    Surface(color = MaterialTheme.colorScheme.background) {
        when {
            state.isConnecting ->{
                Column(modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center) {
                    CircularProgressIndicator()
                    Text("Is Connecting...")
                }
            }
            else -> {
                DeviceScreen(
                    state = state,
                    onStartScan = viewModel::startScan,
                    onStopScan = viewModel::stopScan,
                    onDeviceClicked = viewModel::connectToDevice,
                    onStartServer = viewModel::waitForIncommingConnections
                )
            }
        }

    }
}