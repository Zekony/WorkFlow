package com.zekony.feature.settings.mvi

import com.finto.domain.home.entities.User

data class SettingsState(
    val currentUser: User? = null
)
