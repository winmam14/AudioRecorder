package at.winter.audioRecorder.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.sql.Blob

@Entity(tableName = "recordings")
data class Recording(
    val name: String,
    val size: Int,
    @ColumnInfo(typeAffinity = ColumnInfo.BLOB)
    val file: ByteArray,
    val duration: Long,
    val unixTimestamp: Long,
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0
)
