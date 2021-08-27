package com.kuberam.android.ui.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.kuberam.android.ui.viewmodel.MainViewModel

@ExperimentalMaterialApi
@Composable
fun FeedbackModalSheet(
    feedbackModalBottomSheetState: ModalBottomSheetState,
    viewModel: MainViewModel
) {
    var feedbackText by rememberSaveable { mutableStateOf("") }
    ModalBottomSheetLayout(
        sheetState = feedbackModalBottomSheetState,
        sheetShape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
        sheetContent = {
            Column {
                Text("Write Review")
                Spacer(Modifier.padding(top = 8.dp))
                OutlinedTextField(
                    value = feedbackText,
                    onValueChange = { feedbackText = it },
                    label = { Text("Review") },
                    placeholder = { Text("Wrire Revew...") },
                    modifier = Modifier.fillMaxWidth(),
                )
                Spacer(Modifier.padding(top = 8.dp))
                Button(
                    onClick = {
                        if (feedbackText.isEmpty()) {
                            return@Button
                        } else {
                            viewModel.uploadFeedback(
                                feedbackText,
                                successListener = {
                                },
                                failureListener = {
                                }
                            )
                        }
                    }
                ) {
                    Text("Send")
                }
            }
        }
    ) {}
}
