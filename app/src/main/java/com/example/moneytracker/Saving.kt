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
import androidx.navigation.NavController

@Composable
fun Saving(navController: NavController) {
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
            SavingBox()
        }
    }
}

@Composable
fun SavingBox() {
    Box(
        modifier = Modifier
            .shadow(elevation = 5.dp, shape = RoundedCornerShape(10.dp))
            .clip(RoundedCornerShape(10.dp))
            .background(Color(0XFFF5FFF6))
            .width(350.dp)
            .height(100.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = "House",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold
                )
                Image(
                    painter = painterResource(id = R.drawable.house),
                    contentDescription = "Food icon",
                    modifier = Modifier.size(50.dp)
                )
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 15.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Column(
                    horizontalAlignment = Alignment.Start,
                    modifier = Modifier.weight(1f)
                ) {
                    Text(text = "Total", color = Color.Gray, fontSize = 12.sp)
                    Text(text = "Rp 300.000")
                }
                Column(
                    horizontalAlignment = Alignment.Start,
                    modifier = Modifier.weight(1f)
                ) {
                    Text(text = "Goal", color = Color.Gray, fontSize = 12.sp)
                    Text(text = "Rp 10.000.000")
                    Text(text = "-Rp 9.700.000", color = Color. Red, fontWeight = FontWeight.Bold)
                }
            }
        }
    }
    Spacer(modifier = Modifier.height(20.dp))
    Box(
        modifier = Modifier
            .shadow(elevation = 5.dp, shape = RoundedCornerShape(10.dp))
            .clip(RoundedCornerShape(10.dp))
            .background(Color(0XFFF5FFF6))
            .width(350.dp)
            .height(100.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = "House",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold
                )
                Image(
                    painter = painterResource(id = R.drawable.house),
                    contentDescription = "Food icon",
                    modifier = Modifier.size(50.dp)
                )
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 15.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Column(
                    horizontalAlignment = Alignment.Start,
                    modifier = Modifier.weight(1f)
                ) {
                    Text(text = "Total", color = Color.Gray, fontSize = 12.sp)
                    Text(text = "Rp 300.000")
                }
                Column(
                    horizontalAlignment = Alignment.Start,
                    modifier = Modifier.weight(1f)
                ) {
                    Text(text = "Goal", color = Color.Gray, fontSize = 12.sp)
                    Text(text = "Rp 10.000.000")
                    Text(text = "-Rp 9.700.000", color = Color. Red, fontWeight = FontWeight.Bold)
                }
            }
        }
    }
    Spacer(modifier = Modifier.height(20.dp))
    Box(
        modifier = Modifier
            .shadow(elevation = 5.dp, shape = RoundedCornerShape(10.dp))
            .clip(RoundedCornerShape(10.dp))
            .background(Color(0XFFF5FFF6))
            .width(350.dp)
            .height(100.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = "House",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold
                )
                Image(
                    painter = painterResource(id = R.drawable.house),
                    contentDescription = "Food icon",
                    modifier = Modifier.size(50.dp)
                )
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 15.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Column(
                    horizontalAlignment = Alignment.Start,
                    modifier = Modifier.weight(1f)
                ) {
                    Text(text = "Total", color = Color.Gray, fontSize = 12.sp)
                    Text(text = "Rp 300.000")
                }
                Column(
                    horizontalAlignment = Alignment.Start,
                    modifier = Modifier.weight(1f)
                ) {
                    Text(text = "Goal", color = Color.Gray, fontSize = 12.sp)
                    Text(text = "Rp 10.000.000")
                    Text(text = "-Rp 9.700.000", color = Color. Red, fontWeight = FontWeight.Bold)
                }
            }
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