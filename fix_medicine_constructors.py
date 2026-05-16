#!/usr/bin/env python3
"""
Script to fix Medicine constructor calls in DatabaseSeeder.kt
Converts positional parameters to named parameters to avoid type mismatch errors.
"""

import re

def fix_medicine_constructors(content):
    """
    Fix Medicine constructor calls by converting positional to named parameters.
    
    Medicine("brand", "generic", "salt", price1, price2, "category", "manufacturer", "form", "strength", "uses", "effects")
    becomes:
    Medicine(brandName = "brand", genericName = "generic", saltComposition = "salt", brandedPrice = price1, genericPrice = price2, category = "category", manufacturer = "manufacturer", dosageForm = "form", strength = "strength", uses = "uses", sideEffects = "effects")
    """
    
    # Pattern to match Medicine constructor calls
    pattern = r'Medicine\(\s*"([^"]*)",\s*"([^"]*)",\s*"([^"]*)",\s*([0-9.]+),\s*([0-9.]+),\s*"([^"]*)",\s*"([^"]*)",\s*"([^"]*)",\s*"([^"]*)",\s*"([^"]*)",\s*"([^"]*)"\s*\)'
    
    def replace_match(match):
        brand_name = match.group(1)
        generic_name = match.group(2)
        salt_composition = match.group(3)
        branded_price = match.group(4)
        generic_price = match.group(5)
        category = match.group(6)
        manufacturer = match.group(7)
        dosage_form = match.group(8)
        strength = match.group(9)
        uses = match.group(10)
        side_effects = match.group(11)
        
        return f'Medicine(brandName = "{brand_name}", genericName = "{generic_name}", saltComposition = "{salt_composition}", brandedPrice = {branded_price}, genericPrice = {generic_price}, category = "{category}", manufacturer = "{manufacturer}", dosageForm = "{dosage_form}", strength = "{strength}", uses = "{uses}", sideEffects = "{side_effects}")'
    
    # Replace all Medicine constructor calls
    fixed_content = re.sub(pattern, replace_match, content)
    
    return fixed_content

def main():
    file_path = "app/src/main/java/com/janaushadhi/finder/data/seeder/DatabaseSeeder.kt"
    
    try:
        # Read the file
        with open(file_path, 'r', encoding='utf-8') as f:
            content = f.read()
        
        print(f"Original file size: {len(content)} characters")
        
        # Fix Medicine constructors
        fixed_content = fix_medicine_constructors(content)
        
        print(f"Fixed file size: {len(fixed_content)} characters")
        
        # Write back the fixed content
        with open(file_path, 'w', encoding='utf-8') as f:
            f.write(fixed_content)
        
        print("✅ Successfully fixed Medicine constructor calls!")
        print("All positional parameters converted to named parameters.")
        
    except Exception as e:
        print(f"❌ Error: {e}")

if __name__ == "__main__":
    main()