package com.target.targetcasestudy

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import com.target.targetcasestudy.navigation.HomeRoute
import com.target.targetcasestudy.navigation.MainNavHost
import com.target.targetcasestudy.navigation.screenList
import com.target.targetcasestudy.ui.theme.AndroidcasestudymainTheme
import com.target.targetcasestudy.ui.theme.Red

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainApp() {
    AndroidcasestudymainTheme {
        val navController = rememberNavController()
        var actionBarTitle by rememberSaveable { mutableStateOf(HomeRoute.title) }
        var shouldShowNavigateUp by rememberSaveable { mutableStateOf(false) }


        LaunchedEffect(navController) {
            navController.currentBackStackEntryFlow .collect { backStackEntry ->
                val destination = backStackEntry.destination
                val screen = screenList.find { destination.route?.contains(it.route) == true } ?: HomeRoute
                actionBarTitle = screen.title
                shouldShowNavigateUp = screen.navigateUp
            }
        }
        Surface(modifier = Modifier.fillMaxSize()) {
            Scaffold(
                topBar = {
                    TopAppBar(
                        title = { Text(text = actionBarTitle) },
                        modifier = Modifier.shadow(4.dp),
                        navigationIcon = {
                            if (shouldShowNavigateUp) {
                                IconButton(onClick = { navController.popBackStack() }) {
                                    Icon(
                                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                        tint = Red,
                                        contentDescription = "Navigate Up"
                                    )
                                }
                            }
                        }
                    )
                },
                modifier = Modifier.fillMaxSize()
            ) { innerPadding ->
                Box(modifier = Modifier.padding(innerPadding)) {
                    MainNavHost(
                        navHostController = navController
                    )
                }
            }
        }
    }
}