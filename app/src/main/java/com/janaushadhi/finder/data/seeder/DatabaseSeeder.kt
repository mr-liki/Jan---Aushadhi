package com.janaushadhi.finder.data.seeder

import com.janaushadhi.finder.data.dao.MedicineDao
import com.janaushadhi.finder.data.dao.StoreDao
import com.janaushadhi.finder.data.model.Medicine
import com.janaushadhi.finder.data.model.Store

/**
 * Seeds the Room database with 500+ medicine records and 30+ Jan-Aushadhi store records.
 * Each seed function checks if data already exists before inserting to avoid duplicates.
 */
object DatabaseSeeder {

    // ─────────────────────────────────────────────────────────────────────────
    // MEDICINES
    // ─────────────────────────────────────────────────────────────────────────

    suspend fun seedMedicines(dao: MedicineDao) {
        if (dao.getMedicineCount() > 0) return

        val analgesics = listOf(
            Medicine("Crocin 500mg", "Paracetamol", "Paracetamol 500mg", 30.0, 5.0, "Analgesic/Antipyretic", "GSK", "Tablet", "500mg", "Fever, mild to moderate pain", "Nausea, rash (rare)"),
            Medicine("Crocin 650mg", "Paracetamol", "Paracetamol 650mg", 42.0, 7.0, "Analgesic/Antipyretic", "GSK", "Tablet", "650mg", "Fever, pain relief", "Liver damage in overdose"),
            Medicine("Dolo 650", "Paracetamol", "Paracetamol 650mg", 30.0, 6.0, "Analgesic/Antipyretic", "Micro Labs", "Tablet", "650mg", "Fever, headache, body ache", "Nausea, allergic reactions"),
            Medicine("Calpol 500mg", "Paracetamol", "Paracetamol 500mg", 28.0, 5.0, "Analgesic/Antipyretic", "GSK", "Tablet", "500mg", "Pain, fever", "Rare skin reactions"),
            Medicine("Combiflam", "Ibuprofen + Paracetamol", "Ibuprofen 400mg + Paracetamol 325mg", 55.0, 12.0, "Analgesic/Antipyretic", "Sanofi", "Tablet", "400mg+325mg", "Pain, fever, inflammation", "GI upset, dizziness"),
            Medicine("Brufen 400mg", "Ibuprofen", "Ibuprofen 400mg", 35.0, 8.0, "Analgesic/Antipyretic", "Abbott", "Tablet", "400mg", "Pain, inflammation, fever", "GI irritation, headache"),
            Medicine("Brufen 600mg", "Ibuprofen", "Ibuprofen 600mg", 50.0, 10.0, "Analgesic/Antipyretic", "Abbott", "Tablet", "600mg", "Moderate pain, arthritis", "GI upset, renal effects"),
            Medicine("Voveran 50mg", "Diclofenac", "Diclofenac Sodium 50mg", 45.0, 9.0, "Analgesic/Antipyretic", "Novartis", "Tablet", "50mg", "Pain, inflammation", "GI upset, liver effects"),
            Medicine("Voveran SR 100", "Diclofenac", "Diclofenac Sodium SR 100mg", 75.0, 14.0, "Analgesic/Antipyretic", "Novartis", "Tablet", "100mg SR", "Chronic pain, arthritis", "GI bleeding, renal effects"),
            Medicine("Nimulid 100mg", "Nimesulide", "Nimesulide 100mg", 40.0, 8.0, "Analgesic/Antipyretic", "Panacea Biotec", "Tablet", "100mg", "Pain, fever, inflammation", "Liver toxicity, GI upset"),
            Medicine("Zerodol P", "Aceclofenac + Paracetamol", "Aceclofenac 100mg + Paracetamol 325mg", 65.0, 13.0, "Analgesic/Antipyretic", "IPCA", "Tablet", "100mg+325mg", "Pain, inflammation", "GI upset, dizziness"),
            Medicine("Zerodol SP", "Aceclofenac + Serratiopeptidase", "Aceclofenac 100mg + Serratiopeptidase 15mg", 90.0, 18.0, "Analgesic/Antipyretic", "IPCA", "Tablet", "100mg+15mg", "Pain, swelling", "GI upset, nausea"),
            Medicine("Hifenac P", "Aceclofenac + Paracetamol", "Aceclofenac 100mg + Paracetamol 325mg", 68.0, 13.0, "Analgesic/Antipyretic", "Intas", "Tablet", "100mg+325mg", "Pain, fever", "GI irritation"),
            Medicine("Nise 100mg", "Nimesulide", "Nimesulide 100mg", 38.0, 7.0, "Analgesic/Antipyretic", "Dr. Reddy's", "Tablet", "100mg", "Pain, fever", "Liver toxicity"),
            Medicine("Ultracet", "Tramadol + Paracetamol", "Tramadol 37.5mg + Paracetamol 325mg", 95.0, 20.0, "Analgesic/Antipyretic", "Janssen", "Tablet", "37.5mg+325mg", "Moderate to severe pain", "Dizziness, nausea, dependence"),
            Medicine("Tramadol 50mg", "Tramadol", "Tramadol HCl 50mg", 60.0, 12.0, "Analgesic/Antipyretic", "Generic", "Capsule", "50mg", "Moderate to severe pain", "Dizziness, constipation, dependence"),
            Medicine("Diclofenac Gel", "Diclofenac", "Diclofenac Diethylamine 1.16%", 85.0, 20.0, "Analgesic/Antipyretic", "Generic", "Gel", "1.16%", "Topical pain relief", "Skin irritation"),
            Medicine("Ibuprofen 200mg", "Ibuprofen", "Ibuprofen 200mg", 25.0, 5.0, "Analgesic/Antipyretic", "Generic", "Tablet", "200mg", "Mild pain, fever", "GI upset"),
            Medicine("Naproxen 250mg", "Naproxen", "Naproxen 250mg", 55.0, 11.0, "Analgesic/Antipyretic", "Generic", "Tablet", "250mg", "Pain, inflammation", "GI upset, headache"),
            Medicine("Naproxen 500mg", "Naproxen", "Naproxen 500mg", 85.0, 17.0, "Analgesic/Antipyretic", "Generic", "Tablet", "500mg", "Arthritis, pain", "GI bleeding, renal effects"),
            Medicine("Meftal Spas", "Mefenamic Acid + Dicyclomine", "Mefenamic Acid 250mg + Dicyclomine 10mg", 75.0, 15.0, "Analgesic/Antipyretic", "Blue Cross", "Tablet", "250mg+10mg", "Dysmenorrhea, abdominal cramps", "Dizziness, dry mouth"),
            Medicine("Meftal P", "Mefenamic Acid + Paracetamol", "Mefenamic Acid 250mg + Paracetamol 325mg", 65.0, 13.0, "Analgesic/Antipyretic", "Blue Cross", "Tablet", "250mg+325mg", "Pain, fever", "GI upset"),
            Medicine("Aceclofenac 100mg", "Aceclofenac", "Aceclofenac 100mg", 45.0, 9.0, "Analgesic/Antipyretic", "Generic", "Tablet", "100mg", "Pain, inflammation", "GI upset"),
            Medicine("Ketorolac 10mg", "Ketorolac", "Ketorolac Tromethamine 10mg", 70.0, 14.0, "Analgesic/Antipyretic", "Generic", "Tablet", "10mg", "Short-term pain relief", "GI bleeding, renal effects"),
            Medicine("Piroxicam 20mg", "Piroxicam", "Piroxicam 20mg", 50.0, 10.0, "Analgesic/Antipyretic", "Generic", "Capsule", "20mg", "Arthritis, pain", "GI upset, photosensitivity"),
            Medicine("Etoricoxib 60mg", "Etoricoxib", "Etoricoxib 60mg", 120.0, 25.0, "Analgesic/Antipyretic", "Generic", "Tablet", "60mg", "Arthritis, acute pain", "Hypertension, GI effects"),
            Medicine("Etoricoxib 90mg", "Etoricoxib", "Etoricoxib 90mg", 150.0, 30.0, "Analgesic/Antipyretic", "Generic", "Tablet", "90mg", "Arthritis, gout", "Cardiovascular risk"),
            Medicine("Etoricoxib 120mg", "Etoricoxib", "Etoricoxib 120mg", 180.0, 36.0, "Analgesic/Antipyretic", "Generic", "Tablet", "120mg", "Acute gout, pain", "Cardiovascular risk")
        )

        val antibiotics = listOf(
            Medicine("Augmentin 625mg", "Amoxicillin + Clavulanate", "Amoxicillin 500mg + Clavulanic Acid 125mg", 180.0, 38.0, "Antibiotic", "GSK", "Tablet", "625mg", "Bacterial infections", "Diarrhea, rash, nausea"),
            Medicine("Augmentin 1g", "Amoxicillin + Clavulanate", "Amoxicillin 875mg + Clavulanic Acid 125mg", 280.0, 58.0, "Antibiotic", "GSK", "Tablet", "1g", "Severe bacterial infections", "GI upset, allergic reactions"),
            Medicine("Amoxyclav 625", "Amoxicillin + Clavulanate", "Amoxicillin 500mg + Clavulanic Acid 125mg", 175.0, 36.0, "Antibiotic", "Cipla", "Tablet", "625mg", "Respiratory, urinary infections", "Diarrhea, nausea"),
            Medicine("Azithral 500mg", "Azithromycin", "Azithromycin 500mg", 95.0, 20.0, "Antibiotic", "Alembic", "Tablet", "500mg", "Respiratory, skin infections", "Nausea, diarrhea, QT prolongation"),
            Medicine("Zithromax 250mg", "Azithromycin", "Azithromycin 250mg", 65.0, 13.0, "Antibiotic", "Pfizer", "Tablet", "250mg", "Community-acquired pneumonia", "GI upset, headache"),
            Medicine("Ciprobid 500mg", "Ciprofloxacin", "Ciprofloxacin 500mg", 85.0, 17.0, "Antibiotic", "Cadila", "Tablet", "500mg", "UTI, respiratory infections", "Nausea, tendinopathy"),
            Medicine("Ciplox 500mg", "Ciprofloxacin", "Ciprofloxacin 500mg", 80.0, 16.0, "Antibiotic", "Cipla", "Tablet", "500mg", "Bacterial infections", "GI upset, photosensitivity"),
            Medicine("Ciplox 250mg", "Ciprofloxacin", "Ciprofloxacin 250mg", 55.0, 11.0, "Antibiotic", "Cipla", "Tablet", "250mg", "UTI, mild infections", "Nausea, dizziness"),
            Medicine("Taxim O 200mg", "Cefixime", "Cefixime 200mg", 120.0, 25.0, "Antibiotic", "Alkem", "Tablet", "200mg", "Respiratory, urinary infections", "Diarrhea, nausea"),
            Medicine("Cefixime 200mg", "Cefixime", "Cefixime 200mg", 110.0, 22.0, "Antibiotic", "Generic", "Tablet", "200mg", "Bacterial infections", "GI upset"),
            Medicine("Monocef 1g", "Ceftriaxone", "Ceftriaxone 1g", 180.0, 38.0, "Antibiotic", "Aristo", "Injection", "1g", "Severe infections, meningitis", "Allergic reactions, diarrhea"),
            Medicine("Ceftriaxone 500mg", "Ceftriaxone", "Ceftriaxone 500mg", 120.0, 25.0, "Antibiotic", "Generic", "Injection", "500mg", "Moderate bacterial infections", "Injection site reactions"),
            Medicine("Clavam 625mg", "Amoxicillin + Clavulanate", "Amoxicillin 500mg + Clavulanic Acid 125mg", 170.0, 35.0, "Antibiotic", "Alkem", "Tablet", "625mg", "Bacterial infections", "Diarrhea, rash"),
            Medicine("Mox 500mg", "Amoxicillin", "Amoxicillin 500mg", 65.0, 13.0, "Antibiotic", "Ranbaxy", "Capsule", "500mg", "Respiratory, ear infections", "Rash, diarrhea"),
            Medicine("Amoxicillin 250mg", "Amoxicillin", "Amoxicillin 250mg", 40.0, 8.0, "Antibiotic", "Generic", "Capsule", "250mg", "Mild bacterial infections", "Allergic reactions"),
            Medicine("Doxycycline 100mg", "Doxycycline", "Doxycycline Hyclate 100mg", 55.0, 11.0, "Antibiotic", "Generic", "Capsule", "100mg", "Acne, respiratory infections, malaria prophylaxis", "Photosensitivity, GI upset"),
            Medicine("Metronidazole 400mg", "Metronidazole", "Metronidazole 400mg", 30.0, 6.0, "Antibiotic", "Generic", "Tablet", "400mg", "Anaerobic infections, amoebiasis", "Metallic taste, nausea"),
            Medicine("Flagyl 400mg", "Metronidazole", "Metronidazole 400mg", 35.0, 7.0, "Antibiotic", "Abbott", "Tablet", "400mg", "Bacterial vaginosis, amoebiasis", "Nausea, headache"),
            Medicine("Tinidazole 500mg", "Tinidazole", "Tinidazole 500mg", 45.0, 9.0, "Antibiotic", "Generic", "Tablet", "500mg", "Amoebiasis, giardiasis", "Metallic taste, nausea"),
            Medicine("Norfloxacin 400mg", "Norfloxacin", "Norfloxacin 400mg", 50.0, 10.0, "Antibiotic", "Generic", "Tablet", "400mg", "UTI, gastroenteritis", "Nausea, dizziness"),
            Medicine("Ofloxacin 200mg", "Ofloxacin", "Ofloxacin 200mg", 55.0, 11.0, "Antibiotic", "Generic", "Tablet", "200mg", "UTI, respiratory infections", "GI upset, photosensitivity"),
            Medicine("Levofloxacin 500mg", "Levofloxacin", "Levofloxacin 500mg", 95.0, 19.0, "Antibiotic", "Generic", "Tablet", "500mg", "Pneumonia, UTI, sinusitis", "Tendinopathy, QT prolongation"),
            Medicine("Levofloxacin 750mg", "Levofloxacin", "Levofloxacin 750mg", 130.0, 26.0, "Antibiotic", "Generic", "Tablet", "750mg", "Severe respiratory infections", "Tendon rupture, nausea"),
            Medicine("Clarithromycin 250mg", "Clarithromycin", "Clarithromycin 250mg", 120.0, 24.0, "Antibiotic", "Generic", "Tablet", "250mg", "Respiratory infections, H. pylori", "GI upset, metallic taste"),
            Medicine("Clarithromycin 500mg", "Clarithromycin", "Clarithromycin 500mg", 180.0, 36.0, "Antibiotic", "Generic", "Tablet", "500mg", "Community-acquired pneumonia", "Nausea, diarrhea"),
            Medicine("Erythromycin 250mg", "Erythromycin", "Erythromycin 250mg", 60.0, 12.0, "Antibiotic", "Generic", "Tablet", "250mg", "Respiratory, skin infections", "GI upset, hepatotoxicity"),
            Medicine("Clindamycin 150mg", "Clindamycin", "Clindamycin 150mg", 90.0, 18.0, "Antibiotic", "Generic", "Capsule", "150mg", "Anaerobic infections, acne", "Pseudomembranous colitis"),
            Medicine("Clindamycin 300mg", "Clindamycin", "Clindamycin 300mg", 150.0, 30.0, "Antibiotic", "Generic", "Capsule", "300mg", "Severe anaerobic infections", "Diarrhea, colitis"),
            Medicine("Vancomycin 500mg", "Vancomycin", "Vancomycin HCl 500mg", 350.0, 70.0, "Antibiotic", "Generic", "Injection", "500mg", "MRSA, severe gram-positive infections", "Red man syndrome, nephrotoxicity"),
            Medicine("Linezolid 600mg", "Linezolid", "Linezolid 600mg", 450.0, 90.0, "Antibiotic", "Generic", "Tablet", "600mg", "MRSA, VRE infections", "Myelosuppression, serotonin syndrome"),
            Medicine("Meropenem 1g", "Meropenem", "Meropenem 1g", 500.0, 100.0, "Antibiotic", "Generic", "Injection", "1g", "Severe hospital-acquired infections", "Seizures, allergic reactions"),
            Medicine("Piperacillin-Tazobactam 4.5g", "Piperacillin + Tazobactam", "Piperacillin 4g + Tazobactam 0.5g", 600.0, 120.0, "Antibiotic", "Generic", "Injection", "4.5g", "Severe mixed infections", "Allergic reactions, diarrhea"),
            Medicine("Cefuroxime 250mg", "Cefuroxime", "Cefuroxime Axetil 250mg", 130.0, 26.0, "Antibiotic", "Generic", "Tablet", "250mg", "Respiratory, urinary infections", "GI upset, rash"),
            Medicine("Cefpodoxime 200mg", "Cefpodoxime", "Cefpodoxime Proxetil 200mg", 140.0, 28.0, "Antibiotic", "Generic", "Tablet", "200mg", "Respiratory, skin infections", "Diarrhea, nausea"),
            Medicine("Imipenem-Cilastatin 500mg", "Imipenem + Cilastatin", "Imipenem 500mg + Cilastatin 500mg", 700.0, 140.0, "Antibiotic", "Generic", "Injection", "500mg", "Severe polymicrobial infections", "Seizures, nausea")
        )

        val antacids = listOf(
            Medicine("Pantop 40mg", "Pantoprazole", "Pantoprazole 40mg", 85.0, 17.0, "Antacid/GI", "Aristo", "Tablet", "40mg", "GERD, peptic ulcer", "Headache, diarrhea"),
            Medicine("Pantop 20mg", "Pantoprazole", "Pantoprazole 20mg", 60.0, 12.0, "Antacid/GI", "Aristo", "Tablet", "20mg", "Acid reflux, gastritis", "Nausea, flatulence"),
            Medicine("Pantoprazole 40mg", "Pantoprazole", "Pantoprazole 40mg", 80.0, 16.0, "Antacid/GI", "Generic", "Tablet", "40mg", "GERD, Zollinger-Ellison syndrome", "Headache, diarrhea"),
            Medicine("Omez 20mg", "Omeprazole", "Omeprazole 20mg", 65.0, 13.0, "Antacid/GI", "Dr. Reddy's", "Capsule", "20mg", "Peptic ulcer, GERD", "Headache, nausea"),
            Medicine("Omeprazole 20mg", "Omeprazole", "Omeprazole 20mg", 55.0, 11.0, "Antacid/GI", "Generic", "Capsule", "20mg", "Acid-related disorders", "Diarrhea, abdominal pain"),
            Medicine("Razo 20mg", "Rabeprazole", "Rabeprazole 20mg", 90.0, 18.0, "Antacid/GI", "Sun Pharma", "Tablet", "20mg", "GERD, peptic ulcer", "Headache, diarrhea"),
            Medicine("Rabeprazole 20mg", "Rabeprazole", "Rabeprazole 20mg", 85.0, 17.0, "Antacid/GI", "Generic", "Tablet", "20mg", "Acid reflux, H. pylori eradication", "Nausea, flatulence"),
            Medicine("Nexpro 40mg", "Esomeprazole", "Esomeprazole 40mg", 110.0, 22.0, "Antacid/GI", "Torrent", "Tablet", "40mg", "GERD, erosive esophagitis", "Headache, diarrhea"),
            Medicine("Esomeprazole 20mg", "Esomeprazole", "Esomeprazole 20mg", 80.0, 16.0, "Antacid/GI", "Generic", "Tablet", "20mg", "Acid-related disorders", "Nausea, abdominal pain"),
            Medicine("Rantac 150mg", "Ranitidine", "Ranitidine 150mg", 35.0, 7.0, "Antacid/GI", "J.B. Chemicals", "Tablet", "150mg", "Peptic ulcer, GERD", "Headache, constipation"),
            Medicine("Ranitidine 150mg", "Ranitidine", "Ranitidine 150mg", 30.0, 6.0, "Antacid/GI", "Generic", "Tablet", "150mg", "Acid reflux, gastritis", "Headache, dizziness"),
            Medicine("Gelusil MPS", "Aluminium + Magnesium + Simethicone", "Aluminium Hydroxide + Magnesium Hydroxide + Simethicone", 95.0, 20.0, "Antacid/GI", "Pfizer", "Suspension", "Combination", "Acidity, gas, heartburn", "Constipation, diarrhea"),
            Medicine("Digene Gel", "Aluminium + Magnesium", "Aluminium Hydroxide + Magnesium Hydroxide", 85.0, 18.0, "Antacid/GI", "Abbott", "Gel", "Combination", "Acidity, indigestion", "Constipation"),
            Medicine("Cremaffin Plus", "Liquid Paraffin + Milk of Magnesia", "Liquid Paraffin + Milk of Magnesia", 120.0, 25.0, "Antacid/GI", "Abbott", "Suspension", "Combination", "Constipation", "Diarrhea, abdominal cramps"),
            Medicine("Dulcolax 5mg", "Bisacodyl", "Bisacodyl 5mg", 45.0, 9.0, "Antacid/GI", "Boehringer Ingelheim", "Tablet", "5mg", "Constipation", "Abdominal cramps, diarrhea"),
            Medicine("Bisacodyl 5mg", "Bisacodyl", "Bisacodyl 5mg", 35.0, 7.0, "Antacid/GI", "Generic", "Tablet", "5mg", "Constipation, bowel preparation", "Abdominal cramps"),
            Medicine("Emeset 4mg", "Ondansetron", "Ondansetron 4mg", 55.0, 11.0, "Antacid/GI", "Cipla", "Tablet", "4mg", "Nausea, vomiting", "Headache, constipation"),
            Medicine("Ondansetron 8mg", "Ondansetron", "Ondansetron 8mg", 80.0, 16.0, "Antacid/GI", "Generic", "Tablet", "8mg", "Chemotherapy-induced nausea", "Headache, QT prolongation"),
            Medicine("Domstal 10mg", "Domperidone", "Domperidone 10mg", 45.0, 9.0, "Antacid/GI", "Torrent", "Tablet", "10mg", "Nausea, vomiting, gastroparesis", "Dry mouth, headache"),
            Medicine("Domperidone 10mg", "Domperidone", "Domperidone 10mg", 40.0, 8.0, "Antacid/GI", "Generic", "Tablet", "10mg", "Nausea, bloating", "Headache, galactorrhea"),
            Medicine("Metoclopramide 10mg", "Metoclopramide", "Metoclopramide 10mg", 30.0, 6.0, "Antacid/GI", "Generic", "Tablet", "10mg", "Nausea, gastroparesis", "Extrapyramidal effects"),
            Medicine("Sucralfate 1g", "Sucralfate", "Sucralfate 1g", 60.0, 12.0, "Antacid/GI", "Generic", "Tablet", "1g", "Peptic ulcer, GERD", "Constipation, dry mouth"),
            Medicine("Famotidine 20mg", "Famotidine", "Famotidine 20mg", 40.0, 8.0, "Antacid/GI", "Generic", "Tablet", "20mg", "Peptic ulcer, GERD", "Headache, dizziness"),
            Medicine("Lansoprazole 30mg", "Lansoprazole", "Lansoprazole 30mg", 90.0, 18.0, "Antacid/GI", "Generic", "Capsule", "30mg", "GERD, peptic ulcer", "Diarrhea, headache"),
            Medicine("Dexlansoprazole 30mg", "Dexlansoprazole", "Dexlansoprazole 30mg", 150.0, 30.0, "Antacid/GI", "Generic", "Capsule", "30mg", "Erosive esophagitis, GERD", "Diarrhea, nausea"),
            Medicine("Mosapride 5mg", "Mosapride", "Mosapride Citrate 5mg", 65.0, 13.0, "Antacid/GI", "Generic", "Tablet", "5mg", "Gastroparesis, GERD", "Diarrhea, abdominal pain"),
            Medicine("Itopride 50mg", "Itopride", "Itopride HCl 50mg", 75.0, 15.0, "Antacid/GI", "Generic", "Tablet", "50mg", "Functional dyspepsia", "Diarrhea, headache")
        )

        val antidiabetics = listOf(
            Medicine("Glycomet 500mg", "Metformin", "Metformin HCl 500mg", 35.0, 7.0, "Antidiabetic", "USV", "Tablet", "500mg", "Type 2 diabetes", "GI upset, lactic acidosis (rare)"),
            Medicine("Glycomet 850mg", "Metformin", "Metformin HCl 850mg", 50.0, 10.0, "Antidiabetic", "USV", "Tablet", "850mg", "Type 2 diabetes", "Nausea, diarrhea"),
            Medicine("Glycomet 1g", "Metformin", "Metformin HCl 1000mg", 65.0, 13.0, "Antidiabetic", "USV", "Tablet", "1000mg", "Type 2 diabetes", "GI upset"),
            Medicine("Metformin 500mg", "Metformin", "Metformin HCl 500mg", 30.0, 6.0, "Antidiabetic", "Generic", "Tablet", "500mg", "Type 2 diabetes, PCOS", "Nausea, diarrhea"),
            Medicine("Januvia 100mg", "Sitagliptin", "Sitagliptin 100mg", 280.0, 56.0, "Antidiabetic", "MSD", "Tablet", "100mg", "Type 2 diabetes", "Nasopharyngitis, headache"),
            Medicine("Sitagliptin 50mg", "Sitagliptin", "Sitagliptin 50mg", 180.0, 36.0, "Antidiabetic", "Generic", "Tablet", "50mg", "Type 2 diabetes", "Upper respiratory infection"),
            Medicine("Galvus 50mg", "Vildagliptin", "Vildagliptin 50mg", 220.0, 44.0, "Antidiabetic", "Novartis", "Tablet", "50mg", "Type 2 diabetes", "Nasopharyngitis, dizziness"),
            Medicine("Vildagliptin 50mg", "Vildagliptin", "Vildagliptin 50mg", 200.0, 40.0, "Antidiabetic", "Generic", "Tablet", "50mg", "Type 2 diabetes", "Headache, nausea"),
            Medicine("Trajenta 5mg", "Linagliptin", "Linagliptin 5mg", 260.0, 52.0, "Antidiabetic", "Boehringer Ingelheim", "Tablet", "5mg", "Type 2 diabetes", "Nasopharyngitis, cough"),
            Medicine("Jardiance 10mg", "Empagliflozin", "Empagliflozin 10mg", 350.0, 70.0, "Antidiabetic", "Boehringer Ingelheim", "Tablet", "10mg", "Type 2 diabetes, heart failure", "UTI, genital infections"),
            Medicine("Jardiance 25mg", "Empagliflozin", "Empagliflozin 25mg", 420.0, 84.0, "Antidiabetic", "Boehringer Ingelheim", "Tablet", "25mg", "Type 2 diabetes, CKD", "UTI, DKA (rare)"),
            Medicine("Forxiga 10mg", "Dapagliflozin", "Dapagliflozin 10mg", 320.0, 64.0, "Antidiabetic", "AstraZeneca", "Tablet", "10mg", "Type 2 diabetes, heart failure", "UTI, genital mycotic infections"),
            Medicine("Dapagliflozin 10mg", "Dapagliflozin", "Dapagliflozin 10mg", 300.0, 60.0, "Antidiabetic", "Generic", "Tablet", "10mg", "Type 2 diabetes", "Polyuria, UTI"),
            Medicine("Glimepiride 1mg", "Glimepiride", "Glimepiride 1mg", 45.0, 9.0, "Antidiabetic", "Generic", "Tablet", "1mg", "Type 2 diabetes", "Hypoglycemia, weight gain"),
            Medicine("Glimepiride 2mg", "Glimepiride", "Glimepiride 2mg", 65.0, 13.0, "Antidiabetic", "Generic", "Tablet", "2mg", "Type 2 diabetes", "Hypoglycemia, nausea"),
            Medicine("Amaryl 1mg", "Glimepiride", "Glimepiride 1mg", 55.0, 11.0, "Antidiabetic", "Sanofi", "Tablet", "1mg", "Type 2 diabetes", "Hypoglycemia, dizziness"),
            Medicine("Gliclazide 80mg", "Gliclazide", "Gliclazide 80mg", 55.0, 11.0, "Antidiabetic", "Generic", "Tablet", "80mg", "Type 2 diabetes", "Hypoglycemia, GI upset"),
            Medicine("Diamicron MR 30mg", "Gliclazide", "Gliclazide MR 30mg", 90.0, 18.0, "Antidiabetic", "Servier", "Tablet", "30mg MR", "Type 2 diabetes", "Hypoglycemia"),
            Medicine("Pioglitazone 15mg", "Pioglitazone", "Pioglitazone 15mg", 75.0, 15.0, "Antidiabetic", "Generic", "Tablet", "15mg", "Type 2 diabetes, insulin resistance", "Weight gain, edema, bladder cancer risk"),
            Medicine("Pioglitazone 30mg", "Pioglitazone", "Pioglitazone 30mg", 110.0, 22.0, "Antidiabetic", "Generic", "Tablet", "30mg", "Type 2 diabetes", "Fluid retention, fractures"),
            Medicine("Insulin Glargine 100IU/mL", "Insulin Glargine", "Insulin Glargine 100 IU/mL", 850.0, 170.0, "Antidiabetic", "Sanofi", "Injection", "100 IU/mL", "Type 1 & 2 diabetes", "Hypoglycemia, injection site reactions"),
            Medicine("Insulin Regular 40IU/mL", "Insulin Regular", "Regular Human Insulin 40 IU/mL", 120.0, 24.0, "Antidiabetic", "Novo Nordisk", "Injection", "40 IU/mL", "Diabetes management", "Hypoglycemia, lipodystrophy"),
            Medicine("Voglibose 0.2mg", "Voglibose", "Voglibose 0.2mg", 60.0, 12.0, "Antidiabetic", "Generic", "Tablet", "0.2mg", "Type 2 diabetes, postprandial hyperglycemia", "Flatulence, diarrhea"),
            Medicine("Voglibose 0.3mg", "Voglibose", "Voglibose 0.3mg", 80.0, 16.0, "Antidiabetic", "Generic", "Tablet", "0.3mg", "Type 2 diabetes", "Abdominal discomfort"),
            Medicine("Teneligliptin 20mg", "Teneligliptin", "Teneligliptin 20mg", 150.0, 30.0, "Antidiabetic", "Generic", "Tablet", "20mg", "Type 2 diabetes", "Nasopharyngitis, constipation"),
            Medicine("Alogliptin 25mg", "Alogliptin", "Alogliptin 25mg", 200.0, 40.0, "Antidiabetic", "Generic", "Tablet", "25mg", "Type 2 diabetes", "Nasopharyngitis, headache"),
            Medicine("Saxagliptin 5mg", "Saxagliptin", "Saxagliptin 5mg", 230.0, 46.0, "Antidiabetic", "AstraZeneca", "Tablet", "5mg", "Type 2 diabetes", "UTI, headache"),
            Medicine("Repaglinide 0.5mg", "Repaglinide", "Repaglinide 0.5mg", 70.0, 14.0, "Antidiabetic", "Generic", "Tablet", "0.5mg", "Type 2 diabetes", "Hypoglycemia, weight gain"),
            Medicine("Nateglinide 120mg", "Nateglinide", "Nateglinide 120mg", 90.0, 18.0, "Antidiabetic", "Generic", "Tablet", "120mg", "Type 2 diabetes", "Hypoglycemia, GI upset")
        )

        val antihypertensives = listOf(
            Medicine("Telma 40mg", "Telmisartan", "Telmisartan 40mg", 120.0, 24.0, "Antihypertensive", "Glenmark", "Tablet", "40mg", "Hypertension, heart failure", "Dizziness, hyperkalemia"),
            Medicine("Telma 80mg", "Telmisartan", "Telmisartan 80mg", 160.0, 32.0, "Antihypertensive", "Glenmark", "Tablet", "80mg", "Hypertension, diabetic nephropathy", "Dizziness, back pain"),
            Medicine("Telmisartan 40mg", "Telmisartan", "Telmisartan 40mg", 110.0, 22.0, "Antihypertensive", "Generic", "Tablet", "40mg", "Hypertension", "Hyperkalemia, dizziness"),
            Medicine("Losartan 50mg", "Losartan", "Losartan Potassium 50mg", 90.0, 18.0, "Antihypertensive", "Generic", "Tablet", "50mg", "Hypertension, diabetic nephropathy", "Dizziness, hyperkalemia"),
            Medicine("Losar 50mg", "Losartan", "Losartan Potassium 50mg", 85.0, 17.0, "Antihypertensive", "Cipla", "Tablet", "50mg", "Hypertension", "Dizziness, fatigue"),
            Medicine("Olmesartan 20mg", "Olmesartan", "Olmesartan Medoxomil 20mg", 130.0, 26.0, "Antihypertensive", "Generic", "Tablet", "20mg", "Hypertension", "Dizziness, diarrhea"),
            Medicine("Benitec 40mg", "Olmesartan", "Olmesartan Medoxomil 40mg", 180.0, 36.0, "Antihypertensive", "Daiichi Sankyo", "Tablet", "40mg", "Hypertension", "Dizziness, hyperkalemia"),
            Medicine("Amlodipine 5mg", "Amlodipine", "Amlodipine Besylate 5mg", 55.0, 11.0, "Antihypertensive", "Generic", "Tablet", "5mg", "Hypertension, angina", "Peripheral edema, flushing"),
            Medicine("Amlodipine 10mg", "Amlodipine", "Amlodipine Besylate 10mg", 80.0, 16.0, "Antihypertensive", "Generic", "Tablet", "10mg", "Hypertension, angina", "Edema, headache"),
            Medicine("Amlokind 5mg", "Amlodipine", "Amlodipine Besylate 5mg", 60.0, 12.0, "Antihypertensive", "Mankind", "Tablet", "5mg", "Hypertension", "Peripheral edema"),
            Medicine("Atenolol 25mg", "Atenolol", "Atenolol 25mg", 35.0, 7.0, "Antihypertensive", "Generic", "Tablet", "25mg", "Hypertension, angina", "Bradycardia, fatigue"),
            Medicine("Atenolol 50mg", "Atenolol", "Atenolol 50mg", 50.0, 10.0, "Antihypertensive", "Generic", "Tablet", "50mg", "Hypertension, post-MI", "Bradycardia, cold extremities"),
            Medicine("Tenormin 50mg", "Atenolol", "Atenolol 50mg", 55.0, 11.0, "Antihypertensive", "AstraZeneca", "Tablet", "50mg", "Hypertension, angina", "Fatigue, bradycardia"),
            Medicine("Metoprolol 25mg", "Metoprolol", "Metoprolol Succinate 25mg", 65.0, 13.0, "Antihypertensive", "Generic", "Tablet", "25mg", "Hypertension, heart failure", "Bradycardia, fatigue"),
            Medicine("Metoprolol 50mg", "Metoprolol", "Metoprolol Succinate 50mg", 90.0, 18.0, "Antihypertensive", "Generic", "Tablet", "50mg", "Hypertension, angina", "Dizziness, bradycardia"),
            Medicine("Nebivolol 5mg", "Nebivolol", "Nebivolol 5mg", 120.0, 24.0, "Antihypertensive", "Generic", "Tablet", "5mg", "Hypertension, heart failure", "Bradycardia, headache"),
            Medicine("Nebicard 5mg", "Nebivolol", "Nebivolol 5mg", 130.0, 26.0, "Antihypertensive", "Torrent", "Tablet", "5mg", "Hypertension", "Fatigue, dizziness"),
            Medicine("Ramipril 2.5mg", "Ramipril", "Ramipril 2.5mg", 65.0, 13.0, "Antihypertensive", "Generic", "Capsule", "2.5mg", "Hypertension, heart failure, post-MI", "Dry cough, hyperkalemia"),
            Medicine("Ramipril 5mg", "Ramipril", "Ramipril 5mg", 90.0, 18.0, "Antihypertensive", "Generic", "Capsule", "5mg", "Hypertension, diabetic nephropathy", "Dry cough, dizziness"),
            Medicine("Cardace 5mg", "Ramipril", "Ramipril 5mg", 95.0, 19.0, "Antihypertensive", "Sanofi", "Capsule", "5mg", "Hypertension, heart failure", "Dry cough, angioedema"),
            Medicine("Enalapril 5mg", "Enalapril", "Enalapril Maleate 5mg", 55.0, 11.0, "Antihypertensive", "Generic", "Tablet", "5mg", "Hypertension, heart failure", "Dry cough, dizziness"),
            Medicine("Lisinopril 5mg", "Lisinopril", "Lisinopril 5mg", 60.0, 12.0, "Antihypertensive", "Generic", "Tablet", "5mg", "Hypertension, heart failure", "Dry cough, hyperkalemia"),
            Medicine("Lisinopril 10mg", "Lisinopril", "Lisinopril 10mg", 85.0, 17.0, "Antihypertensive", "Generic", "Tablet", "10mg", "Hypertension, diabetic nephropathy", "Dry cough, angioedema"),
            Medicine("Perindopril 4mg", "Perindopril", "Perindopril Erbumine 4mg", 110.0, 22.0, "Antihypertensive", "Generic", "Tablet", "4mg", "Hypertension, stable coronary artery disease", "Dry cough, dizziness"),
            Medicine("Candesartan 8mg", "Candesartan", "Candesartan Cilexetil 8mg", 120.0, 24.0, "Antihypertensive", "Generic", "Tablet", "8mg", "Hypertension, heart failure", "Dizziness, hyperkalemia"),
            Medicine("Valsartan 80mg", "Valsartan", "Valsartan 80mg", 110.0, 22.0, "Antihypertensive", "Generic", "Tablet", "80mg", "Hypertension, heart failure", "Dizziness, hyperkalemia"),
            Medicine("Irbesartan 150mg", "Irbesartan", "Irbesartan 150mg", 130.0, 26.0, "Antihypertensive", "Generic", "Tablet", "150mg", "Hypertension, diabetic nephropathy", "Dizziness, fatigue"),
            Medicine("Hydrochlorothiazide 25mg", "Hydrochlorothiazide", "Hydrochlorothiazide 25mg", 30.0, 6.0, "Antihypertensive", "Generic", "Tablet", "25mg", "Hypertension, edema", "Hypokalemia, hyperuricemia"),
            Medicine("Furosemide 40mg", "Furosemide", "Furosemide 40mg", 25.0, 5.0, "Antihypertensive", "Generic", "Tablet", "40mg", "Edema, hypertension, heart failure", "Hypokalemia, dehydration"),
            Medicine("Lasix 40mg", "Furosemide", "Furosemide 40mg", 30.0, 6.0, "Antihypertensive", "Sanofi", "Tablet", "40mg", "Edema, heart failure", "Electrolyte imbalance"),
            Medicine("Spironolactone 25mg", "Spironolactone", "Spironolactone 25mg", 55.0, 11.0, "Antihypertensive", "Generic", "Tablet", "25mg", "Heart failure, hypertension, hyperaldosteronism", "Hyperkalemia, gynecomastia"),
            Medicine("Aldactone 25mg", "Spironolactone", "Spironolactone 25mg", 60.0, 12.0, "Antihypertensive", "Pfizer", "Tablet", "25mg", "Edema, heart failure", "Gynecomastia, hyperkalemia"),
            Medicine("Clonidine 0.1mg", "Clonidine", "Clonidine HCl 0.1mg", 40.0, 8.0, "Antihypertensive", "Generic", "Tablet", "0.1mg", "Hypertension, ADHD", "Dry mouth, sedation"),
            Medicine("Prazosin 1mg", "Prazosin", "Prazosin HCl 1mg", 45.0, 9.0, "Antihypertensive", "Generic", "Tablet", "1mg", "Hypertension, BPH", "First-dose hypotension, dizziness"),
            Medicine("Doxazosin 2mg", "Doxazosin", "Doxazosin Mesylate 2mg", 70.0, 14.0, "Antihypertensive", "Generic", "Tablet", "2mg", "Hypertension, BPH", "Dizziness, orthostatic hypotension")
        )

        val statinsCardiac = listOf(
            Medicine("Atorvastatin 10mg", "Atorvastatin", "Atorvastatin Calcium 10mg", 85.0, 17.0, "Statin/Cardiac", "Generic", "Tablet", "10mg", "Hypercholesterolemia, CVD prevention", "Myopathy, liver enzyme elevation"),
            Medicine("Atorvastatin 20mg", "Atorvastatin", "Atorvastatin Calcium 20mg", 120.0, 24.0, "Statin/Cardiac", "Generic", "Tablet", "20mg", "High cholesterol, atherosclerosis", "Muscle pain, rhabdomyolysis (rare)"),
            Medicine("Atorvastatin 40mg", "Atorvastatin", "Atorvastatin Calcium 40mg", 160.0, 32.0, "Statin/Cardiac", "Generic", "Tablet", "40mg", "High-risk CVD patients", "Myopathy, hepatotoxicity"),
            Medicine("Lipitor 10mg", "Atorvastatin", "Atorvastatin Calcium 10mg", 95.0, 19.0, "Statin/Cardiac", "Pfizer", "Tablet", "10mg", "Hypercholesterolemia", "Muscle aches, GI upset"),
            Medicine("Rosuvastatin 10mg", "Rosuvastatin", "Rosuvastatin Calcium 10mg", 130.0, 26.0, "Statin/Cardiac", "Generic", "Tablet", "10mg", "Hypercholesterolemia, CVD prevention", "Myopathy, proteinuria"),
            Medicine("Rosuvastatin 20mg", "Rosuvastatin", "Rosuvastatin Calcium 20mg", 180.0, 36.0, "Statin/Cardiac", "Generic", "Tablet", "20mg", "High cholesterol", "Muscle pain, liver effects"),
            Medicine("Crestor 10mg", "Rosuvastatin", "Rosuvastatin Calcium 10mg", 145.0, 29.0, "Statin/Cardiac", "AstraZeneca", "Tablet", "10mg", "Hypercholesterolemia", "Myopathy, headache"),
            Medicine("Simvastatin 20mg", "Simvastatin", "Simvastatin 20mg", 90.0, 18.0, "Statin/Cardiac", "Generic", "Tablet", "20mg", "Hypercholesterolemia", "Myopathy, liver effects"),
            Medicine("Clopidogrel 75mg", "Clopidogrel", "Clopidogrel Bisulfate 75mg", 95.0, 19.0, "Statin/Cardiac", "Generic", "Tablet", "75mg", "ACS, stroke prevention, post-stent", "Bleeding, bruising"),
            Medicine("Plavix 75mg", "Clopidogrel", "Clopidogrel Bisulfate 75mg", 110.0, 22.0, "Statin/Cardiac", "Sanofi", "Tablet", "75mg", "Antiplatelet therapy", "Bleeding, GI upset"),
            Medicine("Aspirin 75mg", "Aspirin", "Acetylsalicylic Acid 75mg", 20.0, 4.0, "Statin/Cardiac", "Generic", "Tablet", "75mg", "CVD prevention, antiplatelet", "GI bleeding, tinnitus"),
            Medicine("Ecosprin 75mg", "Aspirin", "Acetylsalicylic Acid 75mg", 25.0, 5.0, "Statin/Cardiac", "USV", "Tablet", "75mg", "Antiplatelet, CVD prevention", "GI irritation, bleeding"),
            Medicine("Ecosprin 150mg", "Aspirin", "Acetylsalicylic Acid 150mg", 35.0, 7.0, "Statin/Cardiac", "USV", "Tablet", "150mg", "Acute MI, stroke", "GI bleeding"),
            Medicine("Digoxin 0.25mg", "Digoxin", "Digoxin 0.25mg", 40.0, 8.0, "Statin/Cardiac", "Generic", "Tablet", "0.25mg", "Heart failure, atrial fibrillation", "Digoxin toxicity, arrhythmias"),
            Medicine("Amiodarone 100mg", "Amiodarone", "Amiodarone HCl 100mg", 120.0, 24.0, "Statin/Cardiac", "Generic", "Tablet", "100mg", "Ventricular arrhythmias, AF", "Thyroid dysfunction, pulmonary toxicity"),
            Medicine("Amiodarone 200mg", "Amiodarone", "Amiodarone HCl 200mg", 180.0, 36.0, "Statin/Cardiac", "Generic", "Tablet", "200mg", "Life-threatening arrhythmias", "Photosensitivity, corneal deposits"),
            Medicine("Bisoprolol 2.5mg", "Bisoprolol", "Bisoprolol Fumarate 2.5mg", 70.0, 14.0, "Statin/Cardiac", "Generic", "Tablet", "2.5mg", "Heart failure, hypertension", "Bradycardia, fatigue"),
            Medicine("Bisoprolol 5mg", "Bisoprolol", "Bisoprolol Fumarate 5mg", 95.0, 19.0, "Statin/Cardiac", "Generic", "Tablet", "5mg", "Heart failure, angina", "Bradycardia, cold extremities"),
            Medicine("Carvedilol 3.125mg", "Carvedilol", "Carvedilol 3.125mg", 80.0, 16.0, "Statin/Cardiac", "Generic", "Tablet", "3.125mg", "Heart failure, hypertension", "Dizziness, bradycardia"),
            Medicine("Carvedilol 6.25mg", "Carvedilol", "Carvedilol 6.25mg", 110.0, 22.0, "Statin/Cardiac", "Generic", "Tablet", "6.25mg", "Heart failure, post-MI", "Hypotension, fatigue"),
            Medicine("Ivabradine 5mg", "Ivabradine", "Ivabradine 5mg", 200.0, 40.0, "Statin/Cardiac", "Generic", "Tablet", "5mg", "Chronic heart failure, angina", "Bradycardia, visual disturbances"),
            Medicine("Ranolazine 500mg", "Ranolazine", "Ranolazine 500mg", 250.0, 50.0, "Statin/Cardiac", "Generic", "Tablet", "500mg SR", "Chronic angina", "Dizziness, nausea, QT prolongation"),
            Medicine("Nitroglycerin 0.5mg", "Nitroglycerin", "Nitroglycerin 0.5mg", 45.0, 9.0, "Statin/Cardiac", "Generic", "Tablet", "0.5mg SL", "Acute angina", "Headache, hypotension"),
            Medicine("Isosorbide Mononitrate 20mg", "Isosorbide Mononitrate", "Isosorbide Mononitrate 20mg", 65.0, 13.0, "Statin/Cardiac", "Generic", "Tablet", "20mg", "Angina prophylaxis", "Headache, hypotension"),
            Medicine("Warfarin 2mg", "Warfarin", "Warfarin Sodium 2mg", 50.0, 10.0, "Statin/Cardiac", "Generic", "Tablet", "2mg", "DVT, PE, AF anticoagulation", "Bleeding, bruising"),
            Medicine("Rivaroxaban 10mg", "Rivaroxaban", "Rivaroxaban 10mg", 350.0, 70.0, "Statin/Cardiac", "Generic", "Tablet", "10mg", "DVT prevention, AF", "Bleeding, nausea"),
            Medicine("Rivaroxaban 20mg", "Rivaroxaban", "Rivaroxaban 20mg", 450.0, 90.0, "Statin/Cardiac", "Generic", "Tablet", "20mg", "DVT treatment, stroke prevention in AF", "Bleeding"),
            Medicine("Apixaban 5mg", "Apixaban", "Apixaban 5mg", 400.0, 80.0, "Statin/Cardiac", "Generic", "Tablet", "5mg", "AF, DVT, PE", "Bleeding, bruising"),
            Medicine("Dabigatran 110mg", "Dabigatran", "Dabigatran Etexilate 110mg", 380.0, 76.0, "Statin/Cardiac", "Generic", "Capsule", "110mg", "AF, DVT prevention", "Bleeding, GI upset"),
            Medicine("Enoxaparin 40mg", "Enoxaparin", "Enoxaparin Sodium 40mg", 180.0, 36.0, "Statin/Cardiac", "Generic", "Injection", "40mg/0.4mL", "DVT prophylaxis, ACS", "Bleeding, thrombocytopenia"),
            Medicine("Fenofibrate 145mg", "Fenofibrate", "Fenofibrate 145mg", 130.0, 26.0, "Statin/Cardiac", "Generic", "Tablet", "145mg", "Hypertriglyceridemia", "GI upset, myopathy")
        )

        val respiratory = listOf(
            Medicine("Asthalin 100mcg", "Salbutamol", "Salbutamol 100mcg/dose", 95.0, 19.0, "Respiratory", "Cipla", "Inhaler", "100mcg", "Acute bronchospasm, asthma", "Tremor, tachycardia, hypokalemia"),
            Medicine("Salbutamol 2mg", "Salbutamol", "Salbutamol Sulphate 2mg", 30.0, 6.0, "Respiratory", "Generic", "Tablet", "2mg", "Bronchospasm, asthma", "Tremor, palpitations"),
            Medicine("Seroflo 250 Inhaler", "Salmeterol + Fluticasone", "Salmeterol 25mcg + Fluticasone 250mcg", 450.0, 90.0, "Respiratory", "Cipla", "Inhaler", "25+250mcg", "Asthma, COPD maintenance", "Oral candidiasis, hoarseness"),
            Medicine("Foracort 200 Inhaler", "Formoterol + Budesonide", "Formoterol 6mcg + Budesonide 200mcg", 420.0, 84.0, "Respiratory", "Cipla", "Inhaler", "6+200mcg", "Asthma, COPD", "Oral candidiasis, tremor"),
            Medicine("Budecort 200 Inhaler", "Budesonide", "Budesonide 200mcg/dose", 280.0, 56.0, "Respiratory", "Cipla", "Inhaler", "200mcg", "Asthma prophylaxis", "Oral candidiasis, hoarseness"),
            Medicine("Budesonide 0.5mg Nebulizer", "Budesonide", "Budesonide 0.5mg/2mL", 180.0, 36.0, "Respiratory", "Generic", "Nebulizer Solution", "0.5mg/2mL", "Asthma, croup", "Oral candidiasis"),
            Medicine("Montek 10mg", "Montelukast", "Montelukast Sodium 10mg", 120.0, 24.0, "Respiratory", "Sun Pharma", "Tablet", "10mg", "Asthma, allergic rhinitis", "Headache, neuropsychiatric effects"),
            Medicine("Montelukast 5mg", "Montelukast", "Montelukast Sodium 5mg", 90.0, 18.0, "Respiratory", "Generic", "Chewable Tablet", "5mg", "Asthma in children", "Headache, abdominal pain"),
            Medicine("Theophylline 100mg", "Theophylline", "Theophylline 100mg", 40.0, 8.0, "Respiratory", "Generic", "Tablet", "100mg", "Asthma, COPD", "Nausea, tachycardia, seizures"),
            Medicine("Deriphyllin Retard 150", "Theophylline + Etofylline", "Theophylline 50mg + Etofylline 100mg", 55.0, 11.0, "Respiratory", "Franco-Indian", "Tablet", "150mg", "Bronchospasm, asthma", "Nausea, palpitations"),
            Medicine("Tiotropium 18mcg", "Tiotropium", "Tiotropium Bromide 18mcg", 350.0, 70.0, "Respiratory", "Generic", "Capsule (Inhaler)", "18mcg", "COPD maintenance", "Dry mouth, urinary retention"),
            Medicine("Ipratropium 20mcg Inhaler", "Ipratropium", "Ipratropium Bromide 20mcg/dose", 220.0, 44.0, "Respiratory", "Generic", "Inhaler", "20mcg", "COPD, acute bronchospasm", "Dry mouth, urinary retention"),
            Medicine("Beclomethasone 100mcg", "Beclomethasone", "Beclomethasone Dipropionate 100mcg", 250.0, 50.0, "Respiratory", "Generic", "Inhaler", "100mcg", "Asthma prophylaxis", "Oral candidiasis, hoarseness"),
            Medicine("Fluticasone 125mcg Inhaler", "Fluticasone", "Fluticasone Propionate 125mcg", 320.0, 64.0, "Respiratory", "Generic", "Inhaler", "125mcg", "Asthma, COPD", "Oral candidiasis, adrenal suppression"),
            Medicine("Ciclesonide 160mcg", "Ciclesonide", "Ciclesonide 160mcg", 380.0, 76.0, "Respiratory", "Generic", "Inhaler", "160mcg", "Asthma", "Headache, nasopharyngitis"),
            Medicine("Roflumilast 500mcg", "Roflumilast", "Roflumilast 500mcg", 280.0, 56.0, "Respiratory", "Generic", "Tablet", "500mcg", "Severe COPD", "Diarrhea, nausea, weight loss"),
            Medicine("Acetylcysteine 600mg", "Acetylcysteine", "N-Acetylcysteine 600mg", 85.0, 17.0, "Respiratory", "Generic", "Effervescent Tablet", "600mg", "Mucolytic, paracetamol overdose", "Nausea, vomiting"),
            Medicine("Ambroxol 30mg", "Ambroxol", "Ambroxol HCl 30mg", 45.0, 9.0, "Respiratory", "Generic", "Tablet", "30mg", "Mucolytic, respiratory infections", "Nausea, GI upset"),
            Medicine("Bromhexine 8mg", "Bromhexine", "Bromhexine HCl 8mg", 35.0, 7.0, "Respiratory", "Generic", "Tablet", "8mg", "Mucolytic, productive cough", "Nausea, dizziness"),
            Medicine("Dextromethorphan 15mg", "Dextromethorphan", "Dextromethorphan HBr 15mg", 40.0, 8.0, "Respiratory", "Generic", "Tablet", "15mg", "Dry cough suppression", "Dizziness, drowsiness"),
            Medicine("Guaifenesin 200mg", "Guaifenesin", "Guaifenesin 200mg", 35.0, 7.0, "Respiratory", "Generic", "Tablet", "200mg", "Expectorant, productive cough", "Nausea, GI upset"),
            Medicine("Formoterol 12mcg", "Formoterol", "Formoterol Fumarate 12mcg", 280.0, 56.0, "Respiratory", "Generic", "Inhaler", "12mcg", "COPD, asthma maintenance", "Tremor, tachycardia"),
            Medicine("Salmeterol 50mcg", "Salmeterol", "Salmeterol Xinafoate 50mcg", 300.0, 60.0, "Respiratory", "Generic", "Inhaler", "50mcg", "Asthma, COPD maintenance", "Tremor, palpitations")
        )

        val vitamins = listOf(
            Medicine("Shelcal 500mg", "Calcium Carbonate + Vitamin D3", "Calcium Carbonate 1250mg + Vitamin D3 250IU", 120.0, 24.0, "Vitamin/Supplement", "Elder", "Tablet", "500mg Ca", "Calcium deficiency, osteoporosis", "Constipation, hypercalcemia"),
            Medicine("Calcium Carbonate 500mg", "Calcium Carbonate", "Calcium Carbonate 1250mg (500mg elemental Ca)", 60.0, 12.0, "Vitamin/Supplement", "Generic", "Tablet", "500mg", "Calcium deficiency, osteoporosis", "Constipation, bloating"),
            Medicine("Calcirol 60000 IU", "Cholecalciferol", "Cholecalciferol 60000 IU", 85.0, 17.0, "Vitamin/Supplement", "Cadila", "Sachet", "60000 IU", "Vitamin D deficiency", "Hypercalcemia in overdose"),
            Medicine("Vitamin D3 1000 IU", "Cholecalciferol", "Cholecalciferol 1000 IU", 45.0, 9.0, "Vitamin/Supplement", "Generic", "Tablet", "1000 IU", "Vitamin D deficiency, bone health", "Hypercalcemia (rare)"),
            Medicine("Becosules Capsules", "B-Complex + Vitamin C", "Vitamin B-Complex + Vitamin C 150mg", 95.0, 19.0, "Vitamin/Supplement", "Pfizer", "Capsule", "Combination", "B-vitamin deficiency, general health", "Nausea (rare)"),
            Medicine("B-Complex Tablet", "Vitamin B-Complex", "Thiamine + Riboflavin + Niacin + B6 + B12", 40.0, 8.0, "Vitamin/Supplement", "Generic", "Tablet", "Combination", "B-vitamin deficiency", "Nausea, urine discoloration"),
            Medicine("Neurobion Forte", "B1+B6+B12", "Thiamine 10mg + Pyridoxine 3mg + Cyanocobalamin 15mcg", 85.0, 17.0, "Vitamin/Supplement", "Merck", "Tablet", "Combination", "Peripheral neuropathy, B-vitamin deficiency", "Nausea (rare)"),
            Medicine("Methylcobalamin 500mcg", "Methylcobalamin", "Methylcobalamin 500mcg", 65.0, 13.0, "Vitamin/Supplement", "Generic", "Tablet", "500mcg", "Vitamin B12 deficiency, neuropathy", "Nausea, diarrhea (rare)"),
            Medicine("Methylcobalamin 1500mcg", "Methylcobalamin", "Methylcobalamin 1500mcg", 95.0, 19.0, "Vitamin/Supplement", "Generic", "Tablet", "1500mcg", "Peripheral neuropathy, B12 deficiency", "Rare adverse effects"),
            Medicine("Folvite 5mg", "Folic Acid", "Folic Acid 5mg", 25.0, 5.0, "Vitamin/Supplement", "Pfizer", "Tablet", "5mg", "Megaloblastic anemia, pregnancy", "Nausea (rare)"),
            Medicine("Folic Acid 1mg", "Folic Acid", "Folic Acid 1mg", 15.0, 3.0, "Vitamin/Supplement", "Generic", "Tablet", "1mg", "Folate deficiency, pregnancy", "Rare adverse effects"),
            Medicine("Ferrous Sulfate 200mg", "Ferrous Sulfate", "Ferrous Sulfate 200mg (65mg elemental Fe)", 30.0, 6.0, "Vitamin/Supplement", "Generic", "Tablet", "200mg", "Iron deficiency anemia", "Constipation, dark stools, nausea"),
            Medicine("Livogen Tablet", "Ferrous Fumarate + Folic Acid", "Ferrous Fumarate 150mg + Folic Acid 0.5mg", 75.0, 15.0, "Vitamin/Supplement", "Merck", "Tablet", "Combination", "Iron deficiency anemia in pregnancy", "Constipation, nausea"),
            Medicine("Zinc 50mg", "Zinc Sulfate", "Zinc Sulfate 220mg (50mg elemental Zinc)", 40.0, 8.0, "Vitamin/Supplement", "Generic", "Tablet", "50mg", "Zinc deficiency, wound healing", "Nausea, metallic taste"),
            Medicine("Zincovit Tablet", "Zinc + Vitamins", "Zinc + Multivitamins + Minerals", 110.0, 22.0, "Vitamin/Supplement", "Apex", "Tablet", "Combination", "Nutritional deficiency, immunity", "Nausea (rare)"),
            Medicine("Vitamin C 500mg", "Ascorbic Acid", "Ascorbic Acid 500mg", 35.0, 7.0, "Vitamin/Supplement", "Generic", "Tablet", "500mg", "Vitamin C deficiency, immunity", "GI upset in high doses"),
            Medicine("Vitamin C 1000mg", "Ascorbic Acid", "Ascorbic Acid 1000mg", 55.0, 11.0, "Vitamin/Supplement", "Generic", "Effervescent Tablet", "1000mg", "Antioxidant, immunity boost", "Kidney stones in high doses"),
            Medicine("Vitamin E 400 IU", "Tocopherol", "d-Alpha Tocopherol 400 IU", 65.0, 13.0, "Vitamin/Supplement", "Generic", "Capsule", "400 IU", "Vitamin E deficiency, antioxidant", "Bleeding risk in high doses"),
            Medicine("Vitamin A 50000 IU", "Retinol", "Retinol 50000 IU", 45.0, 9.0, "Vitamin/Supplement", "Generic", "Capsule", "50000 IU", "Vitamin A deficiency, night blindness", "Teratogenicity, hepatotoxicity in overdose"),
            Medicine("Vitamin K1 10mg", "Phytomenadione", "Phytomenadione 10mg", 55.0, 11.0, "Vitamin/Supplement", "Generic", "Tablet", "10mg", "Vitamin K deficiency, anticoagulant reversal", "Rare adverse effects"),
            Medicine("Biotin 5mg", "Biotin", "Biotin 5mg", 75.0, 15.0, "Vitamin/Supplement", "Generic", "Tablet", "5mg", "Biotin deficiency, hair and nail health", "Rare adverse effects"),
            Medicine("Magnesium 250mg", "Magnesium Oxide", "Magnesium Oxide 250mg", 50.0, 10.0, "Vitamin/Supplement", "Generic", "Tablet", "250mg", "Magnesium deficiency, muscle cramps", "Diarrhea, nausea"),
            Medicine("Potassium Chloride 600mg", "Potassium Chloride", "Potassium Chloride 600mg", 45.0, 9.0, "Vitamin/Supplement", "Generic", "Tablet", "600mg", "Hypokalemia", "GI irritation, hyperkalemia"),
            Medicine("Selenium 200mcg", "Selenium", "Selenium 200mcg", 60.0, 12.0, "Vitamin/Supplement", "Generic", "Tablet", "200mcg", "Selenium deficiency, antioxidant", "Selenosis in overdose"),
            Medicine("Chromium Picolinate 200mcg", "Chromium", "Chromium Picolinate 200mcg", 55.0, 11.0, "Vitamin/Supplement", "Generic", "Tablet", "200mcg", "Chromium deficiency, glucose metabolism", "Rare adverse effects"),
            Medicine("Coenzyme Q10 100mg", "Ubiquinone", "Coenzyme Q10 100mg", 180.0, 36.0, "Vitamin/Supplement", "Generic", "Capsule", "100mg", "Heart failure, statin-induced myopathy", "GI upset, insomnia")
        )

        val thyroid = listOf(
            Medicine("Eltroxin 50mcg", "Levothyroxine", "Levothyroxine Sodium 50mcg", 55.0, 11.0, "Thyroid", "GSK", "Tablet", "50mcg", "Hypothyroidism", "Palpitations, weight loss in overdose"),
            Medicine("Eltroxin 100mcg", "Levothyroxine", "Levothyroxine Sodium 100mcg", 75.0, 15.0, "Thyroid", "GSK", "Tablet", "100mcg", "Hypothyroidism, goiter", "Tachycardia, insomnia in overdose"),
            Medicine("Thyronorm 25mcg", "Levothyroxine", "Levothyroxine Sodium 25mcg", 45.0, 9.0, "Thyroid", "Abbott", "Tablet", "25mcg", "Hypothyroidism", "Palpitations, tremor in overdose"),
            Medicine("Thyronorm 50mcg", "Levothyroxine", "Levothyroxine Sodium 50mcg", 60.0, 12.0, "Thyroid", "Abbott", "Tablet", "50mcg", "Hypothyroidism", "Tachycardia, weight loss in overdose"),
            Medicine("Thyronorm 75mcg", "Levothyroxine", "Levothyroxine Sodium 75mcg", 70.0, 14.0, "Thyroid", "Abbott", "Tablet", "75mcg", "Hypothyroidism", "Palpitations, insomnia"),
            Medicine("Thyronorm 100mcg", "Levothyroxine", "Levothyroxine Sodium 100mcg", 80.0, 16.0, "Thyroid", "Abbott", "Tablet", "100mcg", "Hypothyroidism, thyroid cancer", "Tachycardia, bone loss"),
            Medicine("Levothyroxine 50mcg", "Levothyroxine", "Levothyroxine Sodium 50mcg", 50.0, 10.0, "Thyroid", "Generic", "Tablet", "50mcg", "Hypothyroidism", "Palpitations, tremor"),
            Medicine("Carbimazole 5mg", "Carbimazole", "Carbimazole 5mg", 45.0, 9.0, "Thyroid", "Generic", "Tablet", "5mg", "Hyperthyroidism, Graves' disease", "Agranulocytosis, rash"),
            Medicine("Neomercazole 5mg", "Carbimazole", "Carbimazole 5mg", 50.0, 10.0, "Thyroid", "Roche", "Tablet", "5mg", "Hyperthyroidism", "Agranulocytosis, hepatotoxicity"),
            Medicine("Propylthiouracil 50mg", "Propylthiouracil", "Propylthiouracil 50mg", 55.0, 11.0, "Thyroid", "Generic", "Tablet", "50mg", "Hyperthyroidism, thyroid storm", "Agranulocytosis, hepatotoxicity"),
            Medicine("Methimazole 10mg", "Methimazole", "Methimazole 10mg", 60.0, 12.0, "Thyroid", "Generic", "Tablet", "10mg", "Hyperthyroidism", "Agranulocytosis, rash"),
            Medicine("Liothyronine 25mcg", "Liothyronine", "Liothyronine Sodium 25mcg", 90.0, 18.0, "Thyroid", "Generic", "Tablet", "25mcg", "Hypothyroidism, myxedema coma", "Palpitations, angina")
        )

        val neuroPsych = listOf(
            Medicine("Nexito 10mg", "Escitalopram", "Escitalopram Oxalate 10mg", 120.0, 24.0, "Neurological/Psychiatric", "Sun Pharma", "Tablet", "10mg", "Depression, anxiety disorders", "Nausea, insomnia, sexual dysfunction"),
            Medicine("Escitalopram 5mg", "Escitalopram", "Escitalopram Oxalate 5mg", 80.0, 16.0, "Neurological/Psychiatric", "Generic", "Tablet", "5mg", "Depression, GAD", "Nausea, headache"),
            Medicine("Escitalopram 20mg", "Escitalopram", "Escitalopram Oxalate 20mg", 160.0, 32.0, "Neurological/Psychiatric", "Generic", "Tablet", "20mg", "Major depression, OCD", "Sexual dysfunction, QT prolongation"),
            Medicine("Fluoxetine 20mg", "Fluoxetine", "Fluoxetine HCl 20mg", 85.0, 17.0, "Neurological/Psychiatric", "Generic", "Capsule", "20mg", "Depression, OCD, bulimia", "Insomnia, nausea, sexual dysfunction"),
            Medicine("Prozac 20mg", "Fluoxetine", "Fluoxetine HCl 20mg", 95.0, 19.0, "Neurological/Psychiatric", "Eli Lilly", "Capsule", "20mg", "Depression, panic disorder", "Agitation, insomnia"),
            Medicine("Sertraline 50mg", "Sertraline", "Sertraline HCl 50mg", 95.0, 19.0, "Neurological/Psychiatric", "Generic", "Tablet", "50mg", "Depression, PTSD, OCD", "Nausea, diarrhea, sexual dysfunction"),
            Medicine("Sertraline 100mg", "Sertraline", "Sertraline HCl 100mg", 140.0, 28.0, "Neurological/Psychiatric", "Generic", "Tablet", "100mg", "Major depression, social anxiety", "GI upset, insomnia"),
            Medicine("Paroxetine 20mg", "Paroxetine", "Paroxetine HCl 20mg", 100.0, 20.0, "Neurological/Psychiatric", "Generic", "Tablet", "20mg", "Depression, panic disorder, PTSD", "Weight gain, sexual dysfunction"),
            Medicine("Venlafaxine 75mg", "Venlafaxine", "Venlafaxine HCl 75mg", 130.0, 26.0, "Neurological/Psychiatric", "Generic", "Capsule", "75mg XR", "Depression, anxiety, fibromyalgia", "Nausea, hypertension, sweating"),
            Medicine("Duloxetine 30mg", "Duloxetine", "Duloxetine HCl 30mg", 150.0, 30.0, "Neurological/Psychiatric", "Generic", "Capsule", "30mg", "Depression, diabetic neuropathy, fibromyalgia", "Nausea, dry mouth, insomnia"),
            Medicine("Duloxetine 60mg", "Duloxetine", "Duloxetine HCl 60mg", 200.0, 40.0, "Neurological/Psychiatric", "Generic", "Capsule", "60mg", "Major depression, GAD", "Nausea, sweating, sexual dysfunction"),
            Medicine("Amitriptyline 10mg", "Amitriptyline", "Amitriptyline HCl 10mg", 45.0, 9.0, "Neurological/Psychiatric", "Generic", "Tablet", "10mg", "Depression, neuropathic pain, migraine prophylaxis", "Dry mouth, sedation, weight gain"),
            Medicine("Amitriptyline 25mg", "Amitriptyline", "Amitriptyline HCl 25mg", 60.0, 12.0, "Neurological/Psychiatric", "Generic", "Tablet", "25mg", "Depression, chronic pain", "Anticholinergic effects, cardiac arrhythmias"),
            Medicine("Clonazepam 0.25mg", "Clonazepam", "Clonazepam 0.25mg", 35.0, 7.0, "Neurological/Psychiatric", "Generic", "Tablet", "0.25mg", "Anxiety, panic disorder, epilepsy", "Sedation, dependence, cognitive impairment"),
            Medicine("Clonazepam 0.5mg", "Clonazepam", "Clonazepam 0.5mg", 45.0, 9.0, "Neurological/Psychiatric", "Generic", "Tablet", "0.5mg", "Anxiety, seizures", "Drowsiness, dependence"),
            Medicine("Rivotril 0.5mg", "Clonazepam", "Clonazepam 0.5mg", 50.0, 10.0, "Neurological/Psychiatric", "Roche", "Tablet", "0.5mg", "Epilepsy, panic disorder", "Sedation, ataxia, dependence"),
            Medicine("Alprazolam 0.25mg", "Alprazolam", "Alprazolam 0.25mg", 30.0, 6.0, "Neurological/Psychiatric", "Generic", "Tablet", "0.25mg", "Anxiety, panic disorder", "Sedation, dependence, memory impairment"),
            Medicine("Alprazolam 0.5mg", "Alprazolam", "Alprazolam 0.5mg", 40.0, 8.0, "Neurological/Psychiatric", "Generic", "Tablet", "0.5mg", "Anxiety disorders", "Drowsiness, dependence"),
            Medicine("Diazepam 5mg", "Diazepam", "Diazepam 5mg", 25.0, 5.0, "Neurological/Psychiatric", "Generic", "Tablet", "5mg", "Anxiety, muscle spasm, alcohol withdrawal", "Sedation, dependence, respiratory depression"),
            Medicine("Lorazepam 1mg", "Lorazepam", "Lorazepam 1mg", 35.0, 7.0, "Neurological/Psychiatric", "Generic", "Tablet", "1mg", "Anxiety, insomnia, status epilepticus", "Sedation, dependence"),
            Medicine("Zolpidem 5mg", "Zolpidem", "Zolpidem Tartrate 5mg", 65.0, 13.0, "Neurological/Psychiatric", "Generic", "Tablet", "5mg", "Insomnia", "Drowsiness, sleepwalking, dependence"),
            Medicine("Zolpidem 10mg", "Zolpidem", "Zolpidem Tartrate 10mg", 90.0, 18.0, "Neurological/Psychiatric", "Generic", "Tablet", "10mg", "Short-term insomnia", "Amnesia, dependence"),
            Medicine("Melatonin 3mg", "Melatonin", "Melatonin 3mg", 55.0, 11.0, "Neurological/Psychiatric", "Generic", "Tablet", "3mg", "Insomnia, jet lag, circadian rhythm disorders", "Drowsiness, headache"),
            Medicine("Pregabalin 75mg", "Pregabalin", "Pregabalin 75mg", 130.0, 26.0, "Neurological/Psychiatric", "Generic", "Capsule", "75mg", "Neuropathic pain, fibromyalgia, epilepsy", "Dizziness, weight gain, edema"),
            Medicine("Pregabalin 150mg", "Pregabalin", "Pregabalin 150mg", 180.0, 36.0, "Neurological/Psychiatric", "Generic", "Capsule", "150mg", "Neuropathic pain, GAD", "Dizziness, somnolence"),
            Medicine("Lyrica 75mg", "Pregabalin", "Pregabalin 75mg", 145.0, 29.0, "Neurological/Psychiatric", "Pfizer", "Capsule", "75mg", "Neuropathic pain, epilepsy", "Dizziness, weight gain"),
            Medicine("Gabapentin 300mg", "Gabapentin", "Gabapentin 300mg", 90.0, 18.0, "Neurological/Psychiatric", "Generic", "Capsule", "300mg", "Neuropathic pain, epilepsy", "Dizziness, somnolence, ataxia"),
            Medicine("Gabapentin 400mg", "Gabapentin", "Gabapentin 400mg", 110.0, 22.0, "Neurological/Psychiatric", "Generic", "Capsule", "400mg", "Partial seizures, postherpetic neuralgia", "Dizziness, fatigue"),
            Medicine("Carbamazepine 200mg", "Carbamazepine", "Carbamazepine 200mg", 55.0, 11.0, "Neurological/Psychiatric", "Generic", "Tablet", "200mg", "Epilepsy, trigeminal neuralgia, bipolar disorder", "Dizziness, diplopia, hyponatremia"),
            Medicine("Valproate 200mg", "Sodium Valproate", "Sodium Valproate 200mg", 50.0, 10.0, "Neurological/Psychiatric", "Generic", "Tablet", "200mg", "Epilepsy, bipolar disorder, migraine prophylaxis", "Weight gain, hair loss, hepatotoxicity"),
            Medicine("Valproate 500mg CR", "Sodium Valproate", "Sodium Valproate 500mg CR", 90.0, 18.0, "Neurological/Psychiatric", "Generic", "Tablet", "500mg CR", "Epilepsy, mania", "Teratogenicity, pancreatitis"),
            Medicine("Phenytoin 100mg", "Phenytoin", "Phenytoin Sodium 100mg", 35.0, 7.0, "Neurological/Psychiatric", "Generic", "Tablet", "100mg", "Epilepsy, status epilepticus", "Gingival hyperplasia, ataxia, hirsutism"),
            Medicine("Levetiracetam 250mg", "Levetiracetam", "Levetiracetam 250mg", 90.0, 18.0, "Neurological/Psychiatric", "Generic", "Tablet", "250mg", "Partial seizures, myoclonic epilepsy", "Somnolence, behavioral changes"),
            Medicine("Levetiracetam 500mg", "Levetiracetam", "Levetiracetam 500mg", 140.0, 28.0, "Neurological/Psychiatric", "Generic", "Tablet", "500mg", "Epilepsy", "Dizziness, irritability"),
            Medicine("Donepezil 5mg", "Donepezil", "Donepezil HCl 5mg", 180.0, 36.0, "Neurological/Psychiatric", "Generic", "Tablet", "5mg", "Alzheimer's disease", "Nausea, diarrhea, insomnia"),
            Medicine("Donepezil 10mg", "Donepezil", "Donepezil HCl 10mg", 250.0, 50.0, "Neurological/Psychiatric", "Generic", "Tablet", "10mg", "Moderate to severe Alzheimer's", "GI upset, muscle cramps"),
            Medicine("Memantine 10mg", "Memantine", "Memantine HCl 10mg", 220.0, 44.0, "Neurological/Psychiatric", "Generic", "Tablet", "10mg", "Moderate to severe Alzheimer's", "Dizziness, headache, constipation"),
            Medicine("Risperidone 1mg", "Risperidone", "Risperidone 1mg", 80.0, 16.0, "Neurological/Psychiatric", "Generic", "Tablet", "1mg", "Schizophrenia, bipolar disorder", "EPS, weight gain, hyperprolactinemia"),
            Medicine("Risperidone 2mg", "Risperidone", "Risperidone 2mg", 120.0, 24.0, "Neurological/Psychiatric", "Generic", "Tablet", "2mg", "Schizophrenia, autism irritability", "Metabolic syndrome, EPS"),
            Medicine("Olanzapine 5mg", "Olanzapine", "Olanzapine 5mg", 110.0, 22.0, "Neurological/Psychiatric", "Generic", "Tablet", "5mg", "Schizophrenia, bipolar disorder", "Weight gain, metabolic syndrome, sedation"),
            Medicine("Olanzapine 10mg", "Olanzapine", "Olanzapine 10mg", 160.0, 32.0, "Neurological/Psychiatric", "Generic", "Tablet", "10mg", "Schizophrenia, acute mania", "Diabetes risk, weight gain"),
            Medicine("Quetiapine 25mg", "Quetiapine", "Quetiapine Fumarate 25mg", 90.0, 18.0, "Neurological/Psychiatric", "Generic", "Tablet", "25mg", "Schizophrenia, bipolar disorder, depression", "Sedation, weight gain, orthostatic hypotension"),
            Medicine("Quetiapine 100mg", "Quetiapine", "Quetiapine Fumarate 100mg", 180.0, 36.0, "Neurological/Psychiatric", "Generic", "Tablet", "100mg", "Schizophrenia, bipolar mania", "Metabolic effects, sedation"),
            Medicine("Haloperidol 1.5mg", "Haloperidol", "Haloperidol 1.5mg", 40.0, 8.0, "Neurological/Psychiatric", "Generic", "Tablet", "1.5mg", "Schizophrenia, acute psychosis, Tourette's", "EPS, tardive dyskinesia, NMS"),
            Medicine("Haloperidol 5mg", "Haloperidol", "Haloperidol 5mg", 65.0, 13.0, "Neurological/Psychiatric", "Generic", "Tablet", "5mg", "Acute psychosis, agitation", "EPS, QT prolongation"),
            Medicine("Lithium 300mg", "Lithium Carbonate", "Lithium Carbonate 300mg", 55.0, 11.0, "Neurological/Psychiatric", "Generic", "Tablet", "300mg", "Bipolar disorder, mania prophylaxis", "Tremor, polyuria, thyroid effects, toxicity"),
            Medicine("Buspirone 10mg", "Buspirone", "Buspirone HCl 10mg", 75.0, 15.0, "Neurological/Psychiatric", "Generic", "Tablet", "10mg", "Generalized anxiety disorder", "Dizziness, nausea, headache"),
            Medicine("Mirtazapine 15mg", "Mirtazapine", "Mirtazapine 15mg", 110.0, 22.0, "Neurological/Psychiatric", "Generic", "Tablet", "15mg", "Depression, anxiety, insomnia", "Sedation, weight gain, increased appetite"),
            Medicine("Mirtazapine 30mg", "Mirtazapine", "Mirtazapine 30mg", 150.0, 30.0, "Neurological/Psychiatric", "Generic", "Tablet", "30mg", "Major depression", "Weight gain, sedation")
        )

        val antifungalAntiviral = listOf(
            Medicine("Fluconazole 150mg", "Fluconazole", "Fluconazole 150mg", 65.0, 13.0, "Antifungal/Antiviral", "Generic", "Capsule", "150mg", "Vaginal candidiasis, oral thrush", "Nausea, headache, hepatotoxicity"),
            Medicine("Fluconazole 200mg", "Fluconazole", "Fluconazole 200mg", 95.0, 19.0, "Antifungal/Antiviral", "Generic", "Tablet", "200mg", "Systemic fungal infections", "Hepatotoxicity, QT prolongation"),
            Medicine("Diflucan 150mg", "Fluconazole", "Fluconazole 150mg", 75.0, 15.0, "Antifungal/Antiviral", "Pfizer", "Capsule", "150mg", "Candidiasis", "Nausea, rash"),
            Medicine("Itraconazole 100mg", "Itraconazole", "Itraconazole 100mg", 120.0, 24.0, "Antifungal/Antiviral", "Generic", "Capsule", "100mg", "Fungal infections, onychomycosis", "Nausea, hepatotoxicity, heart failure"),
            Medicine("Itraconazole 200mg", "Itraconazole", "Itraconazole 200mg", 200.0, 40.0, "Antifungal/Antiviral", "Generic", "Capsule", "200mg", "Systemic fungal infections", "Hepatotoxicity, drug interactions"),
            Medicine("Terbinafine 250mg", "Terbinafine", "Terbinafine HCl 250mg", 150.0, 30.0, "Antifungal/Antiviral", "Generic", "Tablet", "250mg", "Onychomycosis, tinea infections", "GI upset, hepatotoxicity, taste disturbance"),
            Medicine("Lamisil 250mg", "Terbinafine", "Terbinafine HCl 250mg", 170.0, 34.0, "Antifungal/Antiviral", "Novartis", "Tablet", "250mg", "Fungal nail infections", "Taste disturbance, rash"),
            Medicine("Clotrimazole 1% Cream", "Clotrimazole", "Clotrimazole 1%", 65.0, 13.0, "Antifungal/Antiviral", "Generic", "Cream", "1%", "Topical fungal infections, candidiasis", "Local irritation, burning"),
            Medicine("Acyclovir 400mg", "Acyclovir", "Acyclovir 400mg", 75.0, 15.0, "Antifungal/Antiviral", "Generic", "Tablet", "400mg", "Herpes simplex, herpes zoster", "Nausea, headache, renal effects"),
            Medicine("Acyclovir 800mg", "Acyclovir", "Acyclovir 800mg", 120.0, 24.0, "Antifungal/Antiviral", "Generic", "Tablet", "800mg", "Herpes zoster, chickenpox", "Nausea, renal impairment"),
            Medicine("Zovirax 200mg", "Acyclovir", "Acyclovir 200mg", 65.0, 13.0, "Antifungal/Antiviral", "GSK", "Tablet", "200mg", "Herpes simplex prophylaxis", "Nausea, headache"),
            Medicine("Valacyclovir 500mg", "Valacyclovir", "Valacyclovir HCl 500mg", 180.0, 36.0, "Antifungal/Antiviral", "Generic", "Tablet", "500mg", "Herpes simplex, herpes zoster", "Nausea, headache, renal effects"),
            Medicine("Valacyclovir 1g", "Valacyclovir", "Valacyclovir HCl 1g", 280.0, 56.0, "Antifungal/Antiviral", "Generic", "Tablet", "1g", "Herpes zoster, cold sores", "Nausea, abdominal pain"),
            Medicine("Oseltamivir 75mg", "Oseltamivir", "Oseltamivir Phosphate 75mg", 350.0, 70.0, "Antifungal/Antiviral", "Generic", "Capsule", "75mg", "Influenza treatment and prophylaxis", "Nausea, vomiting, headache"),
            Medicine("Tamiflu 75mg", "Oseltamivir", "Oseltamivir Phosphate 75mg", 400.0, 80.0, "Antifungal/Antiviral", "Roche", "Capsule", "75mg", "Influenza A and B", "Nausea, vomiting"),
            Medicine("Tenofovir 300mg", "Tenofovir", "Tenofovir Disoproxil Fumarate 300mg", 250.0, 50.0, "Antifungal/Antiviral", "Generic", "Tablet", "300mg", "HIV, chronic hepatitis B", "Renal toxicity, bone density loss"),
            Medicine("Lamivudine 150mg", "Lamivudine", "Lamivudine 150mg", 120.0, 24.0, "Antifungal/Antiviral", "Generic", "Tablet", "150mg", "HIV, chronic hepatitis B", "Nausea, headache, lactic acidosis"),
            Medicine("Efavirenz 600mg", "Efavirenz", "Efavirenz 600mg", 300.0, 60.0, "Antifungal/Antiviral", "Generic", "Tablet", "600mg", "HIV-1 infection", "CNS effects, rash, hepatotoxicity"),
            Medicine("Lopinavir + Ritonavir 200/50mg", "Lopinavir + Ritonavir", "Lopinavir 200mg + Ritonavir 50mg", 450.0, 90.0, "Antifungal/Antiviral", "Generic", "Tablet", "200/50mg", "HIV infection", "GI upset, lipid abnormalities, hepatotoxicity"),
            Medicine("Sofosbuvir 400mg", "Sofosbuvir", "Sofosbuvir 400mg", 1200.0, 240.0, "Antifungal/Antiviral", "Generic", "Tablet", "400mg", "Chronic hepatitis C", "Fatigue, headache, nausea"),
            Medicine("Ribavirin 200mg", "Ribavirin", "Ribavirin 200mg", 180.0, 36.0, "Antifungal/Antiviral", "Generic", "Capsule", "200mg", "Hepatitis C, RSV", "Hemolytic anemia, teratogenicity"),
            Medicine("Ganciclovir 250mg", "Ganciclovir", "Ganciclovir 250mg", 350.0, 70.0, "Antifungal/Antiviral", "Generic", "Capsule", "250mg", "CMV retinitis, CMV prophylaxis", "Myelosuppression, nephrotoxicity")
        )

        val dermatology = listOf(
            Medicine("Betnovate Cream", "Betamethasone", "Betamethasone Valerate 0.1%", 85.0, 17.0, "Dermatology", "GSK", "Cream", "0.1%", "Eczema, psoriasis, dermatitis", "Skin atrophy, striae, adrenal suppression"),
            Medicine("Betamethasone 0.05% Cream", "Betamethasone", "Betamethasone Dipropionate 0.05%", 75.0, 15.0, "Dermatology", "Generic", "Cream", "0.05%", "Inflammatory skin conditions", "Skin thinning, telangiectasia"),
            Medicine("Clobetasol 0.05% Cream", "Clobetasol", "Clobetasol Propionate 0.05%", 90.0, 18.0, "Dermatology", "Generic", "Cream", "0.05%", "Severe psoriasis, lichen planus", "Skin atrophy, HPA axis suppression"),
            Medicine("Temovate Cream", "Clobetasol", "Clobetasol Propionate 0.05%", 100.0, 20.0, "Dermatology", "GSK", "Cream", "0.05%", "Severe inflammatory skin conditions", "Skin atrophy, adrenal suppression"),
            Medicine("Hydrocortisone 1% Cream", "Hydrocortisone", "Hydrocortisone 1%", 55.0, 11.0, "Dermatology", "Generic", "Cream", "1%", "Mild eczema, insect bites, contact dermatitis", "Skin thinning with prolonged use"),
            Medicine("Mometasone 0.1% Cream", "Mometasone", "Mometasone Furoate 0.1%", 95.0, 19.0, "Dermatology", "Generic", "Cream", "0.1%", "Eczema, psoriasis, dermatitis", "Skin atrophy, perioral dermatitis"),
            Medicine("Elocon Cream", "Mometasone", "Mometasone Furoate 0.1%", 110.0, 22.0, "Dermatology", "Organon", "Cream", "0.1%", "Inflammatory skin conditions", "Skin thinning, striae"),
            Medicine("Tacrolimus 0.1% Ointment", "Tacrolimus", "Tacrolimus 0.1%", 350.0, 70.0, "Dermatology", "Generic", "Ointment", "0.1%", "Atopic dermatitis, eczema", "Burning, itching, skin infections"),
            Medicine("Tretinoin 0.025% Cream", "Tretinoin", "Tretinoin 0.025%", 120.0, 24.0, "Dermatology", "Generic", "Cream", "0.025%", "Acne, photoaging, hyperpigmentation", "Dryness, peeling, photosensitivity"),
            Medicine("Tretinoin 0.05% Cream", "Tretinoin", "Tretinoin 0.05%", 150.0, 30.0, "Dermatology", "Generic", "Cream", "0.05%", "Acne vulgaris, wrinkles", "Erythema, peeling, photosensitivity"),
            Medicine("Adapalene 0.1% Gel", "Adapalene", "Adapalene 0.1%", 130.0, 26.0, "Dermatology", "Generic", "Gel", "0.1%", "Acne vulgaris", "Dryness, erythema, peeling"),
            Medicine("Benzoyl Peroxide 2.5% Gel", "Benzoyl Peroxide", "Benzoyl Peroxide 2.5%", 85.0, 17.0, "Dermatology", "Generic", "Gel", "2.5%", "Acne vulgaris", "Dryness, bleaching of fabric"),
            Medicine("Salicylic Acid 2% Lotion", "Salicylic Acid", "Salicylic Acid 2%", 75.0, 15.0, "Dermatology", "Generic", "Lotion", "2%", "Acne, dandruff, psoriasis", "Dryness, irritation"),
            Medicine("Ketoconazole 2% Shampoo", "Ketoconazole", "Ketoconazole 2%", 120.0, 24.0, "Dermatology", "Generic", "Shampoo", "2%", "Dandruff, seborrheic dermatitis, tinea versicolor", "Scalp irritation, hair texture change"),
            Medicine("Ketoconazole 2% Cream", "Ketoconazole", "Ketoconazole 2%", 95.0, 19.0, "Dermatology", "Generic", "Cream", "2%", "Fungal skin infections", "Local irritation, burning"),
            Medicine("Miconazole 2% Cream", "Miconazole", "Miconazole Nitrate 2%", 80.0, 16.0, "Dermatology", "Generic", "Cream", "2%", "Tinea infections, candidiasis", "Local irritation"),
            Medicine("Permethrin 5% Cream", "Permethrin", "Permethrin 5%", 110.0, 22.0, "Dermatology", "Generic", "Cream", "5%", "Scabies", "Burning, stinging, pruritus"),
            Medicine("Ivermectin 1% Cream", "Ivermectin", "Ivermectin 1%", 180.0, 36.0, "Dermatology", "Generic", "Cream", "1%", "Rosacea, scabies", "Skin irritation, burning"),
            Medicine("Mupirocin 2% Ointment", "Mupirocin", "Mupirocin 2%", 95.0, 19.0, "Dermatology", "Generic", "Ointment", "2%", "Impetigo, skin infections, MRSA decolonization", "Local irritation, burning"),
            Medicine("Fusidic Acid 2% Cream", "Fusidic Acid", "Fusidic Acid 2%", 110.0, 22.0, "Dermatology", "Generic", "Cream", "2%", "Bacterial skin infections, impetigo", "Local irritation"),
            Medicine("Silver Sulfadiazine 1% Cream", "Silver Sulfadiazine", "Silver Sulfadiazine 1%", 120.0, 24.0, "Dermatology", "Generic", "Cream", "1%", "Burns, wound infections", "Leukopenia, local irritation"),
            Medicine("Calamine Lotion", "Calamine", "Calamine + Zinc Oxide", 45.0, 9.0, "Dermatology", "Generic", "Lotion", "Combination", "Pruritus, sunburn, insect bites", "Dryness")
        )

        val ophthalmology = listOf(
            Medicine("Ciprofloxacin Eye Drops 0.3%", "Ciprofloxacin", "Ciprofloxacin HCl 0.3%", 55.0, 11.0, "Ophthalmology", "Generic", "Eye Drops", "0.3%", "Bacterial conjunctivitis, corneal ulcer", "Local burning, stinging"),
            Medicine("Moxifloxacin Eye Drops 0.5%", "Moxifloxacin", "Moxifloxacin HCl 0.5%", 95.0, 19.0, "Ophthalmology", "Generic", "Eye Drops", "0.5%", "Bacterial conjunctivitis, keratitis", "Local irritation, eye pain"),
            Medicine("Tobramycin Eye Drops 0.3%", "Tobramycin", "Tobramycin 0.3%", 75.0, 15.0, "Ophthalmology", "Generic", "Eye Drops", "0.3%", "Bacterial eye infections", "Local irritation, hypersensitivity"),
            Medicine("Gentamicin Eye Drops 0.3%", "Gentamicin", "Gentamicin Sulphate 0.3%", 50.0, 10.0, "Ophthalmology", "Generic", "Eye Drops", "0.3%", "Bacterial conjunctivitis", "Local burning, stinging"),
            Medicine("Prednisolone Eye Drops 1%", "Prednisolone", "Prednisolone Acetate 1%", 85.0, 17.0, "Ophthalmology", "Generic", "Eye Drops", "1%", "Ocular inflammation, uveitis", "Increased IOP, cataract"),
            Medicine("Dexamethasone Eye Drops 0.1%", "Dexamethasone", "Dexamethasone 0.1%", 70.0, 14.0, "Ophthalmology", "Generic", "Eye Drops", "0.1%", "Ocular inflammation, allergic conjunctivitis", "Increased IOP, cataract"),
            Medicine("Timolol Eye Drops 0.5%", "Timolol", "Timolol Maleate 0.5%", 65.0, 13.0, "Ophthalmology", "Generic", "Eye Drops", "0.5%", "Glaucoma, ocular hypertension", "Bradycardia, bronchospasm"),
            Medicine("Latanoprost Eye Drops 0.005%", "Latanoprost", "Latanoprost 0.005%", 180.0, 36.0, "Ophthalmology", "Generic", "Eye Drops", "0.005%", "Open-angle glaucoma, ocular hypertension", "Iris pigmentation, eyelash growth"),
            Medicine("Brimonidine Eye Drops 0.2%", "Brimonidine", "Brimonidine Tartrate 0.2%", 120.0, 24.0, "Ophthalmology", "Generic", "Eye Drops", "0.2%", "Glaucoma, ocular hypertension", "Dry mouth, drowsiness, local irritation"),
            Medicine("Dorzolamide Eye Drops 2%", "Dorzolamide", "Dorzolamide HCl 2%", 150.0, 30.0, "Ophthalmology", "Generic", "Eye Drops", "2%", "Glaucoma, ocular hypertension", "Bitter taste, local irritation"),
            Medicine("Artificial Tears 0.5%", "Carboxymethylcellulose", "Carboxymethylcellulose Sodium 0.5%", 75.0, 15.0, "Ophthalmology", "Generic", "Eye Drops", "0.5%", "Dry eye syndrome", "Temporary blurred vision"),
            Medicine("Sodium Hyaluronate Eye Drops 0.1%", "Sodium Hyaluronate", "Sodium Hyaluronate 0.1%", 120.0, 24.0, "Ophthalmology", "Generic", "Eye Drops", "0.1%", "Dry eye, ocular surface disease", "Temporary blurred vision"),
            Medicine("Cyclopentolate Eye Drops 1%", "Cyclopentolate", "Cyclopentolate HCl 1%", 65.0, 13.0, "Ophthalmology", "Generic", "Eye Drops", "1%", "Cycloplegia, mydriasis for refraction", "Photophobia, blurred vision"),
            Medicine("Tropicamide Eye Drops 1%", "Tropicamide", "Tropicamide 1%", 55.0, 11.0, "Ophthalmology", "Generic", "Eye Drops", "1%", "Mydriasis for fundus examination", "Photophobia, blurred vision"),
            Medicine("Pilocarpine Eye Drops 2%", "Pilocarpine", "Pilocarpine HCl 2%", 60.0, 12.0, "Ophthalmology", "Generic", "Eye Drops", "2%", "Glaucoma, miosis induction", "Brow ache, myopia, retinal detachment risk")
        )

        val hormones = listOf(
            Medicine("Progesterone 200mg", "Progesterone", "Micronized Progesterone 200mg", 180.0, 36.0, "Hormones/Contraceptives", "Generic", "Capsule", "200mg", "Luteal phase support, threatened abortion", "Dizziness, drowsiness, breast tenderness"),
            Medicine("Duphaston 10mg", "Dydrogesterone", "Dydrogesterone 10mg", 220.0, 44.0, "Hormones/Contraceptives", "Abbott", "Tablet", "10mg", "Endometriosis, dysmenorrhea, threatened abortion", "Nausea, headache, breast tenderness"),
            Medicine("Dydrogesterone 10mg", "Dydrogesterone", "Dydrogesterone 10mg", 200.0, 40.0, "Hormones/Contraceptives", "Generic", "Tablet", "10mg", "Progesterone deficiency, recurrent miscarriage", "Nausea, headache"),
            Medicine("Estradiol 1mg", "Estradiol", "Estradiol Valerate 1mg", 120.0, 24.0, "Hormones/Contraceptives", "Generic", "Tablet", "1mg", "Menopausal symptoms, HRT", "Nausea, breast tenderness, thrombosis risk"),
            Medicine("Estradiol 2mg", "Estradiol", "Estradiol Valerate 2mg", 160.0, 32.0, "Hormones/Contraceptives", "Generic", "Tablet", "2mg", "Menopausal HRT, hypogonadism", "Nausea, fluid retention"),
            Medicine("Premarin 0.625mg", "Conjugated Estrogen", "Conjugated Estrogens 0.625mg", 180.0, 36.0, "Hormones/Contraceptives", "Pfizer", "Tablet", "0.625mg", "Menopausal symptoms, osteoporosis prevention", "Nausea, breast tenderness, thrombosis"),
            Medicine("Testosterone 40mg", "Testosterone", "Testosterone Undecanoate 40mg", 150.0, 30.0, "Hormones/Contraceptives", "Generic", "Capsule", "40mg", "Male hypogonadism, delayed puberty", "Acne, polycythemia, liver effects"),
            Medicine("Clomiphene 50mg", "Clomiphene", "Clomiphene Citrate 50mg", 95.0, 19.0, "Hormones/Contraceptives", "Generic", "Tablet", "50mg", "Ovulation induction, infertility", "Hot flashes, ovarian hyperstimulation, visual disturbances"),
            Medicine("Letrozole 2.5mg", "Letrozole", "Letrozole 2.5mg", 120.0, 24.0, "Hormones/Contraceptives", "Generic", "Tablet", "2.5mg", "Ovulation induction, breast cancer", "Hot flashes, arthralgia, bone loss"),
            Medicine("Levonorgestrel 0.75mg", "Levonorgestrel", "Levonorgestrel 0.75mg", 85.0, 17.0, "Hormones/Contraceptives", "Generic", "Tablet", "0.75mg", "Emergency contraception", "Nausea, irregular bleeding, headache"),
            Medicine("Norethisterone 5mg", "Norethisterone", "Norethisterone 5mg", 75.0, 15.0, "Hormones/Contraceptives", "Generic", "Tablet", "5mg", "Endometriosis, menorrhagia, contraception", "Nausea, weight gain, acne"),
            Medicine("Medroxyprogesterone 10mg", "Medroxyprogesterone", "Medroxyprogesterone Acetate 10mg", 90.0, 18.0, "Hormones/Contraceptives", "Generic", "Tablet", "10mg", "Abnormal uterine bleeding, endometriosis", "Weight gain, depression, bone loss"),
            Medicine("Mifepristone 200mg", "Mifepristone", "Mifepristone 200mg", 250.0, 50.0, "Hormones/Contraceptives", "Generic", "Tablet", "200mg", "Medical abortion, emergency contraception", "Nausea, cramping, bleeding"),
            Medicine("Misoprostol 200mcg", "Misoprostol", "Misoprostol 200mcg", 120.0, 24.0, "Hormones/Contraceptives", "Generic", "Tablet", "200mcg", "Medical abortion, cervical ripening, PPH prevention", "Cramping, diarrhea, fever"),
            Medicine("Oxytocin 5 IU Injection", "Oxytocin", "Oxytocin 5 IU/mL", 45.0, 9.0, "Hormones/Contraceptives", "Generic", "Injection", "5 IU/mL", "Labor induction, PPH prevention", "Uterine hyperstimulation, water intoxication"),
            Medicine("Methylergometrine 0.2mg", "Methylergometrine", "Methylergometrine Maleate 0.2mg", 35.0, 7.0, "Hormones/Contraceptives", "Generic", "Tablet", "0.2mg", "Postpartum hemorrhage, uterine atony", "Nausea, hypertension, vasospasm")
        )

        val urology = listOf(
            Medicine("Tamsulosin 0.4mg", "Tamsulosin", "Tamsulosin HCl 0.4mg", 120.0, 24.0, "Urology/Nephrology", "Generic", "Capsule", "0.4mg", "BPH, ureteral stones", "Retrograde ejaculation, dizziness, orthostatic hypotension"),
            Medicine("Flomax 0.4mg", "Tamsulosin", "Tamsulosin HCl 0.4mg", 140.0, 28.0, "Urology/Nephrology", "Boehringer Ingelheim", "Capsule", "0.4mg", "BPH symptoms", "Dizziness, retrograde ejaculation"),
            Medicine("Finasteride 5mg", "Finasteride", "Finasteride 5mg", 130.0, 26.0, "Urology/Nephrology", "Generic", "Tablet", "5mg", "BPH, male pattern baldness", "Sexual dysfunction, gynecomastia"),
            Medicine("Proscar 5mg", "Finasteride", "Finasteride 5mg", 150.0, 30.0, "Urology/Nephrology", "MSD", "Tablet", "5mg", "BPH", "Decreased libido, erectile dysfunction"),
            Medicine("Sildenafil 50mg", "Sildenafil", "Sildenafil Citrate 50mg", 180.0, 36.0, "Urology/Nephrology", "Generic", "Tablet", "50mg", "Erectile dysfunction, pulmonary arterial hypertension", "Headache, flushing, visual disturbances"),
            Medicine("Sildenafil 100mg", "Sildenafil", "Sildenafil Citrate 100mg", 250.0, 50.0, "Urology/Nephrology", "Generic", "Tablet", "100mg", "Erectile dysfunction", "Headache, flushing, hypotension"),
            Medicine("Tadalafil 10mg", "Tadalafil", "Tadalafil 10mg", 200.0, 40.0, "Urology/Nephrology", "Generic", "Tablet", "10mg", "Erectile dysfunction, BPH", "Headache, back pain, myalgia"),
            Medicine("Tadalafil 20mg", "Tadalafil", "Tadalafil 20mg", 280.0, 56.0, "Urology/Nephrology", "Generic", "Tablet", "20mg", "Erectile dysfunction, PAH", "Headache, flushing, back pain"),
            Medicine("Vardenafil 10mg", "Vardenafil", "Vardenafil HCl 10mg", 220.0, 44.0, "Urology/Nephrology", "Generic", "Tablet", "10mg", "Erectile dysfunction", "Headache, flushing, QT prolongation"),
            Medicine("Alfuzosin 10mg", "Alfuzosin", "Alfuzosin HCl 10mg", 130.0, 26.0, "Urology/Nephrology", "Generic", "Tablet", "10mg XR", "BPH", "Dizziness, orthostatic hypotension"),
            Medicine("Dutasteride 0.5mg", "Dutasteride", "Dutasteride 0.5mg", 160.0, 32.0, "Urology/Nephrology", "Generic", "Capsule", "0.5mg", "BPH, male pattern baldness", "Sexual dysfunction, gynecomastia"),
            Medicine("Solifenacin 5mg", "Solifenacin", "Solifenacin Succinate 5mg", 180.0, 36.0, "Urology/Nephrology", "Generic", "Tablet", "5mg", "Overactive bladder, urge incontinence", "Dry mouth, constipation, blurred vision"),
            Medicine("Tolterodine 2mg", "Tolterodine", "Tolterodine Tartrate 2mg", 160.0, 32.0, "Urology/Nephrology", "Generic", "Tablet", "2mg", "Overactive bladder", "Dry mouth, constipation, dizziness"),
            Medicine("Oxybutynin 5mg", "Oxybutynin", "Oxybutynin HCl 5mg", 90.0, 18.0, "Urology/Nephrology", "Generic", "Tablet", "5mg", "Overactive bladder, urge incontinence", "Dry mouth, constipation, blurred vision"),
            Medicine("Desmopressin 0.1mg", "Desmopressin", "Desmopressin Acetate 0.1mg", 150.0, 30.0, "Urology/Nephrology", "Generic", "Tablet", "0.1mg", "Nocturnal enuresis, diabetes insipidus", "Hyponatremia, headache"),
            Medicine("Torsemide 10mg", "Torsemide", "Torsemide 10mg", 65.0, 13.0, "Urology/Nephrology", "Generic", "Tablet", "10mg", "Edema, heart failure, hypertension", "Hypokalemia, dehydration"),
            Medicine("Mannitol 20% Injection", "Mannitol", "Mannitol 20% w/v", 120.0, 24.0, "Urology/Nephrology", "Generic", "Injection", "20%", "Cerebral edema, raised ICP, acute renal failure", "Electrolyte imbalance, fluid overload"),
            Medicine("Acetazolamide 250mg", "Acetazolamide", "Acetazolamide 250mg", 75.0, 15.0, "Urology/Nephrology", "Generic", "Tablet", "250mg", "Glaucoma, altitude sickness, epilepsy", "Metabolic acidosis, paresthesia, kidney stones"),
            Medicine("Allopurinol 100mg", "Allopurinol", "Allopurinol 100mg", 35.0, 7.0, "Urology/Nephrology", "Generic", "Tablet", "100mg", "Gout, hyperuricemia, kidney stones", "Rash, GI upset, Stevens-Johnson syndrome"),
            Medicine("Allopurinol 300mg", "Allopurinol", "Allopurinol 300mg", 65.0, 13.0, "Urology/Nephrology", "Generic", "Tablet", "300mg", "Chronic gout, tumor lysis syndrome", "Rash, hepatotoxicity"),
            Medicine("Febuxostat 40mg", "Febuxostat", "Febuxostat 40mg", 150.0, 30.0, "Urology/Nephrology", "Generic", "Tablet", "40mg", "Chronic gout, hyperuricemia", "Liver function abnormalities, nausea"),
            Medicine("Febuxostat 80mg", "Febuxostat", "Febuxostat 80mg", 200.0, 40.0, "Urology/Nephrology", "Generic", "Tablet", "80mg", "Gout, hyperuricemia", "Gout flares, cardiovascular events"),
            Medicine("Colchicine 0.5mg", "Colchicine", "Colchicine 0.5mg", 55.0, 11.0, "Urology/Nephrology", "Generic", "Tablet", "0.5mg", "Acute gout, gout prophylaxis, FMF", "Diarrhea, nausea, myelosuppression"),
            Medicine("Probenecid 500mg", "Probenecid", "Probenecid 500mg", 70.0, 14.0, "Urology/Nephrology", "Generic", "Tablet", "500mg", "Gout, hyperuricemia", "GI upset, kidney stones, rash")
        )

        val immunoOncology = listOf(
            Medicine("Methotrexate 2.5mg", "Methotrexate", "Methotrexate 2.5mg", 55.0, 11.0, "Immunosuppressant/Oncology", "Generic", "Tablet", "2.5mg", "Rheumatoid arthritis, psoriasis, cancer", "Myelosuppression, hepatotoxicity, mucositis"),
            Medicine("Methotrexate 10mg", "Methotrexate", "Methotrexate 10mg", 120.0, 24.0, "Immunosuppressant/Oncology", "Generic", "Tablet", "10mg", "Cancer, severe psoriasis, RA", "Hepatotoxicity, pulmonary toxicity"),
            Medicine("Azathioprine 50mg", "Azathioprine", "Azathioprine 50mg", 90.0, 18.0, "Immunosuppressant/Oncology", "Generic", "Tablet", "50mg", "Organ transplant, autoimmune diseases", "Myelosuppression, hepatotoxicity, infections"),
            Medicine("Cyclosporine 25mg", "Cyclosporine", "Cyclosporine 25mg", 180.0, 36.0, "Immunosuppressant/Oncology", "Generic", "Capsule", "25mg", "Organ transplant, psoriasis, RA", "Nephrotoxicity, hypertension, hirsutism"),
            Medicine("Cyclosporine 100mg", "Cyclosporine", "Cyclosporine 100mg", 450.0, 90.0, "Immunosuppressant/Oncology", "Generic", "Capsule", "100mg", "Transplant rejection prevention", "Nephrotoxicity, neurotoxicity"),
            Medicine("Tacrolimus 1mg", "Tacrolimus", "Tacrolimus 1mg", 350.0, 70.0, "Immunosuppressant/Oncology", "Generic", "Capsule", "1mg", "Organ transplant, atopic dermatitis", "Nephrotoxicity, neurotoxicity, diabetes"),
            Medicine("Mycophenolate 500mg", "Mycophenolate Mofetil", "Mycophenolate Mofetil 500mg", 280.0, 56.0, "Immunosuppressant/Oncology", "Generic", "Tablet", "500mg", "Organ transplant rejection prevention", "GI upset, myelosuppression, infections"),
            Medicine("Hydroxychloroquine 200mg", "Hydroxychloroquine", "Hydroxychloroquine Sulphate 200mg", 85.0, 17.0, "Immunosuppressant/Oncology", "Generic", "Tablet", "200mg", "Rheumatoid arthritis, SLE, malaria", "Retinopathy, GI upset, QT prolongation"),
            Medicine("Hydroxychloroquine 400mg", "Hydroxychloroquine", "Hydroxychloroquine Sulphate 400mg", 140.0, 28.0, "Immunosuppressant/Oncology", "Generic", "Tablet", "400mg", "SLE, RA", "Retinal toxicity, cardiomyopathy"),
            Medicine("Sulfasalazine 500mg", "Sulfasalazine", "Sulfasalazine 500mg", 65.0, 13.0, "Immunosuppressant/Oncology", "Generic", "Tablet", "500mg", "Rheumatoid arthritis, IBD, ankylosing spondylitis", "GI upset, rash, myelosuppression"),
            Medicine("Leflunomide 10mg", "Leflunomide", "Leflunomide 10mg", 150.0, 30.0, "Immunosuppressant/Oncology", "Generic", "Tablet", "10mg", "Rheumatoid arthritis, psoriatic arthritis", "Hepatotoxicity, diarrhea, hypertension"),
            Medicine("Leflunomide 20mg", "Leflunomide", "Leflunomide 20mg", 200.0, 40.0, "Immunosuppressant/Oncology", "Generic", "Tablet", "20mg", "RA, psoriatic arthritis", "Liver toxicity, teratogenicity"),
            Medicine("Prednisolone 5mg", "Prednisolone", "Prednisolone 5mg", 25.0, 5.0, "Immunosuppressant/Oncology", "Generic", "Tablet", "5mg", "Inflammation, autoimmune diseases, asthma", "Cushing's syndrome, osteoporosis, infections"),
            Medicine("Prednisolone 10mg", "Prednisolone", "Prednisolone 10mg", 40.0, 8.0, "Immunosuppressant/Oncology", "Generic", "Tablet", "10mg", "Severe inflammation, allergic reactions", "Hyperglycemia, weight gain, adrenal suppression"),
            Medicine("Prednisolone 20mg", "Prednisolone", "Prednisolone 20mg", 65.0, 13.0, "Immunosuppressant/Oncology", "Generic", "Tablet", "20mg", "Autoimmune diseases, organ transplant", "Osteoporosis, infections, hypertension"),
            Medicine("Dexamethasone 0.5mg", "Dexamethasone", "Dexamethasone 0.5mg", 20.0, 4.0, "Immunosuppressant/Oncology", "Generic", "Tablet", "0.5mg", "Inflammation, cerebral edema, nausea in chemotherapy", "Hyperglycemia, adrenal suppression"),
            Medicine("Dexamethasone 4mg", "Dexamethasone", "Dexamethasone 4mg", 55.0, 11.0, "Immunosuppressant/Oncology", "Generic", "Tablet", "4mg", "Severe inflammation, cerebral edema, COVID-19", "Cushing's syndrome, infections"),
            Medicine("Methylprednisolone 4mg", "Methylprednisolone", "Methylprednisolone 4mg", 65.0, 13.0, "Immunosuppressant/Oncology", "Generic", "Tablet", "4mg", "Inflammation, allergic reactions, MS", "Hyperglycemia, weight gain, osteoporosis"),
            Medicine("Methylprednisolone 16mg", "Methylprednisolone", "Methylprednisolone 16mg", 150.0, 30.0, "Immunosuppressant/Oncology", "Generic", "Tablet", "16mg", "Severe inflammation, organ transplant", "Adrenal suppression, infections"),
            Medicine("Imatinib 100mg", "Imatinib", "Imatinib Mesylate 100mg", 800.0, 160.0, "Immunosuppressant/Oncology", "Generic", "Tablet", "100mg", "CML, GIST", "Edema, nausea, myelosuppression"),
            Medicine("Imatinib 400mg", "Imatinib", "Imatinib Mesylate 400mg", 2500.0, 500.0, "Immunosuppressant/Oncology", "Generic", "Tablet", "400mg", "Chronic myeloid leukemia", "Fluid retention, hepatotoxicity"),
            Medicine("Tamoxifen 10mg", "Tamoxifen", "Tamoxifen Citrate 10mg", 80.0, 16.0, "Immunosuppressant/Oncology", "Generic", "Tablet", "10mg", "Breast cancer, gynecomastia", "Hot flashes, thromboembolism, endometrial cancer"),
            Medicine("Tamoxifen 20mg", "Tamoxifen", "Tamoxifen Citrate 20mg", 130.0, 26.0, "Immunosuppressant/Oncology", "Generic", "Tablet", "20mg", "Breast cancer treatment and prevention", "Uterine cancer risk, DVT"),
            Medicine("Anastrozole 1mg", "Anastrozole", "Anastrozole 1mg", 180.0, 36.0, "Immunosuppressant/Oncology", "Generic", "Tablet", "1mg", "Postmenopausal breast cancer", "Arthralgia, hot flashes, bone loss"),
            Medicine("Capecitabine 500mg", "Capecitabine", "Capecitabine 500mg", 650.0, 130.0, "Immunosuppressant/Oncology", "Generic", "Tablet", "500mg", "Colorectal cancer, breast cancer", "Hand-foot syndrome, diarrhea, myelosuppression"),
            Medicine("Hydroxyurea 500mg", "Hydroxyurea", "Hydroxyurea 500mg", 120.0, 24.0, "Immunosuppressant/Oncology", "Generic", "Capsule", "500mg", "CML, sickle cell disease, polycythemia vera", "Myelosuppression, skin ulcers, teratogenicity"),
            Medicine("Thalidomide 50mg", "Thalidomide", "Thalidomide 50mg", 350.0, 70.0, "Immunosuppressant/Oncology", "Generic", "Capsule", "50mg", "Multiple myeloma, leprosy", "Teratogenicity, peripheral neuropathy, DVT"),
            Medicine("Lenalidomide 10mg", "Lenalidomide", "Lenalidomide 10mg", 1500.0, 300.0, "Immunosuppressant/Oncology", "Generic", "Capsule", "10mg", "Multiple myeloma, MDS", "Myelosuppression, DVT, teratogenicity")
        )

        dao.insertAll(analgesics)
        dao.insertAll(antibiotics)
        dao.insertAll(antacids)
        dao.insertAll(antidiabetics)
        dao.insertAll(antihypertensives)
        dao.insertAll(statinsCardiac)
        dao.insertAll(respiratory)
        dao.insertAll(vitamins)
        dao.insertAll(thyroid)
        dao.insertAll(neuroPsych)
        dao.insertAll(antifungalAntiviral)
        dao.insertAll(dermatology)
        dao.insertAll(ophthalmology)
        dao.insertAll(hormones)
        dao.insertAll(urology)
        dao.insertAll(immunoOncology)
        seedMedicinesExtra(dao)
    }

    // ─────────────────────────────────────────────────────────────────────────
    // EXTRA MEDICINES (batch 2 — brings total to 500+)
    // ─────────────────────────────────────────────────────────────────────────

    private suspend fun seedMedicinesExtra(dao: MedicineDao) {
        val gi2 = listOf(
            Medicine("Pantoprazole + Domperidone", "Pantoprazole + Domperidone", "Pantoprazole 40mg + Domperidone 10mg", 95.0, 19.0, "Antacid/GI", "Generic", "Capsule", "40mg+10mg", "GERD with nausea", "Headache, diarrhea"),
            Medicine("Rabeprazole + Domperidone", "Rabeprazole + Domperidone", "Rabeprazole 20mg + Domperidone 10mg", 100.0, 20.0, "Antacid/GI", "Generic", "Capsule", "20mg+10mg", "GERD, gastroparesis", "Headache, dry mouth"),
            Medicine("Lactulose 10g/15mL", "Lactulose", "Lactulose 10g/15mL", 90.0, 18.0, "Antacid/GI", "Generic", "Syrup", "10g/15mL", "Constipation, hepatic encephalopathy", "Flatulence, diarrhea"),
            Medicine("Ispaghula Husk", "Ispaghula", "Ispaghula Husk 3.5g/sachet", 65.0, 13.0, "Antacid/GI", "Generic", "Sachet", "3.5g", "Constipation, IBS", "Bloating, flatulence"),
            Medicine("Loperamide 2mg", "Loperamide", "Loperamide HCl 2mg", 35.0, 7.0, "Antacid/GI", "Generic", "Capsule", "2mg", "Acute diarrhea, IBS-D", "Constipation, abdominal cramps"),
            Medicine("ORS Sachet", "Oral Rehydration Salts", "Sodium Chloride + Potassium Chloride + Glucose", 15.0, 3.0, "Antacid/GI", "Generic", "Sachet", "Combination", "Dehydration due to diarrhea", "Hypernatremia in overdose"),
            Medicine("Zinc Sulfate 20mg", "Zinc Sulfate", "Zinc Sulfate 20mg", 30.0, 6.0, "Antacid/GI", "Generic", "Tablet", "20mg", "Diarrhea in children, zinc deficiency", "Nausea, metallic taste"),
            Medicine("Ursodeoxycholic Acid 300mg", "Ursodeoxycholic Acid", "Ursodeoxycholic Acid 300mg", 180.0, 36.0, "Antacid/GI", "Generic", "Tablet", "300mg", "Gallstones, primary biliary cholangitis", "Diarrhea, pruritus"),
            Medicine("Rifaximin 400mg", "Rifaximin", "Rifaximin 400mg", 250.0, 50.0, "Antacid/GI", "Generic", "Tablet", "400mg", "Traveler's diarrhea, IBS-D, hepatic encephalopathy", "Nausea, flatulence"),
            Medicine("Mesalazine 400mg", "Mesalazine", "Mesalazine 400mg", 180.0, 36.0, "Antacid/GI", "Generic", "Tablet", "400mg", "Ulcerative colitis, Crohn's disease", "Nausea, headache, nephrotoxicity")
        )
        val analgesics2 = listOf(
            Medicine("Tapentadol 50mg", "Tapentadol", "Tapentadol HCl 50mg", 120.0, 24.0, "Analgesic/Antipyretic", "Generic", "Tablet", "50mg", "Moderate to severe pain", "Nausea, dizziness, dependence"),
            Medicine("Tapentadol 100mg", "Tapentadol", "Tapentadol HCl 100mg", 180.0, 36.0, "Analgesic/Antipyretic", "Generic", "Tablet", "100mg", "Severe pain, diabetic neuropathy", "Constipation, somnolence"),
            Medicine("Morphine 10mg", "Morphine", "Morphine Sulphate 10mg", 90.0, 18.0, "Analgesic/Antipyretic", "Generic", "Tablet", "10mg", "Severe pain, palliative care", "Constipation, respiratory depression, dependence"),
            Medicine("Codeine 30mg", "Codeine", "Codeine Phosphate 30mg", 55.0, 11.0, "Analgesic/Antipyretic", "Generic", "Tablet", "30mg", "Mild to moderate pain, cough", "Constipation, drowsiness, dependence"),
            Medicine("Pentazocine 25mg", "Pentazocine", "Pentazocine 25mg", 65.0, 13.0, "Analgesic/Antipyretic", "Generic", "Tablet", "25mg", "Moderate to severe pain", "Dizziness, nausea, hallucinations"),
            Medicine("Celecoxib 100mg", "Celecoxib", "Celecoxib 100mg", 130.0, 26.0, "Analgesic/Antipyretic", "Generic", "Capsule", "100mg", "Arthritis, acute pain, dysmenorrhea", "Cardiovascular risk, GI effects"),
            Medicine("Celecoxib 200mg", "Celecoxib", "Celecoxib 200mg", 190.0, 38.0, "Analgesic/Antipyretic", "Generic", "Capsule", "200mg", "Osteoarthritis, rheumatoid arthritis", "Hypertension, edema"),
            Medicine("Paracetamol IV 1g/100mL", "Paracetamol", "Paracetamol 1g/100mL", 180.0, 36.0, "Analgesic/Antipyretic", "Generic", "Infusion", "1g/100mL", "Fever and pain when oral route not possible", "Hepatotoxicity in overdose"),
            Medicine("Diclofenac Injection 75mg", "Diclofenac", "Diclofenac Sodium 75mg/3mL", 55.0, 11.0, "Analgesic/Antipyretic", "Generic", "Injection", "75mg/3mL", "Acute pain, renal colic", "Injection site pain, GI effects"),
            Medicine("Ketorolac Injection 30mg", "Ketorolac", "Ketorolac Tromethamine 30mg/mL", 85.0, 17.0, "Analgesic/Antipyretic", "Generic", "Injection", "30mg/mL", "Short-term acute pain", "GI bleeding, renal effects")
        )
        val antibiotics2 = listOf(
            Medicine("Azithromycin 100mg/5mL Syrup", "Azithromycin", "Azithromycin 100mg/5mL", 95.0, 19.0, "Antibiotic", "Generic", "Syrup", "100mg/5mL", "Pediatric respiratory infections", "Nausea, diarrhea"),
            Medicine("Amoxicillin 125mg/5mL Syrup", "Amoxicillin", "Amoxicillin 125mg/5mL", 65.0, 13.0, "Antibiotic", "Generic", "Syrup", "125mg/5mL", "Pediatric bacterial infections", "Rash, diarrhea"),
            Medicine("Co-trimoxazole 480mg", "Cotrimoxazole", "Trimethoprim 80mg + Sulfamethoxazole 400mg", 35.0, 7.0, "Antibiotic", "Generic", "Tablet", "480mg", "UTI, PCP prophylaxis, respiratory infections", "Rash, Stevens-Johnson syndrome"),
            Medicine("Co-trimoxazole 960mg", "Cotrimoxazole", "Trimethoprim 160mg + Sulfamethoxazole 800mg", 55.0, 11.0, "Antibiotic", "Generic", "Tablet", "960mg", "Severe UTI, PCP treatment", "Myelosuppression, rash"),
            Medicine("Nitrofurantoin 100mg", "Nitrofurantoin", "Nitrofurantoin 100mg", 90.0, 18.0, "Antibiotic", "Generic", "Capsule", "100mg", "Uncomplicated UTI", "Nausea, pulmonary toxicity"),
            Medicine("Fosfomycin 3g", "Fosfomycin", "Fosfomycin Trometamol 3g", 280.0, 56.0, "Antibiotic", "Generic", "Sachet", "3g", "Uncomplicated UTI", "Diarrhea, nausea"),
            Medicine("Doxycycline 200mg", "Doxycycline", "Doxycycline Hyclate 200mg", 90.0, 18.0, "Antibiotic", "Generic", "Tablet", "200mg", "Severe infections, malaria treatment", "Photosensitivity, GI upset"),
            Medicine("Cefadroxil 500mg", "Cefadroxil", "Cefadroxil 500mg", 95.0, 19.0, "Antibiotic", "Generic", "Capsule", "500mg", "Skin, soft tissue, urinary infections", "Diarrhea, rash"),
            Medicine("Cephalexin 500mg", "Cephalexin", "Cephalexin 500mg", 80.0, 16.0, "Antibiotic", "Generic", "Capsule", "500mg", "Skin, respiratory, urinary infections", "GI upset, rash"),
            Medicine("Amikacin 500mg Injection", "Amikacin", "Amikacin Sulphate 500mg/2mL", 120.0, 24.0, "Antibiotic", "Generic", "Injection", "500mg/2mL", "Gram-negative infections, TB", "Nephrotoxicity, ototoxicity")
        )
        val antihypertensives2 = listOf(
            Medicine("Amlodipine + Atenolol 5/50mg", "Amlodipine + Atenolol", "Amlodipine 5mg + Atenolol 50mg", 90.0, 18.0, "Antihypertensive", "Generic", "Tablet", "5mg+50mg", "Hypertension, angina", "Bradycardia, edema"),
            Medicine("Telmisartan + Amlodipine 40/5mg", "Telmisartan + Amlodipine", "Telmisartan 40mg + Amlodipine 5mg", 160.0, 32.0, "Antihypertensive", "Generic", "Tablet", "40mg+5mg", "Hypertension", "Dizziness, edema"),
            Medicine("Losartan + Hydrochlorothiazide 50/12.5mg", "Losartan + HCTZ", "Losartan 50mg + Hydrochlorothiazide 12.5mg", 120.0, 24.0, "Antihypertensive", "Generic", "Tablet", "50mg+12.5mg", "Hypertension", "Dizziness, hypokalemia"),
            Medicine("Ramipril + Amlodipine 5/5mg", "Ramipril + Amlodipine", "Ramipril 5mg + Amlodipine 5mg", 140.0, 28.0, "Antihypertensive", "Generic", "Tablet", "5mg+5mg", "Hypertension", "Dry cough, edema"),
            Medicine("Metoprolol + Amlodipine 50/5mg", "Metoprolol + Amlodipine", "Metoprolol 50mg + Amlodipine 5mg", 130.0, 26.0, "Antihypertensive", "Generic", "Tablet", "50mg+5mg", "Hypertension, angina", "Bradycardia, edema"),
            Medicine("Indapamide 1.5mg", "Indapamide", "Indapamide 1.5mg SR", 75.0, 15.0, "Antihypertensive", "Generic", "Tablet", "1.5mg SR", "Hypertension, edema", "Hypokalemia, hyponatremia"),
            Medicine("Chlorthalidone 12.5mg", "Chlorthalidone", "Chlorthalidone 12.5mg", 45.0, 9.0, "Antihypertensive", "Generic", "Tablet", "12.5mg", "Hypertension, edema", "Hypokalemia, hyperuricemia"),
            Medicine("Nifedipine 10mg", "Nifedipine", "Nifedipine 10mg", 35.0, 7.0, "Antihypertensive", "Generic", "Capsule", "10mg", "Hypertension, angina, Raynaud's", "Flushing, headache, edema"),
            Medicine("Nifedipine 30mg SR", "Nifedipine", "Nifedipine 30mg SR", 75.0, 15.0, "Antihypertensive", "Generic", "Tablet", "30mg SR", "Hypertension, chronic angina", "Headache, flushing, edema"),
            Medicine("Diltiazem 60mg", "Diltiazem", "Diltiazem HCl 60mg", 65.0, 13.0, "Antihypertensive", "Generic", "Tablet", "60mg", "Hypertension, angina, atrial fibrillation", "Bradycardia, edema, constipation")
        )
        val neuroPsych2 = listOf(
            Medicine("Topiramate 25mg", "Topiramate", "Topiramate 25mg", 90.0, 18.0, "Neurological/Psychiatric", "Generic", "Tablet", "25mg", "Epilepsy, migraine prophylaxis", "Cognitive impairment, kidney stones, weight loss"),
            Medicine("Topiramate 50mg", "Topiramate", "Topiramate 50mg", 130.0, 26.0, "Neurological/Psychiatric", "Generic", "Tablet", "50mg", "Epilepsy, bipolar disorder", "Paresthesia, cognitive effects"),
            Medicine("Lamotrigine 25mg", "Lamotrigine", "Lamotrigine 25mg", 110.0, 22.0, "Neurological/Psychiatric", "Generic", "Tablet", "25mg", "Epilepsy, bipolar disorder", "Rash, Stevens-Johnson syndrome, dizziness"),
            Medicine("Lamotrigine 50mg", "Lamotrigine", "Lamotrigine 50mg", 160.0, 32.0, "Neurological/Psychiatric", "Generic", "Tablet", "50mg", "Epilepsy, bipolar depression", "Rash, headache, diplopia"),
            Medicine("Oxcarbazepine 300mg", "Oxcarbazepine", "Oxcarbazepine 300mg", 120.0, 24.0, "Neurological/Psychiatric", "Generic", "Tablet", "300mg", "Epilepsy, trigeminal neuralgia", "Hyponatremia, dizziness, rash"),
            Medicine("Clozapine 25mg", "Clozapine", "Clozapine 25mg", 90.0, 18.0, "Neurological/Psychiatric", "Generic", "Tablet", "25mg", "Treatment-resistant schizophrenia", "Agranulocytosis, seizures, metabolic syndrome"),
            Medicine("Aripiprazole 10mg", "Aripiprazole", "Aripiprazole 10mg", 180.0, 36.0, "Neurological/Psychiatric", "Generic", "Tablet", "10mg", "Schizophrenia, bipolar disorder, depression augmentation", "Akathisia, nausea, insomnia"),
            Medicine("Paliperidone 3mg", "Paliperidone", "Paliperidone 3mg", 220.0, 44.0, "Neurological/Psychiatric", "Generic", "Tablet", "3mg ER", "Schizophrenia, schizoaffective disorder", "EPS, weight gain, hyperprolactinemia"),
            Medicine("Trihexyphenidyl 2mg", "Trihexyphenidyl", "Trihexyphenidyl HCl 2mg", 30.0, 6.0, "Neurological/Psychiatric", "Generic", "Tablet", "2mg", "Parkinson's disease, drug-induced EPS", "Dry mouth, blurred vision, urinary retention"),
            Medicine("Levodopa + Carbidopa 100/25mg", "Levodopa + Carbidopa", "Levodopa 100mg + Carbidopa 25mg", 85.0, 17.0, "Neurological/Psychiatric", "Generic", "Tablet", "100mg+25mg", "Parkinson's disease", "Dyskinesia, nausea, orthostatic hypotension"),
            Medicine("Pramipexole 0.5mg", "Pramipexole", "Pramipexole Dihydrochloride 0.5mg", 150.0, 30.0, "Neurological/Psychiatric", "Generic", "Tablet", "0.5mg", "Parkinson's disease, restless legs syndrome", "Somnolence, impulse control disorders"),
            Medicine("Sumatriptan 50mg", "Sumatriptan", "Sumatriptan Succinate 50mg", 120.0, 24.0, "Neurological/Psychiatric", "Generic", "Tablet", "50mg", "Acute migraine, cluster headache", "Chest tightness, flushing, dizziness"),
            Medicine("Propranolol 40mg", "Propranolol", "Propranolol HCl 40mg", 35.0, 7.0, "Neurological/Psychiatric", "Generic", "Tablet", "40mg", "Migraine prophylaxis, anxiety, tremor, hypertension", "Bradycardia, fatigue, bronchospasm"),
            Medicine("Flunarizine 5mg", "Flunarizine", "Flunarizine HCl 5mg", 65.0, 13.0, "Neurological/Psychiatric", "Generic", "Capsule", "5mg", "Migraine prophylaxis, vertigo", "Weight gain, depression, extrapyramidal effects"),
            Medicine("Betahistine 8mg", "Betahistine", "Betahistine Dihydrochloride 8mg", 75.0, 15.0, "Neurological/Psychiatric", "Generic", "Tablet", "8mg", "Meniere's disease, vertigo", "Nausea, headache, GI upset"),
            Medicine("Cinnarizine 25mg", "Cinnarizine", "Cinnarizine 25mg", 45.0, 9.0, "Neurological/Psychiatric", "Generic", "Tablet", "25mg", "Vertigo, motion sickness, migraine", "Drowsiness, weight gain, extrapyramidal effects"),
            Medicine("Dimenhydrinate 50mg", "Dimenhydrinate", "Dimenhydrinate 50mg", 35.0, 7.0, "Neurological/Psychiatric", "Generic", "Tablet", "50mg", "Motion sickness, nausea, vertigo", "Drowsiness, dry mouth, blurred vision"),
            Medicine("Promethazine 25mg", "Promethazine", "Promethazine HCl 25mg", 30.0, 6.0, "Neurological/Psychiatric", "Generic", "Tablet", "25mg", "Nausea, vomiting, allergies, sedation", "Sedation, anticholinergic effects, EPS"),
            Medicine("Hydroxyzine 25mg", "Hydroxyzine", "Hydroxyzine HCl 25mg", 55.0, 11.0, "Neurological/Psychiatric", "Generic", "Tablet", "25mg", "Anxiety, pruritus, insomnia", "Drowsiness, dry mouth, QT prolongation"),
            Medicine("Trazodone 50mg", "Trazodone", "Trazodone HCl 50mg", 90.0, 18.0, "Neurological/Psychiatric", "Generic", "Tablet", "50mg", "Depression, insomnia", "Sedation, orthostatic hypotension, priapism")
        )
        val antidiabetics2 = listOf(
            Medicine("Glimepiride + Metformin 1/500mg", "Glimepiride + Metformin", "Glimepiride 1mg + Metformin 500mg", 75.0, 15.0, "Antidiabetic", "Generic", "Tablet", "1mg+500mg", "Type 2 diabetes", "Hypoglycemia, GI upset"),
            Medicine("Glimepiride + Metformin 2/500mg", "Glimepiride + Metformin", "Glimepiride 2mg + Metformin 500mg", 95.0, 19.0, "Antidiabetic", "Generic", "Tablet", "2mg+500mg", "Type 2 diabetes", "Hypoglycemia, nausea"),
            Medicine("Sitagliptin + Metformin 50/500mg", "Sitagliptin + Metformin", "Sitagliptin 50mg + Metformin 500mg", 280.0, 56.0, "Antidiabetic", "Generic", "Tablet", "50mg+500mg", "Type 2 diabetes", "Nasopharyngitis, GI upset"),
            Medicine("Vildagliptin + Metformin 50/500mg", "Vildagliptin + Metformin", "Vildagliptin 50mg + Metformin 500mg", 260.0, 52.0, "Antidiabetic", "Generic", "Tablet", "50mg+500mg", "Type 2 diabetes", "Nasopharyngitis, dizziness"),
            Medicine("Dapagliflozin + Metformin 10/500mg", "Dapagliflozin + Metformin", "Dapagliflozin 10mg + Metformin 500mg", 380.0, 76.0, "Antidiabetic", "Generic", "Tablet", "10mg+500mg", "Type 2 diabetes", "UTI, GI upset"),
            Medicine("Insulin NPH 40IU/mL", "Insulin NPH", "Isophane Insulin 40 IU/mL", 130.0, 26.0, "Antidiabetic", "Generic", "Injection", "40 IU/mL", "Type 1 & 2 diabetes", "Hypoglycemia, lipodystrophy"),
            Medicine("Insulin Aspart 100IU/mL", "Insulin Aspart", "Insulin Aspart 100 IU/mL", 900.0, 180.0, "Antidiabetic", "Novo Nordisk", "Injection", "100 IU/mL", "Type 1 & 2 diabetes, mealtime insulin", "Hypoglycemia, injection site reactions"),
            Medicine("Insulin Lispro 100IU/mL", "Insulin Lispro", "Insulin Lispro 100 IU/mL", 880.0, 176.0, "Antidiabetic", "Eli Lilly", "Injection", "100 IU/mL", "Type 1 & 2 diabetes", "Hypoglycemia, weight gain"),
            Medicine("Canagliflozin 100mg", "Canagliflozin", "Canagliflozin 100mg", 340.0, 68.0, "Antidiabetic", "Generic", "Tablet", "100mg", "Type 2 diabetes, heart failure, CKD", "UTI, genital infections, DKA"),
            Medicine("Ertugliflozin 5mg", "Ertugliflozin", "Ertugliflozin 5mg", 320.0, 64.0, "Antidiabetic", "Generic", "Tablet", "5mg", "Type 2 diabetes", "UTI, genital mycotic infections")
        )
        val vitamins2 = listOf(
            Medicine("Iron + Folic Acid Tablet", "Ferrous Sulphate + Folic Acid", "Ferrous Sulphate 60mg + Folic Acid 0.5mg", 20.0, 4.0, "Vitamin/Supplement", "Generic", "Tablet", "60mg+0.5mg", "Iron deficiency anemia in pregnancy", "Constipation, dark stools"),
            Medicine("Calcium + Vitamin D3 + Zinc", "Calcium + Vit D3 + Zinc", "Calcium 500mg + Vit D3 250IU + Zinc 7.5mg", 130.0, 26.0, "Vitamin/Supplement", "Generic", "Tablet", "Combination", "Bone health, nutritional deficiency", "Constipation, nausea"),
            Medicine("Omega-3 Fatty Acids 1000mg", "Omega-3", "EPA 180mg + DHA 120mg", 180.0, 36.0, "Vitamin/Supplement", "Generic", "Capsule", "1000mg", "Hypertriglyceridemia, cardiovascular health", "Fishy aftertaste, GI upset"),
            Medicine("Multivitamin + Multimineral", "Multivitamin", "Vitamins A, B-complex, C, D, E + Minerals", 120.0, 24.0, "Vitamin/Supplement", "Generic", "Tablet", "Combination", "Nutritional deficiency, general health", "Nausea (rare)"),
            Medicine("Vitamin B12 Injection 1000mcg", "Cyanocobalamin", "Cyanocobalamin 1000mcg/mL", 45.0, 9.0, "Vitamin/Supplement", "Generic", "Injection", "1000mcg/mL", "Vitamin B12 deficiency, pernicious anemia", "Rare allergic reactions"),
            Medicine("Iron Sucrose 100mg Injection", "Iron Sucrose", "Iron Sucrose 100mg/5mL", 180.0, 36.0, "Vitamin/Supplement", "Generic", "Injection", "100mg/5mL", "Iron deficiency anemia when oral not tolerated", "Hypotension, nausea, anaphylaxis"),
            Medicine("Ferric Carboxymaltose 500mg", "Ferric Carboxymaltose", "Ferric Carboxymaltose 500mg/10mL", 850.0, 170.0, "Vitamin/Supplement", "Generic", "Injection", "500mg/10mL", "Iron deficiency anemia", "Nausea, hypophosphatemia"),
            Medicine("Cholecalciferol 1000 IU Drops", "Cholecalciferol", "Cholecalciferol 1000 IU/mL", 95.0, 19.0, "Vitamin/Supplement", "Generic", "Oral Drops", "1000 IU/mL", "Vitamin D deficiency in infants", "Hypercalcemia in overdose"),
            Medicine("Zinc + Vitamin C Effervescent", "Zinc + Vitamin C", "Zinc 10mg + Vitamin C 500mg", 85.0, 17.0, "Vitamin/Supplement", "Generic", "Effervescent Tablet", "Combination", "Immunity, zinc deficiency", "GI upset"),
            Medicine("Lycopene + Antioxidants", "Lycopene", "Lycopene 5000mcg + Vitamins + Minerals", 150.0, 30.0, "Vitamin/Supplement", "Generic", "Capsule", "Combination", "Antioxidant, prostate health", "Rare adverse effects")
        )
        dao.insertAll(gi2)
        dao.insertAll(analgesics2)
        dao.insertAll(antibiotics2)
        dao.insertAll(antihypertensives2)
        dao.insertAll(neuroPsych2)
        dao.insertAll(antidiabetics2)
        dao.insertAll(vitamins2)
    }

    // ─────────────────────────────────────────────────────────────────────────
    // STORES
    // ─────────────────────────────────────────────────────────────────────────

    suspend fun seedStores(dao: StoreDao) {
        if (dao.getStoreCount() > 0) return

        val stores = listOf(
            // Delhi
            Store(
                name = "Jan Aushadhi Kendra - Connaught Place",
                address = "Shop No. 12, Palika Bazaar, Connaught Place",
                city = "New Delhi", state = "Delhi", pincode = "110001",
                phone = "011-23412345", latitude = 28.6315, longitude = 77.2167,
                isOpenNow = true, openingTime = "09:00", closingTime = "21:00", rating = 4.3f
            ),
            Store(
                name = "Jan Aushadhi Kendra - Rohini",
                address = "Sector 11, Rohini, Near Metro Station",
                city = "New Delhi", state = "Delhi", pincode = "110085",
                phone = "011-27051234", latitude = 28.7041, longitude = 77.1025,
                isOpenNow = true, openingTime = "08:30", closingTime = "20:30", rating = 4.1f
            ),
            Store(
                name = "Jan Aushadhi Kendra - Lajpat Nagar",
                address = "Central Market, Lajpat Nagar II",
                city = "New Delhi", state = "Delhi", pincode = "110024",
                phone = "011-29834567", latitude = 28.5665, longitude = 77.2431,
                isOpenNow = true, openingTime = "09:00", closingTime = "21:00", rating = 4.2f
            ),
            Store(
                name = "Jan Aushadhi Kendra - Dwarka",
                address = "Sector 10, Dwarka, Near Community Centre",
                city = "New Delhi", state = "Delhi", pincode = "110075",
                phone = "011-25089876", latitude = 28.5921, longitude = 77.0460,
                isOpenNow = true, openingTime = "09:00", closingTime = "21:00", rating = 4.0f
            ),
            // Mumbai
            Store(
                name = "Jan Aushadhi Kendra - Dadar",
                address = "Shop 5, Dadar West, Near Shivaji Park",
                city = "Mumbai", state = "Maharashtra", pincode = "400028",
                phone = "022-24321234", latitude = 19.0178, longitude = 72.8478,
                isOpenNow = true, openingTime = "09:00", closingTime = "21:00", rating = 4.4f
            ),
            Store(
                name = "Jan Aushadhi Kendra - Andheri",
                address = "Andheri West, Near Lokhandwala Market",
                city = "Mumbai", state = "Maharashtra", pincode = "400053",
                phone = "022-26731234", latitude = 19.1197, longitude = 72.8464,
                isOpenNow = true, openingTime = "09:00", closingTime = "21:30", rating = 4.2f
            ),
            Store(
                name = "Jan Aushadhi Kendra - Borivali",
                address = "Borivali West, Near Station Road",
                city = "Mumbai", state = "Maharashtra", pincode = "400092",
                phone = "022-28912345", latitude = 19.2307, longitude = 72.8567,
                isOpenNow = true, openingTime = "08:30", closingTime = "21:00", rating = 4.1f
            ),
            // Bangalore
            Store(
                name = "Jan Aushadhi Kendra - Jayanagar",
                address = "4th Block, Jayanagar, Near BDA Complex",
                city = "Bangalore", state = "Karnataka", pincode = "560011",
                phone = "080-26561234", latitude = 12.9250, longitude = 77.5938,
                isOpenNow = true, openingTime = "09:00", closingTime = "21:00", rating = 4.5f
            ),
            Store(
                name = "Jan Aushadhi Kendra - Koramangala",
                address = "5th Block, Koramangala, Near Forum Mall",
                city = "Bangalore", state = "Karnataka", pincode = "560095",
                phone = "080-25531234", latitude = 12.9352, longitude = 77.6245,
                isOpenNow = true, openingTime = "09:00", closingTime = "21:30", rating = 4.3f
            ),
            Store(
                name = "Jan Aushadhi Kendra - Rajajinagar",
                address = "2nd Block, Rajajinagar, Near Chord Road",
                city = "Bangalore", state = "Karnataka", pincode = "560010",
                phone = "080-23301234", latitude = 12.9916, longitude = 77.5530,
                isOpenNow = true, openingTime = "09:00", closingTime = "21:00", rating = 4.0f
            ),
            // Chennai
            Store(
                name = "Jan Aushadhi Kendra - T. Nagar",
                address = "Usman Road, T. Nagar, Near Pondy Bazaar",
                city = "Chennai", state = "Tamil Nadu", pincode = "600017",
                phone = "044-24341234", latitude = 13.0418, longitude = 80.2341,
                isOpenNow = true, openingTime = "09:00", closingTime = "21:00", rating = 4.2f
            ),
            Store(
                name = "Jan Aushadhi Kendra - Anna Nagar",
                address = "2nd Avenue, Anna Nagar, Near Tower Park",
                city = "Chennai", state = "Tamil Nadu", pincode = "600040",
                phone = "044-26261234", latitude = 13.0850, longitude = 80.2101,
                isOpenNow = true, openingTime = "09:00", closingTime = "21:00", rating = 4.1f
            ),
            Store(
                name = "Jan Aushadhi Kendra - Velachery",
                address = "Velachery Main Road, Near Vijaya Hospital",
                city = "Chennai", state = "Tamil Nadu", pincode = "600042",
                phone = "044-22431234", latitude = 12.9815, longitude = 80.2180,
                isOpenNow = true, openingTime = "09:00", closingTime = "21:00", rating = 4.0f
            ),
            // Hyderabad
            Store(
                name = "Jan Aushadhi Kendra - Banjara Hills",
                address = "Road No. 12, Banjara Hills, Near Care Hospital",
                city = "Hyderabad", state = "Telangana", pincode = "500034",
                phone = "040-23541234", latitude = 17.4126, longitude = 78.4483,
                isOpenNow = true, openingTime = "09:00", closingTime = "21:00", rating = 4.4f
            ),
            Store(
                name = "Jan Aushadhi Kendra - Secunderabad",
                address = "MG Road, Secunderabad, Near Clock Tower",
                city = "Hyderabad", state = "Telangana", pincode = "500003",
                phone = "040-27891234", latitude = 17.4399, longitude = 78.4983,
                isOpenNow = true, openingTime = "09:00", closingTime = "21:00", rating = 4.2f
            ),
            Store(
                name = "Jan Aushadhi Kendra - Kukatpally",
                address = "KPHB Colony, Kukatpally, Near JNTU",
                city = "Hyderabad", state = "Telangana", pincode = "500072",
                phone = "040-23081234", latitude = 17.4849, longitude = 78.3961,
                isOpenNow = true, openingTime = "09:00", closingTime = "21:00", rating = 4.1f
            ),
            // Kolkata
            Store(
                name = "Jan Aushadhi Kendra - Salt Lake",
                address = "Sector V, Salt Lake City, Near Techno India",
                city = "Kolkata", state = "West Bengal", pincode = "700091",
                phone = "033-23571234", latitude = 22.5726, longitude = 88.4312,
                isOpenNow = true, openingTime = "09:00", closingTime = "21:00", rating = 4.2f
            ),
            Store(
                name = "Jan Aushadhi Kendra - Gariahat",
                address = "Gariahat Road, Near Gariahat Market",
                city = "Kolkata", state = "West Bengal", pincode = "700019",
                phone = "033-24611234", latitude = 22.5204, longitude = 88.3647,
                isOpenNow = true, openingTime = "09:00", closingTime = "21:00", rating = 4.0f
            ),
            // Pune
            Store(
                name = "Jan Aushadhi Kendra - Kothrud",
                address = "Karve Road, Kothrud, Near Chandani Chowk",
                city = "Pune", state = "Maharashtra", pincode = "411038",
                phone = "020-25381234", latitude = 18.5074, longitude = 73.8077,
                isOpenNow = true, openingTime = "09:00", closingTime = "21:00", rating = 4.3f
            ),
            Store(
                name = "Jan Aushadhi Kendra - Hadapsar",
                address = "Magarpatta Road, Hadapsar, Near Seasons Mall",
                city = "Pune", state = "Maharashtra", pincode = "411028",
                phone = "020-26891234", latitude = 18.5089, longitude = 73.9260,
                isOpenNow = true, openingTime = "09:00", closingTime = "21:00", rating = 4.1f
            ),
            // Ahmedabad
            Store(
                name = "Jan Aushadhi Kendra - Navrangpura",
                address = "CG Road, Navrangpura, Near Swastik Char Rasta",
                city = "Ahmedabad", state = "Gujarat", pincode = "380009",
                phone = "079-26561234", latitude = 23.0395, longitude = 72.5619,
                isOpenNow = true, openingTime = "09:00", closingTime = "21:00", rating = 4.2f
            ),
            Store(
                name = "Jan Aushadhi Kendra - Satellite",
                address = "Satellite Road, Near Jodhpur Cross Roads",
                city = "Ahmedabad", state = "Gujarat", pincode = "380015",
                phone = "079-26741234", latitude = 23.0225, longitude = 72.5114,
                isOpenNow = true, openingTime = "09:00", closingTime = "21:00", rating = 4.0f
            ),
            // Jaipur
            Store(
                name = "Jan Aushadhi Kendra - Vaishali Nagar",
                address = "Vaishali Nagar, Near Apex Circle",
                city = "Jaipur", state = "Rajasthan", pincode = "302021",
                phone = "0141-2351234", latitude = 26.9124, longitude = 75.7873,
                isOpenNow = true, openingTime = "09:00", closingTime = "21:00", rating = 4.3f
            ),
            Store(
                name = "Jan Aushadhi Kendra - Malviya Nagar",
                address = "Malviya Nagar, Near JLN Marg",
                city = "Jaipur", state = "Rajasthan", pincode = "302017",
                phone = "0141-2721234", latitude = 26.8631, longitude = 75.8236,
                isOpenNow = true, openingTime = "09:00", closingTime = "21:00", rating = 4.1f
            ),
            // Lucknow
            Store(
                name = "Jan Aushadhi Kendra - Hazratganj",
                address = "Hazratganj, Near GPO, Mahatma Gandhi Marg",
                city = "Lucknow", state = "Uttar Pradesh", pincode = "226001",
                phone = "0522-2231234", latitude = 26.8467, longitude = 80.9462,
                isOpenNow = true, openingTime = "09:00", closingTime = "21:00", rating = 4.2f
            ),
            Store(
                name = "Jan Aushadhi Kendra - Gomti Nagar",
                address = "Vibhuti Khand, Gomti Nagar, Near Sahara Ganj",
                city = "Lucknow", state = "Uttar Pradesh", pincode = "226010",
                phone = "0522-4001234", latitude = 26.8553, longitude = 81.0006,
                isOpenNow = true, openingTime = "09:00", closingTime = "21:00", rating = 4.0f
            ),
            // Chandigarh
            Store(
                name = "Jan Aushadhi Kendra - Sector 17",
                address = "Sector 17-C, Near ISBT, Chandigarh",
                city = "Chandigarh", state = "Chandigarh", pincode = "160017",
                phone = "0172-2701234", latitude = 30.7414, longitude = 76.7682,
                isOpenNow = true, openingTime = "09:00", closingTime = "21:00", rating = 4.3f
            ),
            // Bhopal
            Store(
                name = "Jan Aushadhi Kendra - MP Nagar",
                address = "Zone-I, MP Nagar, Near Bittan Market",
                city = "Bhopal", state = "Madhya Pradesh", pincode = "462011",
                phone = "0755-4271234", latitude = 23.2332, longitude = 77.4272,
                isOpenNow = true, openingTime = "09:00", closingTime = "21:00", rating = 4.1f
            ),
            // Patna
            Store(
                name = "Jan Aushadhi Kendra - Boring Road",
                address = "Boring Road, Near Patna Zoo",
                city = "Patna", state = "Bihar", pincode = "800001",
                phone = "0612-2231234", latitude = 25.6093, longitude = 85.1376,
                isOpenNow = true, openingTime = "09:00", closingTime = "21:00", rating = 4.0f
            ),
            // Kochi
            Store(
                name = "Jan Aushadhi Kendra - Ernakulam",
                address = "MG Road, Ernakulam, Near Durbar Hall Ground",
                city = "Kochi", state = "Kerala", pincode = "682016",
                phone = "0484-2381234", latitude = 9.9312, longitude = 76.2673,
                isOpenNow = true, openingTime = "09:00", closingTime = "21:00", rating = 4.4f
            ),
            // Nagpur
            Store(
                name = "Jan Aushadhi Kendra - Dharampeth",
                address = "Dharampeth, Near Lokmat Square",
                city = "Nagpur", state = "Maharashtra", pincode = "440010",
                phone = "0712-2531234", latitude = 21.1458, longitude = 79.0882,
                isOpenNow = true, openingTime = "09:00", closingTime = "21:00", rating = 4.1f
            ),
            // Indore
            Store(
                name = "Jan Aushadhi Kendra - Vijay Nagar",
                address = "Vijay Nagar, Near Treasure Island Mall",
                city = "Indore", state = "Madhya Pradesh", pincode = "452010",
                phone = "0731-4071234", latitude = 22.7196, longitude = 75.8577,
                isOpenNow = true, openingTime = "09:00", closingTime = "21:00", rating = 4.2f
            ),
            // Surat
            Store(
                name = "Jan Aushadhi Kendra - Adajan",
                address = "Adajan Patia, Near Surat Airport Road",
                city = "Surat", state = "Gujarat", pincode = "395009",
                phone = "0261-2771234", latitude = 21.2120, longitude = 72.8397,
                isOpenNow = true, openingTime = "09:00", closingTime = "21:00", rating = 4.0f
            )
        )

        dao.insertAll(stores)
    }
}
