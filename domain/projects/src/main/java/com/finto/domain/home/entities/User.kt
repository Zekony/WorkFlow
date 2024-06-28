package com.finto.domain.home.entities


data class User(
    val id: String = "",
    val name: String = "",
    val userProjectsIds: List<String> = emptyList()
)
