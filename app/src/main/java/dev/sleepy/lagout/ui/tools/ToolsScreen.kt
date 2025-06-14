package dev.sleepy.lagout.ui.tools

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import dev.sleepy.lagout.optimizer.Optimizer

@Composable
fun ToolsScreen() {
    val context = LocalContext.current
    val optimizer = Optimizer(context)
    val oneTapBoostResult = remember { mutableStateOf("") }
    val disableAnimationsResult = remember { mutableStateOf("") }
    val enableAnimationsResult = remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Button(onClick = {
            oneTapBoostResult.value = optimizer.oneTapBoost()
        }) {
            Text("One-Tap Boost")
        }
        Text(text = oneTapBoostResult.value)

        Button(onClick = {
            disableAnimationsResult.value = optimizer.disableAnimations()
        }) {
            Text("Disable Animations")
        }
        Text(text = disableAnimationsResult.value)

        Button(onClick = {
            enableAnimationsResult.value = optimizer.enableAnimations()
        }) {
            Text("Enable Animations")
        }
        Text(text = enableAnimationsResult.value)
    }
}

