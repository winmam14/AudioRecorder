package at.winter.audioRecorder.navigation

import android.app.Activity
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import at.winter.audioRecorder.screens.*
import at.winter.audioRecorder.screens.main.MainScreen
import at.winter.audioRecorder.screens.main.RecordingsScreen
import at.winter.audioRecorder.screens.main.settings.SettingsScreen
import at.winter.audioRecorder.utils.RecordingEvent
import at.winter.audioRecorder.utils.RecordingState

@Composable
fun Navigation(state: RecordingState, activity: Activity, onEvent: (RecordingEvent) -> Unit) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Screen.LandingScreen.route) {
        composable(route = Screen.LandingScreen.route) {
            LandingScreen(state = state, onOpenMainScreen = {
                navController.navigate(Screen.MainScreen.route)
            })
        }
        composable(route = Screen.MainScreen.route) {
            MainScreen(
                state = state,
                onEvent = onEvent,
                activity = activity,
                onOpenRecordings = { navController.navigate(Screen.RecordingsScreen.route) },
                onOpenSettings = { navController.navigate(Screen.SettingsScreen.route) }
            )
        }
        composable(route = Screen.RecordingsScreen.route) {
            RecordingsScreen(state = state, onEvent = onEvent)
        }
        composable(route = Screen.SettingsScreen.route) {
            SettingsScreen()
        }
    }
}