package com.kuberam.android.ui.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.produceState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.kuberam.android.R
import com.kuberam.android.component.TextBox
import com.kuberam.android.ui.viewmodel.MainViewModel
import com.kuberam.android.utils.Constant.GITHUB_LINK
import com.kuberam.android.utils.Constant.HASHNODE_LINK
import com.kuberam.android.utils.Constant.LINKEDIN_LINK
import com.kuberam.android.utils.Constant.PLAY_STORE_LINK
import com.kuberam.android.utils.cardBackground
import com.kuberam.android.utils.chipColor
import com.kuberam.android.utils.openInBrowser
import com.kuberam.android.utils.textNormalColor

@Composable
fun AboutScreen(viewModel: MainViewModel) {
    val isDarkTheme =
        produceState(initialValue = false, key1 = viewModel.darkTheme.value) {
            value = viewModel.darkTheme.value
        }
    val context = LocalContext.current
    Scaffold {
        Column {
            Box(contentAlignment = Alignment.Center) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.padding(8.dp)
                ) {
                    Image(
                        painter = painterResource(R.drawable.ic_kuberam_transparent),
                        contentDescription = "logo",
                        modifier = Modifier.size(120.dp)
                    )
                    Text(
                        text = stringResource(R.string.app_name),
                        style = MaterialTheme.typography.h1
                    )
                    Text(
                        "About Developer",
                        style = MaterialTheme.typography.h2,
                        color = textNormalColor(isDarkTheme.value)
                    )
                    Spacer(Modifier.padding(top = 8.dp))
                    Text(
                        stringResource(R.string.about_developer),
                        color = textNormalColor(isDarkTheme.value)
                    )
                    Spacer(Modifier.padding(top = 16.dp))
                    Text(
                        "About Kuberam",
                        style = MaterialTheme.typography.h2,
                        color = textNormalColor(isDarkTheme.value)
                    )
                    Spacer(Modifier.padding(top = 8.dp))
                    Text(
                        stringResource(R.string.about_kuberam),
                        color = textNormalColor(isDarkTheme.value)
                    )
                    Spacer(Modifier.padding(top = 16.dp))
                    Text(
                        "About Auth0",
                        style = MaterialTheme.typography.h2,
                        color = textNormalColor(isDarkTheme.value)
                    )
                    Spacer(Modifier.padding(top = 8.dp))
                    Text(
                        stringResource(R.string.about_auth0),
                        color = textNormalColor(isDarkTheme.value)
                    )
                    Spacer(Modifier.padding(top = 16.dp))
                }
            }
            Column(
                modifier = Modifier.clip(RoundedCornerShape(topEnd = 26.dp, topStart = 26.dp))
                    .fillMaxHeight().padding(top = 1.dp).background(chipColor(isDarkTheme.value)),
            ) {
                TextBox(
                    text = "Github",
                    clickListener = {
                        openInBrowser(link = GITHUB_LINK, context = context)
                    },
                    backgroundColor = cardBackground(isDarkTheme.value),
                    textColor = textNormalColor(isDarkTheme.value),
                    isDarkTheme = isDarkTheme.value
                )
                TextBox(
                    text = "LinkedIn",
                    clickListener = {
                        openInBrowser(link = LINKEDIN_LINK, context = context)
                    },
                    backgroundColor = cardBackground(isDarkTheme.value),
                    textColor = textNormalColor(isDarkTheme.value),
                    isDarkTheme = isDarkTheme.value
                )
                TextBox(
                    text = "Hashnode",
                    clickListener = {
                        openInBrowser(link = HASHNODE_LINK, context = context)
                    },
                    backgroundColor = cardBackground(isDarkTheme.value),
                    textColor = textNormalColor(isDarkTheme.value),
                    isDarkTheme = isDarkTheme.value,
                )
                TextBox(
                    text = "Play Store",
                    clickListener = {
                        openInBrowser(link = PLAY_STORE_LINK, context = context)
                    },
                    backgroundColor = cardBackground(isDarkTheme.value),
                    textColor = textNormalColor(isDarkTheme.value),
                    isDarkTheme = isDarkTheme.value
                )
            }
        }
    }
}
