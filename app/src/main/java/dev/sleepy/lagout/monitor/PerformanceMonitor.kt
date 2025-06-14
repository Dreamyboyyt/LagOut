package dev.sleepy.lagout.monitor

import android.app.ActivityManager
import android.content.Context
import android.os.BatteryManager
import android.os.Build
import android.os.Handler
import android.os.Looper
import android.view.Choreographer
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.io.RandomAccessFile
import kotlin.math.roundToInt

class PerformanceMonitor(private val context: Context) {

    private val handler = Handler(Looper.getMainLooper())
    private val updateIntervalMs = 1000L // Update every 1 second

    private val _fps = MutableStateFlow(0)
    val fps: StateFlow<Int> = _fps

    private val _cpuUsage = MutableStateFlow(0)
    val cpuUsage: StateFlow<Int> = _cpuUsage

    private val _ramUsage = MutableStateFlow(0L)
    val ramUsage: StateFlow<Long> = _ramUsage

    private val _batteryTemp = MutableStateFlow(0)
    val batteryTemp: StateFlow<Int> = _batteryTemp

    private var lastFrameTimeNanos: Long = 0
    private var frameCount: Int = 0

    private var lastCpuTime: Long = 0
    private var lastIdleTime: Long = 0

    private val choreographerCallback = Choreographer.FrameCallback {
        if (lastFrameTimeNanos != 0L) {
            val frameTimeDiff = it - lastFrameTimeNanos
            val fps = (1_000_000_000.0 / frameTimeDiff).roundToInt()
            _fps.value = fps
        }
        lastFrameTimeNanos = it
        Choreographer.getInstance().postFrameCallback(this)
    }

    fun startMonitoring() {
        Choreographer.getInstance().postFrameCallback(choreographerCallback)
        handler.post(monitoringRunnable)
    }

    fun stopMonitoring() {
        Choreographer.getInstance().removeFrameCallback(choreographerCallback)
        handler.removeCallbacks(monitoringRunnable)
    }

    private val monitoringRunnable = object : Runnable {
        override fun run() {
            updateCpuUsage()
            updateRamUsage()
            updateBatteryTemp()
            handler.postDelayed(this, updateIntervalMs)
        }
    }

    private fun updateCpuUsage() {
        try {
            val reader = RandomAccessFile("/proc/stat", "r")
            val load = reader.readLine()
            val toks = load.split(" ".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
            val idle1 = toks[4].toLong()
            val cpu1 = toks[2].toLong() + toks[3].toLong() + toks[5].toLong() + toks[6].toLong() + toks[7].toLong() + toks[8].toLong()

            if (lastCpuTime != 0L && lastIdleTime != 0L) {
                val totalCpuTime = cpu1 - lastCpuTime
                val totalIdleTime = idle1 - lastIdleTime
                val cpuPercentage = ((totalCpuTime - totalIdleTime).toFloat() / totalCpuTime) * 100
                _cpuUsage.value = cpuPercentage.roundToInt()
            }
            lastCpuTime = cpu1
            lastIdleTime = idle1
            reader.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun updateRamUsage() {
        val actManager = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        val memInfo = ActivityManager.MemoryInfo()
        actManager.getMemoryInfo(memInfo)
        _ramUsage.value = memInfo.availMem // Available memory in bytes
    }

    private fun updateBatteryTemp() {
        val batteryManager = context.getSystemService(Context.BATTERY_SERVICE) as BatteryManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val temp = batteryManager.getIntProperty(BatteryManager.BATTERY_PROPERTY_TEMPERATURE)
            _batteryTemp.value = temp / 10 // Temperature in tenths of a degree Celsius
        }
    }
}

