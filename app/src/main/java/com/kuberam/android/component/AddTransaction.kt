package com.kuberam.android.ui.view

import android.annotation.SuppressLint
import android.widget.CalendarView
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
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
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.material.OutlinedButton
import androidx.compose.material.ScaffoldState
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.google.accompanist.flowlayout.FlowRow
import com.google.accompanist.flowlayout.MainAxisAlignment
import com.google.accompanist.flowlayout.SizeMode
import com.kuberam.android.R
import com.kuberam.android.component.MyOutLineTextField
import com.kuberam.android.data.model.CategoryDataModel
import com.kuberam.android.data.model.TransactionDetailsModel
import com.kuberam.android.ui.viewmodel.MainViewModel
import com.kuberam.android.utils.Constant.EXPENSE_DATA
import com.kuberam.android.utils.Constant.INCOME_DATA
import com.kuberam.android.utils.cardBackground
import com.kuberam.android.utils.chipColor
import com.kuberam.android.utils.selectedChipColor
import com.kuberam.android.utils.textHeadingColor
import com.kuberam.android.utils.textNormalColor
import kotlinx.coroutines.launch
import java.util.Calendar

@SuppressLint("UnrememberedMutableState")
@ExperimentalMaterialApi
@Composable
fun AddTransaction(
    viewModel: MainViewModel,
    categorySheetState: ModalBottomSheetState,
    addTransactionSheetState: ModalBottomSheetState,
    scaffoldState: ScaffoldState
) {
    val context = LocalContext.current
    var amout by remember { mutableStateOf("") }
    var note by remember { mutableStateOf("") }
    val radioTransactionTypeOption = listOf(INCOME_DATA, EXPENSE_DATA)
    val (selectionOption, onOptionSelected) = remember { mutableStateOf(radioTransactionTypeOption[0]) }
    val (selectionIncomeCategoryOption, onOptionIncomeCategorySelected) = remember {
        mutableStateOf(
            ""
        )
    }
    val incomeCategoryState =
        produceState(initialValue = emptyList<CategoryDataModel>(), viewModel.incomeData.value) {
            value = viewModel.incomeData.value
        }
    val expenseCategoryState =
        produceState(initialValue = emptyList<CategoryDataModel>(), viewModel.expenseData.value) {
            value = viewModel.expenseData.value
        }
    val isDarkTheme =
        produceState(initialValue = false, key1 = viewModel.darkTheme.value) {
            value = viewModel.darkTheme.value
        }
    val datePickerDialog = remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()
    val date = remember { mutableStateOf(context.resources.getString(R.string.today)) }

    val interactionSource = remember { MutableInteractionSource() }

    Column(modifier = Modifier.padding(8.dp).fillMaxWidth().wrapContentHeight()) {
        Spacer(Modifier.padding(top = 16.dp))
        Text(
            text = "Add Transaction",
            style = MaterialTheme.typography.h1,
            color = textHeadingColor(isDarkTheme.value)
        )
        Spacer(Modifier.padding(top = 16.dp))
        MyOutLineTextField(
            value = amout,
            onChange = {
                amout = it
            },
            label = stringResource(R.string.amount),
            placeholder = stringResource(R.string.enter_amount),
            leadingIconPainterResource = painterResource(R.drawable.ic_baseline_attach_money_24),
            isDarkTheme = isDarkTheme.value,
        )
        Spacer(Modifier.padding(top = 16.dp))
        MyOutLineTextField(
            value = note,
            onChange = {
                note = it
            },
            label = stringResource(R.string.note),
            placeholder = stringResource(R.string.enter_note),
            leadingIconImageVector = Icons.Outlined.Edit,
            isDarkTheme = isDarkTheme.value
        )
        Spacer(Modifier.padding(top = 16.dp))
        OutlinedButton(
            onClick = {
                datePickerDialog.value = true
            },
            modifier = Modifier.fillMaxWidth().height(48.dp),
        ) {
            Text(
                date.value,
                style = MaterialTheme.typography.h2,
                color = textNormalColor(isDarkTheme.value)
            )
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
                                    selectedChipColor(isDarkTheme.value)
                                } else {
                                    chipColor(isDarkTheme.value)
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
        Box(modifier = Modifier.selectableGroup().fillMaxWidth()) {
            if (selectionOption == INCOME_DATA) {
                FlowRow(
                    mainAxisAlignment = MainAxisAlignment.Center,
                    mainAxisSize = SizeMode.Expand,
                    crossAxisSpacing = 12.dp,
                    mainAxisSpacing = 8.dp
                ) {
                    incomeCategoryState.value.forEach { text ->
                        Row(
                            Modifier
                                .selectable(
                                    selected = (text.categoryName == selectionIncomeCategoryOption),
                                    onClick = { onOptionIncomeCategorySelected(text.categoryName) },
                                    role = Role.RadioButton,
                                    interactionSource = remember { interactionSource },
                                    indication = null
                                )
                        ) {
                            Box(
                                contentAlignment = Alignment.Center,
                                modifier = Modifier
                                    .padding(start = 15.dp, top = 15.dp, bottom = 15.dp)
                                    .clip(RoundedCornerShape(10.dp))
                                    .background(
                                        if (text.categoryName == selectionIncomeCategoryOption) {
                                            selectedChipColor(isDarkTheme.value)
                                        } else {
                                            chipColor(isDarkTheme.value)
                                        }
                                    )
                                    .padding(15.dp)
                            ) {
                                Text(text = text.categoryName, color = Color.White)
                            }
                        }
                    }
                }
            } else {
                FlowRow(
                    mainAxisAlignment = MainAxisAlignment.Center,
                    mainAxisSize = SizeMode.Expand,
                    crossAxisSpacing = 12.dp,
                    mainAxisSpacing = 8.dp
                ) {
                    expenseCategoryState.value.forEach { text ->
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
                                            selectedChipColor(isDarkTheme.value)
                                        } else {
                                            chipColor(isDarkTheme.value)
                                        }
                                    )
                                    .padding(15.dp)
                            ) {
                                Text(text = text.categoryName, color = Color.White)
                            }
                        }
                    }
                }
            }
        }
        Text(
            "Add More Category",
            modifier = Modifier.clickable {
                scope.launch {
                    addTransactionSheetState.hide()
                    categorySheetState.show()
                }
            }.align(Alignment.End),
        )
        Spacer(Modifier.padding(top = 16.dp))
        Button(
            onClick = {
                when {
                    selectionIncomeCategoryOption.isEmpty() -> {
                        Toast.makeText(
                            context,
                            context.resources.getString(R.string.please_select_category),
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    amout.isEmpty() -> {
                        Toast.makeText(
                            context,
                            context.resources.getString(R.string.enter_amount),
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                    note.isEmpty() -> {
                        Toast.makeText(
                            context,
                            context.resources.getString(R.string.enter_note),
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    else -> {
                        val transactionDetailsModel = TransactionDetailsModel(
                            transactionId = System.currentTimeMillis().toString(),
                            transactionType = selectionOption,
                            transactionAmount = amout,
                            transactionCategory = selectionIncomeCategoryOption,
                            transactionTitle = note,
                            transactionDate = Calendar.getInstance().time
                        )
                        viewModel.addTransaction(
                            transactionDetailsModel,
                            successListener = {
                                viewModel.getAllTransaction()
                                if (selectionOption == INCOME_DATA) {
                                    viewModel.getIncomeData()
                                } else {
                                    viewModel.getExpenseData()
                                }
                                scope.launch {
                                    addTransactionSheetState.hide()
                                    scaffoldState.snackbarHostState.showSnackbar(
                                        context.resources.getString(R.string.message_added)
                                    )
                                }
                            },
                            failureListener = {
                                scope.launch {
                                    addTransactionSheetState.hide()
                                    scaffoldState.snackbarHostState.showSnackbar(
                                        context.resources.getString(R.string.message_failed)
                                    )
                                }
                            }
                        )
                    }
                }
            },
            modifier = Modifier.fillMaxWidth().height(48.dp).clip(RoundedCornerShape(12.dp)),
            colors = ButtonDefaults.buttonColors(
                backgroundColor = cardBackground(isDarkTheme.value),
                contentColor = textNormalColor(isDarkTheme.value)
            ),
        ) {
            Text(
                stringResource(R.string.add_transaction),
                style = MaterialTheme.typography.h2,
                color = textNormalColor(isDarkTheme.value)
            )
        }
    }
    if (datePickerDialog.value) {
        AlertDialog(
            onDismissRequest = {
                date.value = context.resources.getString(R.string.select_date)
                datePickerDialog.value = false
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        datePickerDialog.value = false
                        date.value = context.resources.getString(R.string.select_date)
                    }
                ) {
                    Text(stringResource(R.string.cancel))
                }
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        datePickerDialog.value = false
                    }
                ) {
                    Text(stringResource(R.string.select))
                }
            },
            title = {
                Text(
                    stringResource(R.string.select_date),
                    color = textNormalColor(isDarkTheme.value)
                )
            },
            text = {
                AndroidView(
                    { CalendarView(it) },
                    modifier = Modifier.wrapContentWidth(),
                    update = { views ->
                        views.setOnDateChangeListener { calendarView, i, i2, i3 ->
                            date.value = "$i/${i2 + 1}/$i3"
                        }
                    },
                )
            }
        )
    }
}
