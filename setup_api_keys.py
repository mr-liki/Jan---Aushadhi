#!/usr/bin/env python3
"""
Jan-Aushadhi Finder - API Key Setup Script
This script helps you configure API keys for Google Maps and Gemini AI.
"""

import os
import re

def setup_api_keys():
    print("🚀 Jan-Aushadhi Finder - API Key Setup")
    print("=" * 50)
    
    # Get API keys from user
    print("\n📍 Google Maps API Key Setup")
    print("Get your key from: https://console.cloud.google.com/")
    maps_key = input("Enter your Google Maps API Key: ").strip()
    
    print("\n🤖 Gemini AI API Key Setup")
    print("Get your key from: https://makersuite.google.com/app/apikey")
    gemini_key = input("Enter your Gemini API Key: ").strip()
    
    if not maps_key or not gemini_key:
        print("❌ Error: Both API keys are required!")
        return False
    
    # Option 1: Create local.properties (Recommended)
    print("\n🔧 Setup Method:")
    print("1. local.properties (Recommended - keeps keys secure)")
    print("2. Direct build.gradle replacement (Quick test)")
    
    choice = input("Choose method (1 or 2): ").strip()
    
    if choice == "1":
        return setup_local_properties(maps_key, gemini_key)
    elif choice == "2":
        return setup_build_gradle(maps_key, gemini_key)
    else:
        print("❌ Invalid choice!")
        return False

def setup_local_properties(maps_key, gemini_key):
    """Create local.properties file with API keys"""
    try:
        # Create local.properties
        local_props_path = "app/local.properties"
        os.makedirs(os.path.dirname(local_props_path), exist_ok=True)
        
        with open(local_props_path, 'w') as f:
            f.write(f"MAPS_API_KEY={maps_key}\n")
            f.write(f"GEMINI_API_KEY={gemini_key}\n")
        
        print(f"✅ Created {local_props_path}")
        
        # Update build.gradle to read from local.properties
        build_gradle_path = "app/build.gradle"
        
        with open(build_gradle_path, 'r') as f:
            content = f.read()
        
        # Add local.properties reading code at the top
        local_props_code = '''// Read API keys from local.properties
def localProperties = new Properties()
def localPropertiesFile = rootProject.file('app/local.properties')
if (localPropertiesFile.exists()) {
    localProperties.load(new FileInputStream(localPropertiesFile))
}

'''
        
        if "def localProperties" not in content:
            content = local_props_code + content
        
        # Replace API key configuration
        content = re.sub(
            r'manifestPlaceholders = \[\s*MAPS_API_KEY: "[^"]*"\s*\]',
            '''manifestPlaceholders = [
            MAPS_API_KEY: localProperties.getProperty('MAPS_API_KEY', 'YOUR_GOOGLE_MAPS_API_KEY')
        ]''',
            content
        )
        
        content = re.sub(
            r'buildConfigField "String", "GEMINI_API_KEY", \'"[^"]*"\'',
            '''buildConfigField "String", "GEMINI_API_KEY", "\\"${localProperties.getProperty('GEMINI_API_KEY', 'YOUR_GEMINI_API_KEY')}\\""''',
            content
        )
        
        with open(build_gradle_path, 'w') as f:
            f.write(content)
        
        print(f"✅ Updated {build_gradle_path}")
        
        # Add to .gitignore
        gitignore_path = ".gitignore"
        if os.path.exists(gitignore_path):
            with open(gitignore_path, 'r') as f:
                gitignore_content = f.read()
            
            if "app/local.properties" not in gitignore_content:
                with open(gitignore_path, 'a') as f:
                    f.write("\n# API Keys\napp/local.properties\n")
                print("✅ Added app/local.properties to .gitignore")
        
        print("\n🎉 Setup Complete!")
        print("✅ API keys configured securely")
        print("✅ Keys will not be committed to Git")
        print("\n📱 Next steps:")
        print("1. Open project in Android Studio")
        print("2. Sync Gradle")
        print("3. Run the app")
        
        return True
        
    except Exception as e:
        print(f"❌ Error setting up local.properties: {e}")
        return False

def setup_build_gradle(maps_key, gemini_key):
    """Directly replace API keys in build.gradle"""
    try:
        build_gradle_path = "app/build.gradle"
        
        with open(build_gradle_path, 'r') as f:
            content = f.read()
        
        # Replace API keys
        content = re.sub(
            r'MAPS_API_KEY: "[^"]*"',
            f'MAPS_API_KEY: "{maps_key}"',
            content
        )
        
        content = re.sub(
            r'buildConfigField "String", "GEMINI_API_KEY", \'"[^"]*"\'',
            f'buildConfigField "String", "GEMINI_API_KEY", \'"{gemini_key}"\'',
            content
        )
        
        with open(build_gradle_path, 'w') as f:
            f.write(content)
        
        print(f"✅ Updated {build_gradle_path}")
        print("\n⚠️  WARNING: API keys are now in build.gradle")
        print("⚠️  Do NOT commit this file to Git with real keys!")
        print("\n🎉 Setup Complete!")
        print("\n📱 Next steps:")
        print("1. Open project in Android Studio")
        print("2. Sync Gradle")
        print("3. Run the app")
        print("4. Remember to remove keys before committing!")
        
        return True
        
    except Exception as e:
        print(f"❌ Error updating build.gradle: {e}")
        return False

def main():
    # Check if we're in the right directory
    if not os.path.exists("app/build.gradle"):
        print("❌ Error: Please run this script from the project root directory")
        print("Current directory should contain 'app/build.gradle'")
        return
    
    print("📁 Current directory:", os.getcwd())
    print("✅ Found app/build.gradle")
    
    success = setup_api_keys()
    
    if success:
        print("\n" + "=" * 50)
        print("🎉 API Key Setup Complete!")
        print("Your Jan-Aushadhi Finder app is ready to run!")
        print("=" * 50)
    else:
        print("\n❌ Setup failed. Please try again or configure manually.")

if __name__ == "__main__":
    main()