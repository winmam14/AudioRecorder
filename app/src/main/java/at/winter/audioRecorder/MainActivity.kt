package at.winter.audioRecorder

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.room.Room
import at.winter.audioRecorder.data.RecordingDatabase
import at.winter.audioRecorder.navigation.Navigation
import at.winter.audioRecorder.ui.theme.AudioRecorderTheme
import at.winter.audioRecorder.utils.RecordingViewModel

class MainActivity : ComponentActivity() {

    private val db by lazy {
        Room.databaseBuilder(
            applicationContext,
            RecordingDatabase::class.java,
            "recordings.db"
        ).build()
    }
    private val viewModel by viewModels<RecordingViewModel>(
        factoryProducer = {
            object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return RecordingViewModel(db.dao) as T
                }
            }
        }
    )


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AudioRecorderTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize()
                ) {
                    val state by viewModel.state.collectAsState()
                    Navigation(state = state, onEvent = viewModel::onEvent, activity = this)
                }
            }
        }
    }
}

//@Preview(showBackground = true)
//@Composable
//fun GreetingPreview() {
//    AudioRecorderTheme {
//        Navigation()
//    }
//}