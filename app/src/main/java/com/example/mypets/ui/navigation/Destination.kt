package com.example.mypets.ui.navigation

sealed class Destination(val route : String) {

    object LoginScreen : Destination("login_screen")
    object RegisterScreen : Destination("register_screen")
    object MainScreen : Destination("main_screen")
    object Pets : Destination("pets")
    object Profile : Destination("profile")
    object Lost : Destination("lost")
    object MissList : Destination("miss")
    object Details : Destination("details/{id}"){
        fun createRoute (id: Int) = "details/${id}"
    }

    object ApplyForAdoption : Destination("apply/{id}"){
        fun createRoute (id: Int) = "apply/${id}"
    }
}