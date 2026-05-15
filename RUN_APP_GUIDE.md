# 🚀 STEP-BY-STEP GUIDE TO RUN JAN-AUSHADHI FINDER

## Complete Guide to Build, Run, and Test Your Android App

---

## 📋 PREREQUISITES CHECKLIST

Before we start, ensure you have:

- [ ] **Android Studio** (Hedgehog 2023.1.1 or later)
- [ ] **Java Development Kit (JDK)** 17 or later
- [ ] **Android SDK** (API 21 to 34)
- [ ] **Google Maps API Key**
- [ ] **Gemini AI API Key**
- [ ] **Android Device** or **Emulator**
- [ ] **Internet Connection** (for Maps and AI features)

---

## 🔧 STEP 1: INSTALL ANDROID STUDIO

### If Android Studio is NOT installed:

1. **Download Android Studio:**
   - Go to https://developer.android.com/studio
   - Download for macOS
   - Install the .dmg file

2. **Setup Android Studio:**
   - Open Android Studio
   - Follow the setup wizard
   - Install Android SDK (API 21-34)
   - Install Android Virtual Device (AVD)

### If Android Studio IS installed:
- Ensure it's updated to latest version
- Check SDK Manager has API 21-34 installed

---

## 🔑 STEP 2: GET API KEYS

### 2.1 Google Maps API Key

1. **Go to Google Cloud Console:**
   ```
   https://console.cloud.google.com/
   ```

2. **Create/Select Project:**
   - Click "Select a project" → "New Project"
   - Name: "Jan-Aushadhi-Finder"
   - Click "Create"

3. **Enable Maps SDK:**
   - Go to "APIs & Services" → "Library"
   - Search "Maps SDK for Android"
   - Click and "Enable"

4. **Create API Key:**
   - Go to "APIs & Services" → "Credentials"
   - Click "Create Credentials" → "API Key"
   - Copy the API key (starts with AIza...)

5. **Restrict API Key (Recommended):**
   - Click on the API key
   - Under "Application restrictions" → "Android apps"
   - Add package name: `com.janaushadhi.finder`

### 2.2 Gemini AI API Key

1. **Go to Google AI Studio:**
   ```
   https://makersuite.google.com/app/apikey
   ```

2. **Create API Key:**
   - Click "Create API Key"
   - Select your Google Cloud project
   - Copy the API key (starts with AIza...)

---

## 📱 STEP 3: SETUP ANDROID DEVICE/EMULATOR

### Option A: Use Physical Android Device (Recommended)

1. **Enable Developer Options:**
   - Go to Settings → About Phone
   - Tap "Build Number" 7 times
   - Go back to Settings → Developer Options

2. **Enable USB Debugging:**
   - In Developer Options, enable "USB Debugging"
   - Connect device to Mac via USB
   - Allow USB debugging when prompted

3. **Verify Connection:**
   ```bash
   adb devices
   ```
   Should show your device listed

### Option B: Use Android Emulator

1. **Open Android Studio**
2. **Create Virtual Device:**
   - Tools → AVD Manager
   - Click "Create Virtual Device"
   - Choose "Pixel 4" or "Pixel 6 Pro"
   - Select API 30 or higher
   - Click "Finish"

3. **Start Emulator:**
   - Click the play button next to your AVD
   - Wait for emulator to boot completely

---

## 🏗️ STEP 4: OPEN PROJECT IN ANDROID STUDIO

1. **Open Android Studio**

2. **Open Project:**
   - Click "Open an Existing Project"
   - Navigate to: `/Users/likhithr/Jan-Aushadhi Finder`
   - Click "Open"

3. **Wait for Gradle Sync:**
   - Android Studio will automatically sync Gradle
   - This may take 2-5 minutes
   - Wait for "Gradle sync finished" message

4. **Check Project Structure:**
   ```
   Jan-Aushadhi Finder/
   ├── app/
   │   ├── src/main/
   │   │   ├── java/com/janaushadhi/finder/
   │   │   ├── res/
   │   │   └── AndroidManifest.xml
   │   └── build.gradle
   ├── build.gradle
   └── settings.gradle
   ```

---

## 🔧 STEP 5: CONFIGURE API KEYS

### Method 1: Using Setup Script (Recommended)

1. **Open Terminal in project directory:**
   ```bash
   cd "/Users/likhithr/Jan-Aushadhi Finder"
   ```

2. **Run setup script:**
   ```bash
   python3 setup_api_keys.py
   ```

3. **Follow prompts:**
   - Enter your Google Maps API Key
   - Enter your Gemini API Key
   - Choose option 1 (local.properties)

### Method 2: Manual Configuration

1. **Create local.properties file:**
   - In Android Studio, right-click on `app` folder
   - New → File → `local.properties`

2. **Add API keys:**
   ```properties
   MAPS_API_KEY=YOUR_ACTUAL_GOOGLE_MAPS_API_KEY
   GEMINI_API_KEY=YOUR_ACTUAL_GEMINI_API_KEY
   ```

3. **Update build.gradle:**
   - Open `app/build.gradle`
   - Add at the top:
   ```gradle
   def localProperties = new Properties()
   def localPropertiesFile = rootProject.file('app/local.properties')
   if (localPropertiesFile.exists()) {
       localProperties.load(new FileInputStream(localPropertiesFile))
   }
   ```

   - Replace the manifestPlaceholders section:
   ```gradle
   manifestPlaceholders = [
       MAPS_API_KEY: localProperties.getProperty('MAPS_API_KEY', 'YOUR_GOOGLE_MAPS_API_KEY')
   ]
   buildConfigField "String", "GEMINI_API_KEY", "\"${localProperties.getProperty('GEMINI_API_KEY', 'YOUR_GEMINI_API_KEY')}\""
   ```

4. **Sync Gradle:**
   - Click "Sync Now" when prompted
   - Or go to File → Sync Project with Gradle Files

---

## ▶️ STEP 6: BUILD AND RUN THE APP

### 6.1 Build the App

1. **Clean Project:**
   - Build → Clean Project
   - Wait for completion

2. **Build Project:**
   - Build → Make Project
   - Check for any errors in Build tab

3. **Verify Build Success:**
   - Look for "BUILD SUCCESSFUL" message
   - No red errors in Build output

### 6.2 Run the App

1. **Select Target Device:**
   - In toolbar, select your device/emulator from dropdown

2. **Run App:**
   - Click the green "Run" button (▶️)
   - Or press `Ctrl + R`

3. **Wait for Installation:**
   - App will compile and install
   - This may take 1-3 minutes for first run

4. **App Should Launch:**
   - Splash screen appears for 2 seconds
   - Main app interface loads

---

## 🧪 STEP 7: TEST ALL FEATURES

### 7.1 Initial App Launch Test

**✅ Expected Behavior:**
- Splash screen shows for 2 seconds
- Main screen loads with bottom navigation
- 5 tabs visible: Search, Stores, Reminders, Favorites, AI Chat

**🔍 What to Check:**
- [ ] App launches without crashes
- [ ] Splash screen displays properly
- [ ] Bottom navigation works
- [ ] All tabs are accessible

### 7.2 Medicine Search Test

1. **Go to Search Tab:**
   - Tap "Search" in bottom navigation

2. **Test Exact Search:**
   - Type "Crocin" in search box
   - Press search or wait for auto-search

**✅ Expected Results:**
- [ ] Search results appear quickly
- [ ] Shows Crocin 650mg
- [ ] Displays branded vs generic price
- [ ] Shows "You Save ₹X (Y%)" message
- [ ] Heart icon for favorites visible

3. **Test Fuzzy Search:**
   - Clear search and type "Paracetmol" (missing 'a')
   - Should find "Paracetamol"

**✅ Expected Results:**
- [ ] Finds correct medicine despite typo
- [ ] Shows generic name (Paracetamol)
- [ ] Price comparison visible

4. **Test Add to Favorites:**
   - Tap heart icon on any medicine
   - Heart should turn red/filled

### 7.3 Store Locator Test

1. **Go to Stores Tab:**
   - Tap "Stores" in bottom navigation

2. **Grant Location Permission:**
   - When prompted, tap "Allow" for location access

**✅ Expected Results:**
- [ ] Google Maps loads
- [ ] Your location appears (blue dot)
- [ ] Red markers show Jan-Aushadhi stores
- [ ] Distance information displays

3. **Test Store Interaction:**
   - Tap on any store marker
   - Should show store details

4. **Test My Location Button:**
   - Tap floating location button
   - Map should center on your location

### 7.4 Reminders Test

1. **Go to Reminders Tab:**
   - Tap "Reminders" in bottom navigation

2. **Add New Reminder:**
   - Tap the floating "+" button
   - Fill in medicine name: "Crocin"
   - Set refill date (tomorrow)
   - Add dosage: "1 tablet twice daily"
   - Tap Save

**✅ Expected Results:**
- [ ] Reminder appears in list
- [ ] Shows medicine name and dosage
- [ ] Shows days until refill
- [ ] Toggle switch works

3. **Test Reminder Management:**
   - Toggle reminder on/off
   - Try deleting a reminder

### 7.5 AI Chat Test

1. **Go to AI Chat Tab:**
   - Tap "AI Chat" in bottom navigation

2. **Test Medicine Questions:**
   - Type: "What is Paracetamol used for?"
   - Tap send button

**✅ Expected Results:**
- [ ] Message appears in chat
- [ ] AI responds with medicine information
- [ ] Response is relevant and helpful

3. **Test More Questions:**
   - "Generic alternative to Crocin?"
   - "Side effects of Aspirin?"

### 7.6 Favorites Test

1. **Go to Favorites Tab:**
   - Should show medicines you added to favorites

**✅ Expected Results:**
- [ ] Previously favorited medicines appear
- [ ] Can remove from favorites
- [ ] Empty state shows when no favorites

### 7.7 Medicine Detail Test

1. **From Search, tap any medicine card:**
   - Should open detailed view

**✅ Expected Results:**
- [ ] Shows complete medicine information
- [ ] Price comparison prominent
- [ ] Uses and side effects visible
- [ ] Can add reminder from detail view

---

## 🐛 STEP 8: TROUBLESHOOTING COMMON ISSUES

### Issue 1: Build Errors

**Error: "Unresolved reference: R"**
```bash
# Solution:
Build → Clean Project
Build → Rebuild Project
```

**Error: "API key not found"**
- Check `app/local.properties` file exists
- Verify API keys are correct (no extra spaces/quotes)
- Sync Gradle after changes

### Issue 2: Maps Not Loading

**Possible Causes:**
- Invalid Google Maps API key
- Maps SDK not enabled
- No internet connection

**Solutions:**
1. Verify API key in Google Cloud Console
2. Enable "Maps SDK for Android"
3. Check internet connection
4. Grant location permission

### Issue 3: AI Chat Not Working

**Possible Causes:**
- Invalid Gemini API key
- No internet connection
- API quota exceeded

**Solutions:**
1. Verify Gemini API key
2. Check internet connection
3. Check API usage in Google AI Studio

### Issue 4: No Search Results

**Possible Causes:**
- Database not seeded
- Search algorithm issue

**Solutions:**
1. Clear app data and reinstall
2. Check Logcat for database errors

### Issue 5: Reminders Not Working

**Possible Causes:**
- Notification permission denied
- WorkManager not initialized

**Solutions:**
1. Grant notification permission in Settings
2. Check notification settings for the app

---

## 📊 STEP 9: PERFORMANCE VERIFICATION

### 9.1 Check App Performance

1. **Search Speed:**
   - Search should return results in <1 second
   - Try searching for various medicines

2. **Navigation Speed:**
   - Switching between tabs should be instant
   - No lag or stuttering

3. **Memory Usage:**
   - App should run smoothly
   - No crashes or "out of memory" errors

### 9.2 Check Database

1. **Medicine Count:**
   - Search should find 502 different medicines
   - Try searching different categories

2. **Store Count:**
   - Maps should show 33 stores
   - Stores should be across different cities

---

## ✅ STEP 10: FINAL VERIFICATION CHECKLIST

### Core Features Working:
- [ ] App launches successfully
- [ ] Medicine search with fuzzy matching works
- [ ] Price comparison displays correctly
- [ ] Store locator shows maps and stores
- [ ] Reminders can be added and managed
- [ ] AI chat responds to questions
- [ ] Favorites can be added/removed
- [ ] All navigation works smoothly

### Performance Checks:
- [ ] Search results appear quickly
- [ ] No crashes or major bugs
- [ ] Smooth navigation between screens
- [ ] Maps load properly
- [ ] Notifications work

### Data Verification:
- [ ] 502 medicines searchable
- [ ] 33 stores visible on map
- [ ] Price comparisons show savings
- [ ] Fuzzy search handles typos

---

## 🎉 SUCCESS! YOUR APP IS RUNNING

If all tests pass, congratulations! Your Jan-Aushadhi Finder app is working perfectly.

### What You've Achieved:
✅ **Complete Android App** running on device  
✅ **All Features Working** as designed  
✅ **502 Medicines** searchable with fuzzy matching  
✅ **33 Stores** visible on Google Maps  
✅ **AI Integration** responding to questions  
✅ **Reminders System** scheduling notifications  
✅ **Professional UI** with Material Design 3  

---

## 📱 NEXT STEPS

### For Development:
1. **Customize Features** - Add more medicines or stores
2. **Enhance UI** - Customize colors or layouts
3. **Add Tests** - Write unit and integration tests
4. **Optimize Performance** - Profile and optimize code

### For Submission:
1. **Take Screenshots** - Capture all major screens
2. **Record Demo Video** - Show all features working
3. **Document Testing** - Complete testing checklist
4. **Prepare Presentation** - Highlight key features and impact

### For Deployment:
1. **Generate Signed APK** - For distribution
2. **Prepare Play Store Listing** - Screenshots, description
3. **Test on Multiple Devices** - Ensure compatibility
4. **Submit for Review** - Google Play Store process

---

## 📞 NEED HELP?

### Check Logs:
```bash
# View app logs
adb logcat | grep JanAushadhi

# View all logs
adb logcat
```

### Common Commands:
```bash
# Restart ADB
adb kill-server && adb start-server

# Install APK manually
adb install app/build/outputs/apk/debug/app-debug.apk

# Clear app data
adb shell pm clear com.janaushadhi.finder
```

### Documentation References:
- `TESTING_GUIDE.md` - Comprehensive testing procedures
- `SETUP_COMPLETE.md` - UI completion guide
- `REQUIREMENTS_COMPLIANCE_REPORT.md` - Requirements verification
- `PROJECT_SUMMARY.md` - Complete project overview

---

**🎯 Your Jan-Aushadhi Finder app is now running and ready to help people access affordable healthcare! 🏥**

**Built with ❤️ for affordable healthcare access in India 🇮🇳**

---

*Last Updated: May 15, 2026*  
*Status: ✅ READY TO RUN AND TEST*