package at.winter.audioRecorder.data

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [Recording::class],
    version = 1
)
abstract class RecordingDatabase: RoomDatabase() {
    abstract val dao: RecordingDao
}