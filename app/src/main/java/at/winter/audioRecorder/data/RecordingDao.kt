package at.winter.audioRecorder.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface RecordingDao {
    @Insert
    suspend fun insertRecording(recording: Recording)

    @Delete
    suspend fun deleteRecord(recording: Recording)

    // Flow notifies if content of table changes
    @Query("SELECT * FROM recordings ORDER BY unixTimestamp")
    fun getRecordsOrderedByTimeStampAsc(): Flow<List<Recording>>

    @Query("SELECT * FROM recordings ORDER BY unixTimestamp DESC")
    fun getRecordsOrderedByTimeStampDesc(): Flow<List<Recording>>

    @Query("SELECT * FROM recordings ORDER BY size DESC")
    fun getRecordsOrderedBySize(): Flow<List<Recording>>

}