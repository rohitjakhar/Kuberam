package com.kuberam.android.ui.view

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.produceState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
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
import com.kuberam.android.utils.openInBrowser
import com.kuberam.android.utils.textNormalColor

@Composable
fun AboutScreen(viewModel: MainViewModel) {
    val context = LocalContext.current
    val isDarkTheme =
        produceState(initialValue = false, key1 = viewModel.darkTheme.value) {
            value = viewModel.darkTheme.value
        }
    val versioncode = context.packageManager.getPackageInfo(context.packageName, 0).versionName
    LazyColumn(Modifier.fillMaxHeight()) {
        item {
            Box(contentAlignment = Alignment.Center, modifier = Modifier.padding(8.dp)) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.padding(8.dp)
                ) {
                    Spacer(Modifier.padding(top = 16.dp))
                    Text(
                        text = stringResource(R.string.app_name),
                        style = MaterialTheme.typography.h1,
                        color = textNormalColor(isDarkTheme.value)
                    )
                    Text(text = "Version: $versioncode")
                    Spacer(Modifier.padding(top = 16.dp))
                    Text(
                        stringResource(R.string.about_developer_lbl),
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
                        stringResource(R.string.about_kuberam_lbl),
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
                        stringResource(R.string.about_auth0_lbl),
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
        }
        item {
            Text(
                text = stringResource(R.string.links),
                style = MaterialTheme.typography.h1,
                color = textNormalColor(isDarkTheme.value),
                modifier = Modifier.padding(start = 16.dp, top = 16.dp)
            )
            TextBox(
                text = stringResource(R.string.github),
                clickListener = {
                    openInBrowser(link = GITHUB_LINK, context = context)
                },
                backgroundColor = cardBackground(isDarkTheme.value),
                textColor = textNormalColor(isDarkTheme.value),
                isDarkTheme = isDarkTheme.value
            )
            TextBox(
                text = stringResource(R.string.linkedin),
                clickListener = {
                    openInBrowser(link = LINKEDIN_LINK, context = context)
                },
                backgroundColor = cardBackground(isDarkTheme.value),
                textColor = textNormalColor(isDarkTheme.value),
                isDarkTheme = isDarkTheme.value
            )
            TextBox(
                text = stringResource(R.string.hashnode),
                clickListener = {
                    openInBrowser(link = HASHNODE_LINK, context = context)
                },
                backgroundColor = cardBackground(isDarkTheme.value),
                textColor = textNormalColor(isDarkTheme.value),
                isDarkTheme = isDarkTheme.value,
            )
            TextBox(
                text = stringResource(R.string.playstore),
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
