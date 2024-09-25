package com.app.ktorcrud.activities

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.app.ktorcrud.ui.ShowInternetStatus
import com.app.ktorcrud.ui.ShowNoInternetStatus
import com.app.ktorcrud.ui.theme.KtorCRUDTheme
import com.app.ktorcrud.utils.NetworkConnection
import com.app.ktorcrud.viewmodel.UserViewModel
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class NavigationActivity : ComponentActivity() {


    private val userViewModel: UserViewModel by viewModel()
    private val networkConnection: NetworkConnection by inject()

    @SuppressLint("FlowOperatorInvokedInComposition", "StateFlowValueCalledInComposition")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            KtorCRUDTheme {
                val networkStatus = networkConnection.networkStatus

                LaunchedEffect(key1 = Unit) {
                    networkStatus.collect { isOffline ->
                        userViewModel.updateStartRoute(if (isOffline) "2" else "1")
                    }
                }
                val startRoute by userViewModel.startRoute.collectAsState()
                NavigationComponent(navController, startRoute)
            }
        }
    }

    @Composable
    private fun NavigationComponent(
        navController: NavHostController,
        startRoute: String
    ) {
        NavHost(navController = navController, startDestination = startRoute) {
            composable("1") {
                ShowNoInternetStatus()
            }
            composable("2") {
                ShowInternetStatus()
//                LoadUsers(userViewModel)
            }
        }
    }
}