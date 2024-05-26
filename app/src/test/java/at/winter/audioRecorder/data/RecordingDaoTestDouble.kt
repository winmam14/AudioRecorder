package at.winter.audioRecorder.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow


class RecordingDaoTestDouble: RecordingDao {
    private val recordings = mutableListOf<Recording>()
    override suspend fun insertRecording(recording: Recording) {
        recordings.add(recording)
    }

    override suspend fun deleteRecord(recording: Recording) {
        recordings.remove(recording)
    }

    override fun getRecordsOrderedByTimeStampAsc(): Flow<List<Recording>> {
        return flow { emit(recordings.sortedBy { it.unixTimestamp }) }
    }

    override fun getRecordsOrderedByTimeStampDesc(): Flow<List<Recording>> {
        return flow { emit(recordings.sortedByDescending { it.unixTimestamp }) }
    }

    override fun getRecordsOrderedBySize(): Flow<List<Recording>> {
        return flow { emit(recordings.sortedBy { it.size }) }
    }
}