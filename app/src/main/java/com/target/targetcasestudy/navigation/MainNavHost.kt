package com.target.targetcasestudy.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.target.targetcasestudy.ui.details.DetailsScreen
import com.target.targetcasestudy.ui.home.HomeScreen

@Composable
fun MainNavHost(
    navHostController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navHostController,
        startDestination = HomeRoute.route,
        modifier = modifier
    ) {
        composable(route = HomeRoute.route) {
            HomeScreen()
        }
        composable(route = DetailsRoute.route) {
            DetailsScreen()
        }
    }
}