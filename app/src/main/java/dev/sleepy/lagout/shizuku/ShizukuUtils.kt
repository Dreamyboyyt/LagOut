package dev.sleepy.lagout.shizuku

import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.os.IBinder
import android.os.Parcel
import android.os.RemoteException
import android.util.Log
import moe.shizuku.api.ShizukuApi
import moe.shizuku.api.ShizukuBinderWrapper
import moe.shizuku.api.ShizukuService
import moe.shizuku.api.ShizukuState
import java.io.BufferedReader
import java.io.InputStreamReader

object ShizukuUtils {

    private const val TAG = "ShizukuUtils"

    fun isShizukuRunning(): Boolean {
        return ShizukuApi.isShizukuRunning()
    }

    fun requestShizukuPermission(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            ShizukuApi.requestPermission(context)
        }
    }

    fun checkShizukuPermission(): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            ShizukuApi.checkPermission()
        } else {
            false
        }
    }

    fun executeShellCommand(command: String): String {
        if (!checkShizukuPermission()) {
            return "Shizuku permission not granted."
        }

        var output = ""
        try {
            val process = ShizukuService.newProcess(arrayOf("sh", "-c", command))
            val reader = BufferedReader(InputStreamReader(process.inputStream))
            var line: String?
            while (reader.readLine().also { line = it } != null) {
                output += line + "\n"
            }
            process.waitFor()
            reader.close()
        } catch (e: RemoteException) {
            Log.e(TAG, "RemoteException: ", e)
            output = "Error: RemoteException - \${e.message}"
        } catch (e: Exception) {
            Log.e(TAG, "Exception: ", e)
            output = "Error: \${e.message}"
        }
        return output
    }
}

