package com.example.moneytracker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.moneytracker.ui.theme.MoneyTrackerTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MoneyTrackerTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    val navController = rememberNavController()
                    val mainViewModel: MainViewModel = viewModel()

                    // Button Navigation
                    NavHost(
                        navController = navController,
                        startDestination = "home",
                        modifier = Modifier.padding(innerPadding)
                    ) {
                        composable("home") {
                            Home(
                                navController = navController,
                                name = "User",
                                viewModel = mainViewModel
                            )
                        }
                        composable("charts") { Chart(navController = navController) }
                        composable("saving") { Saving(navController = navController, viewModel = mainViewModel) }
                        composable("payment") { Payment(navController = navController) }
                        composable("activity") {
                            // Activity ketika user menambahkan data pengeluaran dan pemasukan
                            Activity(
                                navController = navController,
                                viewModel = mainViewModel
                            )
                        }
                        composable("input_payment") { InputPayment(navController = navController) }
                    }
                }
            }
        }
    }
}