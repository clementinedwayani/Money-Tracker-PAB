package com.example.moneytracker

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun Chart(navController: NavController) {
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
                        .clickable { navController.popBackStack() }
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
            ChartPage()
        }
    }
}

@Composable
fun ChartPage() {
    LazyColumn (
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            Row (
                modifier = Modifier
                    .width(350.dp)
                    .height(40.dp)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxHeight()
                        .width(113.dp)
                        .background(Color(0XFF9CE5B2)),
                    contentAlignment = Alignment.Center

                ) {
                    Text(
                        text = "Weekly",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Medium
                    )
                }

                Spacer(modifier = Modifier.width(5.dp))

                Box(
                    modifier = Modifier
                        .fillMaxHeight()
                        .width(113.dp)
                        .background(Color(0XFF639F86)),
                    contentAlignment = Alignment.Center

                ) {
                    Text(
                        text = "Monthly",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color.White
                    )
                }
                Spacer(modifier = Modifier.width(5.dp))
                Box(
                    modifier = Modifier
                        .fillMaxHeight()
                        .width(113.dp)
                        .background(Color(0XFF9CE5B2)),
                    contentAlignment = Alignment.Center

                ) {
                    Text(
                        text = "Yearly",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Medium
                    )
                }

            }
        }

        item {
            Spacer(modifier = Modifier.height(20.dp))
        }
        item {
            Box(
                modifier = Modifier
                    .padding(8.dp)
                    .shadow(elevation = 5.dp, shape = RoundedCornerShape(10.dp))
                    .clip(RoundedCornerShape(10.dp))
                    .background(Color(0XFFF5FFF6))
                    .width(330.dp)
                    .height(250.dp),
            ) {
                Column {
                    Row (
                        modifier = Modifier.fillMaxWidth()
                            .padding(10.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        IconButton(onClick = { }) {
                            Icon(
                                Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                                contentDescription = "",
                                modifier = Modifier.size(35.dp)
                            )
                        }

                        Text (
                            modifier = Modifier
                                .padding(10.dp),
                            text = "May",
                            fontSize = 23.sp,
                            fontWeight = FontWeight.Medium
                        )

                        IconButton(onClick = { }) {
                            Icon(
                                Icons.AutoMirrored.Filled.KeyboardArrowRight,
                                contentDescription = "",
                                modifier = Modifier.size(35.dp)
                            )
                        }
                    }
                    Image(
                        painter = painterResource(id = R.drawable.chart_image),
                        contentDescription = "",
                        modifier = Modifier
                            .size(170.dp)
                            .align(Alignment.CenterHorizontally)
                    )
                }
            }
        }

        item {
            Spacer(modifier = Modifier.size(30.dp))
        }

        item {
            Column (
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                ChartDetails1()
                ChartDetails2()
                ChartDetails3()
            }
        }
    }
}

@Composable
fun ChartDetails1() {
    Box(
        modifier = Modifier
            .padding(8.dp)
            .shadow(elevation = 5.dp, shape = RoundedCornerShape(10.dp))
            .clip(RoundedCornerShape(10.dp))
            .background(Color(0XFFF5FFF6))
            .width(330.dp)
            .height(60.dp),
    ) {
        Row (
            modifier = Modifier
                .fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ){
            Row (
                modifier = Modifier
                    .fillMaxHeight()
                    .padding(start = 10.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Box(
                    modifier = Modifier
                        .padding(3.dp)
                        .background(Color(0XFF3A86FF))
                        .width(60.dp)
                        .height(35.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "40%",
                        color = Color.White,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
                Text(
                    text = "Food",
                    fontSize = 20.sp,
                    modifier = Modifier.padding(start = 15.dp),
                )
            }


            Text(
                text = "Rp 40.000,00",
                fontSize = 20.sp,
                modifier = Modifier.padding(end = 10.dp)
            )
        }
    }
}

@Composable
fun ChartDetails2() {
    Box(
        modifier = Modifier
            .padding(8.dp)
            .shadow(elevation = 5.dp, shape = RoundedCornerShape(10.dp))
            .clip(RoundedCornerShape(10.dp))
            .background(Color(0XFFF5FFF6))
            .width(330.dp)
            .height(60.dp),
    ) {
        Row (
            modifier = Modifier
                .fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ){
            Row (
                modifier = Modifier
                    .fillMaxHeight()
                    .padding(start = 10.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Box(
                    modifier = Modifier
                        .padding(3.dp)
                        .background(Color(0XFFF94144))
                        .width(60.dp)
                        .height(35.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "25%",
                        color = Color.White,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
                Text(
                    text = "Transport",
                    fontSize = 20.sp,
                    modifier = Modifier.padding(start = 15.dp),
                )
            }


            Text(
                text = "Rp 25.000,00",
                fontSize = 20.sp,
                modifier = Modifier.padding(end = 10.dp)
            )
        }
    }
}

@Composable
fun ChartDetails3() {
    Box(
        modifier = Modifier
            .padding(8.dp)
            .shadow(elevation = 5.dp, shape = RoundedCornerShape(10.dp))
            .clip(RoundedCornerShape(10.dp))
            .background(Color(0XFFF5FFF6))
            .width(330.dp)
            .height(60.dp),
    ) {
        Row (
            modifier = Modifier
                .fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ){
            Row (
                modifier = Modifier
                    .fillMaxHeight()
                    .padding(start = 10.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Box(
                    modifier = Modifier
                        .padding(3.dp)
                        .background(Color(0XFFFFBE0B))
                        .width(60.dp)
                        .height(35.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "35%",
                        color = Color.White,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
                Text(
                    text = "Shop",
                    fontSize = 20.sp,
                    modifier = Modifier.padding(start = 15.dp),
                )
            }


            Text(
                text = "Rp 35.000,00",
                fontSize = 20.sp,
                modifier = Modifier.padding(end = 10.dp)
            )
        }
    }
}
//@Preview(showBackground = true, heightDp = 800, widthDp = 400)
//@Composable
//fun ChartPreview() {
//    Chart(navController)
//}
//
//@Preview(showBackground = true, heightDp = 400, widthDp = 400)
//@Composable
//fun ChartPagePreview() {
//    Column(
//        modifier = Modifier
//            .fillMaxSize()
//            .background(Color(0XFFFBFAF5))
//            .padding(16.dp),
//        horizontalAlignment = Alignment.CenterHorizontally,
//        verticalArrangement = Arrangement.Center
//    ) {
//        ChartPage()
//    }
//}
//
//@Preview(showBackground = true, heightDp = 100, widthDp = 400)
//@Composable
//fun ButtonNavPreview() {
//    ButtonNav()
//}