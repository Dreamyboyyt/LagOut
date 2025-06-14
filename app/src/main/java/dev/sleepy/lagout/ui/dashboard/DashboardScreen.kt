package dev.sleepy.lagout.ui.dashboard

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import dev.sleepy.lagout.monitor.PerformanceMonitor

@Composable
fun DashboardScreen() {
    val context = LocalContext.current
    val performanceMonitor = PerformanceMonitor(context)
    performanceMonitor.startMonitoring()

    val fps by performanceMonitor.fps.collectAsState()
    val cpuUsage by performanceMonitor.cpuUsage.collectAsState()
    val ramUsage by performanceMonitor.ramUsage.collectAsState()
    val batteryTemp by performanceMonitor.batteryTemp.collectAsState()

    val context = LocalContext.current
    val overlayPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (Settings.canDrawOverlays(context)) {
            // Permission granted, start the service
            context.startService(Intent(context, FloatingBubbleService::class.java))
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(text = "FPS: \$fps")
        Text(text = "CPU Usage: \$cpuUsage%")
        Text(text = "RAM Usage: \${ramUsage / (1024 * 1024)} MB")
        Text(text = "Battery Temp: \$batteryTemp°C")

        Button(onClick = {
            if (Settings.canDrawOverlays(context)) {
                context.startService(Intent(context, FloatingBubbleService::class.java))
            } else {
                val intent = Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:" + context.packageName))
                overlayPermissionLauncher.launch(intent)
            }
        }) {
            Text("Show Floating FPS")
        }
    }
}



import android.provider.Settings
import android.net.Uri
import android.content.Intent
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.material3.Button
import androidx.compose.runtime.remember

