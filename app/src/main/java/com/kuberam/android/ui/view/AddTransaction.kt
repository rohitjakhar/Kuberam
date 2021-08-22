package com.kuberam.android.ui.view

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.RadioButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.kuberam.android.data.model.TransactionDetailsModel
import com.kuberam.android.ui.viewmodel.MainViewModel
import com.kuberam.android.utils.Constant.EXPENSE_DATA
import com.kuberam.android.utils.Constant.INCOME_DATA
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
            incomeCategoryList.firstOrNull()
        )
    }

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
        Row(modifier = Modifier.selectableGroup().fillMaxWidth()) {
            radioTransactionTypeOption.forEach { text ->
                Row(
                    Modifier
                        .selectable(
                            selected = (text == selectionOption),
                            onClick = { onOptionSelected(text) },
                            role = Role.RadioButton
                        )
                ) {
                    RadioButton(
                        selected = (text == selectionOption),
                        onClick = null // null recommended for accessibility with screenreaders
                    )
                    Text(
                        text = text,
                        style = MaterialTheme.typography.body1.merge(),
                        modifier = Modifier.padding(start = 16.dp)
                    )
                }
            }
        }
        Spacer(Modifier.padding(top = 16.dp))
        Row(modifier = Modifier.selectableGroup().fillMaxWidth()) {
            if (selectionOption == INCOME_DATA) {
                incomeCategoryList.forEach { text ->
                    Row(
                        Modifier
                            .selectable(
                                selected = (text == selectionIncomeCategoryOption),
                                onClick = { onOptionIncomeCategorySelected(text) },
                                role = Role.RadioButton
                            )
                    ) {
                        RadioButton(
                            selected = (text == selectionIncomeCategoryOption),
                            onClick = null // null recommended for accessibility with screenreaders
                        )
                        Text(
                            text = text.categoryName,
                            style = MaterialTheme.typography.body1.merge(),
                            modifier = Modifier.padding(start = 16.dp)
                        )
                    }
                }
            } else {
                expenseCategoryList.forEach { text ->
                    Row(
                        Modifier
                            .selectable(
                                selected = (text == selectionIncomeCategoryOption),
                                onClick = { onOptionIncomeCategorySelected(text) },
                                role = Role.RadioButton
                            )
                    ) {
                        RadioButton(
                            selected = (text == selectionIncomeCategoryOption),
                            onClick = null // null recommended for accessibility with screenreaders
                        )
                        Text(
                            text = text.categoryName,
                            style = MaterialTheme.typography.body1.merge(),
                            modifier = Modifier.padding(start = 16.dp)
                        )
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
                    transactionCategory = selectionIncomeCategoryOption?.categoryName ?: "",
                    transactionTitle = note,
                    transactionDate = Calendar.getInstance().time
                )
                viewModel.addTransaction(transactionDetailsModel)
            },
            modifier = Modifier.fillMaxWidth().height(48.dp)
        ) {
            Text("Add")
        }
    }
}
