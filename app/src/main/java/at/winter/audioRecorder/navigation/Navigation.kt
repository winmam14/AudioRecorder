package at.winter.audioRecorder.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import at.winter.audioRecorder.screens.*

@Composable
fun Navigation(){
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Screen.MainScreen.route){
        composable(route = Screen.MainScreen.route){
            MainScreen { navController.navigate(Screen.RecordingsScreen.route) }
        }
        composable(route = Screen.RecordingsScreen.route){
            RecordingsScreen()
        }

    }
}