
// https://leetcode.com/problems/wildcard-matching/solutions/3402050/detailed-explaination-with-pictures-in-c-java-python-dp-top-down-tabulation/
fun isMatch(input: String, pattern: String): Boolean {
    var sIndex = 0
    var pIndex = 0
    var matchIndex = 0
    var starIndex = -1
    while (sIndex < input.length) {
        if (pIndex < pattern.length && (input[sIndex] == pattern[pIndex] || pattern[pIndex] == '?')) {
            sIndex++
            pIndex++
        } else if (pIndex < pattern.length && pattern[pIndex] == '*') {
            starIndex = pIndex
            matchIndex = sIndex
            pIndex++
        } else if (starIndex != -1) {
            pIndex = starIndex + 1
            matchIndex++
            sIndex = matchIndex
        } else {
            return false
        }
    }
    while (pIndex < pattern.length && pattern[pIndex] == '*') {
        pIndex++
    }
    return pIndex == pattern.length
}