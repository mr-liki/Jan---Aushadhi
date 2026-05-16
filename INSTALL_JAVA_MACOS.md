# 🍎 Install Java JDK on macOS

## ❌ **Current Issue:**
```
zsh: command not found: brew
```

## 🔧 **Solution: Install Homebrew First, Then Java**

### Step 1: Install Homebrew
```bash
# Install Homebrew (package manager for macOS)
/bin/bash -c "$(curl -fsSL https://raw.githubusercontent.com/Homebrew/install/HEAD/install.sh)"

# Add Homebrew to your PATH (for Apple Silicon Macs)
echo 'eval "$(/opt/homebrew/bin/brew shellenv)"' >> ~/.zshrc
source ~/.zshrc

# For Intel Macs, use:
# echo 'eval "$(/usr/local/bin/brew shellenv)"' >> ~/.zshrc
```

### Step 2: Install Java JDK
```bash
# Install OpenJDK 17 (recommended for Android development)
brew install openjdk@17

# Set JAVA_HOME environment variable
echo 'export JAVA_HOME=/opt/homebrew/opt/openjdk@17/libexec/openjdk.jdk/Contents/Home' >> ~/.zshrc
echo 'export PATH=$JAVA_HOME/bin:$PATH' >> ~/.zshrc

# Reload shell configuration
source ~/.zshrc
```

### Step 3: Verify Installation
```bash
# Check Java version
java -version
javac -version

# Check JAVA_HOME
echo $JAVA_HOME
```

## 🚀 **Alternative: Direct Java Installation (Without Homebrew)**

If you prefer not to use Homebrew:

### Option 1: Oracle JDK
1. Visit: https://www.oracle.com/java/technologies/downloads/
2. Download JDK 17 for macOS
3. Install the .dmg file
4. Set JAVA_HOME manually

### Option 2: OpenJDK from Adoptium
1. Visit: https://adoptium.net/
2. Download OpenJDK 17 for macOS
3. Install and configure

### Option 3: Using SDKMAN
```bash
# Install SDKMAN
curl -s "https://get.sdkman.io" | bash
source ~/.zshrc

# Install Java
sdk install java 17.0.7-tem
sdk use java 17.0.7-tem
```

## 🎯 **After Java Installation**

Once Java is installed, you can build your Android app:

```bash
# Navigate to your project
cd "/Users/likhithr/Jan-Aushadhi Finder"

# Clean and build
./gradlew clean
./gradlew assembleDebug

# Or build and install on device
./gradlew installDebug
```

## 🔍 **Troubleshooting**

### If `./gradlew` fails:
```bash
# Make gradlew executable
chmod +x gradlew

# Try again
./gradlew clean
```

### If JAVA_HOME is not set:
```bash
# Find Java installation
/usr/libexec/java_home -V

# Set JAVA_HOME to the output path
export JAVA_HOME=$(/usr/libexec/java_home)
```

### Check Android SDK:
```bash
# If you have Android Studio installed
export ANDROID_HOME=$HOME/Library/Android/sdk
export PATH=$PATH:$ANDROID_HOME/tools:$ANDROID_HOME/platform-tools
```

---
**Recommended**: Use Homebrew method as it's the most straightforward for macOS development.