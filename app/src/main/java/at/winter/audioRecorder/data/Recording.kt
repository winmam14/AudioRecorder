package at.winter.audioRecorder.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "recordings")
data class Recording(
    val name: String,
    val size: Int,
    val file: ByteArray,
    val duration: Long,
    val unixTimestamp: Long,
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0
)
