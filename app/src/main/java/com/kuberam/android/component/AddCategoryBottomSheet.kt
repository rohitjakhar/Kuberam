package com.kuberam.android.component

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.DrawerDefaults
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.material.ScaffoldState
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Check
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import com.kuberam.android.data.model.CategoryDataModel
import com.kuberam.android.ui.viewmodel.MainViewModel
import com.kuberam.android.utils.Constant.EXPENSE_DATA
import com.kuberam.android.utils.Constant.INCOME_DATA
import com.kuberam.android.utils.cardBackground
import com.kuberam.android.utils.categoryColors
import com.kuberam.android.utils.chipColor
import com.kuberam.android.utils.selectedChipColor
import com.kuberam.android.utils.textHeadingColor
import com.kuberam.android.utils.textNormalColor
import kotlinx.coroutines.launch

@ExperimentalComposeUiApi
@ExperimentalMaterialApi
@Composable
fun AddCategoryBottomSheet(
    categoryAddSheet: ModalBottomSheetState,
    viewModel: MainViewModel,
    scaffoldState: ScaffoldState,
) {
    var categoryName by rememberSaveable { mutableStateOf("") }
    val radioOption = listOf(INCOME_DATA, EXPENSE_DATA)
    val (selectionOption, onOptionSelected) = remember { mutableStateOf(radioOption[0]) }
    val (selectedColor, onColorSelected) = remember { mutableStateOf(categoryColors[0]) }
    val scrollableState = rememberScrollState()
    val scope = rememberCoroutineScope()
    val focusManager = LocalFocusManager.current
    val context = LocalContext.current
    val isDarkTheme =
        produceState(initialValue = false, key1 = viewModel.darkTheme.value) {
            value = viewModel.darkTheme.value
        }
    ModalBottomSheetLayout(
        sheetState = categoryAddSheet,
        sheetShape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
        sheetContent = {
            Column(
                modifier = Modifier.padding(8.dp).fillMaxWidth().wrapContentHeight()
            ) {
                Spacer(Modifier.padding(top = 16.dp))
                Text(
                    text = "Add Category",
                    style = MaterialTheme.typography.h1,
                    color = textHeadingColor(isDarkTheme.value)
                )
                Spacer(Modifier.padding(top = 6.dp))
                MyOutLineTextField(
                    categoryName, isDarkTheme = isDarkTheme.value,
                    onChange = {
                        categoryName = it
                    },
                    focusManager = focusManager,
                    label = "Category",
                    placeholder = "Add Category",
                    leadingIconImageVector = Icons.Default.AddCircle
                )
                Spacer(Modifier.padding(top = 16.dp))
                Row(
                    modifier = Modifier.selectableGroup().fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    radioOption.forEach { text ->
                        Row(
                            Modifier
                                .selectable(
                                    selected = (text == selectionOption),
                                    onClick = { onOptionSelected(text) },
                                    role = Role.RadioButton
                                ).weight(1F)
                                .padding(8.dp)
                        ) {
                            Box(
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
                                    .fillMaxWidth(),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = text,
                                    style = MaterialTheme.typography.h2,
                                    color = textNormalColor(isDarkTheme.value),
                                )
                            }
                        }
                    }
                }
                Row(
                    modifier = Modifier.selectableGroup().fillMaxWidth()
                        .horizontalScroll(scrollableState, enabled = true)
                ) {
                    categoryColors.forEach { color ->
                        Row(
                            Modifier
                                .selectable(
                                    selected = (color == selectedColor),
                                    onClick = { onColorSelected(color) },
                                    role = Role.RadioButton
                                ).padding(2.dp)
                        ) {
                            Box(
                                contentAlignment = Alignment.Center,
                                modifier = Modifier.padding(15.dp)
                                    .clip(RoundedCornerShape(16.dp))
                            ) {
                                if (color == selectedColor) {
                                    Text(".", modifier = Modifier.size(32.dp).background(color))
                                    Icon(
                                        Icons.Default.Check,
                                        contentDescription = null,
                                        tint = Color.White
                                    )
                                } else {
                                    Text(".", modifier = Modifier.size(32.dp).background(color))
                                }
                            }
                        }
                    }
                }
                Spacer(Modifier.padding(top = 16.dp))
                Button(
                    onClick = {
                        if (categoryName.isEmpty()) {
                            Toast.makeText(context, "Enter Category Name", Toast.LENGTH_SHORT)
                                .show()
                            return@Button
                        } else {
                            val categoryDataModel = CategoryDataModel(
                                categoryName = categoryName,
                                transactionType = selectionOption,
                                colorCode = "#${
                                selectedColor.value.toString(16).removeSuffix("00000000")
                                }"
                            )
                            viewModel.createCategory(
                                categoryDataModel,
                                successListener = {
                                    categoryName = ""
                                    if (selectionOption == INCOME_DATA) {
                                        viewModel.getIncomeData()
                                    } else {
                                        viewModel.getExpenseData()
                                    }
                                    scope.launch {
                                        categoryAddSheet.hide()
                                        scaffoldState.snackbarHostState.showSnackbar("Added")
                                    }
                                },
                                failureListener = {
                                    scope.launch {
                                        categoryAddSheet.hide()
                                        scaffoldState.snackbarHostState.showSnackbar("Failed")
                                    }
                                }
                            )
                        }
                    },
                    modifier = Modifier.fillMaxWidth().height(48.dp)
                        .clip(RoundedCornerShape(12.dp)),
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = cardBackground(isDarkTheme.value),
                        contentColor = textNormalColor(isDarkTheme.value)
                    )
                ) {
                    Text(
                        "Add Category",
                        style = MaterialTheme.typography.h2,
                        color = textNormalColor(isDarkTheme.value)
                    )
                }
            }
        },
        scrimColor = DrawerDefaults.scrimColor,
        sheetElevation = 16.dp,
        modifier = Modifier.padding(top = 16.dp)
    ) {}
}
