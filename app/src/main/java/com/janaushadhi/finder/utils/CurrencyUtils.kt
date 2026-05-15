package com.janaushadhi.finder.utils

import java.text.NumberFormat
import java.util.Locale

object CurrencyUtils {

    private val indianFormat = NumberFormat.getCurrencyInstance(Locale("en", "IN"))

    /**
     * Formats a price as Indian Rupees: ₹100.00
     */
    fun formatPrice(amount: Double): String {
        return "₹${String.format("%.0f", amount)}"
    }

    /**
     * Formats savings: "You Save ₹80 (80%)"
     */
    fun formatSavings(branded: Double, generic: Double): String {
        val savings = branded - generic
        val percent = if (branded > 0) ((savings / branded) * 100).toInt() else 0
        return "You Save ${formatPrice(savings)} ($percent%)"
    }

    /**
     * Returns savings percentage as integer.
     */
    fun savingsPercent(branded: Double, generic: Double): Int {
        if (branded <= 0) return 0
        return (((branded - generic) / branded) * 100).toInt()
    }
}
