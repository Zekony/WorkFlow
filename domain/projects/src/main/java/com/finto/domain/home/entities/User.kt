package com.finto.domain.home.entities

import java.util.UUID

data class User(
    val id: String = UUID.randomUUID().toString(),
    val name: String = ""
)
