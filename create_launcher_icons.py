#!/usr/bin/env python3
"""
Simple script to create basic launcher icons for Android app.
This creates simple colored squares as temporary launcher icons.
"""

import os
from PIL import Image, ImageDraw

def create_icon(size, output_path, color="#0288D1"):
    """Create a simple colored square icon with a medical cross."""
    # Create image with background color
    img = Image.new('RGB', (size, size), color)
    draw = ImageDraw.Draw(img)
    
    # Draw white medical cross
    cross_width = size // 8
    cross_length = size // 2
    center = size // 2
    
    # Horizontal bar
    draw.rectangle([
        center - cross_length//2, center - cross_width//2,
        center + cross_length//2, center + cross_width//2
    ], fill='white')
    
    # Vertical bar
    draw.rectangle([
        center - cross_width//2, center - cross_length//2,
        center + cross_width//2, center + cross_length//2
    ], fill='white')
    
    # Save the image
    img.save(output_path, 'PNG')
    print(f"Created icon: {output_path}")

def create_round_icon(size, output_path, color="#0288D1"):
    """Create a simple round colored icon with a medical cross."""
    # Create transparent image
    img = Image.new('RGBA', (size, size), (0, 0, 0, 0))
    draw = ImageDraw.Draw(img)
    
    # Draw colored circle
    draw.ellipse([0, 0, size-1, size-1], fill=color)
    
    # Draw white medical cross
    cross_width = size // 8
    cross_length = size // 2
    center = size // 2
    
    # Horizontal bar
    draw.rectangle([
        center - cross_length//2, center - cross_width//2,
        center + cross_length//2, center + cross_width//2
    ], fill='white')
    
    # Vertical bar
    draw.rectangle([
        center - cross_width//2, center - cross_length//2,
        center + cross_width//2, center + cross_length//2
    ], fill='white')
    
    # Save the image
    img.save(output_path, 'PNG')
    print(f"Created round icon: {output_path}")

def main():
    # Icon sizes for different densities
    sizes = {
        'mipmap-mdpi': 48,
        'mipmap-hdpi': 72,
        'mipmap-xhdpi': 96,
        'mipmap-xxhdpi': 144,
        'mipmap-xxxhdpi': 192
    }
    
    base_path = "app/src/main/res"
    
    for density, size in sizes.items():
        density_path = os.path.join(base_path, density)
        
        # Create regular launcher icon
        icon_path = os.path.join(density_path, "ic_launcher.png")
        create_icon(size, icon_path)
        
        # Create round launcher icon
        round_icon_path = os.path.join(density_path, "ic_launcher_round.png")
        create_round_icon(size, round_icon_path)

if __name__ == "__main__":
    try:
        main()
        print("\n✅ Launcher icons created successfully!")
        print("Note: These are basic temporary icons. Consider creating professional icons later.")
    except ImportError:
        print("❌ PIL (Pillow) not found. Installing...")
        os.system("pip3 install Pillow")
        main()
    except Exception as e:
        print(f"❌ Error creating icons: {e}")
        print("\nAlternative: You can manually create PNG files or use Android Studio's Image Asset Studio.")