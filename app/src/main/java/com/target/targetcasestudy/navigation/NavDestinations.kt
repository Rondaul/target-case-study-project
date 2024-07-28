package com.target.targetcasestudy.navigation

/**
 * This file holds all the routes of the app along with the necessary data required during
 * navigation
 */

/**
 * Sealed Interface for all destinations
 * [title] = title of the app bar of the current screen
 * [route] = route corresponding to the current screen
 * [navigateUp] = boolean flag to show or hide the navigate up icon in the TopAppBar
 */
sealed interface NavDestinations {
    val title: String
    val route: String
    val navigateUp: Boolean
}

/**
 * Route for HomeScreen
 */
data object HomeRoute: NavDestinations {
    override val title = "List"
    override val route = "home"
    override val navigateUp = false
}

/**
 * Route for DetailsScreen
 */
data object DetailsRoute: NavDestinations {
    override val title = "Details"
    override val route = "details"
    override val navigateUp = true
}

/**
 * Holds list of all the routes in the app
 */
val screenList = listOf(HomeRoute, DetailsRoute)