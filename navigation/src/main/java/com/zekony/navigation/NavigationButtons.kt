package com.zekony.navigation

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.zekony.feature.home.HOME_ROUTE


enum class NavigationButtons(
    @StringRes val screenName: Int,
    @DrawableRes val activeIcon: Int,
    @DrawableRes val notActiveIcon: Int,
    val route: String
) {
    Home(
        com.zekony.resources.R.string.home_screen,
        com.zekony.resources.R.drawable.home_active_icon,
        com.zekony.resources.R.drawable.home_disabled_icon,
        HOME_ROUTE
    ),
    Chat(
        com.zekony.resources.R.string.chat_screen,
        com.zekony.resources.R.drawable.chat_active_icon,
        com.zekony.resources.R.drawable.chat_disabled_icon,
        "" // TODO() add ROUTE
    ),
    CreateNewTask(
        com.zekony.resources.R.string.chat_screen,
        com.zekony.resources.R.drawable.new_message_icon,
        com.zekony.resources.R.drawable.chat_disabled_icon,
        "" // TODO() add ROUTE
    ),
    Calendar(
        com.zekony.resources.R.string.calendar_screen,
        com.zekony.resources.R.drawable.calendar_active_icon,
        com.zekony.resources.R.drawable.calendar_disabled_icon,
        "" // TODO() add ROUTE
    ),
    Notification(
        com.zekony.resources.R.string.notification_screen,
        com.zekony.resources.R.drawable.notification_active_icon,
        com.zekony.resources.R.drawable.notification_disabled_icon,
        "" // TODO() add ROUTE
    )
}