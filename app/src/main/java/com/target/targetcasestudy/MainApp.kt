package com.target.targetcasestudy

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
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.target.targetcasestudy.navigation.HomeRoute
import com.target.targetcasestudy.navigation.MainNavHost
import com.target.targetcasestudy.navigation.screenList
import com.target.targetcasestudy.ui.theme.AndroidcasestudymainTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainApp() {
    AndroidcasestudymainTheme {
        val navController = rememberNavController()
        val currentBackStack by navController.currentBackStackEntryAsState()
        val currentDestination = currentBackStack?.destination
        val currentScreen = screenList.find { it.route == currentDestination?.route } ?: HomeRoute
        Surface(modifier = Modifier.fillMaxSize()) {
            Scaffold(
                topBar = {
                    TopAppBar(
                        title = { Text(text = currentScreen.title) },
                        modifier = Modifier.shadow(4.dp),
                        navigationIcon = {
                            if (currentScreen.navigateUp) {
                                IconButton(onClick = { /* do something */ }) {
                                    Icon(
                                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                        contentDescription = "Navigate Up"
                                    )
                                }
                            }
                        }
                    )
                },
                modifier = Modifier.fillMaxSize()
            ) { innerPadding ->
                MainNavHost(
                    navHostController = navController,
                    modifier = Modifier.padding(innerPadding)
                )
            }
        }
    }
}