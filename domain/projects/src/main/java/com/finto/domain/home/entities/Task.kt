package com.finto.domain.home.entities

import java.util.UUID

data class Task(
    val id: String = UUID.randomUUID().toString(),
    val name: String = "",
    val completed: Boolean = false
)
