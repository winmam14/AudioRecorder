package at.winter.audioRecorder.utils

import android.os.Build
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Date
import java.util.Locale

class StopWatch {
    var formattedTime by mutableStateOf("00:00:000")

    private var coroutineScope = CoroutineScope(Dispatchers.Main)
    private var isActive = false

    private var timeMillis = 0L
    private var lastTimestamp = 0L

    fun start(){
        if(isActive) return

        coroutineScope.launch {
            lastTimestamp = System.currentTimeMillis()
            isActive = true
            while(isActive){
                delay(25L)
                timeMillis += System.currentTimeMillis() - lastTimestamp
                lastTimestamp = System.currentTimeMillis()
                formattedTime = formatTime(timeMillis)
            }
        }
    }

    fun pause(){
        isActive = false
    }

    fun reset(){
        coroutineScope.cancel()
        coroutineScope = CoroutineScope(Dispatchers.Main)
        timeMillis = 0L
        lastTimestamp = 0L
        formattedTime = "00:00:000"
        isActive = false
    }

    private fun formatTime(timeMillis: Long): String{
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val localDateTime =
                LocalDateTime.ofInstant(
                    Instant.ofEpochMilli(timeMillis),
                    ZoneId.systemDefault()
                )
            val formatter = DateTimeFormatter.ofPattern("mm:ss:SSS", Locale.getDefault())
            localDateTime.format(formatter)
        } else {
            Date(timeMillis).toLocaleString()
        }
    }
}