package com.example.mypets.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.mypets.ui.app.AppScreen
import com.example.mypets.ui.login.LoginScreen
import com.example.mypets.ui.register.RegisterScreen


@Composable
fun NavigationHost(navController: NavHostController) {


    NavHost(navController = navController, startDestination = Destination.LoginScreen.route) {
        composable(route = Destination.LoginScreen.route) {
           LoginScreen(navController = navController)
        }
        composable(route = Destination.RegisterScreen.route){
            RegisterScreen(navController)
        }
        composable(route = Destination.MainScreen.route) {
           AppScreen()
        }
    }
}
