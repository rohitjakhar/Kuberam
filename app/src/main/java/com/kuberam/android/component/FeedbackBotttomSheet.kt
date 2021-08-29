package com.kuberam.android.ui.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.material.ScaffoldState
import androidx.compose.material.SnackbarDuration
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.kuberam.android.R
import com.kuberam.android.component.MyOutLineTextField
import com.kuberam.android.ui.viewmodel.MainViewModel
import com.kuberam.android.utils.cardBackground
import com.kuberam.android.utils.textHeadingColor
import com.kuberam.android.utils.textNormalColor
import kotlinx.coroutines.launch

@ExperimentalMaterialApi
@Composable
fun FeedbackModalSheet(
    feedbackModalBottomSheetState: ModalBottomSheetState,
    viewModel: MainViewModel,
    scaffoldState: ScaffoldState
) {
    var feedbackText by remember { mutableStateOf("") }
    val scope = rememberCoroutineScope()
    val isDarkTheme =
        produceState(initialValue = false, key1 = viewModel.darkTheme.value) {
            value = viewModel.darkTheme.value
        }
    val context = LocalContext.current
    ModalBottomSheetLayout(
        sheetState = feedbackModalBottomSheetState,
        sheetShape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
        sheetContent = {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    stringResource(R.string.write_review_lbl),
                    style = MaterialTheme.typography.h1,
                    color = textHeadingColor(isDarkTheme.value)
                )
                Spacer(Modifier.padding(top = 8.dp))
                MyOutLineTextField(
                    value = feedbackText,
                    onChange = {
                        feedbackText = it
                    },
                    label = stringResource(R.string.review),
                    placeholder = stringResource(R.string.write_review),
                    leadingIconImageVector = Icons.Default.Edit,
                    isDarkTheme = isDarkTheme.value
                )
                Spacer(Modifier.padding(top = 16.dp))
                Button(
                    onClick = {
                        if (feedbackText.isEmpty()) {
                            return@Button
                        } else {
                            viewModel.uploadFeedback(
                                feedbackText,
                                successListener = {
                                    scope.launch {
                                        feedbackModalBottomSheetState.hide()
                                        scaffoldState.snackbarHostState.showSnackbar(
                                            context.resources.getString(R.string.message_added),
                                            duration = SnackbarDuration.Short
                                        )
                                    }
                                },
                                failureListener = {
                                    scope.launch {
                                        feedbackModalBottomSheetState.hide()
                                        scaffoldState.snackbarHostState.showSnackbar(
                                            context.resources.getString(R.string.message_failed),
                                            duration = SnackbarDuration.Short
                                        )
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
                    ),
                ) {
                    Text(
                        "Submit", style = MaterialTheme.typography.h2,
                        color = textNormalColor(isDarkTheme.value)
                    )
                }
            }
        }
    ) {}
}
