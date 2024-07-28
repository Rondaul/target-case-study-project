package com.target.targetcasestudy.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.EaseIn
import androidx.compose.animation.core.EaseOut
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraph.Companion.findStartDestination
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
        enterTransition = { EnterTransition.None },
        exitTransition = { ExitTransition.None },
        modifier = modifier
    ) {
        composable(route = HomeRoute.route) {
            HomeScreen(
                onDealItemClick = {
                    navHostController.navigateWithArgs(DetailsRoute.route, 1)
                }
            )
        }
        composable(
            route = DetailsRoute.routeWithArgs,
            arguments = DetailsRoute.arguments,
            enterTransition = {
                fadeIn(
                    animationSpec = tween(
                        300, easing = LinearEasing
                    )
                ) + slideIntoContainer(
                    animationSpec = tween(300, easing = EaseIn),
                    towards = AnimatedContentTransitionScope.SlideDirection.Start
                )
            },
            exitTransition = {
                fadeOut(
                    animationSpec = tween(
                        300, easing = LinearEasing
                    )
                ) + slideOutOfContainer(
                    animationSpec = tween(300, easing = EaseOut),
                    towards = AnimatedContentTransitionScope.SlideDirection.End
                )
            }
            ) { navBackStackEntry ->
            val id = navBackStackEntry.arguments?.getInt(DetailsRoute.idArg)
            DetailsScreen(id = id)
        }
    }
}

fun NavHostController.navigateSingleTopTo(route: String) = this.navigate(route) {
        popUpTo(
            this@navigateSingleTopTo.graph.findStartDestination().id
        ) { saveState = true }
        launchSingleTop = true
        restoreState = true
    }

private fun NavHostController.navigateWithArgs(route: String, id: Int) {
    this.navigateSingleTopTo("$route/$id")
}