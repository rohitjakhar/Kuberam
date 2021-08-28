package com.kuberam.android.ui.view

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import coil.transform.CircleCropTransformation
import com.auth0.android.Auth0
import com.google.android.play.core.review.testing.FakeReviewManager
import com.kuberam.android.R
import com.kuberam.android.component.TextBox
import com.kuberam.android.data.model.ProfileDataModel
import com.kuberam.android.navigation.Screen
import com.kuberam.android.ui.viewmodel.MainViewModel
import com.kuberam.android.utils.NetworkResponse
import com.kuberam.android.utils.cardBackground
import com.kuberam.android.utils.openInBrowser
import com.kuberam.android.utils.textBoxBrush
import com.kuberam.android.utils.textHeadingColor
import com.kuberam.android.utils.textNormalColor
import kotlinx.coroutines.launch

@ExperimentalMaterialApi
@Composable
fun ProfileScreen(navController: NavController, viewModel: MainViewModel) {

    viewModel.getUserDetails()
    val context = LocalContext.current
    val manager = FakeReviewManager(context)
    val request = manager.requestReviewFlow()
    val auth0 = Auth0(
        domain = context.resources.getString(R.string.domain),
        clientId = context.resources.getString(R.string.client_id)
    )
    val scope = rememberCoroutineScope()
    val feedbackModalSheet = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden
    )
    val isDarkTheme =
        produceState(initialValue = false, key1 = viewModel.darkTheme.value) {
            value = viewModel.darkTheme.value
        }
    val profileModel = remember { mutableStateOf(ProfileDataModel()) }
    val scaffoldState = rememberScaffoldState()
    LaunchedEffect(viewModel.userProfileData.value) {
        viewModel.getUserDetails()
        when (viewModel.userProfileData.value) {
            is NetworkResponse.Failure -> {
            }
            is NetworkResponse.Loading -> {
            }
            is NetworkResponse.Success -> {
                profileModel.value = viewModel.userProfileData.value.data!!
            }
        }
    }
    Scaffold(scaffoldState = scaffoldState) {
        Column {
            Icon(
                Icons.Default.ArrowBack,
                contentDescription = null,
                modifier = Modifier.padding(16.dp).size(24.dp).clickable {
                    navController.navigateUp()
                }.width(24.dp)
            )
            Card(
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier.fillMaxWidth().padding(16.dp),
                border = BorderStroke(2.dp, brush = textBoxBrush(isDarkTheme.value)),
                backgroundColor = cardBackground(isDarkTheme.value),
                elevation = 26.dp,
            ) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier.padding(vertical = 16.dp)
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Box(contentAlignment = Alignment.Center) {
                            Image(
                                painter = rememberImagePainter(
                                    data =
                                    profileModel.value.profileUrl,
                                    builder = {
                                        transformations(CircleCropTransformation())
                                    },
                                ),
                                contentDescription = null,
                                modifier = Modifier.size(128.dp),
                                alignment = Alignment.Center,

                            )
                        }
                        Spacer(Modifier.padding(top = 8.dp))
                        Box(contentAlignment = Alignment.Center) {
                            Text(
                                text = profileModel.value.name,
                                style = MaterialTheme.typography.h1,
                                color = textHeadingColor(isDarkTheme.value)
                            )
                        }
                        Spacer(Modifier.padding(top = 8.dp))
                        Box(contentAlignment = Alignment.Center) {
                            Text(
                                text = profileModel.value.email,
                                style = MaterialTheme.typography.h2
                            )
                        }
                    }
                }
            }
            Column(modifier = Modifier.fillMaxHeight()) {
                TextBox(
                    text = "About Us",
                    clickListener = {
                        navController.navigate(Screen.AboutScreen.route)
                    },
                    backgroundColor = cardBackground(isDarkTheme.value),
                    textColor = textNormalColor(isDarkTheme.value),
                    isDarkTheme = isDarkTheme.value
                )
                TextBox(
                    text = "Term & Condition",
                    clickListener = {
                        openInBrowser(
                            link = "https://sites.google.com/view/kuberam-privacy-policy",
                            context = context
                        )
                    },
                    backgroundColor = cardBackground(isDarkTheme.value),
                    textColor = textNormalColor(isDarkTheme.value),
                    isDarkTheme = isDarkTheme.value
                )
                TextBox(
                    text = "Write Review",
                    clickListener = {
                        val uri = Uri.parse("market://details?id=" + context.packageName)
                        val intent = Intent(Intent.ACTION_VIEW, uri)
                        request.addOnCompleteListener {
                            if (it.isSuccessful) {
                                val reviewInfo = it.result
                                val flow = manager.launchReviewFlow(context as Activity, reviewInfo)
                                flow.addOnCompleteListener {
                                    if (it.isSuccessful) {
                                    } else {
                                        try {
                                            context.startActivity(intent)
                                        } catch (activityNotFound: ActivityNotFoundException) {
                                        }
                                    }
                                }.addOnFailureListener {
                                    context.startActivity(intent)
                                }
                            } else {
                                try {
                                    context.startActivity(intent)
                                } catch (activityNotFound: ActivityNotFoundException) {
                                }
                            }
                        }.addOnFailureListener {
                            context.startActivity(intent)
                        }
                    },
                    backgroundColor = cardBackground(isDarkTheme.value),
                    textColor = textNormalColor(isDarkTheme.value),
                    isDarkTheme = isDarkTheme.value
                )
                TextBox(
                    text = "Feedback",
                    clickListener = {
                        scope.launch {
                            if (feedbackModalSheet.isVisible) feedbackModalSheet.hide() else feedbackModalSheet.show()
                        }
                    },
                    backgroundColor = cardBackground(isDarkTheme.value),
                    textColor = textNormalColor(isDarkTheme.value),
                    isDarkTheme = isDarkTheme.value
                )
                TextBox(
                    text = "Share App",
                    clickListener = {
                        val shareIntent = Intent(Intent.ACTION_SEND)
                        shareIntent.type = "text/plain"
                        shareIntent.putExtra(
                            Intent.EXTRA_TEXT,
                            "Hi!, I am using Kuberam App for managing my all transaction. Let's try it once.\nhttps://play.google.com/store/apps/details?id=${context.packageName} "
                        )
                        context.startActivity(Intent.createChooser(shareIntent, "Kuberam App"))
                    },
                    backgroundColor = cardBackground(isDarkTheme.value),
                    textColor = textNormalColor(isDarkTheme.value),
                    isDarkTheme = isDarkTheme.value
                )
                TextBox(
                    text = "Logout",
                    backgroundColor = MaterialTheme.colors.error,
                    clickListener = {
                        viewModel.logoutUser(
                            auth0 = auth0,
                            context = context,
                            successListener = {
                                navController.navigate(Screen.Login.route) {
                                    popUpTo(Screen.Profile.route) {
                                        inclusive = true
                                    }
                                }
                            },
                            failureListener = {}
                        )
                    },
                    textColor = textNormalColor(isDarkTheme.value),
                    isDarkTheme = isDarkTheme.value
                )
            }
        }
    }
    FeedbackModalSheet(feedbackModalSheet, viewModel, scaffoldState)
}
