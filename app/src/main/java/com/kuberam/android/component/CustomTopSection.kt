package com.kuberam.android.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.shape.ZeroCornerSize
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import coil.transform.CircleCropTransformation
import com.kuberam.android.data.model.ProfileDataModel
import com.kuberam.android.ui.theme.Typography
import com.kuberam.android.ui.viewmodel.MainViewModel
import com.kuberam.android.utils.ButtonBackground
import com.kuberam.android.utils.NetworkResponse

@Composable
fun CustomTopSection(
    viewModel: MainViewModel
) {
    viewModel.getUserDetails()
    val userProfile = remember { mutableStateOf(ProfileDataModel()) }
    val isDarkTheme =
        produceState(initialValue = false, key1 = viewModel.darkTheme.value) {
            value = viewModel.darkTheme.value
        }
    LaunchedEffect(viewModel.userProfileData.value) {
        viewModel.getUserDetails()
        when (viewModel.userProfileData.value) {
            is NetworkResponse.Failure -> {
            }
            is NetworkResponse.Loading -> {
            }
            is NetworkResponse.Success -> {
                userProfile.value = viewModel.userProfileData.value.data!!
            }
        }
    }
    Surface(
        color = ButtonBackground(isDarkTheme.value),
        modifier = Modifier.wrapContentHeight().fillMaxWidth()
            .padding(bottom = 16.dp),
        shape = RoundedCornerShape(32.dp).copy(
            topStart = ZeroCornerSize,
            topEnd = ZeroCornerSize
        )
    ) {
        Column(
            Modifier.padding(
                bottom = 48.dp,
                top = 16.dp,
                start = 16.dp,
                end = 16.dp
            ).fillMaxWidth()
        ) {
            Row {
                Image(
                    painter = rememberImagePainter(
                        data =
                        userProfile.value.profileUrl,
                        builder = {
                            transformations(CircleCropTransformation())
                        },
                    ),
                    contentDescription = null,
                    modifier = Modifier.padding(end = 16.dp)
                )
                Text(
                    text = "Hi \n${userProfile.value.name}",
                    style = Typography.h1,
                )
            }
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth().padding(top = 8.dp)
            ) {
                Card(
                    modifier = Modifier.padding(8.dp),
                    elevation = 8.dp,
                    backgroundColor = MaterialTheme.colors.secondary
                ) {
                    Text(
                        "Income: ${userProfile.value.totalIncome}",
                        style = Typography.h2,
                        modifier = Modifier.padding(8.dp)
                    )
                }
                Card(
                    elevation = 8.dp,
                    modifier = Modifier.padding(8.dp),
                    backgroundColor = MaterialTheme.colors.secondary
                ) {
                    Text(
                        "Expense: ${userProfile.value.totalExpense}",
                        style = Typography.h2,
                        modifier = Modifier.padding(8.dp)
                    )
                }
            }
        }
    }
}
