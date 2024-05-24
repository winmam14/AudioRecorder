package at.winter.audioRecorder.navigation

sealed class Screen(val route: String) {
    object MainScreen : Screen("main_screen")
    object RecordingsScreen : Screen("recordings_screen")
}