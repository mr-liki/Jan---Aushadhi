#!/usr/bin/env python3
"""
API Key Setup Script for Jan-Aushadhi Finder
This script helps configure Google Maps and Gemini API keys
"""

import os
import sys

def setup_api_keys():
    print("🔧 Jan-Aushadhi Finder - API Key Setup")
    print("=" * 50)
    
    # Check if local.properties exists
    local_props_path = "local.properties"
    
    if not os.path.exists(local_props_path):
        print("❌ local.properties file not found!")
        return
    
    print("📋 Current API Key Configuration:")
    print()
    
    # Read current content
    with open(local_props_path, 'r') as f:
        content = f.read()
    
    # Check current keys
    has_maps_key = "MAPS_API_KEY=" in content and "YOUR_GOOGLE_MAPS_API_KEY" not in content
    has_gemini_key = "GEMINI_API_KEY=" in content and "YOUR_GEMINI_API_KEY" not in content
    
    print(f"🗺️  Google Maps API Key: {'✅ Configured' if has_maps_key else '❌ Not configured'}")
    print(f"🤖 Gemini AI API Key: {'✅ Configured' if has_gemini_key else '❌ Not configured'}")
    print()
    
    if has_maps_key and has_gemini_key:
        print("🎉 All API keys are configured!")
        return
    
    print("📝 To configure API keys:")
    print()
    
    if not has_maps_key:
        print("1. Get Google Maps API Key:")
        print("   • Go to: https://console.cloud.google.com/")
        print("   • Create a project or select existing")
        print("   • Enable Maps SDK for Android")
        print("   • Create API key in Credentials")
        print("   • Restrict key to Android apps")
        print()
    
    if not has_gemini_key:
        print("2. Get Gemini API Key:")
        print("   • Go to: https://makersuite.google.com/app/apikey")
        print("   • Sign in with Google account")
        print("   • Click 'Create API Key'")
        print("   • Copy the generated key")
        print()
    
    print("3. Update local.properties file:")
    print(f"   • Open: {local_props_path}")
    print("   • Replace YOUR_GOOGLE_MAPS_API_KEY with your Maps API key")
    print("   • Replace YOUR_GEMINI_API_KEY with your Gemini API key")
    print()
    
    print("4. Rebuild the app:")
    print("   • Run: ./gradlew clean assembleDebug")
    print("   • Or rebuild in Android Studio")
    print()
    
    print("⚠️  Important:")
    print("   • Never commit API keys to version control")
    print("   • local.properties is already in .gitignore")
    print("   • Keep your API keys secure")
    print()

def show_sample_config():
    print("📄 Sample local.properties configuration:")
    print()
    print("# SDK location")
    print("sdk.dir=/path/to/android/sdk")
    print()
    print("# API Keys")
    print("MAPS_API_KEY=AIzaSyC1234567890abcdefghijklmnopqrstuvwx")
    print("GEMINI_API_KEY=AIzaSyD0987654321zyxwvutsrqponmlkjihgfedcba")
    print()

if __name__ == "__main__":
    if len(sys.argv) > 1 and sys.argv[1] == "--sample":
        show_sample_config()
    else:
        setup_api_keys()