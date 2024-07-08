package at.winter.audioRecorder.viewModels

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow

class SettingsViewModel: ViewModel() {
    val state = MutableStateFlow(SettingsState())

}