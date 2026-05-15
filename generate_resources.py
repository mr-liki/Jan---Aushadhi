#!/usr/bin/env python3
"""
Script to generate remaining Android resource files and activities.
Run this to complete the project structure.
"""

import os

BASE_PATH = "/Users/likhithr/Jan-Aushadhi Finder/app/src/main"

# Create directories
dirs = [
    f"{BASE_PATH}/res/layout",
    f"{BASE_PATH}/res/values",
    f"{BASE_PATH}/res/drawable",
    f"{BASE_PATH}/res/mipmap-hdpi",
    f"{BASE_PATH}/res/mipmap-mdpi",
    f"{BASE_PATH}/res/mipmap-xhdpi",
    f"{BASE_PATH}/res/mipmap-xxhdpi",
    f"{BASE_PATH}/res/mipmap-xxxhdpi",
    f"{BASE_PATH}/java/com/janaushadhi/finder/ui/splash",
    f"{BASE_PATH}/java/com/janaushadhi/finder/ui/main",
    f"{BASE_PATH}/java/com/janaushadhi/finder/ui/medicine",
    f"{BASE_PATH}/java/com/janaushadhi/finder/ui/ai",
    f"{BASE_PATH}/java/com/janaushadhi/finder/adapter",
]

for d in dirs:
    os.makedirs(d, exist_ok=True)
    print(f"✓ Created {d}")

print("\n✅ All directories created!")
print("\nNext steps:")
print("1. Add your Google Maps API key to app/build.gradle")
print("2. Add your Gemini API key to app/build.gradle")
print("3. Run the remaining file generation scripts")
print("4. Sync Gradle and build the project")
