package com.example.moneytracker

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun Home(navController: NavController, name: String, modifier: Modifier = Modifier, viewModel: MainViewModel = viewModel()) {
    val transactions = viewModel.transactions ?: emptyList() // Fallback to empty list if null
    Log.d("Home", "Transactions in Home: ${transactions.size}")
    Log.d("HomeVM", "Hash: ${viewModel.hashCode()}, Size: ${viewModel.transactions.size}")

    val currentDate = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(Date())

    val totalIncome = transactions.filter { it.type == "Income" }.sumOf { it.amount }
    val totalExpenses = transactions.filter { it.type == "Expense" }.sumOf { it.amount }
    val totalBalance = totalIncome - totalExpenses

    Box(modifier = modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFFFBFAF5)),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Header
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFF639F86))
                    .padding(top = 40.dp)
                    .height(70.dp),
                contentAlignment = Alignment.TopCenter
            ) {
                Text(
                    text = "Money Tracker",
                    color = Color.White,
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold
                )
            }
            // Date and Calendar
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = currentDate,
                    modifier = Modifier.padding(start = 16.dp),
                    fontSize = 18.sp,
                    color = Color.Black
                )
                Image(
                    painter = painterResource(id = R.drawable.calendar),
                    contentDescription = "Calendar Icon",
                    modifier = Modifier.size(24.dp)
                )
            }
            // Summary Box
            Box(
                modifier = Modifier
                    .shadow(elevation = 5.dp, shape = RoundedCornerShape(10.dp))
                    .clip(RoundedCornerShape(10.dp))
                    .background(Color(0xFFF5FFF6))
                    .width(350.dp)
                    .height(70.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(text = "Rp ${totalBalance.toInt()}", fontWeight = FontWeight.Bold)
                        Text(text = "Total")
                    }
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(text = "Rp ${totalIncome.toInt()}", fontWeight = FontWeight.Bold, color = Color.Green)
                        Text(text = "Income")
                    }
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(text = "Rp ${totalExpenses.toInt()}", fontWeight = FontWeight.Bold, color = Color.Red)
                        Text(text = "Expenses")
                    }
                }
            }
            // Time Period Tabs
            Spacer(modifier = Modifier.height(20.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                listOf("Weekly", "Monthly", "Yearly").forEach { period ->
                    Box(
                        modifier = Modifier
                            .padding(start = 5.dp)
                            .clip(RoundedCornerShape(10.dp))
                            .background(if (period == "Monthly") Color(0xFF639F86) else Color(0xFF9CE5B2))
                            .width(90.dp)
                            .height(30.dp)
                            .clickable { /* Handle period change */ },
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = period,
                            color = if (period == "Monthly") Color.White else Color.Black
                        )
                    }
                }
            }
            // Transaction List
            Spacer(modifier = Modifier.height(10.dp))
            val groupedTransactions = transactions.groupBy {
                SimpleDateFormat("EEE, dd/MM", Locale.getDefault()).format(it.date.time)
            }

            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 10.dp)
            ) {
                groupedTransactions.forEach { (date, dailyTransactions) ->
                    item {
                        // Tampilkan tanggal sekali
                        Text(
                            text = date,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(vertical = 8.dp)
                        )
                    }

                    items(dailyTransactions) { transaction ->
                        TransactionItem(transaction)
                        Spacer(modifier = Modifier.height(8.dp))
                    }
                }
            }
        }
        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
        ) {
            ButtonNav(navController)
        }
        Box(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(end = 24.dp, bottom = 100.dp)
        ) {
            IconButton(
                onClick = { navController.navigate("activity") },
                modifier = Modifier
                    .clip(CircleShape)
                    .background(Color(0xFF79B99C))
                    .size(56.dp)
            ) {
                Icon(
                    imageVector = Icons.Rounded.Add,
                    contentDescription = "Add Transaction",
                    modifier = Modifier.size(40.dp),
                    tint = Color.White
                )
            }
        }
    }
}

@Composable
fun TransactionItem(transaction: Transaction) {
    val splitCategory = transaction.category.split(" ", limit = 2)
    val emoji = splitCategory.getOrNull(0) ?: "❓"
    val categoryName = splitCategory.getOrNull(1) ?: transaction.category

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(elevation = 5.dp, shape = RoundedCornerShape(10.dp))
            .clip(RoundedCornerShape(10.dp))
            .background(Color(0xFFF5FFF6))
            .height(80.dp)
            .padding(horizontal = 16.dp),
        contentAlignment = Alignment.Center
    ) {
        Row(
            modifier = Modifier.fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(text = emoji, fontSize = 24.sp)
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = categoryName, fontSize = 18.sp)
            }
            Text(
                text = if (transaction.type == "Income") "+Rp ${transaction.amount.toInt()}" else "-Rp ${transaction.amount.toInt()}",
                color = if (transaction.type == "Income") Color.Green else Color.Red,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
fun ButtonNav(navController: NavController) {
    val items = listOf("Home", "Charts", "Saving", "Payment")
    val icons = listOf(
        R.drawable.home,
        R.drawable.chart,
        R.drawable.saving,
        R.drawable.payment
    )
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFF639F86))
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        items.forEachIndexed { index, label ->
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier
                    .clickable {
                        when (label) {
                            "Home" -> navController.navigate("home") {
                                popUpTo(navController.graph.startDestinationId)
                                launchSingleTop = true
                            }
                            "Charts" -> navController.navigate("charts") {
                                popUpTo(navController.graph.startDestinationId)
                                launchSingleTop = true
                            }
                            "Saving" -> navController.navigate("saving") {
                                popUpTo(navController.graph.startDestinationId)
                                launchSingleTop = true
                            }
                            "Payment" -> navController.navigate("payment") {
                                popUpTo(navController.graph.startDestinationId)
                                launchSingleTop = true
                            }
                        }
                    }
            ) {
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                        .background(Color(0xFFF5FFF6)),
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = painterResource(id = icons[index]),
                        contentDescription = label,
                        modifier = Modifier.size(30.dp)
                    )
                }
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = label,
                    color = Color.Black,
                    fontSize = 14.sp
                )
            }
        }
    }

    @Composable
    fun TransactionItem(transaction: Transaction) {
        val dateFormat = SimpleDateFormat("EEE, dd/MM", Locale.getDefault())
        val transactionDate = dateFormat.format(transaction.date.time)
        val splitCategory = transaction.category.split(" ", limit = 2)
        val emoji = splitCategory.getOrNull(0) ?: "❓"
        val categoryName = splitCategory.getOrNull(1) ?: transaction.category

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 5.dp)
        ) {
            Box(
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .offset(y = (-10).dp)
                    .clip(RoundedCornerShape(bottomEnd = 10.dp))
                    .background(Color(0xFF9CE5B2))
                    .padding(horizontal = 16.dp, vertical = 6.dp)
            ) {
                Text(
                    text = transactionDate,
                    color = Color.Black,
                    fontWeight = FontWeight.SemiBold
                )
            }
            Box(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .shadow(elevation = 5.dp, shape = RoundedCornerShape(10.dp))
                    .clip(RoundedCornerShape(10.dp))
                    .background(Color(0xFFF5FFF6))
                    .fillMaxWidth()
                    .height(80.dp)
                    .padding(horizontal = 16.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxSize(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = emoji,
                            fontSize = 24.sp
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(text = categoryName, fontSize = 18.sp)
                    }
                    Text(
                        text = if (transaction.type == "Income") "+Rp ${transaction.amount.toInt()}" else "-Rp ${transaction.amount.toInt()}",
                        color = if (transaction.type == "Income") Color.Green else Color.Red,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}