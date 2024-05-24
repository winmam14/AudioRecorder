package at.winter.audioRecorder.utils

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.room.PrimaryKey
import at.winter.audioRecorder.data.Recording
import at.winter.audioRecorder.data.RecordingDao
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.File

class RecordingViewModel(
    private val dao: RecordingDao
): ViewModel(){
    private val _sortType = MutableStateFlow(SortType.TIMESTAMP_DESC)
    @OptIn(ExperimentalCoroutinesApi::class)
    private val _recordings = _sortType
        .flatMapLatest { sortType ->
            when(sortType){
                SortType.TIMESTAMP_ASC -> dao.getRecordsOrderedByTimeStampAsc()
                SortType.TIMESTAMP_DESC -> dao.getRecordsOrderedByTimeStampDesc()
                SortType.FILE_SIZE -> dao.getRecordsOrderedBySize()
            }
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())
    private val _state = MutableStateFlow(RecordingState())

    val state = combine(_state, _sortType, _recordings){ state, sortType, contacts ->
        state.copy(
            recordings = contacts,
            sortType = sortType

        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), RecordingState())

    fun onEvent(event: RecordingEvent){
        when(event){
            is RecordingEvent.SortRecordings -> {
                _sortType.value = event.sortType
            }

            is RecordingEvent.StopRecording -> {
                val name = state.value.name
                val size = state.value.size
                val file = state.value.file
                val duration = state.value.duration
                val unixTimestamp = state.value.unixTimestamp

                if (name.isBlank() || size <= 0 || file.isEmpty() || duration <= 0 || unixTimestamp <= 0){
                    return
                }

                val recording = Recording(
                    name = name,
                    size = size,
                    file = file,
                    duration = duration,
                    unixTimestamp = unixTimestamp
                )

                viewModelScope.launch {
                    dao.insertRecording(recording)
                }

                _state.update { it.copy(
                    isRecording = false
                ) }

            }

            is RecordingEvent.DeleteRecording -> {
                viewModelScope.launch {
                    dao.deleteRecord(event.recording)
                }
            }

            RecordingEvent.StartRecording -> {
                _state.update { it.copy(
                    isRecording = true
                ) }
            }
        }
    }
}