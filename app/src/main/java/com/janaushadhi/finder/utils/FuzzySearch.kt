package com.janaushadhi.finder.utils

/**
 * Fuzzy search utility using Levenshtein distance algorithm.
 * Handles small spelling mistakes in medicine names.
 */
object FuzzySearch {

    /**
     * Calculates the Levenshtein edit distance between two strings.
     * Lower = more similar.
     */
    fun levenshteinDistance(s1: String, s2: String): Int {
        val m = s1.length
        val n = s2.length
        val dp = Array(m + 1) { IntArray(n + 1) }

        for (i in 0..m) dp[i][0] = i
        for (j in 0..n) dp[0][j] = j

        for (i in 1..m) {
            for (j in 1..n) {
                dp[i][j] = if (s1[i - 1].lowercaseChar() == s2[j - 1].lowercaseChar()) {
                    dp[i - 1][j - 1]
                } else {
                    1 + minOf(dp[i - 1][j], dp[i][j - 1], dp[i - 1][j - 1])
                }
            }
        }
        return dp[m][n]
    }

    /**
     * Returns a similarity score between 0.0 and 1.0.
     * 1.0 = identical, 0.0 = completely different.
     */
    fun similarity(s1: String, s2: String): Double {
        if (s1.isEmpty() && s2.isEmpty()) return 1.0
        if (s1.isEmpty() || s2.isEmpty()) return 0.0
        val maxLen = maxOf(s1.length, s2.length)
        val dist = levenshteinDistance(s1, s2)
        return 1.0 - dist.toDouble() / maxLen
    }

    /**
     * Returns true if the query fuzzy-matches the target.
     * Threshold: similarity >= 0.6 OR target contains query as substring.
     */
    fun matches(query: String, target: String, threshold: Double = 0.6): Boolean {
        val q = query.trim().lowercase()
        val t = target.trim().lowercase()
        if (q.isEmpty()) return true
        if (t.contains(q)) return true
        // Check word-by-word for multi-word targets
        val words = t.split(" ", "-", "/")
        for (word in words) {
            if (word.startsWith(q)) return true
            if (similarity(q, word) >= threshold) return true
        }
        // Full string similarity
        return similarity(q, t) >= threshold
    }

    /**
     * Scores a medicine name against a query for ranking.
     * Higher score = better match.
     */
    fun score(query: String, target: String): Double {
        val q = query.trim().lowercase()
        val t = target.trim().lowercase()
        return when {
            t == q -> 1.0
            t.startsWith(q) -> 0.95
            t.contains(q) -> 0.85
            else -> similarity(q, t)
        }
    }
}
