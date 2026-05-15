# Jan-Aushadhi Finder - Complete Setup Guide

## 📋 Project Status

✅ **Core Architecture Complete:**
- Database layer (Room) with 502 medicines & 33 stores
- Repository pattern with fuzzy search (Levenshtein algorithm)
- MVVM architecture with ViewModels
- WorkManager for reminders
- Gemini AI integration
- Google Maps integration

⚠️ **Remaining Tasks:**
- XML layout files (activities & fragments)
- Drawable resources (icons)
- Complete testing

---

## 🚀 Quick Start (5 Steps)

### Step 1: Open Project in Android Studio
```bash
cd "/Users/likhithr/Jan-Aushadhi Finder"
```
Open in Android Studio Hedgehog or later.

### Step 2: Configure API Keys

#### A. Google Maps API Key
1. Visit: https://console.cloud.google.com/
2. Create/Select project
3. Enable "Maps SDK for Android"
4. Create API Key
5. Edit `app/build.gradle` line 20:
```gradle
manifestPlaceholders = [
    MAPS_API_KEY: "AIzaSyXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX"  // Replace with your key
]
```

#### B. Gemini API Key
1. Visit: https://makersuite.google.com/app/apikey
2. Create API Key
3. Edit `app/build.gradle` line 23:
```gradle
buildConfigField "String", "GEMINI_API_KEY", '"AIzaSyYYYYYYYYYYYYYYYYYYYYYYYYYYYYYY"'  // Replace
```

### Step 3: Create Missing Layout Files

Run this Python script to generate basic layouts:

```python
# save as create_layouts.py
import os

BASE = "/Users/likhithr/Jan-Aushadhi Finder/app/src/main/res"

layouts = {
    "activity_splash.xml": '''<?xml version="1.0" encoding="utf-8"?>
<RelativeLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:background="@color/splash_background"
    android:gravity="center">
    <TextView
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="Jan-Aushadhi Finder"
        android:textSize="32sp"
        android:textColor="@color/text_white"
        android:textStyle="bold"/>
</RelativeLayout>''',

    "activity_main.xml": '''<?xml version="1.0" encoding="utf-8"?>
<androidx.constraintlayout.widget.ConstraintLayout 
    xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    android:layout_width="match_parent"
    android:layout_height="match_parent">
    
    <FrameLayout
        android:id="@+id/fragment_container"
        android:layout_width="0dp"
        android:layout_height="0dp"
        app:layout_constraintTop_toTopOf="parent"
        app:layout_constraintBottom_toTopOf="@id/bottom_navigation"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintEnd_toEndOf="parent"/>
    
    <com.google.android.material.bottomnavigation.BottomNavigationView
        android:id="@+id/bottom_navigation"
        android:layout_width="0dp"
        android:layout_height="wrap_content"
        app:menu="@menu/bottom_nav_menu"
        app:layout_constraintBottom_toBottomOf="parent"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintEnd_toEndOf="parent"/>
</androidx.constraintlayout.widget.ConstraintLayout>''',

    "fragment_search.xml": '''<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:orientation="vertical">
    
    <androidx.appcompat.widget.SearchView
        android:id="@+id/search_view"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:queryHint="@string/search_hint"/>
    
    <ProgressBar
        android:id="@+id/progress_bar"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_gravity="center"
        android:visibility="gone"/>
    
    <androidx.recyclerview.widget.RecyclerView
        android:id="@+id/recycler_view"
        android:layout_width="match_parent"
        android:layout_height="0dp"
        android:layout_weight="1"/>
    
    <TextView
        android:id="@+id/empty_view"
        android:layout_width="match_parent"
        android:layout_height="0dp"
        android:layout_weight="1"
        android:gravity="center"
        android:text="@string/search_hint"
        android:textSize="16sp"/>
</LinearLayout>''',

    "fragment_stores.xml": '''<?xml version="1.0" encoding="utf-8"?>
<FrameLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="match_parent">
    
    <fragment
        android:id="@+id/map"
        android:name="com.google.android.gms.maps.SupportMapFragment"
        android:layout_width="match_parent"
        android:layout_height="match_parent"/>
</FrameLayout>''',

    "fragment_reminders.xml": '''<?xml version="1.0" encoding="utf-8"?>
<androidx.coordinatorlayout.widget.CoordinatorLayout 
    xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    android:layout_width="match_parent"
    android:layout_height="match_parent">
    
    <androidx.recyclerview.widget.RecyclerView
        android:id="@+id/recycler_view"
        android:layout_width="match_parent"
        android:layout_height="match_parent"/>
    
    <TextView
        android:id="@+id/empty_view"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:gravity="center"
        android:text="@string/no_reminders"
        android:visibility="gone"/>
    
    <com.google.android.material.floatingactionbutton.FloatingActionButton
        android:id="@+id/fab_add_reminder"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_gravity="bottom|end"
        android:layout_margin="16dp"
        android:src="@android:drawable/ic_input_add"/>
</androidx.coordinatorlayout.widget.CoordinatorLayout>''',

    "fragment_favorites.xml": '''<?xml version="1.0" encoding="utf-8"?>
<FrameLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="match_parent">
    
    <androidx.recyclerview.widget.RecyclerView
        android:id="@+id/recycler_view"
        android:layout_width="match_parent"
        android:layout_height="match_parent"/>
    
    <TextView
        android:id="@+id/empty_view"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:gravity="center"
        android:text="@string/no_favorites"/>
</FrameLayout>''',

    "item_medicine.xml": '''<?xml version="1.0" encoding="utf-8"?>
<com.google.android.material.card.MaterialCardView 
    xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    style="@style/CardView.Medicine"
    android:layout_width="match_parent"
    android:layout_height="wrap_content">
    
    <LinearLayout
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:orientation="vertical"
        android:padding="16dp">
        
        <LinearLayout
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:orientation="horizontal">
            
            <LinearLayout
                android:layout_width="0dp"
                android:layout_height="wrap_content"
                android:layout_weight="1"
                android:orientation="vertical">
                
                <TextView
                    android:id="@+id/tv_brand_name"
                    android:layout_width="wrap_content"
                    android:layout_height="wrap_content"
                    android:textStyle="bold"
                    android:textSize="18sp"/>
                
                <TextView
                    android:id="@+id/tv_generic_name"
                    android:layout_width="wrap_content"
                    android:layout_height="wrap_content"
                    android:textSize="14sp"
                    android:textColor="@color/text_secondary"/>
            </LinearLayout>
            
            <ImageView
                android:id="@+id/iv_favorite"
                android:layout_width="24dp"
                android:layout_height="24dp"
                android:src="@android:drawable/star_big_off"/>
        </LinearLayout>
        
        <LinearLayout
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:orientation="horizontal"
            android:layout_marginTop="8dp">
            
            <TextView
                android:id="@+id/tv_branded_price"
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:textColor="@color/price_branded"/>
            
            <TextView
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:text=" vs "
                android:layout_marginHorizontal="4dp"/>
            
            <TextView
                android:id="@+id/tv_generic_price"
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:textColor="@color/price_generic"/>
        </LinearLayout>
        
        <TextView
            android:id="@+id/tv_savings"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:textColor="@color/savings"
            android:textStyle="bold"
            android:layout_marginTop="4dp"/>
        
        <TextView
            android:id="@+id/tv_category"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:textSize="12sp"
            android:textColor="@color/text_secondary"
            android:layout_marginTop="4dp"/>
    </LinearLayout>
</com.google.android.material.card.MaterialCardView>''',
}

for filename, content in layouts.items():
    path = f"{BASE}/layout/{filename}"
    with open(path, 'w') as f:
        f.write(content)
    print(f"✓ Created {filename}")

print("\n✅ All layout files created!")
```

Run: `python3 create_layouts.py`

### Step 4: Create Drawable Resources

Create these icon files in `app/src/main/res/drawable/`:

```xml
<!-- ic_favorite_border.xml -->
<vector xmlns:android="http://schemas.android.com/apk/res/android"
    android:width="24dp" android:height="24dp" android:viewportWidth="24" android:viewportHeight="24">
    <path android:fillColor="@color/text_secondary"
        android:pathData="M16.5,3c-1.74,0 -3.41,0.81 -4.5,2.09C10.91,3.81 9.24,3 7.5,3 4.42,3 2,5.42 2,8.5c0,3.78 3.4,6.86 8.55,11.54L12,21.35l1.45,-1.32C18.6,15.36 22,12.28 22,8.5 22,5.42 19.58,3 16.5,3zM12.1,18.55l-0.1,0.1 -0.1,-0.1C7.14,14.24 4,11.39 4,8.5 4,6.5 5.5,5 7.5,5c1.54,0 3.04,0.99 3.57,2.36h1.87C13.46,5.99 14.96,5 16.5,5c2,0 3.5,1.5 3.5,3.5 0,2.89 -3.14,5.74 -7.9,10.05z"/>
</vector>

<!-- ic_favorite_filled.xml -->
<vector xmlns:android="http://schemas.android.com/apk/res/android"
    android:width="24dp" android:height="24dp" android:viewportWidth="24" android:viewportHeight="24">
    <path android:fillColor="@color/error"
        android:pathData="M12,21.35l-1.45,-1.32C5.4,15.36 2,12.28 2,8.5 2,5.42 4.42,3 7.5,3c1.74,0 3.41,0.81 4.5,2.09C13.09,3.81 14.76,3 16.5,3 19.58,3 22,5.42 22,8.5c0,3.78 -3.4,6.86 -8.55,11.54L12,21.35z"/>
</vector>

<!-- ic_notification.xml -->
<vector xmlns:android="http://schemas.android.com/apk/res/android"
    android:width="24dp" android:height="24dp" android:viewportWidth="24" android:viewportHeight="24">
    <path android:fillColor="@color/notification_icon"
        android:pathData="M12,22c1.1,0 2,-0.9 2,-2h-4c0,1.1 0.89,2 2,2zM18,16v-5c0,-3.07 -1.64,-5.64 -4.5,-6.32V4c0,-0.83 -0.67,-1.5 -1.5,-1.5s-1.5,0.67 -1.5,1.5v0.68C7.63,5.36 6,7.92 6,11v5l-2,2v1h16v-1l-2,-2z"/>
</vector>
```

### Step 5: Create Menu Resource

Create `app/src/main/res/menu/bottom_nav_menu.xml`:

```xml
<?xml version="1.0" encoding="utf-8"?>
<menu xmlns:android="http://schemas.android.com/apk/res/android">
    <item
        android:id="@+id/nav_search"
        android:icon="@android:drawable/ic_menu_search"
        android:title="@string/nav_search"/>
    <item
        android:id="@+id/nav_stores"
        android:icon="@android:drawable/ic_dialog_map"
        android:title="@string/nav_stores"/>
    <item
        android:id="@+id/nav_reminders"
        android:icon="@android:drawable/ic_menu_recent_history"
        android:title="@string/nav_reminders"/>
    <item
        android:id="@+id/nav_favorites"
        android:icon="@android:drawable/star_big_on"
        android:title="@string/nav_favorites"/>
    <item
        android:id="@+id/nav_ai"
        android:icon="@android:drawable/ic_menu_help"
        android:title="AI Chat"/>
</menu>
```

---

## 🔧 Build & Run

```bash
# Sync Gradle
./gradlew build

# Install on device/emulator
./gradlew installDebug

# Or click Run ▶️ in Android Studio
```

---

## ✅ Verification Checklist

- [ ] API keys configured in `app/build.gradle`
- [ ] All layout files created
- [ ] Drawable icons created
- [ ] Menu resource created
- [ ] Gradle sync successful
- [ ] App builds without errors
- [ ] App installs on device/emulator
- [ ] Database seeds with 502 medicines
- [ ] Search works with fuzzy matching
- [ ] Maps shows stores
- [ ] Reminders can be added

---

## 🐛 Common Issues

### Issue: "Unresolved reference: R"
**Solution:** Sync Gradle, Clean Project, Rebuild

### Issue: Maps not showing
**Solution:** Check API key, enable Maps SDK, grant location permission

### Issue: Gemini not responding
**Solution:** Verify API key, check internet connection

### Issue: Database empty
**Solution:** Clear app data, reinstall

---

## 📁 Project Structure Summary

```
Jan-Aushadhi Finder/
├── app/
│   ├── build.gradle (⚠️ ADD API KEYS HERE)
│   └── src/main/
│       ├── AndroidManifest.xml ✅
│       ├── java/com/janaushadhi/finder/
│       │   ├── data/ ✅ (models, DAOs, database, repositories)
│       │   ├── ui/ ✅ (activities, fragments)
│       │   ├── viewmodel/ ✅
│       │   ├── adapter/ ✅
│       │   ├── utils/ ✅
│       │   ├── worker/ ✅
│       │   ├── receiver/ ✅
│       │   └── ai/ ✅
│       └── res/
│           ├── layout/ ⚠️ (CREATE USING SCRIPT)
│           ├── values/ ✅ (strings, colors, themes)
│           ├── drawable/ ⚠️ (CREATE ICONS)
│           └── menu/ ⚠️ (CREATE MENU)
├── build.gradle ✅
├── settings.gradle ✅
└── README.md ✅
```

**Legend:**
- ✅ Complete
- ⚠️ Needs manual creation (see steps above)

---

## 🎯 Next Steps After Setup

1. Test medicine search with typos
2. Test store locator with real location
3. Add sample reminders
4. Test AI chat
5. Customize UI colors/themes
6. Add more medicines to database
7. Implement stock request UI
8. Add unit tests

---

## 📞 Support

Check Logcat for errors:
```bash
adb logcat | grep JanAushadhi
```

---

**You're almost there! Follow the 5 steps above to complete the setup.** 🚀
