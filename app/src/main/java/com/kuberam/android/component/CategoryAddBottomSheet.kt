package com.kuberam.android.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.DrawerDefaults
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.RadioButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Check
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.kuberam.android.data.model.CategoryDataModel
import com.kuberam.android.ui.viewmodel.MainViewModel
import com.kuberam.android.utils.categoryColors

@ExperimentalMaterialApi
@Composable
fun CategoryAddBottomSheet(
    categoryAddSheet: ModalBottomSheetState,
    viewModel: MainViewModel
) {
    var categoryName by rememberSaveable { mutableStateOf("") }
    val radioOption = listOf("Income", "Expense")
    val (selectionOption, onOptionSelected) = remember { mutableStateOf(radioOption[0]) }
    val (selectedColor, onColorSelected) = remember { mutableStateOf(categoryColors[0]) }

    ModalBottomSheetLayout(
        sheetState = categoryAddSheet,
        sheetShape = MaterialTheme.shapes.large,
        sheetContent = {
            OutlinedTextField(
                value = categoryName,
                onValueChange = { categoryName = it },
                label = { Text(" Amount") },
                placeholder = { Text("Enter Category Name") },
                leadingIcon = { Icon(Icons.Default.AccountBox, contentDescription = null) },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
            )
            Spacer(Modifier.padding(top = 16.dp))
            Row(modifier = Modifier.selectableGroup().fillMaxWidth()) {
                radioOption.forEach { text ->
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
            Row(modifier = Modifier.selectableGroup().fillMaxWidth()) {
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
                                Icon(Icons.Default.Check, contentDescription = null)
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
                    val categoryDataModel = CategoryDataModel(
                        categoryName = categoryName,
                        transactionType = selectionOption,
                        colorCode = "0x${selectedColor.value.toString(16).removeSuffix("00000000")}"
                    )
                    viewModel.createCategory(categoryDataModel)
                },
                modifier = Modifier.fillMaxWidth().height(48.dp)
            ) {
                Text("Add")
            }
        },
        scrimColor = DrawerDefaults.scrimColor,
        sheetElevation = 16.dp,
    ) {}
}
