package com.kuberam.android.ui.view

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import coil.transform.CircleCropTransformation
import com.kuberam.android.component.LoadingComponent
import com.kuberam.android.ui.viewmodel.MainViewModel
import com.kuberam.android.utils.NetworkResponse

@Composable
fun ProfileScreen(navController: NavController, viewModel: MainViewModel) {

    viewModel.getUserDetails()
    val context = LocalContext.current
    Scaffold {
        val profileModel by viewModel.userProfile
        Column {
            Icon(
                Icons.Default.ArrowBack,
                contentDescription = null,
                modifier = Modifier.padding(16.dp).size(24.dp).clickable {
                    navController.navigateUp()
                }
            )
            Card(
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier.fillMaxWidth().padding(16.dp),
                border = BorderStroke(2.dp, Color.Black),
                backgroundColor = Color(0xff23C4ED),
                elevation = 16.dp
            ) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier.padding(vertical = 16.dp)
                ) {
                    when (profileModel) {
                        is NetworkResponse.Success -> {
                            Column {
                                Box(contentAlignment = Alignment.Center) {
                                    Image(
                                        painter = rememberImagePainter(
                                            data =
                                            profileModel.data?.profileUrl,
                                            builder = {
                                                transformations(CircleCropTransformation())
                                            },
                                        ),
                                        contentDescription = null,
                                        modifier = Modifier.size(128.dp),
                                        alignment = Alignment.Center,

                                    )
                                }
                                Box(contentAlignment = Alignment.Center) {
                                    profileModel.let { it1 ->
                                        it1.data?.name?.let { it2 ->
                                            Text(
                                                it2
                                            )
                                        }
                                    } ?: Text("Name is null")
                                }
                                Box(contentAlignment = Alignment.Center) {
                                    profileModel.let { it1 ->
                                        it1.data?.email?.let { it2 ->
                                            Text(
                                                it2
                                            )
                                        }
                                    } ?: Text("Email is null")
                                }
                            }
                        }
                        is NetworkResponse.Loading -> {
                            LoadingComponent()
                        }
                    }
                }
            }
            TextBox(text = "About Us")
            TextBox(Color.DarkGray, text = "Setting")
            OutlinedButton(
                onClick = {
                    viewModel.logoutUser(context)
                },
                modifier = Modifier.padding(16.dp).align(Alignment.CenterHorizontally)
            ) {
                Text("Logout")
            }
        }
    }
}

@Composable
fun TextBox(
    color: Color = Color.Red,
    text: String
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .padding(15.dp)
            .clip(RoundedCornerShape(10.dp))
            .background(color)
            .padding(horizontal = 10.dp, vertical = 20.dp)
            .fillMaxWidth()
    ) {
        Column {
            Text(
                text = text,
                style = MaterialTheme.typography.h5
            )
        }
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
                .background(Color.Blue)
                .padding(10.dp)
        ) {
            Icon(
                Icons.Default.ArrowForward,
                contentDescription = "Play",
                tint = Color.White,
                modifier = Modifier.size(16.dp)
            )
        }
    }
}
