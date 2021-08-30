package com.kuberam.android.utils

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.kuberam.android.navigation.Screen
import hu.ma.charts.legend.data.LegendEntry

@Composable
internal fun buildValuePercentString(item: LegendEntry) = buildAnnotatedString {
    item.value?.let { value ->
        withStyle(
            style = MaterialTheme.typography.body2.toSpanStyle()
                .copy(color = MaterialTheme.colors.primary)
        ) {
            append(value.toInt().toString())
        }
        append(" ")
    }
}

internal var currentList = listOf(
    "INR",
    "USD",
    "GBP",
    "EUR",
    "JPY",
)
internal val categoryColors = listOf(
    Color(0xFFEF5350),
    Color(0xffec407a),
    Color(0xFFAB47BC),
    Color(0xFFBF3312),
    Color(0xFFE83A59),
    Color(0xFF51E1ED),
    Color(0xFF383CC1),
    Color(0xFF03203C),
    Color(0xFF4DD637),
    Color(0xFF50DBB4),
    Color(0xFFEDC126),
    Color(0xFFC7C11A),
    Color(0xfFFF6666),
    Color(0xFF6A1B4D),
    Color(0xFF03C6C7),
)

@Composable
internal fun RowScope.CustomVerticalLegend(entries: List<LegendEntry>) {
    LazyColumn(
        modifier = Modifier.weight(1f),
    ) {
        itemsIndexed(
            items = entries,
            key = { index, item ->
                item.hashCode()
            }
        ) { index, item ->
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(vertical = 14.dp)
            ) {
                Box(
                    Modifier
                        .requiredSize(item.shape.size)
                        .background(item.shape.color, item.shape.shape)
                )
                Spacer(modifier = Modifier.requiredSize(8.dp))
                Text(
                    text = item.text,
                    style = MaterialTheme.typography.caption
                )
                Spacer(modifier = Modifier.weight(1f))
                Text(
                    text = buildValuePercentString(item),
                    style = MaterialTheme.typography.caption,
                )
            }
            if (index != entries.lastIndex)
                Divider()
        }
    }
}

fun openInBrowser(link: String, context: Context) {
    context.startActivity(
        Intent(Intent.ACTION_VIEW).also {
            it.data = Uri.parse(link)
        }
    )
}

fun bioMetricsPrompts(fr: AppCompatActivity, navController: NavController) {
    val promptInfo = BiometricPrompt.PromptInfo.Builder()
        .setTitle("Unlock")
        .setSubtitle("Use Finger")
        .setAllowedAuthenticators(BiometricManager.Authenticators.BIOMETRIC_STRONG or BiometricManager.Authenticators.DEVICE_CREDENTIAL)
        .build()

    val biometricPrompt = BiometricPrompt(
        fr,
        object : BiometricPrompt.AuthenticationCallback() {
            override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                super.onAuthenticationError(errorCode, errString)
                Toast.makeText(fr, "$errString", Toast.LENGTH_SHORT).show()
            }

            override fun onAuthenticationFailed() {
                super.onAuthenticationFailed()
                fr.finish()
            }

            override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                super.onAuthenticationSucceeded(result)
                navController.navigate(Screen.DashboardScreen.route) {
                    popUpTo(Screen.DashboardScreen.route) {
                        inclusive = true
                    }
                }
            }
        }
    )
    biometricPrompt.authenticate(promptInfo)
}
