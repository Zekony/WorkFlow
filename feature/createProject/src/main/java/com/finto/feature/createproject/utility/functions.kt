package com.finto.feature.createproject.utility

fun List<Int>.increaseList(): List<Int> = this.toMutableList().apply {
    repeat(10) {
        addAll(this)
    }
}