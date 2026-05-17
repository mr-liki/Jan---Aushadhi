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
                return "AI Assistant requires a Gemini API key to be configured. Please add your API key to local.properties file."
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
                    "Please configure your Gemini API key in the app settings."
                e.message?.contains("not found") == true -> 
                    "AI service is temporarily unavailable. Please try again later."
                e.message?.contains("quota") == true -> 
                    "AI service quota exceeded. Please try again later."
                else -> 
                    "Sorry, I'm having trouble connecting to the AI service. Please check your internet connection and try again."
            }
        }
    }

    /**
     * Gets generic medicine suggestions for a branded medicine name.
     */
    suspend fun getBrandToGenericSuggestion(brandName: String): String {
        return try {
            if (BuildConfig.GEMINI_API_KEY == "YOUR_GEMINI_API_KEY" || BuildConfig.GEMINI_API_KEY.isBlank()) {
                return "AI Assistant requires a Gemini API key to be configured."
            }

            val prompt = """
                Given the branded medicine name "$brandName", provide:
                1. The generic/salt name
                2. Common uses
                3. Typical dosage forms
                
                Format the response clearly and concisely.
            """.trimIndent()

            val response = model.generateContent(prompt)
            response.text ?: "No information found for $brandName"
        } catch (e: Exception) {
            "Unable to get information for $brandName. Please try again later."
        }
    }

    /**
     * Explains the difference between branded and generic medicines.
     */
    suspend fun explainGenericVsBranded(): String {
        return try {
            if (BuildConfig.GEMINI_API_KEY == "YOUR_GEMINI_API_KEY" || BuildConfig.GEMINI_API_KEY.isBlank()) {
                return """
                    Generic medicines contain the same active ingredients as branded medicines but cost significantly less.
                    
                    Key differences:
                    • Same effectiveness and safety
                    • Lower cost (up to 90% savings)
                    • Different packaging and name
                    • Approved by drug authorities
                    
                    Jan-Aushadhi stores offer quality generic medicines at affordable prices.
                """.trimIndent()
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
            response.text ?: "Generic medicines contain the same active ingredients as branded medicines but cost less."
        } catch (e: Exception) {
            """
                Generic medicines are bioequivalent to branded medicines but sold at lower prices.
                
                They contain the same active ingredients and are equally effective, but don't include expensive branding and marketing costs.
                
                Jan-Aushadhi stores provide quality generic medicines approved by Indian drug authorities.
            """.trimIndent()
        }
    }
}
