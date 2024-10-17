package com.edteam.webserviceandroidapp.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.edteam.webserviceandroidapp.presentation.list.DirectoryScreen
import com.edteam.webserviceandroidapp.presentation.register.RegisterDeveloperScreen

@Composable
fun SetupNavigation() {

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "directory_screen"
    ){

        composable(route = "directory_screen"){
            DirectoryScreen(
                onNavigation = { id ->
                    navController.navigate("register_screen/?id=$id")
                },
            )
        }
        composable(
            route = "register_screen/?id={id}",
            arguments = listOf(navArgument("id"){ defaultValue = -1 })
        ){
            val id = it.arguments?.getInt("id")
            requireNotNull(id)
            RegisterDeveloperScreen(
                id = id,
                onBack = {
                    navController.popBackStack()
                }
            )
        }

    }


}