package com.example.moneytracker

import android.text.Layout
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.AddCircle
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ComposableTarget
import androidx.compose.runtime.remember
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
import androidx.compose.runtime.mutableStateOf

@Composable
fun InputPayment(name: String, modifier: Modifier = Modifier) {
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

        Column(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(top = 130.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            InputPaymentPage()
        }
    }
}

@Composable
fun InputPaymentPage() {
    LazyColumn (
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item { InputTitle() }
        item { InputIcon() }
        item { InputTotalPayment() }
        item { InputDate() }
        item { InputReminder() }
        item {
            Box(
                modifier = Modifier
                    .padding(9.dp)
                    .shadow(elevation = 3.dp, shape = RoundedCornerShape(30.dp))
                    .clip(RoundedCornerShape(30.dp))
                    .background(Color(0XFF79B99C))
                    .width(330.dp)
                    .height(50.dp),
                contentAlignment = Alignment.Center
            ) {
                Text (
                    text = "Add",
                    color = Color.White,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Composable
fun InputTitle() {
    val titleState = remember { mutableStateOf("") }
    Box(
        modifier = Modifier
            .padding(8.dp)
            .shadow(elevation = 5.dp, shape = RoundedCornerShape(10.dp))
            .clip(RoundedCornerShape(10.dp))
            .background(Color(0XFFF5FFF6))
            .width(330.dp)
            .height(120.dp),
    ) {
        Column (
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp),
            verticalArrangement = Arrangement.SpaceAround,
        ) {
            Text(
                text = "Title",
                fontSize = 17.sp,
                fontWeight = FontWeight.Bold,
            )

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .border(1.dp, Color.Black, shape = RoundedCornerShape(10.dp))
            ) {
                TextField(
                    value = titleState.value,
                    onValueChange = { newText -> titleState.value = newText },
                    modifier = Modifier
                        .fillMaxSize()
                        .align(Alignment.Center),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.Transparent,
                        unfocusedContainerColor = Color.Transparent,
                        disabledContainerColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent
                    ),
                    placeholder = { Text(text = "Enter title") }

                )
            }
        }
    }
}

@Composable
fun InputIcon() {
    Box(
        modifier = Modifier
            .padding(8.dp)
            .shadow(elevation = 5.dp, shape = RoundedCornerShape(10.dp))
            .clip(RoundedCornerShape(10.dp))
            .background(Color(0XFFF5FFF6))
            .width(330.dp)
            .height(50.dp),
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 20.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Icon",
                fontSize = 17.sp,
                fontWeight = FontWeight.Bold,
            )
            Box (
                modifier = Modifier
                .width(70.dp)
                .height(50.dp)
                .border(1.dp, Color.Black, shape = RoundedCornerShape(10.dp))

            ) {
                IconButton (
                    onClick = {},
                    modifier = Modifier.align(Alignment.Center)
                ) {
                    Icon(
                        Icons.Default.Face,
                        contentDescription = "",
                        modifier = Modifier
                            .size(30.dp)
                            .align(Alignment.Center)
                    )
                }
            }
        }

    }
}

@Composable
fun InputTotalPayment() {
    val titleState = remember { mutableStateOf("") }
    Box(
        modifier = Modifier
            .padding(8.dp)
            .shadow(elevation = 5.dp, shape = RoundedCornerShape(10.dp))
            .clip(RoundedCornerShape(10.dp))
            .background(Color(0XFFF5FFF6))
            .width(330.dp)
            .height(120.dp),
    ) {
        Column (
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp),
            verticalArrangement = Arrangement.SpaceAround,
        ) {
            Text(
                text = "Payment",
                fontSize = 17.sp,
                fontWeight = FontWeight.Bold,
            )

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .border(1.dp, Color.Black, shape = RoundedCornerShape(10.dp))
            ) {
                TextField(
                    value = titleState.value,
                    onValueChange = { newText -> titleState.value = newText },
                    modifier = Modifier
                        .fillMaxSize()
                        .align(Alignment.Center),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.Transparent,
                        unfocusedContainerColor = Color.Transparent,
                        disabledContainerColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent
                    ),
                    placeholder = { Text(text = "Rp 0") }

                )
            }
        }
    }
}

@Composable
fun InputDate() {
    val titleState = remember { mutableStateOf("") }
    Box(
        modifier = Modifier
            .padding(8.dp)
            .shadow(elevation = 5.dp, shape = RoundedCornerShape(10.dp))
            .clip(RoundedCornerShape(10.dp))
            .background(Color(0XFFF5FFF6))
            .width(330.dp)
            .height(120.dp),
    ) {
        Column (
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp),
            verticalArrangement = Arrangement.SpaceAround,
        ) {
            Text(
                text = "Date",
                fontSize = 17.sp,
                fontWeight = FontWeight.Bold,
            )

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .border(1.dp, Color.Black, shape = RoundedCornerShape(10.dp))
            ) {
                TextField(
                    value = titleState.value,
                    onValueChange = { newText -> titleState.value = newText },
                    modifier = Modifier
                        .fillMaxSize()
                        .align(Alignment.Center),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.Transparent,
                        unfocusedContainerColor = Color.Transparent,
                        disabledContainerColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent
                    ),
                    placeholder = { Text(text = "MM/DD/YY") }
                )
            }
        }
    }
}

@Composable
fun InputReminder() {
    val titleState = remember { mutableStateOf("") }
    Box(
        modifier = Modifier
            .padding(8.dp)
            .shadow(elevation = 5.dp, shape = RoundedCornerShape(10.dp))
            .clip(RoundedCornerShape(10.dp))
            .background(Color(0XFFF5FFF6))
            .width(330.dp)
            .height(120.dp),
    ) {
        Column (
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp),
            verticalArrangement = Arrangement.SpaceAround,
        ) {
            Text(
                text = "Reminder",
                fontSize = 17.sp,
                fontWeight = FontWeight.Bold,
            )

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .border(1.dp, Color.Black, shape = RoundedCornerShape(10.dp)),
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(start = 20.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Every Day",
                        fontSize = 17.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color.Gray
                    )
                    Box(
                        modifier = Modifier
                            .width(70.dp)
                            .height(50.dp)
                            .border(1.dp, Color.Black, shape = RoundedCornerShape(10.dp))

                    ) {
                        IconButton(
                            onClick = {},
                            modifier = Modifier.align(Alignment.Center)
                        ) {
                            Icon(
                                Icons.Default.ArrowDropDown,
                                contentDescription = "",
                                modifier = Modifier
                                    .size(40.dp)
                                    .align(Alignment.Center)
                            )
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true, heightDp = 800, widthDp = 400)
@Composable
fun InputPaymentPreview() {
    InputPayment(name = "Sample")
}

@Preview(showBackground = true, heightDp = 400, widthDp = 400)
@Composable
fun InputPaymentPagePreview() {
    InputPaymentPage()
}