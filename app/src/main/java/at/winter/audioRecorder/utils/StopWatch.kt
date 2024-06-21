package at.winter.audioRecorder.utils

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class StopWatch {
    var formattedTime by mutableStateOf("00:00:000")
    var timeMillis = 0L
    private set

    private var coroutineScope = CoroutineScope(Dispatchers.Main)
    private var isActive = false

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
                formattedTime = SimpleDateFormatter.formatTime(timeMillis, "mm:ss:SSS")
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
}