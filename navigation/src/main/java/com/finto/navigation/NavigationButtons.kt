package com.finto.navigation

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.finto.feature.createproject.CREATE_PROJECT_ROUTE
import com.finto.feature.home.HOME_ROUTE


enum class NavigationButtons(
    @StringRes val screenName: Int,
    @DrawableRes val activeIcon: Int,
    @DrawableRes val notActiveIcon: Int,
    val route: String
) {
    Home(
        com.finto.resources.R.string.home_screen,
        com.finto.resources.R.drawable.home_active_icon,
        com.finto.resources.R.drawable.home_disabled_icon,
        HOME_ROUTE
    ),
    Chat(
        com.finto.resources.R.string.chat_screen,
        com.finto.resources.R.drawable.chat_active_icon,
        com.finto.resources.R.drawable.chat_disabled_icon,
        "" // TODO() add ROUTE
    ),
    CreateNewTask(
        com.finto.resources.R.string.chat_screen,
        com.finto.resources.R.drawable.new_message_icon,
        com.finto.resources.R.drawable.chat_disabled_icon,
        CREATE_PROJECT_ROUTE
    ),
    Calendar(
        com.finto.resources.R.string.calendar_screen,
        com.finto.resources.R.drawable.calendar_active_icon,
        com.finto.resources.R.drawable.calendar_disabled_icon,
        "" // TODO() add ROUTE
    ),
    Notification(
        com.finto.resources.R.string.notification_screen,
        com.finto.resources.R.drawable.notification_active_icon,
        com.finto.resources.R.drawable.notification_disabled_icon,
        "" // TODO() add ROUTE
    )
}