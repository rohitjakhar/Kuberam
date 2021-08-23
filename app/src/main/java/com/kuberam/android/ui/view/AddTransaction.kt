package com.kuberam.android.ui.view

import android.annotation.SuppressLint
import android.widget.CalendarView
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.kuberam.android.data.model.TransactionDetailsModel
import com.kuberam.android.ui.viewmodel.MainViewModel
import com.kuberam.android.utils.Constant.EXPENSE_DATA
import com.kuberam.android.utils.Constant.INCOME_DATA
import com.kuberam.android.utils.NetworkResponse
import java.util.Calendar

@SuppressLint("UnrememberedMutableState")
@ExperimentalMaterialApi
@Composable
fun AddTransaction(viewModel: MainViewModel) {
    var amout by rememberSaveable { mutableStateOf("") }
    var note by rememberSaveable { mutableStateOf("") }
    val incomeCategoryList by viewModel.incomeData
    val expenseCategoryList by viewModel.expenseData
    val radioTransactionTypeOption = listOf(INCOME_DATA, EXPENSE_DATA)
    val (selectionOption, onOptionSelected) = remember { mutableStateOf(radioTransactionTypeOption[0]) }
    val (selectionIncomeCategoryOption, onOptionIncomeCategorySelected) = remember {
        mutableStateOf(
            incomeCategoryList.data?.get(0)?.categoryName
        )
    }
    val datePickerDialog = remember { mutableStateOf(false) }
    val date = remember { mutableStateOf("Select Date") }
    Column(modifier = Modifier.padding(8.dp).fillMaxWidth().wrapContentHeight()) {
        OutlinedTextField(
            value = amout,
            onValueChange = { amout = it },
            label = { Text(" Amount") },
            placeholder = { Text("Enter Amount") },
            leadingIcon = { Icon(Icons.Default.AccountBox, contentDescription = null) },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )
        Spacer(Modifier.padding(top = 16.dp))
        OutlinedTextField(
            value = note,
            onValueChange = { note = it },
            label = { Text(" Amount") },
            placeholder = { Text("Enter Amount") },
            leadingIcon = { Icon(Icons.Default.AccountBox, contentDescription = null) },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
        )
        Spacer(Modifier.padding(top = 16.dp))
        OutlinedButton(
            onClick = {
                datePickerDialog.value = true
            },
            modifier = Modifier.fillMaxWidth().height(48.dp)
        ) {
            Text(date.value)
        }
        Spacer(Modifier.padding(top = 16.dp))
        Row(
            modifier = Modifier.selectableGroup().fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround,
        ) {
            radioTransactionTypeOption.forEach { text ->
                Row(
                    Modifier
                        .selectable(
                            selected = (text == selectionOption),
                            onClick = { onOptionSelected(text) },
                            role = Role.RadioButton
                        )
                        .weight(1F)
                        .padding(8.dp)
                ) {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .clip(RoundedCornerShape(10.dp))
                            .background(
                                if (text == selectionOption) {
                                    Color.Green
                                } else {
                                    Color.DarkGray
                                }
                            )
                            .padding(10.dp)
                            .fillMaxWidth()
                    ) {
                        Text(text = text, color = Color.White)
                    }
                }
            }
        }
        Spacer(Modifier.padding(top = 16.dp))

        Row(modifier = Modifier.selectableGroup().fillMaxWidth()) {
            if (selectionOption == INCOME_DATA) {
                when (incomeCategoryList) {
                    is NetworkResponse.Success -> {
                        incomeCategoryList.data?.forEach { text ->
                            Row(
                                Modifier
                                    .selectable(
                                        selected = (text.categoryName == selectionIncomeCategoryOption),
                                        onClick = { onOptionIncomeCategorySelected(text.categoryName) },
                                        role = Role.RadioButton
                                    )
                            ) {
                                Box(
                                    contentAlignment = Alignment.Center,
                                    modifier = Modifier
                                        .padding(start = 15.dp, top = 15.dp, bottom = 15.dp)
                                        .clip(RoundedCornerShape(10.dp))
                                        .background(
                                            if (text.categoryName == selectionIncomeCategoryOption) {
                                                Color.Green
                                            } else {
                                                Color.DarkGray
                                            }
                                        )
                                        .padding(15.dp)
                                ) {
                                    Text(text = text.categoryName, color = Color.White)
                                }
                            }
                        }
                    }
                    is NetworkResponse.Loading -> {
                        // TODO: 8/22/21 Loding State 
                    }
                    is NetworkResponse.Failure -> {
                        // TODO: 8/22/21 show error message
                    }
                }
            } else {
                when (expenseCategoryList) {
                    is NetworkResponse.Success -> {
                        expenseCategoryList.data?.forEach { text ->
                            Row(
                                Modifier
                                    .selectable(
                                        selected = (text.categoryName == selectionIncomeCategoryOption),
                                        onClick = { onOptionIncomeCategorySelected(text.categoryName) },
                                        role = Role.RadioButton
                                    )
                            ) {
                                Box(
                                    contentAlignment = Alignment.Center,
                                    modifier = Modifier
                                        .padding(start = 15.dp, top = 15.dp, bottom = 15.dp)
                                        .clip(RoundedCornerShape(10.dp))
                                        .background(
                                            if (text.categoryName == selectionIncomeCategoryOption) {
                                                Color.Green
                                            } else {
                                                Color.DarkGray
                                            }
                                        )
                                        .padding(15.dp)
                                ) {
                                    Text(text = text.categoryName, color = Color.White)
                                }
                            }
                        }
                    }
                    is NetworkResponse.Loading -> {
                    }
                    is NetworkResponse.Failure -> {
                    }
                }
            }
        }
        Spacer(Modifier.padding(top = 16.dp))
        Button(
            onClick = {
                val transactionDetailsModel = TransactionDetailsModel(
                    transactionType = selectionOption,
                    transactionAmount = amout,
                    transactionCategory = selectionIncomeCategoryOption ?: "",
                    transactionTitle = note,
                    transactionDate = Calendar.getInstance().time
                )
                // viewModel.addTransaction(transactionDetailsModel)
            },
            modifier = Modifier.fillMaxWidth().height(48.dp)
        ) {
            Text("Add")
        }
    }
    if (datePickerDialog.value) {
        AlertDialog(
            onDismissRequest = {
                date.value = "Select Date"
                datePickerDialog.value = false
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        datePickerDialog.value = false
                        date.value = "Select Date"
                    }
                ) {
                    Text("Cancel")
                }
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        datePickerDialog.value = false
                    }
                ) {
                    Text("Select")
                }
            },
            title = {
                Text("Select Date")
            },
            text = {
                AndroidView(
                    { CalendarView(it) },
                    modifier = Modifier.wrapContentWidth(),
                    update = { views ->
                        views.setOnDateChangeListener { calendarView, i, i2, i3 ->
                            date.value = "$i/${i2 + 1}/$i3"
                        }
                    }
                )
            }
        )
    }
}
