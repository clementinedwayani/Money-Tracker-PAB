package com.example.moneytracker

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController

@Composable
fun Saving(navController: NavController, viewModel: MainViewModel = viewModel()) {
    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0XFFFBFAF5)),
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
                    text = "Savings",
                    color = Color(0xFFFFFFFF),
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        }
        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
        ) {
            ButtonNav(navController)
        }
        Column(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(top = 130.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            SavingBox(viewModel)
        }
    }
}

@Composable
fun SavingBox(viewModel: MainViewModel) {
    val summaries = viewModel.getSavingSummary()

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        summaries.forEach { summary ->
            val split = summary.category.split(" ", limit = 2)
            val emoji = split.getOrNull(0) ?: "💰"
            val name = split.getOrNull(1) ?: summary.category

            Box(
                modifier = Modifier
                    .shadow(elevation = 5.dp, shape = RoundedCornerShape(10.dp))
                    .clip(RoundedCornerShape(10.dp))
                    .background(Color(0XFFF5FFF6))
                    .width(350.dp)
                    .height(100.dp)
                    .padding(16.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxSize(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(text = name, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                        Text(text = emoji, fontSize = 32.sp)
                    }
                    Column(horizontalAlignment = Alignment.Start) {
                        Text(text = "Total", color = Color.Gray, fontSize = 12.sp)
                        Text(text = "Rp ${summary.total.toInt()}")
                    }
                    Column(horizontalAlignment = Alignment.Start) {
                        Text(text = "Goal", color = Color.Gray, fontSize = 12.sp)
                        Text(text = "Rp ${summary.goal.toInt()}")
                        Text(
                            text = "Sisa: Rp ${summary.remaining.toInt()}",
                            color = if (summary.remaining > 0) Color.Red else Color.Green,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
        }

        if (summaries.isEmpty()) {
            Text("Belum ada data tabungan.", color = Color.Gray)
        }
    }
}

//@Preview(showBackground = true, heightDp = 800, widthDp = 400)
//@Composable
//fun SavingPreview() {
//    Saving(name = "Sample")
//}
//
//@Preview(showBackground = true, heightDp = 100, widthDp = 400)
//@Composable
//fun SavingBoxPreview() {
//    SavingBox()
//}