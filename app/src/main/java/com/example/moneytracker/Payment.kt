package com.example.moneytracker

import android.text.Layout
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.AddCircle
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun Payment(name: String, modifier: Modifier = Modifier) {
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
        ) {
            ButtonNav()
        }

        Box(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(end = 30.dp, bottom = 120.dp)
                .size(50.dp)
                .background(Color(0xFF639F86), shape = CircleShape),
        ) {
            IconButton(onClick = { }) {
                Icon(
                    Icons.Rounded.Add,
                    contentDescription = "",
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
            PaymentPage()
        }
    }
}

@Composable
fun PaymentPage() {
    LazyColumn (
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            Text(
                text = "May 21 2025, Wednesday",
                fontSize = 15.sp,
                modifier = Modifier.padding(top = 20.dp, bottom = 5.dp)
            )
        }

        item {
            Box(
                modifier = Modifier
                    .padding(8.dp)
                    .shadow(elevation = 5.dp, shape = RoundedCornerShape(10.dp))
                    .clip(RoundedCornerShape(10.dp))
                    .background(Color(0XFFF5FFF6))
                    .width(330.dp)
                    .height(90.dp),
            ) {
                Row (
                    modifier = Modifier.fillMaxSize(),
                    horizontalArrangement = Arrangement.SpaceAround,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row (
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Start
                    ){
                        Image(
                            painter = painterResource(R.drawable.bank),
                            contentDescription = null,
                            modifier = Modifier
                                .size(70.dp)
                        )
                        Spacer(modifier = Modifier.size(5.dp))
                        Column {
                            Text(
                                text = "Bank",
                                fontSize = 17.sp,
                                fontWeight = FontWeight.Bold,
                            )
                            Text(
                                text = "One Time",
                                fontSize = 12.sp,
                            )
                        }
                    }
                    Text(
                        text = "Rp 10.000.000,00",
                        fontSize = 17.sp,
                        color = Color.Red,
                        fontWeight = FontWeight.Bold
                    )

                }
            }

        }

        item {
            Text(
                text = "June 1 2025, Wednesday",
                fontSize = 15.sp,
                modifier = Modifier.padding(top = 30.dp, bottom = 5.dp)
            )
        }

        item {
            Box(
                modifier = Modifier
                    .padding(8.dp)
                    .shadow(elevation = 5.dp, shape = RoundedCornerShape(10.dp))
                    .clip(RoundedCornerShape(10.dp))
                    .background(Color(0XFFF5FFF6))
                    .width(330.dp)
                    .height(90.dp),
            ) {
                Row (
                    modifier = Modifier.fillMaxSize(),
                    horizontalArrangement = Arrangement.SpaceAround,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row (
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Start
                    ){
                        Image(
                            painter = painterResource(R.drawable.tuition),
                            contentDescription = null,
                            modifier = Modifier
                                .size(70.dp)
                        )
                        Spacer(modifier = Modifier.size(5.dp))
                        Column {
                            Text(
                                text = "Tuition Fee",
                                fontSize = 17.sp,
                                fontWeight = FontWeight.Bold,
                            )
                            Text(
                                text = "Every Month",
                                fontSize = 12.sp,
                            )
                        }
                    }
                    Text(
                        text = "Rp 750.000,00",
                        fontSize = 17.sp,
                        color = Color.Red,
                        fontWeight = FontWeight.Bold
                    )

                }
            }

        }
    }
}

@Preview(showBackground = true, heightDp = 800, widthDp = 400)
@Composable
fun PaymentPreview() {
    Payment(name = "Sample")
}

@Preview(showBackground = true, heightDp = 400, widthDp = 400)
@Composable
fun PaymentPagePreview() {
    PaymentPage()
}