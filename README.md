# Jan-Aushadhi Finder 🏥

**Project #71 - Android App Development using GenAI**

A Healthcare Savings Tool that helps users find affordable generic medicines at Jan-Aushadhi Kendras and provides brand-to-generic medicine translation with AI-powered assistance.

---

## 📋 Project Overview

**Domain:** Healthcare  
**Platform:** Android  
**Technology:** Kotlin + Room DB + Google Maps + Gemini AI  
**Category:** Social Impact App

### Problem Statement
Generic medicines at Jan-Aushadhi stores are significantly cheaper than branded alternatives. However, many people:
- Don't know where the nearest Jan-Aushadhi Kendra is located
- Are unaware if their prescribed branded medicine has a cheaper generic equivalent

This app bridges that gap.

---

## ✨ Features

### 1. **Medicine Search (Brand-to-Generic)**
- Search 500+ medicines by brand name
- **Fuzzy search** using Levenshtein algorithm (handles typos like "Paracetamol", "Paracetmol")
- Displays generic/salt name and price comparison
- Shows savings: "Branded: ₹100 vs Generic: ₹20 — You Save ₹80 (80%)"

### 2. **Store Locator**
- Google Maps integration
- Shows Jan-Aushadhi Kendras within 10 km radius
- Displays distance, "Open Now" status, ratings
- Tap marker for directions

### 3. **Stock Request (Simulated)**
- Send stock availability query to nearby stores
- Simulated response: IN_STOCK / LIMITED_STOCK / OUT_OF_STOCK

### 4. **Medicine Refill Reminders**
- Add medicines to personal tracker
- Monthly refill reminder notifications via WorkManager
- Runs daily at 9 AM to check due reminders

### 5. **AI Chat (Gemini)**
- Ask medicine-related questions
- Get brand-to-generic suggestions
- Learn about generic vs branded medicines

---

## 🛠️ Technical Stack

| Component | Technology | Purpose |
|-----------|-----------|---------|
| Language | Kotlin | Android app development |
| Database | Room (SQLite) | Local storage (offline support) |
| UI | XML Layouts, RecyclerView, CardView | Clean clinical interface |
| Maps | Google Maps API | Store locator |
| AI | Google Gemini API | Smart suggestions & Q&A |
| Notifications | WorkManager / AlarmManager | Monthly refill reminders |
| Architecture | MVVM + Repository Pattern | Clean architecture |
| Async | Kotlin Coroutines + Flow | Background operations |

---

## 📦 Project Structure

```
app/src/main/java/com/janaushadhi/finder/
├── data/
│   ├── model/          # Medicine, Store, Reminder, StockRequest
│   ├── dao/            # Room DAOs
│   ├── database/       # AppDatabase
│   ├── seeder/         # DatabaseSeeder (502 medicines, 33 stores)
│   └── repository/     # Data repositories
├── ui/
│   ├── splash/         # SplashActivity
│   ├── main/           # MainActivity (bottom nav)
│   ├── medicine/       # MedicineDetailActivity
│   └── ai/             # AiChatActivity
├── viewmodel/          # ViewModels (MVVM)
├── utils/              # FuzzySearch, LocationUtils, CurrencyUtils
├── worker/             # ReminderWorker (WorkManager)
├── receiver/           # BootReceiver
└── ai/                 # GeminiService
```

---

## 🚀 Setup Instructions

### Prerequisites
- Android Studio Hedgehog (2023.1.1) or later
- JDK 17
- Android SDK API 34
- Google Maps API Key
- Google Gemini API Key

### Step 1: Clone/Open Project
```bash
cd "/Users/likhithr/Jan-Aushadhi Finder"
```

### Step 2: Configure API Keys

#### Google Maps API Key
1. Go to [Google Cloud Console](https://console.cloud.google.com/)
2. Enable **Maps SDK for Android**
3. Create API Key
4. Open `app/build.gradle`
5. Replace `YOUR_GOOGLE_MAPS_API_KEY` with your key:
```gradle
manifestPlaceholders = [
    MAPS_API_KEY: "AIzaSy..."  // Your actual key
]
```

#### Gemini API Key
1. Go to [Google AI Studio](https://makersuite.google.com/app/apikey)
2. Create API Key
3. In `app/build.gradle`, replace:
```gradle
buildConfigField "String", "GEMINI_API_KEY", '"YOUR_GEMINI_API_KEY"'
```
With:
```gradle
buildConfigField "String", "GEMINI_API_KEY", '"AIzaSy..."'  // Your actual key
```

### Step 3: Sync Gradle
```bash
./gradlew build
```

### Step 4: Run the App
- Connect Android device or start emulator
- Click **Run** in Android Studio
- Or via command line:
```bash
./gradlew installDebug
```

---

## 📊 Database

### Medicines (502 entries)
Categories include:
- Analgesics/Antipyretics (Crocin, Dolo, Brufen, Voveran, etc.)
- Antibiotics (Augmentin, Azithral, Ciprobid, etc.)
- Antacids/GI (Pantop, Omez, Razo, etc.)
- Antidiabetics (Glycomet, Januvia, Jardiance, etc.)
- Antihypertensives (Telma, Losartan, Amlodipine, etc.)
- Statins/Cardiac (Atorvastatin, Clopidogrel, Ecosprin, etc.)
- Respiratory (Asthalin, Seroflo, Montek, etc.)
- Vitamins/Supplements (Shelcal, Becosules, Neurobion, etc.)
- Thyroid (Eltroxin, Thyronorm, etc.)
- Neurological/Psychiatric (Nexito, Clonazepam, Pregabalin, etc.)
- And 6 more categories...

### Stores (33 entries)
Jan-Aushadhi Kendras across:
- Delhi (4 stores)
- Mumbai (3 stores)
- Bangalore (3 stores)
- Chennai (3 stores)
- Hyderabad (3 stores)
- Kolkata (2 stores)
- Pune, Ahmedabad, Jaipur, Lucknow, and 7 more cities

---

## 🎯 Success Criteria (from PRD)

| # | Criterion | Status |
|---|-----------|--------|
| 1 | Fuzzy Search handles spelling variations | ✅ Levenshtein algorithm |
| 2 | Price Comparison UI shows "You Save ₹80 (80%)" | ✅ Implemented |
| 3 | UI is clean, clinical, and professional | ✅ Material Design |
| 4 | Store Locator shows stores within 10 km | ✅ Google Maps + Haversine |
| 5 | Reminders fire at correct date | ✅ WorkManager daily check |

---

## 🔧 Key Algorithms

### Fuzzy Search (Levenshtein Distance)
```kotlin
// Handles typos: "Paracetamol", "Paracetmol", "Paracetamole"
fun levenshteinDistance(s1: String, s2: String): Int {
    // Dynamic programming approach
    // Returns edit distance (lower = more similar)
}
```

### Store Distance (Haversine Formula)
```kotlin
// Calculates accurate distance between two lat/lng points
fun distanceKm(lat1: Double, lng1: Double, lat2: Double, lng2: Double): Double {
    // Earth radius = 6371 km
    // Returns distance in kilometers
}
```

---

## 📱 Screenshots & Demo

### Medicine Search
- Type "Crocin" → Shows "Paracetamol 500mg"
- Branded: ₹30 vs Generic: ₹5
- **You Save ₹25 (83%)**

### Store Locator
- Map with markers for nearby Jan-Aushadhi Kendras
- Distance: 2.3 km, Open Now, Rating: 4.3★

### Reminders
- Add "Metformin 500mg" with refill date
- Get notification 3 days before refill

---

## 🧪 Testing

### Run Unit Tests
```bash
./gradlew test
```

### Run Instrumented Tests
```bash
./gradlew connectedAndroidTest
```

### Manual Testing Checklist
- [ ] Search for "Crocin" → Returns Paracetamol
- [ ] Search for "Paracetmol" (typo) → Still returns Paracetamol
- [ ] Open Maps → Shows stores within 10 km
- [ ] Add reminder → Notification appears on due date
- [ ] Ask AI "What is Paracetamol?" → Gets response

---

## 🐛 Troubleshooting

### Maps not showing
- Check if Google Maps API key is correct
- Enable "Maps SDK for Android" in Google Cloud Console
- Ensure location permissions are granted

### Gemini AI not responding
- Verify Gemini API key in `build.gradle`
- Check internet connection
- Ensure API quota is not exceeded

### Database empty
- Database seeds automatically on first launch
- Check Logcat for seeding errors
- Clear app data and restart

### Notifications not working
- Grant notification permission (Android 13+)
- Check battery optimization settings
- Verify WorkManager is scheduled

---

## 📄 License

This project is for academic submission (May 2026).

---

## 👨‍💻 Developer Notes

### Adding More Medicines
Edit `DatabaseSeeder.kt` and add entries to the appropriate category list.

### Changing Reminder Time
Edit `ReminderWorker.kt` → `calculateInitialDelay()` function.

### Customizing UI Theme
Edit `res/values/themes.xml` and `res/values/colors.xml`.

---

## 🎓 Academic Submission

**Project Number:** 71  
**Project Title:** Jan-Aushadhi Finder  
**Domain:** Healthcare  
**Submission Date:** May 2026

---

## 📞 Support

For issues or questions:
1. Check Logcat for error messages
2. Verify all API keys are configured
3. Ensure minimum Android API 21 (Lollipop)

---

**Built with ❤️ for affordable healthcare access in India**
