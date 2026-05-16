# 📱 Android Studio Setup & Testing Guide - Jan-Aushadhi Finder

## 🚀 **Step 1: Open Project in Android Studio**

### Open the Project:
1. **Launch Android Studio**
2. **Click "Open"** or **"Open an Existing Project"**
3. **Navigate to**: `/Users/likhithr/Jan-Aushadhi Finder`
4. **Select the project folder** and click **"Open"**

### Initial Setup:
- Android Studio will automatically detect it's a Gradle project
- Wait for **"Gradle Sync"** to complete (may take 2-5 minutes)
- If prompted, click **"Trust Project"**

## 🔧 **Step 2: Configure Android Studio**

### SDK Requirements:
- **Target SDK**: 34 (Android 14)
- **Min SDK**: 21 (Android 5.0)
- **Compile SDK**: 34

### Check SDK Installation:
1. Go to **Tools → SDK Manager**
2. Ensure these are installed:
   - ✅ **Android 14.0 (API 34)** - SDK Platform
   - ✅ **Android SDK Build-Tools 34.0.0**
   - ✅ **Google Play Services**
   - ✅ **Google Repository**

## 📱 **Step 3: Set Up Device/Emulator**

### Option A: Physical Android Device (Recommended)
1. **Enable Developer Options**:
   - Go to **Settings → About Phone**
   - Tap **"Build Number"** 7 times
   - Go back to **Settings → Developer Options**
   - Enable **"USB Debugging"**

2. **Connect Device**:
   - Connect via USB cable
   - Allow USB debugging when prompted
   - Device should appear in Android Studio toolbar

### Option B: Android Emulator
1. **Create Virtual Device**:
   - Click **Tools → AVD Manager**
   - Click **"Create Virtual Device"**
   - Choose **Pixel 6** or similar modern device
   - Select **API 34 (Android 14)**
   - Click **"Finish"**

2. **Start Emulator**:
   - Click the **play button** next to your virtual device
   - Wait for emulator to boot up completely

## 🎯 **Step 4: Configure API Keys (Optional but Recommended)**

### Set Up Google Maps API:
1. **Get API Key**:
   - Go to [Google Cloud Console](https://console.cloud.google.com/)
   - Create new project or select existing
   - Enable **"Maps SDK for Android"**
   - Create credentials → API Key
   - Restrict key to Android apps

2. **Add to Project**:
   ```properties
   # Create/Edit: app/local.properties
   MAPS_API_KEY=your_actual_google_maps_api_key_here
   ```

### Set Up Gemini AI API:
1. **Get API Key**:
   - Go to [Google AI Studio](https://makersuite.google.com/app/apikey)
   - Create new API key

2. **Add to Project**:
   ```properties
   # Add to: app/local.properties
   GEMINI_API_KEY=your_actual_gemini_api_key_here
   ```

## ▶️ **Step 5: Run the App**

### Build and Run:
1. **Select Device**: Choose your device/emulator from dropdown
2. **Click Run**: Green play button or **Shift + F10**
3. **Wait for Build**: First build may take 2-3 minutes
4. **App Launches**: Jan-Aushadhi Finder will open on device

### Build Variants:
- **Debug**: For testing (includes debugging info)
- **Release**: For production (optimized, smaller size)

## 🧪 **Step 6: Test All Features**

### 1. **Medicine Search** 🔍
- **Test**: Search for "Crocin", "Paracetamol", "Dolo 650"
- **Expected**: Fuzzy search results with price comparison
- **Check**: Branded vs Generic prices, savings calculation

### 2. **Store Locator** 📍
- **Test**: Navigate to "Stores" tab
- **Expected**: List of nearby Jan-Aushadhi Kendras
- **Check**: Distance calculation, store details
- **Note**: Maps require API key for full functionality

### 3. **Medicine Details** 📋
- **Test**: Tap any medicine from search results
- **Expected**: Detailed medicine information screen
- **Check**: Price comparison, uses, side effects, favorite button

### 4. **Favorites System** ❤️
- **Test**: Tap heart icon on any medicine
- **Expected**: Medicine added to favorites
- **Check**: Navigate to "Favorites" tab to see saved medicines

### 5. **Reminders** ⏰
- **Test**: Navigate to "Reminders" tab
- **Expected**: Empty state with "Add Reminder" option
- **Check**: Reminder creation flow

### 6. **AI Chat Assistant** 🤖
- **Test**: Navigate to "AI Chat" tab
- **Expected**: Chat interface with welcome message
- **Check**: Ask questions about medicines
- **Note**: Requires Gemini API key for responses

## 🐛 **Step 7: Debug Common Issues**

### Gradle Sync Issues:
```bash
# In Android Studio Terminal:
./gradlew clean
./gradlew build
```

### Build Errors:
1. **Clean Project**: Build → Clean Project
2. **Rebuild**: Build → Rebuild Project
3. **Invalidate Caches**: File → Invalidate Caches and Restart

### Device Connection Issues:
1. **Check USB Debugging** is enabled
2. **Revoke USB Debugging** authorizations and reconnect
3. **Try different USB cable/port**

### Emulator Issues:
1. **Wipe Data**: AVD Manager → Actions → Wipe Data
2. **Cold Boot**: AVD Manager → Actions → Cold Boot Now
3. **Increase RAM**: Edit AVD → Advanced → RAM to 4GB+

## 📊 **Step 8: Performance Testing**

### Test Database Performance:
- Search for medicines with typos: "Crocn", "Paracetmol"
- Verify fuzzy search works correctly
- Check search response time (should be < 1 second)

### Test UI Responsiveness:
- Navigate between tabs quickly
- Scroll through medicine lists
- Test on different screen orientations

### Memory Usage:
- Monitor in **Android Studio Profiler**
- Check for memory leaks during navigation
- Verify smooth scrolling in RecyclerViews

## 🎯 **Step 9: Production Testing**

### Test Release Build:
1. **Build → Generate Signed Bundle/APK**
2. **Choose APK**
3. **Create new keystore** (for production)
4. **Build release APK**
5. **Install and test** release version

### Performance Optimization:
- **Enable R8/ProGuard** for code shrinking
- **Test on low-end devices** (API 21+)
- **Verify offline functionality** (database works without internet)

## 📱 **Expected App Behavior**

### On First Launch:
1. **Splash Screen** appears for 2 seconds
2. **Main Activity** loads with Search tab active
3. **Database seeding** happens automatically (500+ medicines)
4. **Permissions requested** for location and notifications

### Core Functionality:
- ✅ **Search**: Instant fuzzy search with typo tolerance
- ✅ **Price Comparison**: Clear branded vs generic pricing
- ✅ **Store Locator**: 30+ Jan-Aushadhi stores across India
- ✅ **Favorites**: Persistent favorite medicines
- ✅ **Reminders**: Medicine refill notifications
- ✅ **AI Chat**: Medicine Q&A (with API key)

## 🏆 **Success Indicators**

### App is Working Correctly When:
- ✅ **Fast Search**: Medicine search returns results in < 1 second
- ✅ **Accurate Data**: 500+ medicines with correct price comparisons
- ✅ **Smooth Navigation**: No crashes when switching tabs
- ✅ **Responsive UI**: Smooth scrolling and animations
- ✅ **Persistent Data**: Favorites and reminders survive app restart
- ✅ **Professional Look**: Material Design 3 UI throughout

---

## 🎊 **Ready to Test!**

Your **Jan-Aushadhi Finder** app is now ready for comprehensive testing in Android Studio. The app provides a complete healthcare savings solution with professional-grade features and performance.

**Happy Testing!** 🚀📱