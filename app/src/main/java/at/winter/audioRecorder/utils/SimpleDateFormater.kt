package at.winter.audioRecorder.utils

import android.os.Build
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Date
import java.util.Locale

class SimpleDateFormatter {
    companion object {
        @JvmStatic
        fun formatTime(timeMillis: Long, pattern: String): String {
            return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val localDateTime =
                    LocalDateTime.ofInstant(
                        Instant.ofEpochMilli(timeMillis),
                        ZoneId.systemDefault()
                    )
                val formatter = DateTimeFormatter.ofPattern(pattern, Locale.getDefault())
                localDateTime.format(formatter)
            } else {
                Date(timeMillis).toLocaleString()
            }
        }
    }
}