package com.example.moneytracker

import android.app.DatePickerDialog
import android.util.Log
import android.widget.DatePicker
import android.widget.Toast
import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import net.objecthunter.exp4j.ExpressionBuilder
import java.text.SimpleDateFormat
import java.util.*

data class Transaction(
    val id: String = UUID.randomUUID().toString(),
    val type: String, // "Expense", "Income", "Saving"
    val category: String,
    val amount: Double,
    val date: Calendar,
    val goalAmount: Double? = null // Untuk menyimpan goals pada halaman Saving
)

@Composable
fun Activity(navController: NavController, viewModel: MainViewModel = viewModel()) {
    var selectedTab by remember { mutableStateOf(0) }
    var calculatorInput by remember { mutableStateOf("") }
    var categoryEmoji by remember { mutableStateOf("") }
    var categoryText by remember { mutableStateOf("") }
    var categoryName by remember { mutableStateOf("") }
    var showEmojiPicker by remember { mutableStateOf(false) }
    var selectedDate by remember { mutableStateOf(Calendar.getInstance()) }
    var showDeleteConfirmation by remember { mutableStateOf(false) }
    var categoryToDelete by remember { mutableStateOf<CategoryData?>(null) }

    // Goal dialog states
    var showGoalDialog by remember { mutableStateOf(false) }
    var goalAmount by remember { mutableStateOf("") }

    // Separate state for each tab's selected category
    var selectedExpenseCategory by remember { mutableStateOf("") }
    var selectedIncomeCategory by remember { mutableStateOf("") }
    var selectedSavingCategory by remember { mutableStateOf("") }

    // State to store transactions
    var transactions by remember { mutableStateOf<List<Transaction>>(emptyList()) }

    val context = LocalContext.current

    // Enhanced emoji list with valid emojis only
    val emojiList = listOf(
        "🍕", "🚗", "🛍️", "🎉", "💄", "📚", "🏥", "🧃",
        "🏠", "💰", "🎯", "✈️", "🍎", "⚡", "🎮", "🎵",
        "🏋️", "🍔", "☕", "🚌", "🛒", "🎪", "💊", "📱",
        "","","","","","","","","","","","","","","","",""
    ).filter { it.isNotEmpty() }

    // Use mutableStateListOf so Compose tracks internal mutations properly
    val expenseCategories = remember {
        mutableStateListOf(
            CategoryData("🍕 Food", R.drawable.ic_launcher_foreground),
            CategoryData("🚗 Transport", R.drawable.ic_launcher_foreground),
            CategoryData("🛍️ Shopping", R.drawable.ic_launcher_foreground),
            CategoryData("💄 Beauty", R.drawable.ic_launcher_foreground),
            CategoryData("🏥 Health", R.drawable.ic_launcher_foreground)
        )
    }

    val incomeCategories = remember {
        mutableStateListOf(
            CategoryData("💼 Salary", R.drawable.ic_launcher_foreground),
            CategoryData("🎁 Bonus", R.drawable.ic_launcher_foreground),
            CategoryData("📈 Investment", R.drawable.ic_launcher_foreground),
            CategoryData("⏰ Part Time", R.drawable.ic_launcher_foreground)
        )
    }

    val savingCategories = remember {
        mutableStateListOf(
            CategoryData("🏠 House", R.drawable.ic_launcher_foreground),
            CategoryData("🚗 Car", R.drawable.ic_launcher_foreground),
            CategoryData("✈️ Travel", R.drawable.ic_launcher_foreground)
        )
    }

    // Current categories based on selected tab
    val currentCategories = when (selectedTab) {
        0 -> expenseCategories
        1 -> incomeCategories
        2 -> savingCategories
        else -> expenseCategories
    }

    // Get current selected category based on tab
    val getCurrentSelectedCategory = {
        val category = when (selectedTab) {
            0 -> selectedExpenseCategory
            1 -> selectedIncomeCategory
            2 -> selectedSavingCategory
            else -> ""
        }
        category
    }

    // Update selected category for current tab
    val updateSelectedCategory = { category: String ->
        when (selectedTab) {
            0 -> selectedExpenseCategory = category
            1 -> selectedIncomeCategory = category
            2 -> selectedSavingCategory = category
        }
    }

    // Reset form when tab changes
    LaunchedEffect(selectedTab) {
        // Clear all selected categories to prevent stale state
        selectedExpenseCategory = ""
        selectedIncomeCategory = ""
        selectedSavingCategory = ""
        categoryEmoji = ""
        categoryText = ""
        categoryName = ""
        showEmojiPicker = false
        calculatorInput = ""
    }

    // Keep original theme color
    fun getTabColor(): Color {
        return Color(0xFF639F86)
    }

    fun getTabName(): String {
        return when (selectedTab) {
            0 -> "Expense"
            1 -> "Income"
            2 -> "Saving"
            else -> "Activity"
        }
    }

    // Date picker dialog setup
    fun showDatePicker() {
        val dialog = DatePickerDialog(
            context,
            { _: DatePicker, year: Int, month: Int, day: Int ->
                val newCal = Calendar.getInstance()
                newCal.set(year, month, day)
                selectedDate = newCal
            },
            selectedDate.get(Calendar.YEAR),
            selectedDate.get(Calendar.MONTH),
            selectedDate.get(Calendar.DAY_OF_MONTH)
        )
        dialog.show()
    }

    //fungsi saveTransaction untuk menambahkan navigasi
    fun saveTransaction() {
        if (calculatorInput.isEmpty()) {
            Toast.makeText(context, "Please enter an amount", Toast.LENGTH_LONG).show()
            return
        }
        if (getCurrentSelectedCategory().isEmpty()) {
            Toast.makeText(context, "Please select a category", Toast.LENGTH_LONG).show()
            return
        }

        try {
            val amount = calculatorInput.toDoubleOrNull()
            if (amount == null || amount <= 0) {
                Toast.makeText(context, "Please enter a valid amount", Toast.LENGTH_LONG).show()
                return
            }

            if (selectedTab == 2) {
                showGoalDialog = true
                return
            }

            val tabType = when (selectedTab) {
                0 -> "Expense"
                1 -> "Income"
                2 -> "Saving"
                else -> "Expense"
            }

            val newTransaction = Transaction(
                type = tabType,
                category = getCurrentSelectedCategory(),
                amount = amount,
                date = selectedDate.clone() as Calendar
            )

            // Tambahkan ke ViewModel
            viewModel.addTransaction(newTransaction)
            Log.d("Activity", "Transaction added: ${newTransaction.category}, ${newTransaction.amount}")
            Log.d("Activity", "Total transactions: ${viewModel.transactions.size}")
            Log.d("ActivityVM", "Hash: ${viewModel.hashCode()}, Size: ${viewModel.transactions.size}")

            // Reset form
            calculatorInput = ""
            updateSelectedCategory("")
            categoryEmoji = ""
            categoryText = ""

            Toast.makeText(context, "Transaction saved!", Toast.LENGTH_SHORT).show()

            // Navigasi ke home setelah menyimpan
            navController.navigate("home") {
                popUpTo("home") { inclusive = true }
            }

        } catch (e: Exception) {
            Toast.makeText(context, "Error saving transaction: ${e.message}", Toast.LENGTH_LONG).show()
        }
    }

    fun saveTransactionWithGoal() {
        try {
            val amount = calculatorInput.toDoubleOrNull()
            val goal = goalAmount.toDoubleOrNull()

            if (amount == null || amount <= 0) {
                Toast.makeText(context, "Please enter a valid amount", Toast.LENGTH_LONG).show()
                return
            }

            val newTransaction = Transaction(
                type = "Saving",
                category = getCurrentSelectedCategory(),
                amount = amount,
                date = selectedDate.clone() as Calendar,
                goalAmount = goal
            )

            // Tambahkan ke ViewModel
            viewModel.addTransaction(newTransaction)

            // Reset form
            calculatorInput = ""
            updateSelectedCategory("")
            categoryEmoji = ""
            categoryText = ""
            goalAmount = ""
            showGoalDialog = false

            Toast.makeText(context, "Saving goal created!", Toast.LENGTH_SHORT).show()

            // Navigasi ke saving setelah menyimpan
            navController.navigate("saving") {
                popUpTo("saving") { inclusive = true }
            }

        } catch (e: Exception) {
            Toast.makeText(context, "Error saving goal: ${e.message}", Toast.LENGTH_LONG).show()
        }
    }

    // Function to add new category
    fun addNewCategory() {
        if (categoryName.isNotEmpty() && categoryEmoji.isNotEmpty()) {
            val newCategory = CategoryData("$categoryEmoji $categoryName", R.drawable.ic_launcher_foreground)
            when (selectedTab) {
                0 -> expenseCategories.add(newCategory)
                1 -> incomeCategories.add(newCategory)
                2 -> savingCategories.add(newCategory)
            }
            categoryName = ""
            categoryEmoji = ""
            showEmojiPicker = false
        }
    }

    // Function to delete category
    fun deleteCategory(category: CategoryData) {
        when (selectedTab) {
            0 -> expenseCategories.remove(category)
            1 -> incomeCategories.remove(category)
            2 -> savingCategories.remove(category)
        }
        if (getCurrentSelectedCategory() == category.name) {
            updateSelectedCategory("")
            categoryEmoji = ""
            categoryText = ""
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0XFFFBFAF5)),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Header
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
                    text = "Add Activity",
                    color = Color.White,
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.align(Alignment.Center)
                )
            }

            // Tabs
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                val tabs = listOf("Expense", "Income", "Saving")
                tabs.forEachIndexed { index, tab ->
                    Button(
                        onClick = { selectedTab = index },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (selectedTab == index) Color(0xFF639F86) else Color(0xFFC6E6C3)
                        ),
                        modifier = Modifier
                            .weight(1f)
                            .height(40.dp),
                        shape = RoundedCornerShape(0.dp)
                    ) {
                        Text(
                            text = tab,
                            color = if (selectedTab == index) Color.White else Color(0xFF3A3A3A),
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }

            // Show selected date
            val dateFormat = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())
            Text(
                "Selected Date: ${dateFormat.format(selectedDate.time)}",
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            // Category Icons Grid with better spacing
            LazyVerticalGrid(
                columns = GridCells.Fixed(4),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .height(200.dp)
            ) {
                itemsIndexed(currentCategories, key = { index, category -> "$selectedTab-$index-${category.name}" }) { index, category ->
                    CategoryIconBox(
                        category = category,
                        isSelected = getCurrentSelectedCategory() == category.name,
                        tabColor = getTabColor(),
                        selectedTab = selectedTab,
                        onClick = {
                            updateSelectedCategory(category.name)
                            Toast.makeText(context, "Selected: ${category.name}", Toast.LENGTH_SHORT).show()
                        },
                        onLongClick = {
                            categoryToDelete = category
                            showDeleteConfirmation = true
                        }
                    )
                }
            }

            // Category input section - improved
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 32.dp, vertical = 16.dp)
            ) {
                // Emoji Picker (positioned above input when visible)
                if (showEmojiPicker) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color.White, RoundedCornerShape(8.dp))
                            .border(1.dp, Color.Gray, RoundedCornerShape(8.dp))
                            .padding(8.dp)
                            .heightIn(max = 100.dp)
                    ) {
                        LazyVerticalGrid(
                            columns = GridCells.Fixed(6),
                            horizontalArrangement = Arrangement.spacedBy(4.dp),
                            verticalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            items(emojiList.size) { index ->
                                Text(
                                    text = emojiList[index],
                                    fontSize = 24.sp,
                                    modifier = Modifier
                                        .padding(4.dp)
                                        .clickable {
                                            categoryEmoji = emojiList[index]
                                            showEmojiPicker = false
                                        },
                                    textAlign = TextAlign.Center
                                )
                            }
                        }
                    }
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(8.dp))
                        .border(1.dp, Color.LightGray, RoundedCornerShape(8.dp))
                        .background(Color.White)
                        .padding(horizontal = 12.dp, vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(40.dp)
                            .clip(RoundedCornerShape(6.dp))
                            .background(Color(0xFFF5F5F5))
                            .clickable {
                                if (getCurrentSelectedCategory().isEmpty()) {
                                    showEmojiPicker = !showEmojiPicker
                                }
                            },
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = if (categoryEmoji.isNotEmpty()) categoryEmoji else "😊",
                            fontSize = 20.sp
                        )
                    }

                    Spacer(modifier = Modifier.width(12.dp))

                    BasicTextField(
                        value = categoryName,
                        onValueChange = { categoryName = it },
                        modifier = Modifier.weight(1f),
                        textStyle = MaterialTheme.typography.bodyMedium.copy(
                            color = Color.Black,
                            fontSize = 16.sp
                        ),
                        decorationBox = { innerTextField ->
                            Box(modifier = Modifier.fillMaxWidth()) {
                                if (categoryName.isEmpty()) {
                                    Text(
                                        text = "Category Name",
                                        color = Color.Gray,
                                        fontSize = 16.sp
                                    )
                                }
                                innerTextField()
                            }
                        }
                    )

                    if (categoryName.isNotEmpty() && categoryEmoji.isNotEmpty()) {
                        Spacer(modifier = Modifier.width(8.dp))
                        Button(
                            onClick = { addNewCategory() },
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF639F86)),
                            modifier = Modifier.height(32.dp),
                            contentPadding = PaddingValues(horizontal = 12.dp, vertical = 4.dp)
                        ) {
                            Text(
                                text = "Add",
                                color = Color.White,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }
        }

        // Goal Dialog for Saving
        if (showGoalDialog) {
            AlertDialog(
                onDismissRequest = {
                    showGoalDialog = false
                    goalAmount = ""
                },
                title = {
                    Text("Set Saving Goal", fontWeight = FontWeight.Bold)
                },
                text = {
                    Column {
                        Text("Current amount: $calculatorInput")
                        Text("Category: ${getCurrentSelectedCategory()}")
                        Spacer(modifier = Modifier.height(8.dp))

                        OutlinedTextField(
                            value = goalAmount,
                            onValueChange = { goalAmount = it },
                            label = { Text("Goal Amount (Optional)") },
                            placeholder = { Text("Enter target amount") },
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                },
                confirmButton = {
                    TextButton(
                        onClick = { saveTransactionWithGoal() }
                    ) {
                        Text("Save Goal", fontWeight = FontWeight.Bold)
                    }
                },
                dismissButton = {
                    TextButton(onClick = {
                        showGoalDialog = false
                        goalAmount = ""
                    }) {
                        Text("Cancel")
                    }
                }
            )
        }

        // Delete confirmation dialog
        if (showDeleteConfirmation && categoryToDelete != null) {
            AlertDialog(
                onDismissRequest = { showDeleteConfirmation = false },
                title = { Text("Delete Category", fontWeight = FontWeight.Bold) },
                text = { Text("Are you sure you want to delete '${categoryToDelete!!.name}'?") },
                confirmButton = {
                    TextButton(
                        onClick = {
                            categoryToDelete?.let { deleteCategory(it) }
                            showDeleteConfirmation = false
                            categoryToDelete = null
                        }
                    ) {
                        Text("Delete", color = Color.Red, fontWeight = FontWeight.Bold)
                    }
                },
                dismissButton = {
                    TextButton(onClick = {
                        showDeleteConfirmation = false
                        categoryToDelete = null
                    }) {
                        Text("Cancel")
                    }
                }
            )
        }

        // Enhanced Calculator Section (Bottom)
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp))
                .background(
                    brush = androidx.compose.ui.graphics.Brush.verticalGradient(
                        colors = listOf(
                            Color(0xFF8FC9A9),
                            Color(0xFF7BB896)
                        )
                    )
                )
                .padding(20.dp)
                .align(Alignment.BottomCenter)
        ) {
            Column {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            Color.White,
                            RoundedCornerShape(12.dp)
                        )
                        .border(2.dp, Color(0xFFE0E0E0), RoundedCornerShape(12.dp))
                        .padding(16.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = getMoneyIcon(selectedTab),
                            fontSize = 20.sp,
                            modifier = Modifier.padding(end = 8.dp)
                        )

                        BasicTextField(
                            value = calculatorInput,
                            onValueChange = { },
                            readOnly = true,
                            modifier = Modifier.weight(1f),
                            textStyle = MaterialTheme.typography.bodyLarge.copy(
                                color = Color.Black,
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold
                            ),
                            decorationBox = { innerTextField ->
                                Box {
                                    if (calculatorInput.isEmpty()) {
                                        Text(
                                            text = "0",
                                            color = Color.Gray,
                                            fontSize = 18.sp,
                                            fontWeight = FontWeight.Bold
                                        )
                                    }
                                    innerTextField()
                                }
                            }
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                val buttonRows = listOf(
                    listOf(getTabIcon(selectedTab), "📅", "=", "✓"),
                    listOf("7", "8", "9", "/"),
                    listOf("4", "5", "6", "-"),
                    listOf("1", "2", "3", "+"),
                    listOf("0", ".", "*", "⌫")
                )

                buttonRows.forEach { row ->
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        row.forEach { buttonText ->
                            CalculatorButtonItem(
                                text = buttonText,
                                selectedTab = selectedTab,
                                modifier = Modifier.weight(1f),
                                onClick = {
                                    handleCalculatorInput(
                                        buttonText,
                                        calculatorInput,
                                        selectedTab,
                                        { calculatorInput = it },
                                        { showDatePicker() },
                                        getCurrentSelectedCategory(),
                                        selectedDate,
                                        { saveTransaction() }
                                    )
                                }
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        }
    }
}

// Function to get different money icon per tab (decorative only)
fun getMoneyIcon(selectedTab: Int): String {
    return when (selectedTab) {
        0 -> "💸" // Expense - money with wings
        1 -> "💰" // Income - money bag
        2 -> "🏦" // Saving - bank
        else -> "💰"
    }
}

// Function to get tab icon different per tab
fun getTabIcon(selectedTab: Int): String {
    return when (selectedTab) {
        0 -> "💸" // Expense
        1 -> "💰" // Income
        2 -> "🏦" // Saving
        else -> "💰"
    }
}

// Enhanced calculator input handler with save function
fun handleCalculatorInput(
    buttonText: String,
    currentInput: String,
    selectedTab: Int,
    updateInput: (String) -> Unit,
    showDatePicker: () -> Unit,
    selectedCategory: String,
    selectedDate: Calendar,
    saveTransaction: () -> Unit
) {
    when (buttonText) {
        "=" -> {
            try {
                val expression = ExpressionBuilder(currentInput).build()
                val result = expression.evaluate()
                updateInput(if (result % 1.0 == 0.0) result.toInt().toString() else result.toString())
            } catch (e: Exception) {
                updateInput("Error")
            }
        }
        "⌫" -> {
            if (currentInput.isNotEmpty()) {
                updateInput(currentInput.dropLast(1))
            }
        }
        "✓" -> {
            saveTransaction()
        }
        "📅" -> {
            showDatePicker()
        }
        "💸", "💰", "🏦" -> {
            // Tab icons are decorative only, do nothing
        }
        else -> {
            if (currentInput != "Error") {
                updateInput(currentInput + buttonText)
            } else {
                updateInput(buttonText)
            }
        }
    }
}

data class CategoryData(val name: String, val iconRes: Int)

@Composable
fun CategoryIconBox(
    category: CategoryData,
    isSelected: Boolean,
    tabColor: Color,
    selectedTab: Int,
    onClick: () -> Unit,
    onLongClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .size(60.dp)
            .shadow(elevation = 4.dp, shape = RoundedCornerShape(8.dp))
            .clip(RoundedCornerShape(8.dp))
            .background(if (isSelected) Color(0xFFE8F5E8) else Color.White)
            .border(
                width = if (isSelected) 2.dp else 0.dp,
                color = if (isSelected) Color(0xFF639F86) else Color.Transparent,
                shape = RoundedCornerShape(8.dp)
            )
            .pointerInput(Unit) {
                detectTapGestures(
                    onLongPress = {
                        onLongClick()
                    },
                    onTap = {
                        onClick()
                    }
                )
            },
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(8.dp)
        ) {
            val split = category.name.split(" ", limit = 2)
            Text(text = split.getOrNull(0) ?: "", fontSize = 20.sp)
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = split.getOrNull(1) ?: "",
                fontSize = 10.sp,
                color = if (isSelected) Color(0xFF639F86) else Color.Black,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
fun CalculatorButtonItem(
    text: String,
    selectedTab: Int,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    val (backgroundColor, textColor) = when {
        text in listOf("✓", "📅", "⌫", "=") -> Pair(Color(0xFF639F86), Color.White)
        text in listOf("💸", "💰", "🏦") -> Pair(Color(0xFFE8F5E8), Color.Black)
        text in listOf("+", "-", "*", "/") -> Pair(Color(0xFFE8F5E8), Color.Black)
        else -> Pair(Color.White, Color.Black)
    }

    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(containerColor = backgroundColor),
        shape = RoundedCornerShape(8.dp),
        modifier = modifier.height(36.dp),
        contentPadding = PaddingValues(4.dp)
    ) {
        Text(
            text = text,
            color = textColor,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold
        )
    }
}