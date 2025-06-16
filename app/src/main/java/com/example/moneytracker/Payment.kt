package com.example.moneytracker

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
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun Payment(navController: NavController, viewModel: MainViewModel = viewModel()) {
    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFFBFAF5)),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
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
                    text = "Scheduled Payments",
                    color = Color(0xFFFFFFFF),
                    fontSize = 25.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        }
        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
        ) {
            ButtonNav(navController = navController)
        }

        Box(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(end = 30.dp, bottom = 120.dp)
                .size(50.dp)
                .background(Color(0xFF639F86), shape = CircleShape),
        ) {
            IconButton(onClick = { navController.navigate("input_payment") }) {
                Icon(
                    Icons.Rounded.Add,
                    contentDescription = "Add payment",
                    modifier = Modifier
                        .size(40.dp)
                        .align(Alignment.Center)
                )
            }
        }

        Column(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(top = 130.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            PaymentPage(viewModel = viewModel)
        }
    }
}

@Composable
fun PaymentPage(viewModel: MainViewModel) {
    var paymentToDelete by remember { mutableStateOf<PaymentEntity?>(null) }

    LazyColumn(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        items(viewModel.payments, key = { payment -> payment.id }) { payment ->
            val dateParts = payment.date.split("/")
            val month = dateParts[0].toInt()
            val day = dateParts[1].toInt()
            val year = dateParts[2].toInt()
            val calendar = Calendar.getInstance()
            calendar.set(year, month - 1, day)

            val monthName = SimpleDateFormat("MMMM", Locale.US).format(calendar.time)
            val dayOfWeek = calendar.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.US)
            val formattedDate = "$monthName $day $year, $dayOfWeek"

            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = formattedDate,
                    fontSize = 15.sp,
                    modifier = Modifier.padding(top = 20.dp, bottom = 5.dp),
                    textAlign = TextAlign.Center
                )
                Box(
                    modifier = Modifier
                        .padding(8.dp)
                        .shadow(elevation = 5.dp, shape = RoundedCornerShape(10.dp))
                        .clip(RoundedCornerShape(10.dp))
                        .background(Color(0xFFF5FFF6))
                        .width(330.dp)
                        .height(90.dp)
                        .clickable { paymentToDelete = payment }
                ) {
                    Row(
                        modifier = Modifier.fillMaxSize(),
                        horizontalArrangement = Arrangement.SpaceAround,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Start
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(70.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = payment.icon,
                                    fontSize = 40.sp,
                                )
                            }
                            Spacer(modifier = Modifier.size(5.dp))
                            Column {
                                Text(
                                    text = payment.title,
                                    fontSize = 17.sp,
                                    fontWeight = FontWeight.Bold,
                                )
                                Text(
                                    text = payment.reminder,
                                    fontSize = 12.sp,
                                )
                            }
                        }
                        Text(
                            text = "Rp ${payment.totalPayment}",
                            fontSize = 17.sp,
                            color = Color.Red,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }
    }

    paymentToDelete?.let { payment ->
        AlertDialog(
            onDismissRequest = { paymentToDelete = null },
            title = { Text("Delete Payment") },
            text = { Text("Are you sure you want to delete '${payment.title}'?") },
            confirmButton = {
                TextButton(onClick = {
                    viewModel.deletePayment(payment)
                    paymentToDelete = null
                }) {
                    Text("Delete", color = Color.Red)
                }
            },
            dismissButton = {
                TextButton(onClick = { paymentToDelete = null }) {
                    Text("Cancel")
                }
            }
        )
    }

    if (viewModel.payments.isEmpty()) {
        Text(
            text = "No scheduled payments yet.",
            color = Color.Gray,
            modifier = Modifier.padding(top = 20.dp)
        )
    }
}