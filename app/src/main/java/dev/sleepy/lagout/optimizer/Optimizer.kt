package dev.sleepy.lagout.optimizer

import android.content.Context
import dev.sleepy.lagout.shizuku.ShizukuUtils

class Optimizer(private val context: Context) {

    fun oneTapBoost(): String {
        // Kill heavy background apps (requires Shizuku permission)
        val killAppsCommand = "am kill-all-background-processes"
        val result = ShizukuUtils.executeShellCommand(killAppsCommand)
        return "One-Tap Boost executed: \n\n" + result
    }

    fun clearAppCache(packageName: String): String {
        val clearCacheCommand = "pm clear \$packageName"
        val result = ShizukuUtils.executeShellCommand(clearCacheCommand)
        return "Cache cleared for \$packageName: \n\n" + result
    }

    fun disableAnimations(): String {
        val disableWindowAnim = "settings put global window_animation_scale 0"
        val disableTransitionAnim = "settings put global transition_animation_scale 0"
        val disableAnimatorDur = "settings put global animator_duration_scale 0"

        var result = ""
        result += ShizukuUtils.executeShellCommand(disableWindowAnim) + "\n"
        result += ShizukuUtils.executeShellCommand(disableTransitionAnim) + "\n"
        result += ShizukuUtils.executeShellCommand(disableAnimatorDur)
        return "Animations disabled: \n\n" + result
    }

    fun enableAnimations(): String {
        val enableWindowAnim = "settings put global window_animation_scale 1"
        val enableTransitionAnim = "settings put global transition_animation_scale 1"
        val enableAnimatorDur = "settings put global animator_duration_scale 1"

        var result = ""
        result += ShizukuUtils.executeShellCommand(enableWindowAnim) + "\n"
        result += ShizukuUtils.executeShellCommand(enableTransitionAnim) + "\n"
        result += ShizukuUtils.executeShellCommand(enableAnimatorDur)
        return "Animations enabled: \n\n" + result
    }

    // TODO: Implement auto switch refresh rate (if supported)
    // TODO: Implement disable sync temporarily
}

