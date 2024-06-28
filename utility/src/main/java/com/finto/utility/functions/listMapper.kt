package com.finto.utility.functions


fun <T> List<T>.addToList(item: T?, condition: Boolean = true): List<T> =
    this.toMutableList().apply {
        if (condition) add(item ?: return@apply)
    }

fun <T> List<T>.removeFromList(item: T, condition: Boolean = true): List<T> =
    this.toMutableList().apply {
        if (condition) remove(item)
    }
fun <T> List<T>.updateListItem(item: T, condition: Boolean = true): List<T> =
    this.toMutableList().apply {
        if (condition) remove(item)
    }