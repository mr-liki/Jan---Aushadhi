package com.janaushadhi.finder.ai

import com.google.ai.client.generativeai.GenerativeModel
import com.google.ai.client.generativeai.type.content
import com.janaushadhi.finder.BuildConfig

/**
 * Service for interacting with Google Gemini AI.
 * Provides medicine-related Q&A and brand-to-generic suggestions.
 */
class GeminiService {

    private val model = GenerativeModel(
        modelName = "gemini-1.5-flash",
        apiKey = BuildConfig.GEMINI_API_KEY
    )

    /**
     * Sends a medicine-related query to Gemini and returns the response.
     */
    suspend fun askMedicineQuestion(question: String): String {
        return try {
            // Check if API key is configured
            if (BuildConfig.GEMINI_API_KEY == "YOUR_GEMINI_API_KEY" || BuildConfig.GEMINI_API_KEY.isBlank()) {
                return getFallbackResponse(question)
            }

            val prompt = """
                You are a helpful medical information assistant for Jan-Aushadhi Finder app.
                Answer questions about generic medicines, their uses, and Jan-Aushadhi stores.
                Keep answers concise, accurate, and patient-friendly.
                Always remind users to consult a doctor for medical advice.
                
                User Question: $question
            """.trimIndent()

            val response = model.generateContent(prompt)
            response.text ?: "I couldn't generate a response. Please try again."
        } catch (e: Exception) {
            when {
                e.message?.contains("API_KEY") == true -> 
                    getFallbackResponse(question)
                e.message?.contains("not found") == true -> 
                    getFallbackResponse(question)
                e.message?.contains("quota") == true -> 
                    "AI service quota exceeded. Please try again later."
                else -> 
                    getFallbackResponse(question)
            }
        }
    }

    /**
     * Gets generic medicine suggestions for a branded medicine name.
     */
    suspend fun getBrandToGenericSuggestion(brandName: String): String {
        return try {
            if (BuildConfig.GEMINI_API_KEY == "YOUR_GEMINI_API_KEY" || BuildConfig.GEMINI_API_KEY.isBlank()) {
                return getFallbackBrandInfo(brandName)
            }

            val prompt = """
                Given the branded medicine name "$brandName", provide:
                1. The generic/salt name
                2. Common uses
                3. Typical dosage forms
                
                Format the response clearly and concisely.
            """.trimIndent()

            val response = model.generateContent(prompt)
            response.text ?: getFallbackBrandInfo(brandName)
        } catch (e: Exception) {
            getFallbackBrandInfo(brandName)
        }
    }

    /**
     * Explains the difference between branded and generic medicines.
     */
    suspend fun explainGenericVsBranded(): String {
        return try {
            if (BuildConfig.GEMINI_API_KEY == "YOUR_GEMINI_API_KEY" || BuildConfig.GEMINI_API_KEY.isBlank()) {
                return getGenericVsBrandedExplanation()
            }

            val prompt = """
                Explain in simple terms:
                1. What are generic medicines?
                2. How are they different from branded medicines?
                3. Are generic medicines as effective as branded ones?
                4. Why are generic medicines cheaper?
                
                Keep it brief and easy to understand for common people.
            """.trimIndent()

            val response = model.generateContent(prompt)
            response.text ?: getGenericVsBrandedExplanation()
        } catch (e: Exception) {
            getGenericVsBrandedExplanation()
        }
    }

    /**
     * Provides fallback responses when AI service is unavailable
     */
    private fun getFallbackResponse(question: String): String {
        val lowerQuestion = question.lowercase()
        
        return when {
            lowerQuestion.contains("generic") || lowerQuestion.contains("branded") -> {
                getGenericVsBrandedExplanation()
            }
            lowerQuestion.contains("paracetamol") || lowerQuestion.contains("crocin") -> {
                """
                Paracetamol is a common pain reliever and fever reducer.
                
                Generic names: Paracetamol, Acetaminophen
                Common brands: Crocin, Dolo, Calpol
                Uses: Fever, headache, body pain
                
                Generic paracetamol costs ₹5-7 vs branded ₹30-42.
                
                ⚠️ Always consult a doctor for proper medical advice.
                """.trimIndent()
            }
            lowerQuestion.contains("ibuprofen") || lowerQuestion.contains("brufen") -> {
                """
                Ibuprofen is an anti-inflammatory pain reliever.
                
                Generic name: Ibuprofen
                Common brands: Brufen, Combiflam
                Uses: Pain, inflammation, fever
                
                Generic ibuprofen costs ₹8-10 vs branded ₹35-55.
                
                ⚠️ Always consult a doctor for proper medical advice.
                """.trimIndent()
            }
            lowerQuestion.contains("store") || lowerQuestion.contains("jan-aushadhi") -> {
                """
                Jan-Aushadhi stores are government-supported pharmacies that sell quality generic medicines at affordable prices.
                
                Benefits:
                • Up to 90% savings on medicines
                • Same quality as branded medicines
                • Available across India
                • Approved by drug authorities
                
                Use the Stores tab to find nearby Jan-Aushadhi Kendras.
                """.trimIndent()
            }
            lowerQuestion.contains("side effect") -> {
                """
                Common medicine side effects include:
                
                • Nausea or stomach upset
                • Dizziness or drowsiness  
                • Allergic reactions (rash, itching)
                • Headache
                
                ⚠️ Important: Always read medicine labels and consult your doctor about potential side effects before taking any medication.
                """.trimIndent()
            }
            else -> {
                """
                I'm here to help with medicine-related questions!
                
                You can ask me about:
                • Generic vs branded medicines
                • Common medicines like Paracetamol, Ibuprofen
                • Jan-Aushadhi stores and savings
                • Medicine side effects
                • Dosage information
                
                ⚠️ Always consult a qualified doctor for medical advice.
                """.trimIndent()
            }
        }
    }

    /**
     * Provides fallback brand information
     */
    private fun getFallbackBrandInfo(brandName: String): String {
        val lowerBrand = brandName.lowercase()
        
        return when {
            lowerBrand.contains("crocin") || lowerBrand.contains("dolo") -> {
                """
                Brand: $brandName
                Generic: Paracetamol
                Uses: Fever, pain relief
                Forms: Tablet, syrup
                
                Generic paracetamol available at Jan-Aushadhi stores for ₹5-7 vs branded ₹30-42.
                """.trimIndent()
            }
            lowerBrand.contains("brufen") -> {
                """
                Brand: $brandName  
                Generic: Ibuprofen
                Uses: Pain, inflammation, fever
                Forms: Tablet, suspension
                
                Generic ibuprofen available at Jan-Aushadhi stores for ₹8-10 vs branded ₹35-50.
                """.trimIndent()
            }
            else -> {
                """
                For detailed information about $brandName, please:
                
                1. Search in the medicines database
                2. Consult a pharmacist at Jan-Aushadhi store
                3. Check with your doctor
                
                Generic alternatives are usually available at 50-90% lower cost.
                """.trimIndent()
            }
        }
    }

    /**
     * Provides generic vs branded explanation
     */
    private fun getGenericVsBrandedExplanation(): String {
        return """
            Generic vs Branded Medicines:
            
            🔹 Generic Medicines:
            • Same active ingredients as branded
            • Same effectiveness and safety
            • 50-90% cheaper
            • Plain packaging
            
            🔹 Branded Medicines:
            • Higher cost due to marketing
            • Fancy packaging
            • Same therapeutic effect
            
            💡 Why choose generic?
            • Approved by drug authorities
            • Quality assured
            • Huge cost savings
            • Available at Jan-Aushadhi stores
            
            ⚠️ Always consult your doctor before switching medicines.
        """.trimIndent()
    }
}
