# LagOut - Android Performance Monitor & Optimizer

LagOut is a fully native Android application built with Kotlin and Jetpack Compose, designed to provide real-time device performance monitoring, intelligent lag profiling, and system-level optimizations. It leverages Shizuku shell access for powerful, non-root system interactions, all wrapped in a clean, modern, and bloat-free user interface.

## 🎯 Core Objectives

*   **Real-time Monitoring:** Track FPS, CPU, RAM, battery temperature, and frame drops.
*   **Lag Detection:** Intelligently identify sources of lag and background pressure.
*   **System Optimizations:** Execute custom shell-based optimizations via Shizuku.
*   **Boost Profiles:** Allow users to define and apply custom optimization profiles with shell commands.
*   **Floating Performance Bubble:** Provide an on-screen FPS and lag bubble, similar to game boosters.
*   **Deep Visibility:** Offer insights into the root causes of slowdowns.

## 🧩 Features Overview

### 🟢 Realtime Monitoring Dashboard

*   **📉 FPS:** Monitor frames per second (via Choreographer callbacks or SurfaceView frame taps).
*   **🔥 CPU Usage:** Display current CPU utilization.
*   **🧠 RAM Usage:** Show total, free, and app-specific RAM usage.
*   **🌡️ Temperature & Battery Stats:** Monitor device temperature and battery health.
*   **📊 Tiny Graph:** Visualize past 30 seconds of performance stats.

### 🛠️ Optimizer Toolkit

*   **🚀 One-Tap Boost:** Quickly kill heavy background applications and clear RAM.
*   **🧹 App Cache Cleaner:** Clear cache for individual apps or all applications.
*   **⚙️ Auto Switch Refresh Rate:** (If supported by device) Automatically adjust screen refresh rate for optimal performance.
*   **📴 Disable Animations & Sync:** Temporarily disable system animations and data synchronization for a performance boost.

### 🎮 Performance Profiles

Users can create and manage custom optimization profiles, each with:

*   **Name:** A descriptive name for the profile.
*   **Icon/Emoji:** A visual identifier for the profile.
*   **List of Shell Commands:** A sequence of shell commands to be executed via Shizuku.

**Example: 🧠 Focus Mode profile:**

```bash
settings put global window_animation_scale 0
settings put global transition_animation_scale 0
am kill com.facebook.katana
cmd package compile -m speed com.example.heavyapp
```

### 🧰 Shizuku Integration

LagOut integrates with Shizuku to execute system-level ADB-like shell commands without requiring root access. This enables:

*   Killing background processes (`am kill`).
*   Clearing RAM by triggering the low memory killer.
*   Changing system settings (`settings put`).
*   Clearing app caches (`pm clear`).
*   Switching CPU profiles (if supported by the kernel).
*   Compiling/optimizing APKs (`cmd package compile`).
*   Scheduling cleanup tasks on screen off/on.

The app will detect if Shizuku is running and prompt the user to grant necessary permissions, with a graceful fallback if Shizuku is not available.

### 📅 Lag History

*   Log app start times, launch lags, and crashes.
*   List 


"most laggy apps" over time.
*   Show graphs of RAM/CPU usage history.

### ⚙️ Developer Tools (Dev Tab)

*   **📦 App-wise RAM Viewer:** Detailed memory usage per application.
*   **🪓 Live Process List:** View all running processes and their resource consumption.
*   **⏱ App Startup Profiler:** Analyze application startup times.
*   **🧪 Shell Playground:** Execute and preview the output of custom shell commands.

### 🧼 UI/UX Highlights (Jetpack Compose)

*   **Dashboard:** Intuitive cards for each performance statistic.
*   **Tabs:** Easy navigation between Dashboard, Profiles, Tools, and History sections.
*   **Material 3 Theme:** Support for both dark and light themes.
*   **Customizable Home Layout:** Drag-and-drop functionality to reorder dashboard cards.
*   **Floating FPS Bubble:** A touch-to-boost floating overlay for real-time FPS and lag monitoring.

## 🛠️ Build Instructions

This project uses Gradle for its build system. To build the application, ensure you have Java Development Kit (JDK) 17 or higher installed.

1.  **Clone the repository:**
    ```bash
    git clone https://github.com/your-username/LagOut.git
    cd LagOut
    ```
2.  **Build the project:**
    ```bash
    ./gradlew build
    ```
    On Windows, use `gradlew.bat build`.

## 🚀 GitHub Actions Workflow

The project includes a GitHub Actions workflow (`.github/workflows/build.yml`) that automatically builds the application on every push and pull request. It sets up JDK 17, grants execute permissions to `gradlew`, builds the project, and uploads the generated AAR and APK files as artifacts.

## ⚠️ Important Notes

*   **Minimum Android Version:** Android 10 (API level 29) is the target, but the `minSdk` is set to 21 for broader compatibility with Shizuku.
*   **Offline Functionality:** The app is designed to work offline, with limited functionality when Shizuku is not available.
*   **Adaptive Icon:** The app includes an adaptive icon for a modern Android look and feel.

## Contributing

Contributions are welcome! Please feel free to open issues or submit pull requests.

## License

[Specify your license here, e.g., MIT, Apache 2.0, etc.]


