package com.example.moneytracker

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavController
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

@Composable
fun InputPayment(navController: NavController) {
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
                    color = Color.White,
                    fontSize = 25.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.align(Alignment.Center)
                )
            }
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) {
                InputPaymentPage(navController)
            }
        }
        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
        ) {
            ButtonNav(navController = navController)
        }
    }
}

@Composable
fun InputPaymentPage(navController: NavController) {
    val titleState = remember { mutableStateOf("") }
    val selectedEmoji = remember { mutableStateOf("💳") }
    val paymentState = remember { mutableStateOf("") }
    val dateState = remember { mutableStateOf("") }
    val context = LocalContext.current
    val selectedDate = remember { mutableStateOf(Calendar.getInstance()) }
    var showDatePicker by remember { mutableStateOf(false) }
    val reminderState = remember { mutableStateOf("Every Day") }
    var expanded by remember { mutableStateOf(false) }
    var isSelected by remember { mutableStateOf(false) }

    val reminderOptions = listOf("Every Day", "Every Week", "Every Month", "Every Year", "One Time")

    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item { Spacer(modifier = Modifier.height(16.dp)) }
        item { InputTitle(titleState) }
        item { InputIcon(selectedEmoji) }
        item { InputTotalPayment(paymentState) }
        item { InputDate(dateState, context, selectedDate, showDatePicker, { showDatePicker = it }) }
        item { InputReminder(reminderState, expanded, isSelected, { expanded = it }, { isSelected = it }) }
        item {
            Box(
                modifier = Modifier
                    .padding(vertical = 16.dp)
                    .shadow(elevation = 3.dp, shape = RoundedCornerShape(30.dp))
                    .clip(RoundedCornerShape(30.dp))
                    .background(Color(0xFF79B99C))
                    .width(330.dp)
                    .height(50.dp)
                    .clickable {
                        val newPayment = PaymentData(
                            title = titleState.value,
                            icon = selectedEmoji.value,
                            totalPayment = paymentState.value,
                            date = dateState.value,
                            reminder = reminderState.value
                        )
                        DataManager.addPayment(newPayment)
                        navController.popBackStack()
                    },
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Add",
                    color = Color.White,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
        item { Spacer(modifier = Modifier.height(80.dp)) }
    }
}

@Composable
fun InputTitle(titleState: MutableState<String>) {
    Box(
        modifier = Modifier
            .shadow(elevation = 5.dp, shape = RoundedCornerShape(10.dp))
            .clip(RoundedCornerShape(10.dp))
            .background(Color(0xFFF5FFF6))
            .width(330.dp)
            .height(120.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp),
            verticalArrangement = Arrangement.SpaceAround
        ) {
            Text(
                text = "Title",
                fontSize = 17.sp,
                fontWeight = FontWeight.Bold
            )
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .border(1.dp, Color.Black, shape = RoundedCornerShape(10.dp))
            ) {
                TextField(
                    value = titleState.value,
                    onValueChange = { titleState.value = it },
                    modifier = Modifier.fillMaxSize(),
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
fun InputIcon(selectedEmoji: MutableState<String>) {
    val emojiList = listOf(
        "💳", "🏫", "⚡", "💧", "🏡", "🚗", "🏍️", "💊"
    ).filter { it.isNotEmpty() }

    var showDialog by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .shadow(elevation = 5.dp, shape = RoundedCornerShape(10.dp))
            .clip(RoundedCornerShape(10.dp))
            .background(Color(0xFFF5FFF6))
            .width(330.dp)
            .height(50.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 20.dp, end = 20.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Icon",
                fontSize = 17.sp,
                fontWeight = FontWeight.Bold
            )
            Box(
                modifier = Modifier
                    .width(50.dp)
                    .height(40.dp)
                    .border(1.dp, Color.Black, shape = RoundedCornerShape(10.dp))
            ) {
                IconButton(
                    onClick = { showDialog = true },
                    modifier = Modifier.fillMaxSize()
                ) {
                    Text(
                        text = selectedEmoji.value,
                        fontSize = 24.sp
                    )
                }
            }
        }
    }

    if (showDialog) {
        Dialog(
            onDismissRequest = { showDialog = false }
        ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White)
            ) {
                Column(
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Select an Emoji",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(bottom = 16.dp)
                    )
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(4),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp),
                        contentPadding = PaddingValues(8.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(emojiList) { emoji ->
                            Box(
                                modifier = Modifier
                                    .size(50.dp)
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(if (emoji == selectedEmoji.value) Color(0xFFE0E0E0) else Color.Transparent)
                                    .clickable {
                                        selectedEmoji.value = emoji
                                        showDialog = false
                                    },
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = emoji,
                                    fontSize = 24.sp
                                )
                            }
                        }
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    TextButton(
                        onClick = { showDialog = false },
                        modifier = Modifier.align(Alignment.End)
                    ) {
                        Text("Cancel")
                    }
                }
            }
        }
    }
}

@Composable
fun InputTotalPayment(paymentState: MutableState<String>) {
    val currencyVisualTransformation = object : VisualTransformation {
        override fun filter(text: AnnotatedString): TransformedText {
            val originalText = text.text
            val transformedText = if (originalText.isNotEmpty()) "Rp $originalText" else ""
            return TransformedText(AnnotatedString(transformedText), offsetMapping = object : OffsetMapping {
                override fun originalToTransformed(offset: Int): Int {
                    return if (originalText.isNotEmpty()) offset + 3 else offset
                }

                override fun transformedToOriginal(offset: Int): Int {
                    return if (originalText.isNotEmpty()) maxOf(0, offset - 3) else offset
                }
            })
        }
    }
    Box(
        modifier = Modifier
            .shadow(elevation = 5.dp, shape = RoundedCornerShape(10.dp))
            .clip(RoundedCornerShape(10.dp))
            .background(Color(0xFFF5FFF6))
            .width(330.dp)
            .height(120.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp),
            verticalArrangement = Arrangement.SpaceAround
        ) {
            Text(
                text = "Payment",
                fontSize = 17.sp,
                fontWeight = FontWeight.Bold
            )
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .height(50.dp)
                    .border(1.dp, Color.Black, shape = RoundedCornerShape(10.dp))
            ) {
                TextField(
                    value = paymentState.value,
                    onValueChange = { newValue ->
                        if (newValue.all { it.isDigit() } || newValue.isEmpty()) {
                            paymentState.value = newValue
                        }
                    },
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 8.dp),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.Transparent,
                        unfocusedContainerColor = Color.Transparent,
                        disabledContainerColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent
                    ),
                    placeholder = { Text(text = "Rp 0") },
                    visualTransformation = currencyVisualTransformation,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )
            }
        }
    }
}

@Composable
fun InputDate(dateState: MutableState<String>, context: Context, selectedDate: MutableState<Calendar>, showDatePicker: Boolean, setShowDatePicker: (Boolean) -> Unit) {
    var localShowDatePicker by remember { mutableStateOf(showDatePicker) }

    Box(
        modifier = Modifier
            .shadow(elevation = 5.dp, shape = RoundedCornerShape(10.dp))
            .clip(RoundedCornerShape(10.dp))
            .background(Color(0xFFF5FFF6))
            .width(330.dp)
            .height(120.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp),
            verticalArrangement = Arrangement.SpaceAround
        ) {
            Text(
                text = "Date",
                fontSize = 17.sp,
                fontWeight = FontWeight.Bold
            )
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .border(1.dp, Color.Black, shape = RoundedCornerShape(10.dp))
                    .clickable { localShowDatePicker = true }
            ) {
                Text(
                    text = dateState.value.ifEmpty { "MM/DD/YYYY" },
                    fontSize = 17.sp,
                    fontWeight = FontWeight.Medium,
                    color = if (dateState.value.isEmpty()) Color.Gray else Color.Black,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(start = 12.dp)
                        .wrapContentHeight(Alignment.CenterVertically)
                )
            }
        }
    }

    if (localShowDatePicker) {
        showDatePicker(context, selectedDate.value) { newDate ->
            selectedDate.value = newDate
            val formatter = SimpleDateFormat("MM/dd/yyyy", Locale.US)
            dateState.value = formatter.format(newDate.time)
            localShowDatePicker = false
            setShowDatePicker(false)
        }
    }
}

fun showDatePicker(context: Context, initialDate: Calendar, onDateSelected: (Calendar) -> Unit) {
    val dialog = android.app.DatePickerDialog(
        context,
        { _, year, month, day ->
            val newCal = Calendar.getInstance()
            newCal.set(year, month, day)
            onDateSelected(newCal)
        },
        initialDate.get(Calendar.YEAR),
        initialDate.get(Calendar.MONTH),
        initialDate.get(Calendar.DAY_OF_MONTH)
    )
    dialog.setOnDismissListener { }
    dialog.show()
}

@Composable
fun InputReminder(reminderState: MutableState<String>, expanded: Boolean, isSelected: Boolean, setExpanded: (Boolean) -> Unit, setIsSelected: (Boolean) -> Unit) {
    var localExpanded by remember { mutableStateOf(expanded) }
    var localIsSelected by remember { mutableStateOf(isSelected) }

    val reminderOptions = listOf("Every Day", "Every Week", "Every Month", "Every Year", "One Time")

    Box(
        modifier = Modifier
            .shadow(elevation = 5.dp, shape = RoundedCornerShape(10.dp))
            .clip(RoundedCornerShape(10.dp))
            .background(Color(0xFFF5FFF6))
            .width(330.dp)
            .height(120.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp),
            verticalArrangement = Arrangement.SpaceAround
        ) {
            Text(
                text = "Reminder",
                fontSize = 17.sp,
                fontWeight = FontWeight.Bold
            )
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .border(1.dp, Color.Black, shape = RoundedCornerShape(10.dp))
                    .clickable { localExpanded = true }
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(start = 12.dp, end = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = reminderState.value,
                        fontSize = 17.sp,
                        fontWeight = FontWeight.Medium,
                        color = if (!localIsSelected) Color.Gray else Color.Black
                    )
                    IconButton(
                        onClick = { localExpanded = true },
                        modifier = Modifier.size(40.dp)
                    ) {
                        Icon(
                            Icons.Default.ArrowDropDown,
                            contentDescription = "Select Reminder",
                            modifier = Modifier.size(24.dp)
                        )
                    }
                }
            }
        }
    }

    DropdownMenu(
        expanded = localExpanded,
        onDismissRequest = { localExpanded = false },
        modifier = Modifier
            .background(Color(0xFFF5FFF6))
            .border(1.dp, Color.Gray, RoundedCornerShape(4.dp))
            .width(330.dp),
        offset = DpOffset(x = 0.dp, y = 4.dp)
    ) {
        reminderOptions.forEach { option ->
            DropdownMenuItem(
                text = { Text(text = option, fontSize = 16.sp, fontWeight = FontWeight.Medium, color = Color.Black) },
                onClick = {
                    reminderState.value = option
                    localExpanded = false
                    localIsSelected = true
                    setExpanded(false)
                    setIsSelected(true)
                },
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}