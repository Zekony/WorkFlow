package com.zekony.feature.registration.util

fun String.containsNumbers(): Boolean {
    val regex = Regex(".*\\d+.*")
    return regex.matches(this)
}
fun String.containsUpperCase(): Boolean {
    val regex = Regex(".*[A-Z]+.*")
    return regex.matches(this)
}
fun String.containsAscii(): Boolean {
    val regex = Regex(".*[A-Za-z\\d]+.*")
    return regex.matches(this)
}

fun String.isEmailValid(): Boolean {
    val emailRegex = "^[A-Za-z](.*)([@]{1})(.{1,})(\\.)(.{1,})"
    return emailRegex.toRegex().matches(this)
}