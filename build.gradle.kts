import com.android.build.api.dsl.ManagedVirtualDevice

// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id("com.android.application") version "8.1.1" apply false
    id("com.android.library") version "8.1.1" apply false
    id("org.jetbrains.kotlin.android") version "1.9.0" apply false
}

// Define managed devices for automated testing
android {
    testOptions {
        managedDevices {
            devices {
                // Configure a Pixel 6 device. 
                pixel6(ManagedVirtualDevice) {
                    device = "Pixel 6"
                    apiLevel = 31
                    systemImage = "aosp-atd"
                }
            }
        }
    }
}

