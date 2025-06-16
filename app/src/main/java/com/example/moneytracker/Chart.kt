package com.example.moneytracker

import android.annotation.SuppressLint
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
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
import java.util.*
import kotlin.math.roundToInt

@SuppressLint("UnrememberedMutableState")
@Composable
fun Chart(navController: NavController, viewModel: MainViewModel = viewModel()) {
    var selectedPeriod by remember { mutableStateOf("Monthly") }

    val transactions by rememberUpdatedState(newValue = viewModel.transactions.filter { it.type == "Expense" })
    val filteredTransactions by derivedStateOf { filterTransactionsByPeriod(selectedPeriod, transactions) }

    val total = filteredTransactions.sumOf { it.amount }
    val grouped = filteredTransactions.groupBy { it.category }.mapValues { it.value.sumOf { it.amount } }

    // Ambil warna dari ViewModel
    val categoryColors = grouped.keys.associateWith { viewModel.getCategoryColor(it) }

    Box(modifier = Modifier.fillMaxSize()) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFFBFAF5)),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color(0xFF639F86))
                        .padding(top = 40.dp)
                        .height(70.dp),
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.back),
                        contentDescription = "Back icon",
                        modifier = Modifier
                            .size(36.dp)
                            .align(Alignment.CenterStart)
                            .padding(start = 16.dp)
                            .clickable { navController.popBackStack() },
                        colorFilter = androidx.compose.ui.graphics.ColorFilter.tint(Color.White)
                    )
                    Text(
                        text = "Expense Recap",
                        color = Color(0xFFFFFFFF),
                        fontSize = 25.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
            }

            item { Spacer(modifier = Modifier.height(16.dp)) }

            item {
                Row(
                    modifier = Modifier.padding(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    listOf("Weekly", "Monthly", "Yearly").forEach { period ->
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(10.dp))
                                .background(if (period == selectedPeriod) Color(0xFF639F86) else Color(0xFF9CE5B2))
                                .clickable { selectedPeriod = period }
                                .padding(horizontal = 16.dp, vertical = 8.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(period, color = if (period == selectedPeriod) Color.White else Color.Black)
                        }
                    }
                }
            }

            item { Spacer(modifier = Modifier.height(16.dp)) }

            item {
                PieChartWithLegend(data = grouped, total = total, colors = categoryColors)
            }

            item { Spacer(modifier = Modifier.height(24.dp)) }

            items(grouped.entries.toList().sortedByDescending { it.value }) { entry ->
                ChartDetailRow(
                    category = entry.key,
                    amount = entry.value,
                    total = total,
                    color = categoryColors[entry.key] ?: Color.Gray
                )
            }

            item { Spacer(modifier = Modifier.height(80.dp)) }
        }

        Box(modifier = Modifier.align(Alignment.BottomCenter)) {
            ButtonNav(navController)
        }
    }
}

fun filterTransactionsByPeriod(period: String, allTransactions: List<Transaction>): List<Transaction> {
    val calendar = Calendar.getInstance()
    val nowTime = calendar.time.time
    return when (period) {
        "Weekly" -> {
            calendar.time = Date(nowTime)
            calendar.set(Calendar.HOUR_OF_DAY, 0)
            calendar.set(Calendar.MINUTE, 0)
            calendar.set(Calendar.SECOND, 0)
            calendar.set(Calendar.MILLISECOND, 0)
            calendar.add(Calendar.DAY_OF_YEAR, -7)
            val startOfWeek = calendar.time.time
            allTransactions.filter { it.date >= startOfWeek && it.date < nowTime }
        }
        "Monthly" -> {
            calendar.time = Date(nowTime)
            calendar.set(Calendar.DAY_OF_MONTH, 1)
            calendar.set(Calendar.HOUR_OF_DAY, 0)
            calendar.set(Calendar.MINUTE, 0)
            calendar.set(Calendar.SECOND, 0)
            calendar.set(Calendar.MILLISECOND, 0)
            val startOfMonth = calendar.time.time
            calendar.add(Calendar.MONTH, 1)
            val startOfNextMonth = calendar.time.time
            allTransactions.filter { it.date >= startOfMonth && it.date < startOfNextMonth }
        }
        "Yearly" -> {
            calendar.time = Date(nowTime)
            calendar.set(Calendar.MONTH, 0)
            calendar.set(Calendar.DAY_OF_MONTH, 1)
            calendar.set(Calendar.HOUR_OF_DAY, 0)
            calendar.set(Calendar.MINUTE, 0)
            calendar.set(Calendar.SECOND, 0)
            calendar.set(Calendar.MILLISECOND, 0)
            val startOfYear = calendar.time.time
            calendar.add(Calendar.YEAR, 1)
            val startOfNextYear = calendar.time.time
            allTransactions.filter { it.date >= startOfYear && it.date < startOfNextYear }
        }
        else -> allTransactions
    }
}

@Composable
fun PieChartWithLegend(data: Map<String, Double>, total: Double, colors: Map<String, Color>) {
    if (data.isEmpty()) {
        Text("No expense data", color = Color.Gray, fontSize = 16.sp)
        return
    }
    Canvas(modifier = Modifier.size(200.dp)) {
        var startAngle = 0f
        data.entries.forEach { entry ->
            val sweepAngle = (entry.value / total * 360).toFloat()
            drawArc(
                color = colors[entry.key] ?: Color.Gray,
                startAngle = startAngle,
                sweepAngle = sweepAngle,
                useCenter = true
            )
            startAngle += sweepAngle
        }
    }
}

@Composable
fun ChartDetailRow(category: String, amount: Double, total: Double, color: Color) {
    val percentage = if (total == 0.0) 0 else (amount / total * 100).roundToInt()
    Box(
        modifier = Modifier
            .padding(8.dp)
            .shadow(elevation = 5.dp, shape = RoundedCornerShape(10.dp))
            .background(Color(0xFFF5FFF6), RoundedCornerShape(10.dp))
            .width(330.dp)
            .height(60.dp),
    ) {
        Row(
            modifier = Modifier.fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(start = 10.dp)
            ) {
                Box(
                    modifier = Modifier
                        .background(color)
                        .width(60.dp)
                        .height(35.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text("${percentage}%", color = Color.White, fontSize = 18.sp, fontWeight = FontWeight.Bold)
                }
                Spacer(modifier = Modifier.width(12.dp))
                Text(category, fontSize = 18.sp, fontWeight = FontWeight.SemiBold)
            }
            Text(
                text = "Rp ${amount.toInt()}",
                fontSize = 18.sp,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.padding(end = 10.dp)
            )
        }
    }
}
