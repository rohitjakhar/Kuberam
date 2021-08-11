package com.kuberam.android.ui.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.RadioButton
import androidx.compose.material.Scaffold
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@ExperimentalMaterialApi
@Preview(showBackground = true)
@Composable
fun AddTransaction() {
    var amout by rememberSaveable { mutableStateOf("") }
    var radiostate by remember { mutableStateOf(true) }
    val radioOption = listOf("Income", "Expense")
    val (selectionOption, onOptionSelected) = remember { mutableStateOf(radioOption[0]) }

    Scaffold { innerPadding ->
        Column(modifier = Modifier.padding(8.dp).fillMaxWidth()) {
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
            Row(modifier = Modifier.selectableGroup().fillMaxWidth().weight(2F)) {
                radioOption.forEach { text ->
                    Row(
                        Modifier.weight(1F)
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
        }
    }
}
